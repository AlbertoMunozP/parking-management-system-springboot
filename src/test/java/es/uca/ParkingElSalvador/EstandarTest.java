package es.uca.ParkingElSalvador;

import org.junit.Test;

import es.uca.ParkingElSalvador.Pagos.Estandar;

import static org.junit.Assert.*;

public class EstandarTest {

    @Test
    public void testPonerPrecioAlMinuto() {
        // Configuración
        Estandar estandar = new Estandar();
        long precio = 10;

        // Ejecución
        estandar.ponerPrecioAlMinuto(precio);

        // Verificación
        assertEquals(precio, estandar.precioMinuto());
    }

    @Test
    public void testPrecioMinutoInicial() {
        // Configuración
        Estandar estandar = new Estandar();

        // Verificación
        assertEquals(0, estandar.precioMinuto());
    }
}
