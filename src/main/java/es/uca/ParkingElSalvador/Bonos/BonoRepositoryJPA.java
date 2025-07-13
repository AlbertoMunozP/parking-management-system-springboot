package es.uca.ParkingElSalvador.Bonos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BonoRepositoryJPA extends JpaRepository<Bono, String> {
    public List<Bono> getBonoByMatricula(String matricula);
}
