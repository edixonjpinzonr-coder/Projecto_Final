package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.services.INotificar;
import co.edu.uniquindio.poo.projecto_final.services.IOperacion;

import java.util.List;

public class InmoSmart implements IOperacion {
    private String nombre;

    private List<Usuario> listaUsuarios;
    private List<Inmueble> listaInmuebles;
    private List<Publicacion> listaPublicaciones;
    private List<Transaccion> listaTransacciones;
    private List<Alerta> listaAlertas;
    private List<INotificar> listaNotificaciones;

    public InmoSmart(String nombre) {
        this.nombre = nombre;
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

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Inmueble> getListaInmuebles() {
        return listaInmuebles;
    }

    public void setListaInmuebles(List<Inmueble> listaInmuebles) {
        this.listaInmuebles = listaInmuebles;
    }

    public List<Publicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaPublicaciones(List<Publicacion> listaPublicaciones) {
        this.listaPublicaciones = listaPublicaciones;
    }

    public List<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }

    public void setListaTransacciones(List<Transaccion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }

    public List<Alerta> getListaAlertas() {
        return listaAlertas;
    }

    public void setListaAlertas(List<Alerta> listaAlertas) {
        this.listaAlertas = listaAlertas;
    }

    public List<INotificar> getListaNotificaciones() {
        return listaNotificaciones;
    }

    public void setListaNotificaciones(List<INotificar> listaNotificaciones) {
        this.listaNotificaciones = listaNotificaciones;
    }

    @Override
    public String toString() {
        return "InmoSmart{" +
                "nombre='" + nombre + '\'' +
                ", listaUsuarios=" + listaUsuarios +
                ", listaInmuebles=" + listaInmuebles +
                ", listaPublicaciones=" + listaPublicaciones +
                ", listaTransacciones=" + listaTransacciones +
                ", listaAlertas=" + listaAlertas +
                ", listaNotificaciones=" + listaNotificaciones +
                '}';
    }


    @Override
    public void buscarinmueble() {

    }

    @Override
    public void PublicarInmueble() {

    }

    @Override
    public void realizarOferta() {

    }

    @Override
    public void generarReporte() {

    }
}
