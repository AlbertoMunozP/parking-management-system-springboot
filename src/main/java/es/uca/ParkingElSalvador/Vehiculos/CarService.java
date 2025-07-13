package es.uca.ParkingElSalvador.Vehiculos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private CarRepository vehiculos;
    @Autowired
    public CarService(CarRepository v ){
        vehiculos = v;
    }

    public CarService(){
        vehiculos = null;
    }

    public void setCarRepository(CarRepository v){
        vehiculos = v;
    }

    public void save(Vehiculo v){
        vehiculos.meter(v);
    }

    public void delete(String mat){
        vehiculos.sacar(mat);
    }

    public Vehiculo getVehiculo(String matricula){
        return vehiculos.obtener(matricula);
    }

    public int getNumCoches(){
        return vehiculos.numCoches();
    }
} 


