package es.uca.ParkingElSalvador;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Estancias.EstanciasService;
import es.uca.ParkingElSalvador.Informes.Informe;
import es.uca.ParkingElSalvador.Parking.Parking;
import es.uca.ParkingElSalvador.Parking.ParkingElSalvador;
import es.uca.ParkingElSalvador.Parking.ParkingService;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InformeTest {
    private Informe informe;

    private Vehiculo vehiculo;

    @Before
    public void setUp() {
        ParkingElSalvador p = new ParkingElSalvador(new Parking());
        ParkingService parking = p.getParkingService();
        vehiculo = new Vehiculo("123ABC");
        vehiculo.getEstancia().setDineroPagado(10.0); // Simulamos que el vehículo ha pagado $10
        EstanciasService libro = parking.getLibro();
        libro.save(vehiculo); // Almacenamos el vehículo en el registro del parking
        informe = new Informe(parking);
    }

    @Test
    public void testFechaInforme() {
        String fechaHoraActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals("La fecha y hora del informe debería ser la fecha y hora actual formateada correctamente", fechaHoraActual, informe.fechaInforme());
    }

    @Test
    public void testFechaInformeLT() {
        assertNotNull("La fecha y hora del informe LocalDateTime no debería ser nula", informe.fechaInformeLT());
    }

    @Test
    public void testIngresoDiario() {
        assertEquals("El ingreso diario debería ser igual al dinero pagado por el vehículo", 10.0, informe.ingresoDiario(), 0.0);
    }

    @Test
    public void testIngresoSemanal() {
        assertEquals("El ingreso semanal debería ser igual al dinero pagado por el vehículo", 10.0, informe.ingresoSemanal(), 0.0);
    }

    @Test
    public void testIngresoMensual() {
        assertEquals("El ingreso mensual debería ser igual al dinero pagado por el vehículo", 10.0, informe.ingresoMensual(), 0.0);
    }
}
