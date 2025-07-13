package es.uca.ParkingElSalvador;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Pagos.Cajero;

import java.math.BigDecimal;

public class CajeroTest {

    private Cajero cajero;
    
    @Before
    public void setUp() {
        cajero = new Cajero();
    }

    @Test
    public void testMeterDinero() {
        // Añadir dinero al cajero y verificar que se haya añadido correctamente
        BigDecimal cantidad = new BigDecimal("50.00");
        cajero.meterDinero(cantidad);
        assertEquals(cantidad, cajero.getDinero());
    }

    @Test
    public void testSacarDinero() {
        // Añadir dinero al cajero, luego sacarlo y verificar que se haya restado correctamente
        BigDecimal cantidad = new BigDecimal("50.00");
        cajero.meterDinero(cantidad);
        BigDecimal cantidadSacada = new BigDecimal("30.00");
        cajero.sacarDinero(cantidadSacada);
        assertEquals(cantidad.subtract(cantidadSacada), cajero.getDinero());
    }

    @Test
    public void testHayCambio() {
        // Agregar dinero al cajero y verificar si hay suficiente para un cambio dado
        cajero.setDinero(new BigDecimal(0));
        BigDecimal cantidad = new BigDecimal("100.00");
        cajero.meterDinero(cantidad);
        BigDecimal otroDinero = new BigDecimal("50.00");
        assertTrue(cajero.hayCambio(otroDinero));
    }

    @Test
    public void testNoHayCambio() {
        // Agregar dinero al cajero y verificar si hay suficiente para un cambio dado
        BigDecimal cantidad = new BigDecimal("30.00");
        cajero.meterDinero(cantidad);
        BigDecimal otroDinero = new BigDecimal("50.00");
        assertFalse(cajero.hayCambio(otroDinero));
    }
}
