package es.uca.ParkingElSalvador.Parking;

public class Barrera {
    private boolean estado; // Tomaremos True como abierta y false como cerrada
  
    public Barrera(){
        estado = false;
    }

    // Metodos modificadores de la barrera
    public void abrirBarrera(){
        if(!estado)
            estado = true;
    }
    
    public void cerrarBarrera(){
        if(estado)
            estado = false;
    }

    // Observador de la barrera
    public boolean estaAbierta(){
        return estado;
    }
    
    public String toString() {
        String barrera = "";
        if(estado)
            barrera = "abierta";
        else
            barrera = "cerrado";
        return "La barrera se encuentra "+barrera;
    }

}
