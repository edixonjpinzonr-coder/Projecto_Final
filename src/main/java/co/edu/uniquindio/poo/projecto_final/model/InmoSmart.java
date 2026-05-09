package co.edu.uniquindio.poo.projecto_final.model;

import java.util.List;

public class InmoSmart {
    private String nombre;

    private List<IOperaciones> listaOperaciones;
    private List<Usuario> listaUsuarios;
    private List<Inmueble> listaInmuebles;
    private List<Publicacion> listaPublicaciones;
    private List<Transacion> listaTransacciones;
    private List<Alerta> listaAlertas;
    private List<INotificacar> listaNotificaciones;

    public InmoSmart(String nombre) {
        this.nombre = nombre;
        this.listaOperaciones = listaOperaciones;
        this.listaUsuarios = listaUsuarios;
        this.listaInmuebles = listaInmuebles;
        this.listaPublicaciones = listaPublicaciones;
        this.listaTransacciones = listaTransacciones;
        this.listaAlertas = listaAlertas;
        this.listaNotificaciones = listaNotificaciones;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<IOperaciones> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(List<IOperaciones> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<inmueble> getListaInmuebles() {
        return listaInmuebles;
    }

    public void setListaInmuebles(List<inmueble> listaInmuebles) {
        this.listaInmuebles = listaInmuebles;
    }

    public List<Publicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaPublicaciones(List<Publicacion> listaPublicaciones) {
        this.listaPublicaciones = listaPublicaciones;
    }

    public List<Transacion> getListaTransacciones() {
        return listaTransacciones;
    }

    public void setListaTransacciones(List<Transacion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }

    public List<Alerta> getListaAlertas() {
        return listaAlertas;
    }

    public void setListaAlertas(List<Alerta> listaAlertas) {
        this.listaAlertas = listaAlertas;
    }

    public List<INotificacar> getListaNotificaciones() {
        return listaNotificaciones;
    }

    public void setListaNotificaciones(List<INotificacar> listaNotificaciones) {
        this.listaNotificaciones = listaNotificaciones;
    }

    @Override
    public String toString() {
        return "InmoSmart{" +
                "nombre='" + nombre + '\'' +
                ", listaOperaciones=" + listaOperaciones +
                ", listaUsuarios=" + listaUsuarios +
                ", listaInmuebles=" + listaInmuebles +
                ", listaPublicaciones=" + listaPublicaciones +
                ", listaTransacciones=" + listaTransacciones +
                ", listaAlertas=" + listaAlertas +
                ", listaNotificaciones=" + listaNotificaciones +
                '}';
    }


}
