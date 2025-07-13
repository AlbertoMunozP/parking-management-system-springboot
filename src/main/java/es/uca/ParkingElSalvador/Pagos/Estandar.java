package es.uca.ParkingElSalvador.Pagos;

public class Estandar {
    private long pMinuto;
    
    public Estandar(){
        pMinuto = 0;
    }

    public void ponerPrecioAlMinuto(long p){
        pMinuto = p;
    }

    // Método observador
    public long precioMinuto(){
        return pMinuto;
    }

    public String toString(){
        return "El precio del minuto de la tarifa estandar actual es "+pMinuto;
    }
}
