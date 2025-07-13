package com.example.ParkingCLI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Locale;  // Añadir esta línea

@ShellComponent
public class MyCommands {

    private final WebClient webClient;

    @Autowired
    public MyCommands(WebClient webClient) {
        this.webClient = webClient;
    }

    // Método helper para realizar llamadas GET y manejar la respuesta
    private String callApiGet(String uri) {
        try {
            Mono<String> response = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class);
            return "Operacion exitosa \n" + response.block();
        } catch (Exception e) {
            return "Error al realizar la operacion GET: " + e.getMessage();
        }
    }

    // Método helper para realizar llamadas POST y manejar la respuesta
    private String callApiPost(String uri, String body) {
        try {
            Mono<String> response = webClient.post()
                    .uri(uri)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .bodyValue(body == null ? "" : body)
                    .retrieve()
                    .bodyToMono(String.class);
            return "Operacion exitosa \n" + response.block();  // Usar block con precaución
        } catch (Exception e) {
            return "Error al realizar la operacion POST: " + e.getMessage();
        }
    }
    
    // Método helper para realizar llamadas DELETE y manejar la respuesta
    private String callApiDelete(String uri) {
        try {
            Mono<String> response = webClient.delete()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class);
            return "Operacion exitosa\n " + response.block();  // Usar block con precaución
        } catch (Exception e) {
            return "Error al realizar la operacion DELETE: " + e.getMessage();
        }
    }

    // Método helper para manejar bloqueo y errores
    private String safeCall(Mono<String> call) {
        try {
            return call.block();
        } catch (Exception e) {
            return "Error al procesar la solicitud: " + e.getMessage();
        }
    }

    // Métodos para interactuar con la API

    @ShellMethod("Obtiene la fecha del sistema")
    public String getSystemDate() {
        return callApiGet("/informes/fecha");
    }
    /* Este metodo... */
    @ShellMethod("Obtiene ingresos diarios")
    public String getDailyIncome() {
        return callApiGet("/informes/ingreso/diario");
    }

    @ShellMethod("Obtiene ingresos semanales")
    public String getWeeklyIncome() {
        return callApiGet("/informes/ingreso/semanal");
    }

    @ShellMethod("Obtiene ingresos mensuales")
    public String getMonthlyIncome() {
        return callApiGet("/informes/ingreso/mensual");
    }

    @ShellMethod("Obtiene la representación en texto del estado actual")
    public String getStateAsString() {
        return callApiGet("/informes/toString");
    }


    @ShellMethod("Configura el parking")
    public String configurarParking(@ShellOption String nombre, @ShellOption String direccionPostal, @ShellOption int capacidadTotal) {
        String requestBody = String.format("nombre=%s&direccionPostal=%s&capacidadTotal=%d", nombre, direccionPostal, capacidadTotal);
        return callApiPost("/parking/configurar/parking", requestBody);
    }
    

    @ShellMethod("Registra una entrada automática")
    public String entrada() {
        return callApiPost("/parking/entrada", null);
    }

    @ShellMethod("Registra una entrada manual")
    public String entradaManual(@ShellOption String matricula) {
        String requestBody = String.format("matricula=%s", matricula);
        return callApiPost("/parking/entrada/manual", requestBody);
    }

    @ShellMethod("Registra una salida automática")
    public String salida() {
        return callApiPost("/parking/salida", null);
    }

    @ShellMethod("Registra una salida manual")
    public String salidaManual(@ShellOption String matricula) {
        String requestBody = String.format("matricula=%s", matricula);
        return callApiPost("/parking/salida/manual", requestBody);
    }

    @ShellMethod("Configura la tarifa")
    public String configurarTarifa(@ShellOption long min) {
        String requestBody = String.format("min=%d", min);
        return callApiPost("/parking/configurar/tarifa", requestBody);
    }

    @ShellMethod("Configura los precios de los bonos")
    public String configurarPreciosBonos(@ShellOption double mes, @ShellOption double tri, @ShellOption double anno) {
        String requestBody = String.format(Locale.US, "mes=%.2f&tri=%.2f&anno=%.2f", mes, tri, anno);
        return callApiPost("/parking/configurar/preciosBonos", requestBody);
    }

    // Métodos para el BonoController

    @ShellMethod("Guardar un bono")
    public String saveBono(@ShellOption String matricula, @ShellOption String tipoBono) {
        String requestBody = String.format("{\"matricula\":\"%s\",\"dtype\":\"%s\"}", matricula, tipoBono);
        return callApiPost("/bonos?matricula=" + matricula, requestBody);
    }

    @ShellMethod("Eliminar un bono por matrícula")
    public String deleteBono(@ShellOption String matricula) {
        return callApiDelete("/bonos/" + matricula);
    }

    @ShellMethod("Obtener bonos por matrícula")
    public String getBonosByMatricula(@ShellOption String matricula) {
        return callApiGet("/bonos/" + matricula);
    }

    @ShellMethod("Obtener todos los bonos")
    public String getAllBonos() {
        return callApiGet("/bonos");
    }
}
   
