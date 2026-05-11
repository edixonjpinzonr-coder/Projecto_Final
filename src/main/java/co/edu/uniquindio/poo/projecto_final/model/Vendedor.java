package co.edu.uniquindio.poo.projecto_final.model;

import java.util.ArrayList;
import java.util.List;

public class  Vendedor extends Usuario {

    private ArrayList<Inmueble> listaInmuebles;

    public Vendedor(String id, String nombre, String identificacion,
                    String telefono, String correo) {
        super(id, nombre, identificacion, telefono, correo);

        this.listaInmuebles= new ArrayList<>();
    }

    public ArrayList<Inmueble> getListaInmuebles() {
        return listaInmuebles;
    }

    public void setListaInmuebles(ArrayList<Inmueble> listaInmuebles) {
        this.listaInmuebles = listaInmuebles;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "listaInmuebles=" + listaInmuebles +
                '}';
    }
}
