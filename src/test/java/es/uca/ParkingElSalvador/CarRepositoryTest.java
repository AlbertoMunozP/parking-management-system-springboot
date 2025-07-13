package es.uca.ParkingElSalvador;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Vehiculos.CarRepositoryInMemoryRepo;
import es.uca.ParkingElSalvador.Vehiculos.CarService;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

public class CarRepositoryTest {
    private CarService carRepository;

    @Before
    public void setUp() {
        carRepository = new CarService(new CarRepositoryInMemoryRepo());
    }

    @Test
    public void testMeter() {
        Vehiculo vehiculo = new Vehiculo("123ABC");
        carRepository.save(vehiculo);
        assertEquals("El número de coches en el repositorio debe ser 1 después de agregar un vehículo", 1, carRepository.getNumCoches());
        assertEquals("El vehículo agregado debe poder ser obtenido del repositorio", vehiculo, carRepository.getVehiculo("123ABC"));
    }

    @Test
    public void testSacar() {
        Vehiculo vehiculo = new Vehiculo("123ABC");
        carRepository.save(vehiculo);
        carRepository.delete(vehiculo.getMatricula());
        assertEquals("El número de coches en el repositorio debe ser 0 después de sacar el vehículo agregado", 0, carRepository.getNumCoches());
        assertNull("El vehículo sacado no debe estar presente en el repositorio", carRepository.getVehiculo("123ABC"));
    }

    @Test
    public void testObtener() {
        Vehiculo vehiculo = new Vehiculo("123ABC");
        carRepository.save(vehiculo);
        assertEquals("Se debería poder obtener el vehículo agregado al repositorio", vehiculo, carRepository.getVehiculo("123ABC"));
        assertNull("No debería devolver ningún vehículo si la matrícula no está presente", carRepository.getVehiculo("999ZZZ"));
    }

    @Test
    public void testNumCoches() {
        assertEquals("El número de coches en el repositorio debe ser 0 al inicio", 0, carRepository.getNumCoches());
        carRepository.save(new Vehiculo("123ABC"));
        assertEquals("El número de coches en el repositorio debe ser 1 después de agregar un vehículo", 1, carRepository.getNumCoches());
    }
}
