package es.uca.ParkingElSalvador;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Bonos.Bono;
import es.uca.ParkingElSalvador.Bonos.BonoAnual;
import es.uca.ParkingElSalvador.Bonos.BonoInMemoryRepo;
import es.uca.ParkingElSalvador.Bonos.BonoMensual;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

import java.math.BigDecimal;
import java.util.List;

public class BonoServiceTest {

    private BonoInMemoryRepo bonoService;
    
    @Before
    public void setUp() {
        bonoService = new BonoInMemoryRepo();
    }

    @Test
    public void testMeter() {
        // Creamos un BonoAnual para realizar la prueba
        Vehiculo vehiculo = new Vehiculo("ABC123");
        BonoAnual.setPrecio(new BigDecimal("100")); // Precio de ejemplo
        Bono bono = new BonoAnual(vehiculo.getEstancia());
        
        // Metemos el bono y verificamos que esté presente
        bonoService.meter(bono);
        List<Bono> bonos = bonoService.bonos("ABC123");
        assertTrue(bonos.contains(bono));
    }

    @Test
    public void testSacar() {
        // Creamos un BonoAnual y lo metemos
        Vehiculo vehiculo = new Vehiculo("ABC123");
        BonoAnual.setPrecio(new BigDecimal("100")); // Precio de ejemplo
        Bono bono = new BonoAnual(vehiculo.getEstancia());
        bonoService.meter(bono);
        
        // Sacamos el bono y verificamos que no esté presente
        bonoService.sacar(bono);
        List<Bono> bonos = bonoService.bonos("ABC123");
        assertFalse(bonos.contains(bono));
    }

    @Test
    public void testBonos() {
        // Creamos varios bonos para una misma matrícula
        Vehiculo vehiculo = new Vehiculo("ABC123");
        BonoAnual.setPrecio(new BigDecimal("100")); // Precio de ejemplo
        Bono bono1 = new BonoAnual(vehiculo.getEstancia());
        Bono bono2 = new BonoMensual(vehiculo.getEstancia());
        bonoService.meter(bono1);
        bonoService.meter(bono2);
        
        // Verificamos que los bonos estén presentes
        List<Bono> bonos = bonoService.bonos("ABC123");
        assertTrue(bonos.contains(bono1));
        assertTrue(bonos.contains(bono2));
    }

    @Test
    public void testGetAllBonos() {
        // Creamos varios bonos
        Vehiculo vehiculo1 = new Vehiculo("ABC123");
        Vehiculo vehiculo2 = new Vehiculo("DEF456");
        BonoAnual.setPrecio(new BigDecimal("100")); // Precio de ejemplo
        Bono bono1 = new BonoAnual(vehiculo1.getEstancia());
        Bono bono2 = new BonoMensual(vehiculo2.getEstancia());
        bonoService.meter(bono1);
        bonoService.meter(bono2);
        
        // Verificamos que todos los bonos estén presentes
        List<Bono> todosBonos = bonoService.getAllBonos();
        assertTrue(todosBonos.contains(bono1));
        assertTrue(todosBonos.contains(bono2));
    }
}
