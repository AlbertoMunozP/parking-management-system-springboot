package es.uca.ParkingElSalvador.Estancias;

import java.util.List;

import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

public interface EstanciasRepository {
    public void almacenar(Vehiculo vehiculo);
    public boolean haestadoCoche(String matricula);
    public List<Estancia> estancias(String matricula);
    public List<Estancia> estancias();
    public long numEstancias();
}
