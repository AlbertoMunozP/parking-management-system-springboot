package es.uca.ParkingElSalvador;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Bonos.BonoInMemoryRepo;
import es.uca.ParkingElSalvador.Bonos.BonoService;
import es.uca.ParkingElSalvador.Pagos.Cajero;
import es.uca.ParkingElSalvador.Pagos.PagoBono;
import es.uca.ParkingElSalvador.Vehiculos.CarRepositoryInMemoryRepo;
import es.uca.ParkingElSalvador.Vehiculos.CarService;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

public class PagoBonoTest {
    private CarService c;
    private Vehiculo vehiculo;
    private PagoBono pago;
    private Cajero cajero;

    @Before
    public void setUp() {
        vehiculo = new Vehiculo("123ABC");
        c = new CarService(new CarRepositoryInMemoryRepo());
        c.save(vehiculo);
        cajero = new Cajero();
        cajero.setDinero(new BigDecimal(500000));
        pago = new PagoBono(c,new BonoService(new BonoInMemoryRepo()));
    }

    @Test
    public void testComprarBonoMensual() {
        try {
            pago.comprarBonoMensual(new BigDecimal(1),vehiculo.getMatricula(),1,'E'); // Compra de bono mensual válido
            assertTrue("El vehículo debería haber comprado un bono mensual", vehiculo.getEstancia().isTieneBono());
            assertNotNull("El vehículo debería tener una fecha de fin de bono", vehiculo.getEstancia().getBono().getFinBono());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testComprarBonoTrimestral() {
        try {
            pago.comprarBonoTrimestral(new BigDecimal(1),vehiculo.getMatricula(),1,'T'); // Compra de bono trimestral válido
            assertTrue("El vehículo debería haber comprado un bono trimestral", vehiculo.getEstancia().isTieneBono());
            assertNotNull("El vehículo debería tener una fecha de fin de bono", vehiculo.getEstancia().getBono().getFinBono());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testComprarBonoAnual() {
        try {
            pago.comprarBonoAnual(new BigDecimal(1),vehiculo.getMatricula(),1,'E'); // Compra de bono anual válido
            assertTrue("El vehículo debería haber comprado un bono anual", vehiculo.getEstancia().isTieneBono());
            assertNotNull("El vehículo debería tener una fecha de fin de bono", vehiculo.getEstancia().getBono().getFinBono());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getMessage());
        }
    }
}
