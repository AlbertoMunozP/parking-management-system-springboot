package es.uca.ParkingElSalvador.Bonos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service 
public class BonoService {
    private BonoRepository bonos; 

    @Autowired
    public BonoService(BonoRepository bonos) {
        this.bonos = bonos;
    }
     
    public void save(Bono b) {
        bonos.meter(b);
    }

    public void delete(Bono b) {
        bonos.sacar(b);
    }

    public List<Bono> getBonos(String matricula) {
        return bonos.bonos(matricula);
    }

    public List<Bono> getBonos() {
        return bonos.getAllBonos();
    }
}
