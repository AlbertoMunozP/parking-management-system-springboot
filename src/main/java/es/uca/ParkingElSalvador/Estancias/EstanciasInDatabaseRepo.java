package es.uca.ParkingElSalvador.Estancias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

@Repository
public class EstanciasInDatabaseRepo implements EstanciasRepository {
    private final EstanciasRepositoryJPA estanciasRepositoryJPA;

    @Autowired
    public EstanciasInDatabaseRepo(EstanciasRepositoryJPA estanciasRepositoryJPA) {
        this.estanciasRepositoryJPA = estanciasRepositoryJPA;
    }

    @Override
    public void almacenar(Vehiculo vehiculo) {
        estanciasRepositoryJPA.save(vehiculo.getEstancia());
    }

    @Override
    public boolean haestadoCoche(String matricula) {
        return estanciasRepositoryJPA.existsByMatricula(matricula);
    }

    @Override
    public List<Estancia> estancias(String matricula) {
        return estanciasRepositoryJPA.findByMatricula(matricula); // Suponiendo que esto devuelve una List<Estancia>
    }
    

    @Override
    public List<Estancia> estancias() {
        return estanciasRepositoryJPA.findAll(); // Esto devuelve una List<Estancia>
    }
    

    @Override
    public long numEstancias() {
        return estanciasRepositoryJPA.count();
    }
}
