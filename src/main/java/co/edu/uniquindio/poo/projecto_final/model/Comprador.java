package co.edu.uniquindio.poo.projecto_final.model;

import java.util.ArrayList;

public class Comprador extends Usuario{

    private ArrayList<Historial> listaHistorial;
    private ArrayList<Oferta> listaOfertas;

    public Comprador(String id, String nombre, String identificacion,
                     String telefono, String correo, ArrayList<Historial> listaHistorial,
                     ArrayList<Oferta> listaOfertas) {
        super(id, nombre, identificacion, telefono, correo);
        this.listaHistorial = listaHistorial;
        this.listaOfertas = listaOfertas;
    }

    public ArrayList<Historial> getListaHistorial() {
        return listaHistorial;
    }

    public void setListaHistorial(ArrayList<Historial> listaHistorial) {
        this.listaHistorial = listaHistorial;
    }

    public ArrayList<Oferta> getListaOfertas() {
        return listaOfertas;
    }

    public void setListaOfertas(ArrayList<Oferta> listaOfertas) {
        this.listaOfertas = listaOfertas;
    }

    @Override
    public String toString() {
        return "Comprador{" +
                "listaHistorial=" + listaHistorial +
                ", listaOfertas=" + listaOfertas +
                '}';
    }

}
