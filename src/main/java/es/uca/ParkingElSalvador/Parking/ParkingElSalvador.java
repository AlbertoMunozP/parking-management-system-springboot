package es.uca.ParkingElSalvador.Parking;

import es.uca.ParkingElSalvador.Bonos.BonoInMemoryRepo;
import es.uca.ParkingElSalvador.Estancias.EstanciasInMemoryRepo;
import es.uca.ParkingElSalvador.Vehiculos.CarRepositoryInMemoryRepo;

public class ParkingElSalvador {
    private final Parking parkES;
    private final ParkingService p;
    
    public ParkingElSalvador(Parking park){
        parkES = park;
        p = new ParkingService(park,new CarRepositoryInMemoryRepo(),new EstanciasInMemoryRepo(), new BonoInMemoryRepo());
    }

    public ParkingService getParkingService(){
        return p;
    }

    public String toString() {
        return parkES.toString();
    }
}
