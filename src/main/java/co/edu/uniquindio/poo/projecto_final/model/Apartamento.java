package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.Estado;

public class Apartamento extends Inmueble{

    private int numHabitaciones;
    public Apartamento(String codigo, String direccion, String ciudad, float area,
                       float precio, Estado estado, Vendedor vendedor, int numHabitaciones) {
        super(codigo, direccion, ciudad, area, precio, estado, vendedor);

        this.numHabitaciones= numHabitaciones;
    }

    public Apartamento(String codigo, String direccion, float area, float precio) {
        super(codigo, direccion, area, precio);
        this.numHabitaciones = numHabitaciones;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    @Override
    public String toString() {
        return "Apartamento{" +
                "numHabitaciones=" + numHabitaciones +
                '}';
    }
}
