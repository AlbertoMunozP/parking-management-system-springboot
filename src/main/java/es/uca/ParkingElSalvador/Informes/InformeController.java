package es.uca.ParkingElSalvador.Informes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/informes")
public class InformeController {

    private final Informe informeService;

    @Autowired
    public InformeController(Informe informeService) {
        this.informeService = informeService;
    }

    @GetMapping("/fecha")
    @Operation(summary = "Obtener fecha del informe", description = "Devuelve la fecha del último informe generado")
    @ApiResponse(responseCode = "200", description = "Fecha del informe devuelta correctamente")
    public ResponseEntity<String> getFechaInforme() {
        String fecha = informeService.fechaInforme();
        return ResponseEntity.ok("Fecha del informe: " + fecha);
    }

    @GetMapping("/ingreso/diario")
    @Operation(summary = "Obtener ingreso diario", description = "Devuelve el total de ingresos del día actual")
    @ApiResponse(responseCode = "200", description = "Ingreso diario devuelto correctamente")
    public ResponseEntity<String> getIngresoDiario() {
        double ingreso = informeService.ingresoDiario();
        return ResponseEntity.ok("Ingreso diario: " + ingreso);
    }

    @GetMapping("/ingreso/semanal")
    @Operation(summary = "Obtener ingreso semanal", description = "Devuelve el total de ingresos de la semana actual")
    @ApiResponse(responseCode = "200", description = "Ingreso semanal devuelto correctamente")
    public ResponseEntity<String> getIngresoSemanal() {
        double ingreso = informeService.ingresoSemanal();
        return ResponseEntity.ok("Ingreso semanal: " + ingreso);
    }

    @GetMapping("/ingreso/mensual")
    @Operation(summary = "Obtener ingreso mensual", description = "Devuelve el total de ingresos del mes actual")
    @ApiResponse(responseCode = "200", description = "Ingreso mensual devuelto correctamente")
    public ResponseEntity<String> getIngresoMensual() {
        double ingreso = informeService.ingresoMensual();
        return ResponseEntity.ok("Ingreso mensual: " + ingreso);
    }

    @GetMapping("/toString")
    @Operation(summary = "Obtener representación de texto del informe", description = "Devuelve una representación en texto del informe actual")
    @ApiResponse(responseCode = "200", description = "Representación de texto del informe devuelta correctamente")
    public ResponseEntity<String> getInformeToString() {
        String informe = informeService.toString();
        return ResponseEntity.ok("Informe: " + informe);
    }
}
