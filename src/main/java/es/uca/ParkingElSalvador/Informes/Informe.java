package es.uca.ParkingElSalvador.Informes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.ParkingElSalvador.Estancias.EstanciasService;
import es.uca.ParkingElSalvador.Parking.ParkingService;

@Service
public class Informe {
    private ParkingService parking;
    private LocalDateTime creacion;

    @Autowired
    public Informe(ParkingService p){
        parking = p;
        creacion = LocalDateTime.now();
    }

    public Informe(){
        parking = null;
        creacion = LocalDateTime.now();
    }

    public void setParkingService(ParkingService p){
        parking = p;
    }

    // Metodos observadores proporcionados por el informe
    public String fechaInforme(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Formatea el LocalDateTime en una cadena usando el formateador
        String fechaHoraString = creacion.format(formatter);
        return fechaHoraString;
    }
    public LocalDateTime fechaInformeLT(){
        return creacion;
    }

    public String toString(){
        return "Informe creado el "+fechaInforme()+" de "+parking.getParking().getNombre();
    }

    public double ingresoDiario(){
        double ingresoDiario = 0;
        EstanciasService libro = parking.getLibro();
        int hoy = LocalDateTime.now().getDayOfYear();
        for(int i = 0; i < libro.numEstancias(); i++){
            if(libro.getAllEstancias().get(i).getLlegada().getDayOfYear() == hoy)
                ingresoDiario += parking.getLibro().getAllEstancias().get(i).getDineroPagado();
        }
        return ingresoDiario;
    }

    
    public double ingresoSemanal(){
        double ingresoSemanal = 0;
        EstanciasService libro = parking.getLibro();
        int semana = LocalDateTime.now().get(WeekFields.ISO.weekOfWeekBasedYear());
        for(int i = 0; i < libro.numEstancias(); i++){
            if(libro.getAllEstancias().get(i).getLlegada().get(WeekFields.ISO.weekOfWeekBasedYear()) == semana)
            ingresoSemanal += parking.getLibro().getAllEstancias().get(i).getDineroPagado();
        }
        return ingresoSemanal;
    }


    public double ingresoMensual(){
        double ingresoMensual = 0;
        EstanciasService libro = parking.getLibro();
        int mes = LocalDateTime.now().getMonthValue();
        for(int i = 0; i < libro.numEstancias(); i++){
            if(libro.getAllEstancias().get(i).getLlegada().getMonthValue() == mes)
            ingresoMensual += parking.getLibro().getAllEstancias().get(i).getDineroPagado();
        }
        return ingresoMensual;
    }

   
}
