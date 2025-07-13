package es.uca.ParkingElSalvador.Parking;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.ParkingElSalvador.Bonos.Bono;
import es.uca.ParkingElSalvador.Bonos.BonoAnual;
import es.uca.ParkingElSalvador.Bonos.BonoMensual;
import es.uca.ParkingElSalvador.Bonos.BonoRepository;
import es.uca.ParkingElSalvador.Bonos.BonoService;
import es.uca.ParkingElSalvador.Bonos.BonoTrimestral;
import es.uca.ParkingElSalvador.Estancias.Estancia;
import es.uca.ParkingElSalvador.Estancias.EstanciasRepository;
import es.uca.ParkingElSalvador.Estancias.EstanciasService;
import es.uca.ParkingElSalvador.Pagos.Cajero;
import es.uca.ParkingElSalvador.Pagos.Estandar;
import es.uca.ParkingElSalvador.Pagos.PagoBono;
import es.uca.ParkingElSalvador.Pagos.PagoEstandar;
import es.uca.ParkingElSalvador.Vehiculos.CarRepository;
import es.uca.ParkingElSalvador.Vehiculos.CarService;
import es.uca.ParkingElSalvador.Vehiculos.Vehiculo;

@Service
public class ParkingService {
    private Parking p;
    private Barrera barrera;
    private QRservice qr;
    private Estandar tarifa;
    private CarService vehiculos;
    private EstanciasService libro;
    private BonoService bonos;
    private Cajero caja;
    @Autowired
    private ParkingRepositoryJPA park;
    @Autowired
    public ParkingService(Parking p, CarRepository c, EstanciasRepository e, BonoRepository b){
        this.p = p;
        barrera = new Barrera();
        qr = new QRservice();
        tarifa = new Estandar(); 
        vehiculos = new CarService(c);
        libro = new EstanciasService(e);
        bonos = new BonoService(b);
        caja = new Cajero();
    }
    
    public void precioEstandar(long min){
        tarifa.ponerPrecioAlMinuto(min);;
    }

    public void ponerPrecioBonos(double mes, double tri, double anno){
        BonoMensual.setPrecio(new BigDecimal(mes));
        BonoTrimestral.setPrecio(new BigDecimal(tri));
        BonoAnual.setPrecio(new BigDecimal(anno));
    }

    public void setDirectorioQR(String d){
        qr.setDirectorio(d);
    }

    // Getters
    public Parking getParking() {
        return p;
    }
    
    public Barrera getBarrera() {
        return barrera;
    }
    
    public QRservice getQr() {
        return qr;
    }
    
    public Estandar getTarifa() {
        return tarifa;
    }
    
    public CarService getVehiculos() {
        return vehiculos;
    }
    
    public EstanciasService getLibro() {
        return libro;
    }
    
    public BonoService getBonos() {
        return bonos;
    }
    
    public Cajero getCaja() {
        return caja;
    }
    
    // Setters
    public void setP(Parking p) {
        this.p = p;
        park.save(p);
    }
    
    public void setBarrera(Barrera barrera) {
        this.barrera = barrera;
    }
    
    public void setQr(QRservice qr) {
        this.qr = qr;
    }
    
    public void setTarifa(Estandar tarifa) {
        this.tarifa = tarifa;
    }
    
    public void setVehiculos(CarService vehiculos) {
        this.vehiculos = vehiculos;
    }
    
    public void setLibro(EstanciasService libro) {
        this.libro = libro;
    }
    
    public void setBonos(BonoService bonos) {
        this.bonos = bonos;
    }
    
    public void setCaja(Cajero caja) {
        this.caja = caja;
    }


    public void entrada() throws Exception {
        // Se lee la matricula del qr generado cuando llega el ultimo coche
        String matricula = qr.leerCodigoQR();
        Vehiculo vehiculo = new Vehiculo(matricula);
        List<Bono> b = bonos.getBonos(matricula);
        for(Bono bono : b){
            if(bono.getTipoBono().equals("Mensual") && bono.getFinBono().isAfter(vehiculo.getEstancia().getLlegada())){
                vehiculo.getEstancia().compraBono();
                vehiculo.getEstancia().setBono(bono);
            }
            else if(bono.getTipoBono().equals("Trimestral") && bono.getFinBono().isAfter(vehiculo.getEstancia().getLlegada())){
                vehiculo.getEstancia().compraBono();
                vehiculo.getEstancia().setBono(bono);
            }
            else if(bono.getTipoBono().equals("Anual") && bono.getFinBono().isAfter(vehiculo.getEstancia().getLlegada())){
                vehiculo.getEstancia().compraBono();
                vehiculo.getEstancia().setBono(bono);
            }
        }
        // Comprobamos que haya espacio
        if(p.getPlazasDisponibles() > 0){
            // Abrimos la barrera en caso de estar cerrada
            if(!barrera.estaAbierta())
                barrera.abrirBarrera();
            // Generamos el ticket y le damos acceso
            qr.generarCodigoQR(matricula);
            vehiculos.save(vehiculo);
            barrera.cerrarBarrera();
            p.decPlazasDisponibles();
            p.incPlazasOcupadas();
        }
    }

