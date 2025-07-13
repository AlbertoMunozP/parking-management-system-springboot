package es.uca.ParkingElSalvador.Estancias;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import es.uca.ParkingElSalvador.Bonos.Bono;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;


@Entity
public class Estancia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime llegada;
    private LocalDateTime salida;
    
    private boolean isPagadoEstandar;
    private double dineroPagado;
    private boolean tieneBono;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehiculo_id", nullable = true)
    @JsonBackReference
    private Vehiculo vehiculo;

    private String matricula;

    @OneToOne
    @JoinColumn(name = "bono_id", referencedColumnName = "id")
    @JsonIgnore
    private Bono bono;

    public Estancia(Vehiculo veh) {
        llegada = LocalDateTime.now();
        salida = null;
        vehiculo = veh;
        isPagadoEstandar = false;
        tieneBono = false; //Por defecto no tendrán
        dineroPagado = 0;
        bono = null;
        matricula = veh.getMatricula();
    }

    public Estancia() {
        llegada = LocalDateTime.now();
        salida = null;
        vehiculo = null;
        isPagadoEstandar = false;
        tieneBono = false; //Por defecto no tendrán
        dineroPagado = 0;
        bono = null;
        matricula = "";
    }

    public void setVehiculo(Vehiculo v){
        vehiculo = v;
    }

    // Getters y setters
    public String getMatriculaVehiculo() {
        return matricula;
    }

    public void setMatriculaVehiculo(String matricula) {
        this.matricula = matricula;
    }

    public String toString() {
        return "Estancia del vehiculo "+matricula+" Llego "+horaLlegada()+". Salio "+horaSalida();
    }

    public void termina(){
        salida = LocalDateTime.now();
    }

    public int duracion(){
        return (int)Duration.between(llegada,LocalDateTime.now()).toMinutes();
    }

    public void compraBono(){
        tieneBono = true;
    }

    public void pagarEstandar(){
        isPagadoEstandar = true;
    }

    

    public void setDineroPagado(double d){
        dineroPagado = d;
    }

    // Getter para la fecha de llegada
    public LocalDateTime getLlegada() {
        return llegada;
    }

    // Getter para la fecha de salida
    public LocalDateTime getSalida() {
        return salida;
    }

    

    // Getter para el estado de pago
    public boolean isPagadoEstandar() {
        return isPagadoEstandar;
    }

    // Getter para el dinero pagado
    public double getDineroPagado() {
        return dineroPagado;
    }

    // Getter para saber si tiene bono
    public boolean isTieneBono() {
        return tieneBono;
    }

    // Getter para el vehículo asociado
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    // Getter para el bono asociado
    public Bono getBono() {
        return bono;
    }

    public void setBono(Bono b) {
        bono = b;
    }



    public String horaLlegada(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Formatea el LocalDateTime en una cadena usando el formateador
        String fechaHoraString = llegada.format(formatter);
        return fechaHoraString;
    }

    public String horaSalida(){
        if(salida!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // Formatea el LocalDateTime en una cadena usando el formateador
            String fechaHoraString = salida.format(formatter);
            return fechaHoraString;
        }
        else
            return "--Vehiculo sigue en el parking--";
        
    }

  

    public boolean bonoValido(){
        if(tieneBono){
            if(LocalDateTime.now().isBefore(bono.getFinBono()))
                return true;
            else
                return false;
        }

        else
            return false;

    }

}
