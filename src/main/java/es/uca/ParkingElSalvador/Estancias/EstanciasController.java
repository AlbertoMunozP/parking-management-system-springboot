package es.uca.ParkingElSalvador.Estancias;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estancias")
public class EstanciasController {

    private final EstanciasService estanciasService;

    @Autowired
    public EstanciasController(EstanciasService estanciasService) {
        this.estanciasService = estanciasService;
    }

    @PostMapping
    @Operation(summary = "Guardar nueva estancia", description = "Guarda una nueva estancia de un vehículo en el sistema")
    @ApiResponse(responseCode = "200", description = "Estancia guardada correctamente")
    public ResponseEntity<?> saveEstancia(@RequestBody Vehiculo vehiculo) {
        estanciasService.save(vehiculo);
        return ResponseEntity.ok("Estancia guardada correctamente para el vehiculo con matricula: " + vehiculo.getMatricula());
    }

    @GetMapping("/existe/{matricula}")
    @Operation(summary = "Verificar existencia de estancia", description = "Verifica si existe una estancia para la matrícula especificada")
    @ApiResponse(responseCode = "200", description = "Existencia de estancia devuelta", 
                content = @Content(schema = @Schema(implementation = Boolean.class)))
    public ResponseEntity<?> existeEstancia(@PathVariable String matricula) {
        boolean existe = estanciasService.esta(matricula);
        return ResponseEntity.ok("Existencia de estancia para la matricula " + matricula + ": " + existe);
    }

    @GetMapping("/{matricula}")
    @Operation(summary = "Obtener estancias por matrícula", description = "Obtiene todas las estancias registradas para una matrícula específica")
    @ApiResponse(responseCode = "200", description = "Lista de estancias devuelta correctamente",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = Estancia.class))))
    public ResponseEntity<List<Estancia>> getEstanciasPorMatricula(@PathVariable String matricula) {
        List<Estancia> estancias = estanciasService.getEstancias(matricula);
        return ResponseEntity.ok(estancias);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las estancias", description = "Obtiene todas las estancias registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de todas las estancias devuelta correctamente",
                 content = @Content(array = @ArraySchema(schema = @Schema(implementation = Estancia.class))))
    public ResponseEntity<List<Estancia>> getAllEstancias() {
        List<Estancia> estancias = estanciasService.getAllEstancias();
        return ResponseEntity.ok(estancias);
    }

    @GetMapping("/count")
    @Operation(summary = "Contar estancias", description = "Cuenta el total de estancias registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Número total de estancias devuelto",
                 content = @Content(schema = @Schema(implementation = Long.class)))
    public ResponseEntity<Long> getNumeroDeEstancias() {
        long count = estanciasService.numEstancias();
        return ResponseEntity.ok(count);
    }   

}
