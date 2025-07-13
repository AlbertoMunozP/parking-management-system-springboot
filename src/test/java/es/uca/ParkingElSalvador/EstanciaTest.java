package es.uca.ParkingElSalvador;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import es.uca.ParkingElSalvador.Estancias.Estancia;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EstanciaTest {
    private Estancia estancia;
    private Vehiculo vehiculo;

    @Before
    public void setUp() {
        vehiculo = new Vehiculo("123ABC");
        estancia = new Estancia(vehiculo);
    }

    @Test
    public void testHoraLlegada() {
        String horaLlegadaEsperada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals("La hora de llegada debe ser igual a la hora actual formateada correctamente", horaLlegadaEsperada, estancia.horaLlegada());
    }

    @Test
    public void testHoraSalida() {
        // Supongamos que el vehículo sale después de 1 hora
        estancia.termina();
        String horaSalidaEsperada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals("La hora de salida debe ser igual a la hora actual formateada correctamente", horaSalidaEsperada, estancia.horaSalida());
    }

    @Test
    public void testHoraLlegadaLT() {
        assertNotNull("La hora de llegada LocalDateTime no debería ser nula", estancia.getLlegada());
    }

    @Test
    public void testHoraSalidaLT() {
        // Supongamos que el vehículo sale después de 1 hora
        estancia.termina();
        assertNotNull("La hora de salida LocalDateTime no debería ser nula", estancia.getSalida());
    }

    @Test
    public void testVehiculo() {
        assertEquals("El vehículo asociado a la estancia debe ser el mismo que se proporcionó en el constructor", vehiculo, estancia.getVehiculo());
    }
}
