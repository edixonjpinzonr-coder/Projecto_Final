package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.Estado;

public class Casa extends Inmueble{

    private  int numPisos;

    public Casa(String codigo, String direccion, String ciudad,
                float area, float precio, Estado estado, Vendedor vendedor, int numPisos) {
        super(codigo, direccion, ciudad, area, precio, estado, vendedor);
        this.numPisos= numPisos;
    }
    public Casa(String codigo,String direccion, float area, float precio){
        super(codigo, direccion, area, precio);
        this.numPisos= numPisos;
    }

    public int getNumPisos() {
        return numPisos;
    }

    public void setNumPisos(int numPisos) {
        this.numPisos = numPisos;
    }

    @Override
    public String toString() {
        return "Casa{" +
                "numPisos=" + numPisos +
                '}';
    }
}
