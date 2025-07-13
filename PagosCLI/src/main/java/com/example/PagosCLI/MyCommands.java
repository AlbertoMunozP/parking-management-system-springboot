package com.example.PagosCLI;

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
            return "Operacion exitosa\n";
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
            return "Operacion exitosa \n    " + response.block();  // Usar block con precaución
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
            return "Operacion exitosa: \n" + response.block();  // Usar block con precaución
        } catch (Exception e) {
            return "Error al realizar la operacion DELETE: \n" + e.getMessage();
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

    @ShellMethod("Realiza un pago estándar")
    public String pagarEstandar(@ShellOption double entregado, @ShellOption String matricula, @ShellOption char formato) {
        String requestBody = String.format(Locale.US,"entregado=%.2f&matricula=%s&formato=%c", entregado, matricula, formato);
        return callApiPost("/estandar", requestBody);
    }

    @ShellMethod("Compra un bono mensual")
    public String pagarBonoMensual(@ShellOption double entregado, @ShellOption int nMeses, @ShellOption String matricula, @ShellOption char formato) {
        String requestBody = String.format(Locale.US, "entregado=%.2f&nMeses=%d&matricula=%s&formato=%c", entregado, nMeses, matricula, formato);
        return callApiPost("/bonoMensual", requestBody);
    }

    @ShellMethod("Compra un bono trimestral")
    public String pagarBonoTrimestral(@ShellOption double entregado, @ShellOption int nTrimestres, @ShellOption String matricula, @ShellOption char formato) {
        String requestBody = String.format(Locale.US,"entregado=%.2f&nTrimestres=%d&matricula=%s&formato=%c", entregado, nTrimestres, matricula, formato);
        return callApiPost("/bonoTrimestral", requestBody);
    }

    @ShellMethod("Compra un bono anual")
    public String pagarBonoAnual(@ShellOption double entregado, @ShellOption int nAnnos, @ShellOption String matricula, @ShellOption char formato) {
        String requestBody = String.format(Locale.US,"entregado=%.2f&nAnnos=%d&matricula=%s&formato=%c", entregado, nAnnos, matricula, formato);
        return callApiPost("/bonoAnual", requestBody);
    }

}
   
