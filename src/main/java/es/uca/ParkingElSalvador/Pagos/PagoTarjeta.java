package es.uca.ParkingElSalvador.Pagos;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class PagoTarjeta implements TipoPago{
    @Override
    public void procesarPago(BigDecimal d){
        try {
            TimeUnit.SECONDS.sleep(1); // Retardo de 1 segundo
        } catch (InterruptedException e) {
            // Manejo de la excepción si es interrumpido mientras está dormido
        }
        System.out.println("Pagando " + d.doubleValue() + " con tarjeta" );
    }

}
