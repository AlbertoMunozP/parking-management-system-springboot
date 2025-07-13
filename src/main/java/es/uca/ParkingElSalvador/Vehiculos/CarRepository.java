package es.uca.ParkingElSalvador.Vehiculos;

public interface CarRepository {
    public void meter(Vehiculo v);
    public void sacar(String matricula);
    public Vehiculo obtener(String matricula);
    public int numCoches();
} 
