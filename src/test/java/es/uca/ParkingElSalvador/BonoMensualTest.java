package es.uca.ParkingElSalvador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Bonos.BonoMensual;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

public class BonoMensualTest {
    private Vehiculo vehiculo;
    private BonoMensual bono;

    @Before
    public void setUp() {
        vehiculo = new Vehiculo("ABC123");
        bono = new BonoMensual(vehiculo.getEstancia());
    }

    @Test
    public void testConstructor() {
        assertNotNull(bono);
        assertEquals(vehiculo, bono.getEstancia().getVehiculo());
    }

    @Test
    public void testSetPrecio() {
        BigDecimal precio = new BigDecimal("50.00");
        BonoMensual.setPrecio(precio);
        assertEquals(precio, bono.getPrecio());
    }

    @Test
    public void testTipoBono() {
        assertEquals("Mensual", bono.getTipoBono());
    }
}
