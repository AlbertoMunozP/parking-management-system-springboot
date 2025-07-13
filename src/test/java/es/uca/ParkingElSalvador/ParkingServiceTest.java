package es.uca.ParkingElSalvador;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Bonos.BonoAnual;
import es.uca.ParkingElSalvador.Bonos.BonoMensual;
import es.uca.ParkingElSalvador.Bonos.BonoTrimestral;
import es.uca.ParkingElSalvador.Parking.Parking;
import es.uca.ParkingElSalvador.Parking.ParkingElSalvador;
import es.uca.ParkingElSalvador.Parking.ParkingService;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

public class ParkingServiceTest {

    private ParkingService parkingService;

    @Before
    public void setUp() {
        Parking parking = new Parking();
        parking.setNombre("Parking Test");
        parking.setDireccionPostal("Direccion Test");
        parking.setCapacidadTotal(100);
        parkingService = new ParkingElSalvador(parking).getParkingService();
    }

    @Test
    public void testPrecioEstandar() {
        long precioMinuto = 5; // Precio por minuto
        parkingService.precioEstandar(precioMinuto);
        assertEquals(precioMinuto, parkingService.getTarifa().precioMinuto());
    }

    @Test
    public void testPonerPrecioBonos() {
        double precioMensual = 50;
        double precioTrimestral = 120;
        double precioAnual = 400;
        parkingService.ponerPrecioBonos(precioMensual, precioTrimestral, precioAnual);
        Vehiculo v = new Vehiculo("1243A");
        BonoMensual mensual = new BonoMensual(v.getEstancia());
        BonoTrimestral trimestral = new BonoTrimestral(v.getEstancia());
        BonoAnual anual = new BonoAnual(v.getEstancia());
        assertEquals(precioMensual, mensual.getPrecio().doubleValue(), 0);
        assertEquals(precioTrimestral, trimestral.getPrecio().doubleValue(), 0);
        assertEquals(precioAnual, anual.getPrecio().doubleValue(), 0);
    }

    @Test
    public void testSetDirectorioQR() {
        String directorio = "Directorio/Test";
        parkingService.setDirectorioQR(directorio);
        assertEquals(directorio, parkingService.getQr().getDirectorio());
    }

    @Test
    public void testEntrada() throws Exception {
        parkingService.entrada("ABC123"); // Simula entrada de vehículo con matrícula ABC123
        assertEquals(99, parkingService.getParking().getPlazasDisponibles());
        assertEquals(1, parkingService.getParking().getPlazasOcupadas());
        parkingService.salida("ABC123"); // Simula salida de vehículo con matrícula ABC123
    }

    @Test
    public void testSalida() throws Exception {
        parkingService.entrada("ABC123"); // Simula entrada de vehículo con matrícula ABC123
        parkingService.vehiculoPagaEstandar(new BigDecimal(0),"ABC123",'T');
        parkingService.salida("ABC123"); // Simula salida de vehículo con matrícula ABC123
        assertEquals(100, parkingService.getParking().getPlazasDisponibles());
        assertEquals(0, parkingService.getParking().getPlazasOcupadas());
    }

    @Test
    public void testVehiculoPagaEstandar() {
        BigDecimal dineroEntregado = new BigDecimal(10); // Dinero entregado por el cliente
        String matricula = "ABC123"; // Matrícula del vehículo
        char formaPago = 'E'; // 'E' para efectivo, 'T' para tarjeta, etc.
        parkingService.entrada(matricula);
        parkingService.vehiculoPagaEstandar(dineroEntregado, matricula, formaPago);

        Vehiculo vehiculo = parkingService.getVehiculos().getVehiculo(matricula);
        parkingService.salida(matricula);
        assertNotNull(vehiculo);
        assertTrue(vehiculo.getEstancia().isPagadoEstandar());
        assertEquals(parkingService.getParking().getPlazasDisponibles(), 100);
        assertEquals(parkingService.getParking().getPlazasOcupadas(), 0);
    }

    @Test
    public void testVehiculoPagaBonoMensual() {
        BigDecimal dineroEntregado = new BigDecimal(100); // Dinero entregado por el cliente
        int meses = 3; // Duración del bono en meses
        String matricula = "ABC123"; // Matrícula del vehículo
        char formaPago = 'E'; // 'E' para efectivo, 'T' para tarjeta, etc.
        parkingService.entrada(matricula);
        parkingService.vehiculoPagaBonoMensual(dineroEntregado, meses, matricula, formaPago);

        Vehiculo vehiculo = parkingService.getVehiculos().getVehiculo(matricula);
        parkingService.salida(matricula);
        assertNotNull(vehiculo);
        assertTrue(vehiculo.getEstancia().isTieneBono());
        assertEquals(parkingService.getParking().getPlazasDisponibles(), 100);
        assertEquals(parkingService.getParking().getPlazasOcupadas(), 0);
    }

    @Test
    public void testVehiculoPagaBonoTrimestral() {
        BigDecimal dineroEntregado = new BigDecimal(300); // Dinero entregado por el cliente
        int trimestres = 2; // Duración del bono en trimestres
        String matricula = "ABC123"; // Matrícula del vehículo
        char formaPago = 'T'; // 'E' para efectivo, 'T' para tarjeta, etc.
        parkingService.entrada(matricula);
        parkingService.vehiculoPagaBonoTrimestral(dineroEntregado, trimestres, matricula, formaPago);

        Vehiculo vehiculo = parkingService.getVehiculos().getVehiculo(matricula);
        parkingService.salida(matricula);
        assertNotNull(vehiculo);
        assertTrue(vehiculo.getEstancia().isTieneBono());
        assertEquals(parkingService.getParking().getPlazasDisponibles(), 100);
        assertEquals(parkingService.getParking().getPlazasOcupadas(), 0);
    }

    @Test
    public void testVehiculoPagaBonoAnual() {
        BigDecimal dineroEntregado = new BigDecimal(800); // Dinero entregado por el cliente
        int annos = 1; // Duración del bono en años
        String matricula = "ABC123"; // Matrícula del vehículo
        char formaPago = 'T'; // 'E' para efectivo, 'T' para tarjeta, etc.
        parkingService.entrada(matricula);
        parkingService.vehiculoPagaBonoAnual(dineroEntregado, annos, matricula, formaPago);

        Vehiculo vehiculo = parkingService.getVehiculos().getVehiculo(matricula);
        parkingService.salida(matricula);
        assertNotNull(vehiculo);
        assertTrue(vehiculo.getEstancia().isTieneBono());
        assertEquals(parkingService.getParking().getPlazasDisponibles(), 100);
        assertEquals(parkingService.getParking().getPlazasOcupadas(), 0);
    }
}


