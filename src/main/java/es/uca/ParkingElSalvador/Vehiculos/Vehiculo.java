package es.uca.ParkingElSalvador.Vehiculos;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import es.uca.ParkingElSalvador.Estancias.Estancia;


@Entity
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matricula;

    @OneToOne(mappedBy = "vehiculo", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Estancia estancia;

    public Vehiculo(String m){
        matricula = m;
        estancia = new Estancia(this);
        estancia.setVehiculo(this);
    }

    public Vehiculo(){
        matricula = "";
        estancia = new Estancia(this);
        estancia.setVehiculo(this); 
    }

    public void setMatricula(String m){
        matricula = m;
    }

    public void sale(){
        estancia.termina();
    }

    // Metodos observadores
    public String getMatricula(){
        return matricula;
    }

    public Estancia getEstancia(){
        return estancia;
    }

    public String toString(){
        return "Vehiculo con matricula " + matricula;
    }

}
