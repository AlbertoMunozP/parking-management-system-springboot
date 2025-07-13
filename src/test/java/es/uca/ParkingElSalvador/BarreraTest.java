package es.uca.ParkingElSalvador;

import org.junit.Test;

import es.uca.ParkingElSalvador.Parking.Barrera;

import static org.junit.Assert.*;

public class BarreraTest {

    @Test
    public void testInicializacionBarreraCerrada() {
        Barrera barrera = new Barrera();
        assertFalse(barrera.estaAbierta());
    }

    @Test
    public void testAbrirBarrera() {
        Barrera barrera = new Barrera();
        barrera.abrirBarrera();
        assertTrue(barrera.estaAbierta());
    }

    @Test
    public void testCerrarBarrera() {
        Barrera barrera = new Barrera();
        barrera.abrirBarrera(); // Abrir primero
        barrera.cerrarBarrera();
        assertFalse(barrera.estaAbierta());
    }

    @Test
    public void testAbrirCerrarBarrera() {
        Barrera barrera = new Barrera();
        barrera.abrirBarrera();
        assertTrue(barrera.estaAbierta());
        barrera.cerrarBarrera();
        assertFalse(barrera.estaAbierta());
    }
}
