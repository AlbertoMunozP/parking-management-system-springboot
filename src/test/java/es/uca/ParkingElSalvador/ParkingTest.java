package es.uca.ParkingElSalvador;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Parking.Parking;

public class ParkingTest {

    private Parking parking;

    @Before
    public void setUp() {
        parking = new Parking();
        parking.setNombre("Nombre del Parking");
        parking.setDireccionPostal("Dirección Postal del Parking");
        parking.setCapacidadTotal(100);
    }

    @Test
    public void testGetNombre() {
        assertEquals("Nombre del Parking", parking.getNombre());
    }

    @Test
    public void testSetNombre() {
        parking.setNombre("Nuevo Nombre del Parking");
        assertEquals("Nuevo Nombre del Parking", parking.getNombre());
    }

    @Test
    public void testGetDireccionPostal() {
        assertEquals("Dirección Postal del Parking", parking.getDireccionPostal());
    }

    @Test
    public void testGetCapacidadTotal() {
        assertEquals(100, parking.getCapacidadTotal());
    }

    @Test
    public void testGetPlazasDisponiblesInicial() {
        assertEquals(100, parking.getPlazasDisponibles());
    }

    @Test
    public void testGetPlazasOcupadasInicial() {
        assertEquals(0, parking.getPlazasOcupadas());
    }

    @Test
    public void testSetPlazasDisponibles() {
        parking.setPlazasDisponibles(80);
        assertEquals(80, parking.getPlazasDisponibles());
    }

    @Test
    public void testSetPlazasOcupadas() {
        parking.setPlazasOcupadas(20);
        assertEquals(20, parking.getPlazasOcupadas());
    }

    @Test
    public void testToString() {
        assertEquals("Nombre del Parking con direccion postal Dirección Postal del Parking tiene una capacidad de 100", parking.toString());
    }
}
