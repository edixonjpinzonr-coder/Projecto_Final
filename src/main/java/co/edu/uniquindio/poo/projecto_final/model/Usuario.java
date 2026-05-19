package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.Rango;

public abstract class Usuario {
    private String nombre;
    private String identificacion;
    private String telefono;
    private String correo;
    protected int puntosReputacion;

    public Usuario(String nombre, String identificacion,
                   String telefono, String correo) {

        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
        this.puntosReputacion = 0;

    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getPuntosReputacion() {
        return puntosReputacion;
    }

    public Rango getRango() {
        return obtenerRango();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                ", nombre='" + nombre + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", puntosReputacion=" + puntosReputacion +
                '}';
    }

    public abstract void sumarPuntosReputacion(String opcion);

    public abstract double calcularBeneficio();

    public Rango obtenerRango(){
        if(puntosReputacion>0 && puntosReputacion<=100){return Rango.PRINCIPIANTE;}
        if(puntosReputacion>100 && puntosReputacion<=500){return Rango.INVERSIONISTA;}
        if(puntosReputacion>500 && puntosReputacion<=2000){return Rango.EXPERTO_INMOBILIARIO;}
        if(puntosReputacion>2000){return Rango.MAGNATE_INMOBILIARIO;}
        return Rango.PRINCIPIANTE;
    }
}
