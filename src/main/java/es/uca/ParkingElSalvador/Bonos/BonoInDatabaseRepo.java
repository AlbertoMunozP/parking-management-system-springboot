package es.uca.ParkingElSalvador.Bonos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BonoInDatabaseRepo implements BonoRepository {
    private final BonoRepositoryJPA bonoRepositoryJPA;

    @Autowired
    public BonoInDatabaseRepo(BonoRepositoryJPA bonoRepositoryJPA) {
        this.bonoRepositoryJPA = bonoRepositoryJPA;
    }

    @Override
    public void meter(Bono b) {
        bonoRepositoryJPA.save(b);
    }

    @Override
    public void sacar(Bono b) {
        bonoRepositoryJPA.delete(b);
    }

    @Override
    public List<Bono> bonos(String matricula) {
        return bonoRepositoryJPA.getBonoByMatricula(matricula);
    }

    @Override
    public List<Bono> getAllBonos() {
        return bonoRepositoryJPA.findAll();
    }
}
