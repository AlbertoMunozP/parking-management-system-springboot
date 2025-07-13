package es.uca.ParkingElSalvador;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Estancias.EstanciasInMemoryRepo;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

public class CarRegisterTest {
    private EstanciasInMemoryRepo carRegister;
    private Vehiculo vehiculo;

    @Before
    public void setUp() {
        carRegister = new EstanciasInMemoryRepo();
        vehiculo = new Vehiculo("123ABC");
    }

    @Test
    public void testAlmacenar() {
        carRegister.almacenar(vehiculo);
        assertEquals("El registro debería tener una estancia después de almacenar un vehículo", 1, carRegister.registro().size());
        assertEquals("La estancia almacenada debería ser la misma que la del vehículo", vehiculo.getEstancia(), carRegister.registro().get(0));
    }

    @Test
    public void testHaEstadoCocheCuandoNoHaEstado() {
        assertFalse("El vehículo no debería haber estado registrado", carRegister.haestadoCoche("999ZZZ"));
    }

    @Test
    public void testHaEstadoCocheCuandoHaEstado() {
        carRegister.almacenar(vehiculo);
        assertTrue("El vehículo debería haber estado registrado", carRegister.haestadoCoche("123ABC"));
    }

    @Test
    public void testEstanciasCuandoNoHayEstancias() {
        assertEquals("No debería haber estancias para una matrícula que no ha estado registrada", 0, carRegister.estancias("999ZZZ").size());
    }

    @Test
    public void testEstanciasCuandoHayEstancias() {
        carRegister.almacenar(vehiculo);
        assertEquals("Debe haber una estancia para la matrícula que ha estado registrada", 1, carRegister.estancias("123ABC").size());
    }
}
