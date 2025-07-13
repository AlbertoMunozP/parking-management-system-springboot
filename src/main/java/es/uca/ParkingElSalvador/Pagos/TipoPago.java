package es.uca.ParkingElSalvador.Pagos;

import java.math.BigDecimal;

public interface TipoPago {
    void procesarPago(BigDecimal d);
}
