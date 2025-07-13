package es.uca.ParkingElSalvador.Estancias;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstanciasRepositoryJPA extends JpaRepository<Estancia, String>{
    boolean existsByMatricula(String matricula);
    public List<Estancia> findByMatricula(String matricula);
}
