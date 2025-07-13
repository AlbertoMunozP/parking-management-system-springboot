package es.uca.ParkingElSalvador.Bonos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BonoInMemoryRepo implements BonoRepository {
    private Map<String, List<Bono>> bonosPorMatricula;

    public BonoInMemoryRepo() {
        bonosPorMatricula = new HashMap<>();
    }

    @Override
    public void meter(Bono b) {
        String matricula = b.getEstancia().getVehiculo().getMatricula();
        if (!bonosPorMatricula.containsKey(matricula)) {
            bonosPorMatricula.put(matricula, new ArrayList<>());
        }
        bonosPorMatricula.get(matricula).add(b);
    }

    @Override
    public void sacar(Bono b) {
        String matricula = b.getEstancia().getVehiculo().getMatricula();
        if (bonosPorMatricula.containsKey(matricula)) {
            bonosPorMatricula.get(matricula).remove(b);
        }
    }

    @Override
    public List<Bono> bonos(String matricula) {
        return bonosPorMatricula.getOrDefault(matricula, new ArrayList<>());
    }

    @Override
    public List<Bono> getAllBonos() {
        List<Bono> todosBonos = new ArrayList<>();
        for (List<Bono> bonos : bonosPorMatricula.values()) {
            todosBonos.addAll(bonos);
        }
        return todosBonos;
    }
}
