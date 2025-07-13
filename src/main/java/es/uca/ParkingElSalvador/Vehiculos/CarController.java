package es.uca.ParkingElSalvador.Vehiculos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/vehiculos")
public class CarController {

    private final CarService carService;
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/{matricula}")
    @Operation(summary = "Registrar nuevo vehículo", description = "Registra un nuevo vehículo usando la matrícula")
    @ApiResponse(responseCode = "200", description = "Vehículo registrado exitosamente")
    public ResponseEntity<String> saveNewCar(@PathVariable String matricula) {
        Vehiculo v = new Vehiculo(matricula);
        carService.save(v);
        return ResponseEntity.ok("Vehiculo registrado exitosamente con matricula: " + matricula);
    }

    @PostMapping
    @Operation(summary = "Guardar vehículo", description = "Guarda un vehículo proporcionado en el cuerpo de la petición")
    @ApiResponse(responseCode = "200", description = "Vehículo guardado exitosamente")
    public ResponseEntity<String> saveCar(@RequestBody Vehiculo vehiculo) {
        carService.save(vehiculo);
        return ResponseEntity.ok("Vehiculo guardado exitosamente con matricula: " + vehiculo.getMatricula());
    }

    @DeleteMapping("/{matricula}")
    @Operation(summary = "Eliminar vehículo", description = "Elimina un vehículo basado en la matrícula proporcionada")
    @ApiResponse(responseCode = "200", description = "Vehículo eliminado exitosamente", 
                 content = @Content(schema = @Schema(implementation = Vehiculo.class)))
    public ResponseEntity<String> deleteCar(@PathVariable String matricula) {
        Vehiculo v = carService.getVehiculo(matricula);
        if (v != null) {
            carService.delete(matricula);
            return ResponseEntity.ok("Vehiculo eliminado exitosamente con matricula: " + matricula);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{matricula}")
    @Operation(summary = "Obtener vehículo", description = "Obtiene detalles de un vehículo por su matrícula")
    @ApiResponse(responseCode = "200", description = "Vehículo encontrado",
                 content = @Content(schema = @Schema(implementation = Vehiculo.class)))
    public ResponseEntity<Vehiculo> getVehiculo(@PathVariable String matricula) {
        Vehiculo v = carService.getVehiculo(matricula);
        if (v != null) {
            return ResponseEntity.ok(v);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    @Operation(summary = "Contar vehículos", description = "Devuelve el número total de vehículos registrados")
    @ApiResponse(responseCode = "200", description = "Número de vehículos contados",
                 content = @Content(schema = @Schema(implementation = Integer.class)))
    public ResponseEntity<Integer> getNumCoches() {
        Integer count = carService.getNumCoches();
        return ResponseEntity.ok(count);
    }
}
