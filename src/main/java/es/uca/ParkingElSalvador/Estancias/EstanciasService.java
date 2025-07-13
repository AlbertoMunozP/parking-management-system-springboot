package es.uca.ParkingElSalvador.Estancias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

@Service
public class EstanciasService {
    private EstanciasRepository estancias;

    @Autowired
    public EstanciasService(EstanciasRepository e){
        estancias = e;
    }

    public void save(Vehiculo v){
        estancias.almacenar(v);
    }

    public boolean esta(String matricula){
        return estancias.haestadoCoche(matricula);
    }

    public List<Estancia> getEstancias(String matricula){
        return estancias.estancias(matricula);
    }

    public List<Estancia> getAllEstancias(){
        return estancias.estancias();
    }

    public long numEstancias(){
        return estancias.numEstancias();
    }
}
