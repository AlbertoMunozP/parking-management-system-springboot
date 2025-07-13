package es.uca.ParkingElSalvador.Pagos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import es.uca.ParkingElSalvador.Bonos.Bono;
import es.uca.ParkingElSalvador.Bonos.BonoAnual;
import es.uca.ParkingElSalvador.Bonos.BonoMensual;
import es.uca.ParkingElSalvador.Bonos.BonoService;
import es.uca.ParkingElSalvador.Bonos.BonoTrimestral;
import es.uca.ParkingElSalvador.Vehiculos.CarService;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

@Service
public class PagoBono {
    private Vehiculo vehiculo=null;
    private CarService v;
    private BonoService b;

    public PagoBono(CarService c, BonoService b) {
        v = c;
        this.b = b;
    }

    public void comprarBono(BigDecimal entregado, Bono bono, String mat, int duracion, char F) {
        vehiculo = v.getVehiculo(mat);
        v.delete(vehiculo.getMatricula());
        if (!vehiculo.getEstancia().isTieneBono()) {
            TipoPago p = null;
            if(F == 'E')
                p = new PagoEfectivo();
            else 
                p = new PagoTarjeta();
           
            double pago = bono.getPrecio().doubleValue() * duracion;
            p.procesarPago(entregado);
            vehiculo.getEstancia().setDineroPagado(pago);
            vehiculo.getEstancia().compraBono();
            LocalDateTime finBono = LocalDateTime.now();
            if (bono instanceof BonoMensual) {
                finBono = finBono.plusMonths(duracion);
            } else if (bono instanceof BonoTrimestral) {
                finBono = finBono.plusMonths(3 * duracion);
            } else if (bono instanceof BonoAnual) {
                finBono = finBono.plusYears(duracion);
            }
            b.save(bono);
            vehiculo.getEstancia().setBono(bono);
            vehiculo.getEstancia().getBono().setFinBono(finBono);
            v.save(vehiculo);
        }
    }

    public void comprarBonoMensual(BigDecimal entregado, String mat, int meses, char F) {
        vehiculo = v.getVehiculo(mat);
        BonoMensual bono = new BonoMensual(vehiculo.getEstancia());
        comprarBono(entregado,bono, mat, meses,F);
    }

    public void comprarBonoTrimestral(BigDecimal entregado, String mat, int trimestres, char F) {
        vehiculo = v.getVehiculo(mat);
        BonoTrimestral bono = new BonoTrimestral(vehiculo.getEstancia());
        comprarBono(entregado,bono, mat, trimestres,F);
    }

    public void comprarBonoAnual(BigDecimal entregado, String mat, int annos, char F) {
        vehiculo = v.getVehiculo(mat);
        BonoAnual bono = new BonoAnual(vehiculo.getEstancia());
        comprarBono(entregado,bono, mat, annos,F);
    }
}
