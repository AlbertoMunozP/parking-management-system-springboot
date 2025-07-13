package es.uca.ParkingElSalvador.Parking;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion_postal;
    private int capacidadTotal;
    private int plazasDisponibles;
    private int plazasOcupadas;

    public Parking(){
        nombre = "";
        direccion_postal = "";
        capacidadTotal = 0;
        plazasDisponibles = 0; 
        plazasOcupadas = 0;
    }

    // Getters y setters 
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nuevo_nombre){
        nombre = nuevo_nombre;
    }

    public String getDireccionPostal() {
        return direccion_postal;
    }

    public void setDireccionPostal(String d){
        direccion_postal = d;
    }

    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    public void setCapacidadTotal(int c){
        capacidadTotal = c;
        plazasDisponibles = c;
    }

    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }

    public int getPlazasOcupadas() {
        return plazasOcupadas;
    }

    public void decPlazasDisponibles() {
        plazasDisponibles--;
    }
    
    public void decPlazasOcupadas() {
        plazasOcupadas--;
    }

    public void incPlazasDisponibles() {
        plazasDisponibles++;
    }
    
    public void incPlazasOcupadas() {
        plazasOcupadas++;
    }


    public void setPlazasDisponibles(int nuevasPlazasDisponibles) {
        this.plazasDisponibles = nuevasPlazasDisponibles;
    }
    
    public void setPlazasOcupadas(int nuevasPlazasOcupadas) {
        this.plazasOcupadas = nuevasPlazasOcupadas;
    }
    

    public String toString(){
        return nombre+" con direccion postal "+direccion_postal+" tiene una capacidad de "+capacidadTotal;
    }

}
