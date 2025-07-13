package es.uca.ParkingElSalvador.Bonos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import es.uca.ParkingElSalvador.Estancias.Estancia;


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BonoMensual.class, name = "Mensual"),
    @JsonSubTypes.Type(value = BonoTrimestral.class, name = "Trimestral"),
    @JsonSubTypes.Type(value = BonoAnual.class, name = "Anual")
})
@Entity
public abstract class Bono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime finBono;
    private String matricula;
    @OneToOne(mappedBy = "bono")
    @JsonIgnore
    private Estancia es;

    public Bono(Estancia estancia){
        if(estancia!=null){
            es = estancia;
            matricula = estancia.getVehiculo().getMatricula();    
        }
        else{
            es = null;
            matricula = "";
        }
    }

    public abstract BigDecimal getPrecio();

    public Estancia getEstancia() {
        return es;
    }

    public void setFinBono(LocalDateTime f){
        finBono = f;
    }

    // Getter para la fecha de fin de bono
    public LocalDateTime getFinBono() {
        return finBono;
    }

    public void setEstancia(Estancia e) {
        es = e;
    }

    public String getMatriculaCoche(){
        return matricula;
    }

    public void setMatricula(String s){
        matricula = s;
    }

    public abstract String getTipoBono();

    @Override
    public String toString() {
        return "Bono del vehiculo: " + es.getVehiculo().getMatricula();
    }
}
