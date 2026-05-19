package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.Estado;

public class Local extends Inmueble{
    private int numEntradas;

    public Local(String codigo, String direccion, String ciudad,
                 float area, float precio, Estado estado, Vendedor vendedor, int numEntradas) {
        super(codigo, direccion, ciudad, area, precio, estado, vendedor);

        this.numEntradas= numEntradas;
    }

    public int getNumEntradas() {
        return numEntradas;
    }

    public void setNumEntradas(int numEntradas) {
        this.numEntradas = numEntradas;
    }

    @Override
    public String toString() {
        return "Local{" +
                "numEntradas=" + numEntradas +
                '}';
    }
}
