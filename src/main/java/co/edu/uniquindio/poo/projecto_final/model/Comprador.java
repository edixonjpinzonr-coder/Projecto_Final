package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.Estado;
import co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta;

import java.util.ArrayList;
import java.util.List;

public class Comprador extends Usuario{

    private List<Historial> listaHistorial;
    private List<Oferta> listaOfertas;

    public Comprador( String nombre, String identificacion,
                     String telefono, String correo) {
        super( nombre, identificacion, telefono, correo);
        this.listaHistorial = new ArrayList<>();
        this.listaOfertas = new ArrayList<>();
    }

    public List<Historial> getListaHistorial() {
        return listaHistorial;
    }

    public void setListaHistorial(ArrayList<Historial> listaHistorial) {
        this.listaHistorial = listaHistorial;
    }

    public List<Oferta> getListaOfertas() {
        return listaOfertas;
    }

    @Override
    public String toString() {
        return "Comprador{" +
                "listaHistorial=" + listaHistorial +
                ", listaOfertas=" + listaOfertas +
                '}';
    }


    @Override
    public void sumarPuntosReputacion(String opcion) {
        switch (opcion) {
            case "1": //Realizar oferta
                this.puntosReputacion += 5;
                System.out.println(this.puntosReputacion);
                break;
            case "2"://Comprar Inmueble
                this.puntosReputacion += 50;
                System.out.println(this.puntosReputacion);
                break;

            case "3"://completar Transaccion
                this.puntosReputacion += 100;
                System.out.println(this.puntosReputacion);
                break;

            default:
                System.out.println("Accion no valida");
                break;

        }

    }

    @Override
    public double calcularBeneficio() {
        double ahorroTotal = 0;
        for (Oferta oferta : listaOfertas) {
            if (oferta.getEstadoOferta() == EstadoOferta.ACEPTADA) {
                double precioOriginal = oferta.getInmueble().getPrecio();
                double precioPagado = oferta.getValorOferta();

                ahorroTotal +=(precioOriginal - precioPagado);
            }
        }
        return ahorroTotal;
    }
}
