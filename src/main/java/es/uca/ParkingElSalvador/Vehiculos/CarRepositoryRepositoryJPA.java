package es.uca.ParkingElSalvador.Vehiculos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepositoryRepositoryJPA extends JpaRepository<Vehiculo, String>{
    public Vehiculo findByMatricula(String matricula);
}
