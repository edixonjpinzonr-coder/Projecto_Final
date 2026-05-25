package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.Estado;

import java.util.ArrayList;
import java.util.List;

public class  Vendedor extends Usuario {

    private List<Inmueble> listaInmuebles;

    public Vendedor(String nombre, String identificacion,
                    String telefono, String correo) {
        super(nombre, identificacion, telefono, correo);

        this.listaInmuebles= new ArrayList<>();
    }

    public List<Inmueble> getListaInmuebles() {
        return listaInmuebles;
    }

    public void setListaInmuebles(List<Inmueble> listaInmuebles) {
        this.listaInmuebles = listaInmuebles;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "listaInmuebles=" + listaInmuebles +
                '}';
    }

    @Override
    public void sumarPuntosReputacion(String opcion) {
        switch (opcion) {
            case "1": //Publicar Inmueble
                this.puntosReputacion += 10;
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
        double totalventas = 0;
        for (Inmueble inmueble : listaInmuebles) {
            if(inmueble.getEstado() == Estado.VENDIDO){
                totalventas += inmueble.getPrecio();
            }
        }
        return totalventas;
    }
}
