package es.uca.ParkingElSalvador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

import com.google.zxing.NotFoundException;

import es.uca.ParkingElSalvador.Parking.QRservice;

public class QRserviceTest {
    private QRservice qrService;

    @Before
    public void setUp() {
        qrService = new QRservice();
        qrService.setDirectorio("test_directory"); // Directorio de prueba
        qrService.limpiarDirectorio();
    }

    @Test
    public void testGenerarCodigoQR() {
        qrService.generarCodigoQR("contenido_de_prueba");
        // Verificar que el archivo se ha creado
        assertNotNull("El archivo QR generado no debería ser nulo", new File("test_directory/codigo_qr.png"));
        qrService.limpiarDirectorio();
    }

    @Test
    public void testLeerCodigoQR() throws Exception {
        try {
            // Generar un código QR de prueba
            qrService.generarCodigoQR("contenido_de_prueba");
            // Leer el código QR recién generado
            String contenidoLeido = qrService.leerCodigoQR();
            assertEquals("El contenido del código QR debe ser igual al original", "contenido_de_prueba", contenidoLeido);
        } catch (IOException | NotFoundException e) {
            assertNull("No se esperaba una excepción al leer el código QR", e);
        }
        qrService.limpiarDirectorio();
    }

}