    public void salida() throws Exception{
        String matricula = qr.leerCodigoQR();
        Vehiculo vehiculo = vehiculos.getVehiculo(matricula);
        if(vehiculo.getEstancia().isPagadoEstandar() || (vehiculo.getEstancia().isTieneBono() && vehiculo.getEstancia().bonoValido())){
            vehiculo.sale();
            barrera.abrirBarrera();
            // Sale del parking
            barrera.cerrarBarrera();
            Estancia estancia = vehiculo.getEstancia();
            if(estancia!=null){
                estancia.setMatriculaVehiculo(matricula);
                estancia.setVehiculo(null);
                estancia.setBono(null);
                libro.save(vehiculo);
            }
            vehiculos.delete(vehiculo.getMatricula());
            libro.save(vehiculo);
            p.incPlazasDisponibles();
            p.decPlazasOcupadas();
        }
    }

    // En caso de no funcionar el lector de qr

    public void entrada(String matricula) {
        Vehiculo vehiculo = new Vehiculo(matricula);
        List<Bono> b = bonos.getBonos(matricula);
        for(Bono bono : b){
            if(bono.getTipoBono().equals("Mensual") && bono.getFinBono().isAfter(vehiculo.getEstancia().getLlegada())){
                vehiculo.getEstancia().compraBono();
                vehiculo.getEstancia().setBono(bono);
            }
            else if(bono.getTipoBono().equals("Trimestral") && bono.getFinBono().isAfter(vehiculo.getEstancia().getLlegada())){
                vehiculo.getEstancia().compraBono();
                vehiculo.getEstancia().setBono(bono);
            }
            else if(bono.getTipoBono().equals("Anual") && bono.getFinBono().isAfter(vehiculo.getEstancia().getLlegada())){
                vehiculo.getEstancia().compraBono();
                vehiculo.getEstancia().setBono(bono);
            }
        }
        // Comprobamos que haya espacio
        if(p.getPlazasDisponibles() > 0){
            // Abrimos la barrera en caso de estar cerrada
            if(!barrera.estaAbierta())
                barrera.abrirBarrera();
            // Generamos el ticket y le damos acceso
            qr.generarCodigoQR(matricula);
            vehiculos.save(vehiculo);
            libro.save(vehiculo);
            barrera.cerrarBarrera();
            p.decPlazasDisponibles();
            p.incPlazasOcupadas();

        }
    }

    public void salida(String matricula){
        Vehiculo vehiculo = vehiculos.getVehiculo(matricula);
        if(vehiculo.getEstancia().isPagadoEstandar() || (vehiculo.getEstancia().isTieneBono() && vehiculo.getEstancia().bonoValido())){
            vehiculo.sale();
            barrera.abrirBarrera();
            // Sale del parking
            barrera.cerrarBarrera();
            Estancia estancia = vehiculo.getEstancia();
            if(estancia!=null){
                estancia.setMatriculaVehiculo(matricula);
                estancia.setVehiculo(null);
                estancia.setBono(null);
                libro.save(vehiculo);
            }
            vehiculos.delete(vehiculo.getMatricula());
            p.incPlazasDisponibles();
            p.decPlazasOcupadas();

        }
    }

    // Operaciones de pago de tarifa estándar y bonos
    public void vehiculoPagaEstandar(BigDecimal entregado, String mat, char F) {
        PagoEstandar pEstandar = new PagoEstandar(vehiculos);
        pEstandar.pagar(entregado,mat, F,tarifa.precioMinuto());
    }

    public void vehiculoPagaBonoMensual(BigDecimal entregado, int nMeses, String mat, char F){
        PagoBono pBono = new PagoBono(vehiculos,bonos);
        pBono.comprarBonoMensual(entregado,mat,nMeses,F);
    }

    public void vehiculoPagaBonoTrimestral(BigDecimal entregado, int nTrimestres, String mat, char F){
        PagoBono pBono = new PagoBono(vehiculos,bonos);
        pBono.comprarBonoTrimestral(entregado,mat,nTrimestres,F);
    }
    
    public void vehiculoPagaBonoAnual(BigDecimal entregado,int nAnnos, String mat, char F){
        PagoBono pBono = new PagoBono(vehiculos,bonos);
        pBono.comprarBonoAnual(entregado,mat,nAnnos,F);
    }
}
