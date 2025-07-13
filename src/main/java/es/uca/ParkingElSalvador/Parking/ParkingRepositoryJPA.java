package es.uca.ParkingElSalvador.Parking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepositoryJPA extends JpaRepository<Parking, Long> {
    Parking findFirstBy();  // Recupera la primera instancia encontrada, asumiendo que solo hay una.
}
