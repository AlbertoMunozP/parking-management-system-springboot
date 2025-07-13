package es.uca.ParkingElSalvador;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Pagos.Cajero;
import es.uca.ParkingElSalvador.Pagos.PagoEstandar;
import es.uca.ParkingElSalvador.Vehiculos.CarRepositoryInMemoryRepo;
import es.uca.ParkingElSalvador.Vehiculos.CarService;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

public class PagoEstandarTest {
    private CarService c;
    private Vehiculo vehiculo;
    private PagoEstandar pago;
    private Cajero cajero;

    @Before
    public void setUp() {
        c = new CarService(new CarRepositoryInMemoryRepo());
        vehiculo = new Vehiculo("123ABC");
        c.save(vehiculo);
        cajero = new Cajero();
        cajero.meterDinero(new BigDecimal(40));
        pago = new PagoEstandar(c); // Precio por minuto
    }

    @Test
    public void testCantidad() {
        assertEquals("La cantidad debe ser 30 si se han usado 60 minutos y el precio por minuto es 0.5", 30, pago.cantidad(60,0.5), 0);
    }

    @Test
    public void testPagar() {
        try {
            pago.pagar(new BigDecimal(10),vehiculo.getMatricula(),'E',4); // Pago de tarifa estándar válido
            assertTrue("El vehículo debería haber pagado la tarifa estándar", vehiculo.getEstancia().isPagadoEstandar());
            assertNotNull("El vehículo debería tener una cantidad pagada", vehiculo.getEstancia().getDineroPagado());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getMessage());
        }
    }
}
