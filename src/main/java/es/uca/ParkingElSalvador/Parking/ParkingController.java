package es.uca.ParkingElSalvador.Parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private Parking p = null;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/configurar/parking")
    @Operation(summary = "Configurar parking", description = "Este endpoint permite configurar los datos del parking")
    @ApiResponse(responseCode = "200", description = "Parking configurado exitosamente")
    public ResponseEntity<?> setParking(@RequestParam("nombre") String nombre, 
                       @RequestParam("direccionPostal") String direccionPostal, 
                       @RequestParam("capacidadTotal") int capacidadTotal) {
    p = new Parking();
    p.setNombre(nombre);
    p.setDireccionPostal(direccionPostal);
    p.setCapacidadTotal(capacidadTotal);
    p.setPlazasDisponibles(capacidadTotal); // Asumiendo que todas las plazas están disponibles inicialmente
    p.setPlazasOcupadas(0); // Asumiendo que no hay plazas ocupadas inicialmente
    parkingService.setP(p);
    return ResponseEntity.ok("Parking configurado con exito: " + nombre);
}


    @PostMapping("/entrada")
    @Operation(summary = "Entrada", description = "Este endpoint permite la entrada al parking")
    @ApiResponse(responseCode = "200", description = "Vehiculo ha entrado exitosamente")
    public ResponseEntity<?> entrada() throws Exception {
        parkingService.entrada();
        parkingService.setP(p);
        return ResponseEntity.ok("Entrada al parking registrada exitosamente.");
    }

    @PostMapping("/entrada/manual")
    @Operation(summary = "Entrada manual", description = "Este endpoint permite la entrada manual al parking")
    @ApiResponse(responseCode = "200", description = "Vehiculo ha entrado exitosamente")
    public ResponseEntity<?> entradaManual(@RequestParam String matricula) {
        parkingService.entrada(matricula);
        parkingService.setP(p);
        return ResponseEntity.ok("Entrada al parking registrada exitosamente.");
    }

    @PostMapping("/salida")
    @Operation(summary = "Salida", description = "Este endpoint permite la salida del parking")
    @ApiResponse(responseCode = "200", description = "Vehículo ha salido exitosamente")
    public ResponseEntity<?> salida() throws Exception {
        parkingService.salida();
        parkingService.setP(p);
        return ResponseEntity.ok("Entrada manual al parking registrada exitosamente para la matricula: " );
    }

    @PostMapping("/salida/manual")
    @Operation(summary = "Salida manual", description = "Este endpoint permite la salida manual del parking")
    @ApiResponse(responseCode = "200", description = "Vehículo ha salido exitosamente")
    public ResponseEntity<?> salidaManual(@RequestParam String matricula) {
        parkingService.salida(matricula);
        parkingService.setP(p);
        return ResponseEntity.ok("Salida manual al parking realizada exitosamente para la matrícula: " + matricula);
    }

    @PostMapping("/configurar/tarifa")
    @Operation(summary = "Configurar tarifa", description = "Este endpoint permite configurar la tarifa de estacionamiento")
    @ApiResponse(responseCode = "200", description = "Tarifa configurada exitosamente")
    public ResponseEntity<?> configurarPrecioEstandar(@RequestParam long min) {
        parkingService.precioEstandar(min);
        return ResponseEntity.ok("Tarifa por minuto configurada exitosamente a: " + min);
    }

    @PostMapping("/configurar/preciosBonos")
    @Operation(summary = "Configurar precios de bonos", description = "Este endpoint permite configurar los precios de los bonos de estacionamiento")
    @ApiResponse(responseCode = "200", description = "Precios de bonos configurados exitosamente")
    public ResponseEntity<?> configurarPreciosBonos(@RequestParam double mes, @RequestParam double tri, @RequestParam double anno) {
        parkingService.ponerPrecioBonos(mes, tri, anno);
        return ResponseEntity.ok("Precios de bonos configurados exitosamente: Mensual: " + mes + ", Trimestral: " + tri + ", Anual: " + anno);
    }

    @PostMapping("/pago/estandar")
    @Operation(summary = "Pago estándar", description = "Este endpoint permite realizar el pago estándar de estacionamiento")
    @ApiResponse(responseCode = "200", description = "Pago realizado exitosamente")
    public ResponseEntity<?> pagarEstandar(@RequestParam BigDecimal entregado, @RequestParam String matricula, @RequestParam char formato) {
        parkingService.vehiculoPagaEstandar(entregado, matricula, formato);
        return ResponseEntity.ok("Pago estandar realizado exitosamente para la matricula: " + matricula);
    }

    @PostMapping("/pago/bonoMensual")
    @Operation(summary = "Pago con bono mensual", description = "Este endpoint permite realizar el pago de estacionamiento con un bono mensual")
    @ApiResponse(responseCode = "200", description = "Pago con bono mensual realizado exitosamente")
    public ResponseEntity<?> pagarBonoMensual(@RequestParam BigDecimal entregado, @RequestParam int nMeses, @RequestParam String matricula, @RequestParam char formato) {
        parkingService.vehiculoPagaBonoMensual(entregado, nMeses, matricula, formato);
        return ResponseEntity.ok("Pago con bono mensual realizado exitosamente para la matricula: " + matricula);
    }

    @PostMapping("/pago/bonoTrimestral")
    @Operation(summary = "Pago con bono trimestral", description = "Este endpoint permite realizar el pago de estacionamiento con un bono trimestral")
    @ApiResponse(responseCode = "200", description = "Pago con bono trimestral realizado exitosamente")
    public ResponseEntity<?> pagarBonoTrimestral(@RequestParam BigDecimal entregado, @RequestParam int nTrimestres, @RequestParam String matricula, @RequestParam char formato) {
        parkingService.vehiculoPagaBonoTrimestral(entregado, nTrimestres, matricula, formato);
        return ResponseEntity.ok("Pago con bono trimestral realizado exitosamente para la matricula: " + matricula);
    }

    @PostMapping("/pago/bonoAnual")
    @Operation(summary = "Pago con bono anual", description = "Este endpoint permite realizar el pago de estacionamiento con un bono anual")
    @ApiResponse(responseCode = "200", description = "Pago con bono anual realizado exitosamente")
    public ResponseEntity<?> pagarBonoAnual(@RequestParam BigDecimal entregado, @RequestParam int nAnnos, @RequestParam String matricula, @RequestParam char formato) {
        parkingService.vehiculoPagaBonoAnual(entregado, nAnnos, matricula, formato);
        return ResponseEntity.ok("Pago con bono anual realizado exitosamente para la matricula: " + matricula);
    }


}
