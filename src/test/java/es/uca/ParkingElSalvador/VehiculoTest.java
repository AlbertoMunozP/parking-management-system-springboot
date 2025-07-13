package es.uca.ParkingElSalvador;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Bonos.BonoMensual;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

import java.time.LocalDateTime;

public class VehiculoTest {
    private Vehiculo vehiculo;

    @Before
    public void setUp() {
        vehiculo = new Vehiculo("123ABC");
    }

    @Test
    public void testCompraBono() {
        assertFalse("Al inicio el vehículo no debe tener bono", vehiculo.getEstancia().isTieneBono());
        vehiculo.getEstancia().compraBono();
        assertTrue("Después de comprar el bono, el vehículo debe tenerlo", vehiculo.getEstancia().isTieneBono());
    }

    @Test
    public void testSetFinBono() {
        BonoMensual bono = new BonoMensual(vehiculo.getEstancia());
        vehiculo.getEstancia().setBono(bono);
        LocalDateTime finBono = LocalDateTime.now().plusHours(1);
        vehiculo.getEstancia().getBono().setFinBono(finBono);
        assertEquals("El fin del bono debe ser igual al valor establecido", finBono, vehiculo.getEstancia().getBono().getFinBono());
    }

    @Test
    public void testMatricula() {
        assertEquals("La matrícula del vehículo debe ser la establecida en el constructor", "123ABC", vehiculo.getMatricula());
    }

    @Test
    public void testDineroPagado() {
        assertEquals("El dinero pagado por el vehículo debe ser 0 al inicio", 0.0, vehiculo.getEstancia().getDineroPagado(), 0.001);
        vehiculo.getEstancia().setDineroPagado(10.5);
        assertEquals("El dinero pagado por el vehículo debe ser el valor establecido", 10.5, vehiculo.getEstancia().getDineroPagado(), 0.001);
    }

    @Test
    public void testBonoValidoSinBono() {
        assertFalse("El vehículo no debería tener un bono al inicio", vehiculo.getEstancia().bonoValido());
    }

    @Test
    public void testBonoValidoConBonoVencido() {
        BonoMensual bono = new BonoMensual(vehiculo.getEstancia());
        vehiculo.getEstancia().setBono(bono);

        vehiculo.getEstancia().compraBono();
        vehiculo.getEstancia().getBono().setFinBono(LocalDateTime.now().minusHours(1)); // Un bono vencido desde hace una hora
        assertFalse("El bono no debería ser válido si ya está vencido", vehiculo.getEstancia().bonoValido());
    }

    @Test
    public void testBonoValidoConBonoVigente() {
        BonoMensual bono = new BonoMensual(vehiculo.getEstancia());
        vehiculo.getEstancia().setBono(bono);
        vehiculo.getEstancia().compraBono();
        vehiculo.getEstancia().getBono().setFinBono(LocalDateTime.now().plusHours(1)); // Un bono válido por una hora
        assertTrue("El bono debería ser válido si aún no ha vencido", vehiculo.getEstancia().bonoValido());
    }

    @Test
    public void testHaPagadoSinPagar() {
        assertFalse("El vehículo no debería haber pagado al inicio", vehiculo.getEstancia().isPagadoEstandar());
    }

    @Test
    public void testHaPagadoDespuesDePagar() {
        vehiculo.getEstancia().pagarEstandar();
        assertTrue("El vehículo debería haber pagado después de llamar al método pagarEstandar()", vehiculo.getEstancia().isPagadoEstandar());
    }
}
