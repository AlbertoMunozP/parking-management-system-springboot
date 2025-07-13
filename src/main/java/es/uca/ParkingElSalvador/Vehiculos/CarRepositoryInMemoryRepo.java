package es.uca.ParkingElSalvador.Vehiculos;
import java.util.HashMap;

public class CarRepositoryInMemoryRepo implements CarRepository{
    private final HashMap<String, Vehiculo> vehiculos;
    public CarRepositoryInMemoryRepo(){
        vehiculos = new HashMap<>();
    }

    // Tratamiento de vehiculos
    public void meter(Vehiculo veh){
        vehiculos.put(veh.getMatricula(), veh);
    }
    public void sacar(String mat){
        vehiculos.remove(mat);
    }

    // Metodos observadores
    public Vehiculo obtener(String matricula){
        return vehiculos.get(matricula);
    }
    public int numCoches(){
        return vehiculos.size();
    }

    public String toString() {
        return "Se encuentran actualmente "+numCoches()+" vehiculos";
    }

}
