package es.uca.ParkingElSalvador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Bonos.BonoAnual;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

public class BonoAnualTest {
    private Vehiculo vehiculo;
    private BonoAnual bono;

    @Before
    public void setUp() {
        vehiculo = new Vehiculo("ABC123");
        bono = new BonoAnual(vehiculo.getEstancia());
    }

    @Test
    public void testConstructor() {
        assertNotNull(bono);
        assertEquals(vehiculo, bono.getEstancia().getVehiculo());
    }

    @Test
    public void testSetPrecio() {
        BigDecimal precio = new BigDecimal("100.00");
        BonoAnual.setPrecio(precio);
        assertEquals(precio, bono.getPrecio());
    }

    @Test
    public void testTipoBono() {
        assertEquals("Anual", bono.getTipoBono());
    }
}
