package es.uca.ParkingElSalvador.Bonos;

import java.util.List;

public interface BonoRepository {
    public void meter(Bono b);
    public void sacar(Bono b);
    public List<Bono> bonos(String matricula);
    public List<Bono> getAllBonos();
}
