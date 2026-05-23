package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.Estado;

public class Terreno extends Inmueble{

    private int metrosCuadrados;

    public Terreno(String codigo, String direccion, String ciudad, float area,
                   float precio, Estado estado, Vendedor vendedor, int metrosCuadrados) {
        super(codigo, direccion, ciudad, area, precio, estado, vendedor);

        this.metrosCuadrados= metrosCuadrados;
    }

    public Terreno(String codigo,String direccion, String ciudad, float area, float precio){
        super(codigo, direccion, ciudad, area, precio);
        this.metrosCuadrados = metrosCuadrados;
    }

    public int getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(int metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    @Override
    public String toString() {
        return "Terreno{" +
                "metrosCuadrados=" + metrosCuadrados +
                '}';
    }
}
