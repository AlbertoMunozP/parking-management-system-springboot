package com.example.VehiculosCLI;

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
            return "Operacion exitosa " + response.block();
        } catch (Exception e) {
            return "Error al realizar la operacion GET: " + e.getMessage();
        }
    }

    // Método helper para realizar llamadas POST y manejar la respuesta
    private String callApiPost(String uri, String body) {
        try {
            Mono<String> response = webClient.post()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .bodyValue(body == null ? "" : body)
                    .retrieve()
                    .bodyToMono(String.class);
            return "Operacion exitosa " + response.block();  // Usar block con precaución
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
            return "Operacion exitosa: " + response.block();  // Usar block con precaución
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

    @ShellMethod("Crea una nueva estancia")
    public String createEstancia(@ShellOption String matricula) {
        String requestBody = String.format("{\"matricula\":\"%s\"}", matricula);
        return callApiPost("/estancias", requestBody);
    }

    @ShellMethod("Verifica si una estancia existe por matrícula")
    public String existeEstancia(@ShellOption String matricula) {
        return callApiGet("/estancias/existe/" + matricula);
    }

    @ShellMethod("Obtiene estancias por matrícula")
    public String getEstanciasPorMatricula(@ShellOption String matricula) {
        return callApiGet("/estancias/" + matricula);
    }

    @ShellMethod("Obtiene todas las estancias")
    public String getAllEstancias() {
        return callApiGet("/estancias");
    }

    @ShellMethod("Obtiene el número total de estancias")
    public String getNumeroDeEstancias() {
        return callApiGet("/estancias/count");
    }

    // Métodos para el CarController

    @ShellMethod("Guardar un nuevo coche")
    public String saveNewCar(@ShellOption String matricula) {
        return callApiPost("/vehiculos/" + matricula, null);
    }

    @ShellMethod("Guardar un coche existente")
    public String saveCar(@ShellOption String matricula) {
        String requestBody = String.format("{\"matricula\":\"%s\"}", matricula);
        return callApiPost("/vehiculos", requestBody);
    }

    @ShellMethod("Eliminar un coche")
    public String deleteCar(@ShellOption String matricula) {
        return callApiDelete("/vehiculos/" + matricula);
    }

    @ShellMethod("Obtener un coche por matrícula")
    public String getCar(@ShellOption String matricula) {
        return callApiGet("/vehiculos/" + matricula);
    }

    @ShellMethod("Obtener el número total de coches")
    public String getNumCars() {
        return callApiGet("/vehiculos/count");
    }
}
   
