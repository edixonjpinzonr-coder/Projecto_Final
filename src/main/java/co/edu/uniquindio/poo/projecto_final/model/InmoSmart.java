package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.services.INotificar;
import co.edu.uniquindio.poo.projecto_final.services.IOperacion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class InmoSmart implements IOperacion {
    private String nombre;

    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Inmueble> listaInmuebles;
    private ArrayList<Publicacion> listaPublicaciones;
    private ArrayList<Transaccion> listaTransacciones;
    private ArrayList<Alerta> listaAlertas;
    private ArrayList<INotificar> listaNotificaciones;
    private ArrayList<Oferta> listaOfertas;

    public InmoSmart(String nombre) {
        this.nombre = nombre;
        this.listaUsuarios = new ArrayList<>();
        this.listaInmuebles = new ArrayList<>();
        this.listaPublicaciones = new ArrayList<>();
        this.listaTransacciones = new ArrayList<>();
        this.listaAlertas = new ArrayList<>();
        this.listaNotificaciones = new ArrayList<>();
        this.listaOfertas = new ArrayList<>();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public ArrayList<Inmueble> getListaInmuebles() {
        return listaInmuebles;
    }

    public void setListaInmuebles(ArrayList<Inmueble> listaInmuebles) {
        this.listaInmuebles = listaInmuebles;
    }

    public ArrayList<Publicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaPublicaciones(ArrayList<Publicacion> listaPublicaciones) {
        this.listaPublicaciones = listaPublicaciones;
    }

    public ArrayList<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }

    public void setListaTransacciones(ArrayList<Transaccion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }

    public ArrayList<Alerta> getListaAlertas() {
        return listaAlertas;
    }

    public void setListaAlertas(ArrayList<Alerta> listaAlertas) {
        this.listaAlertas = listaAlertas;
    }

    public ArrayList<INotificar> getListaNotificaciones() {
        return listaNotificaciones;
    }

    public void setListaNotificaciones(ArrayList<INotificar> listaNotificaciones) {
        this.listaNotificaciones = listaNotificaciones;
    }

    public ArrayList<Oferta> getListaOfertas() {
        return listaOfertas;
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
                ", listaOfertas=" + listaOfertas +
                '}';
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        boolean bandera = false;
        Usuario usuarioEncontrado = buscarUsuario(usuario.getIdentificacion());

        if(usuarioEncontrado == null){
            listaUsuarios.add(usuario);
            bandera = true;
        }
        return bandera;
    }

    public Usuario buscarUsuario(String id) {
        return listaUsuarios.stream()
                .filter(u -> u.getIdentificacion().equals(id))
                .findFirst()
                .orElse(null);
        }

    @Override
    public Inmueble buscarinmueble(String codigo) {
        for(Inmueble inmueble : listaInmuebles){
            if(inmueble.getCodigo().equals(codigo)){
                return inmueble;
            }
        }
        return null;
    }


    @Override
    public boolean publicarInmueble(Inmueble inmueble) {
        boolean bandera = false;
        Inmueble inmueble1 = buscarinmueble(inmueble.getCodigo());
        if(inmueble1 == null){
            listaInmuebles.add(inmueble);
            bandera = true;
        }
        return bandera;
    }

    @Override
    public boolean realizarOferta(Oferta oferta) {
        boolean bandera = false;
        if(oferta==null || oferta.getComprador()==null || oferta.getInmueble()==null){
            return false;
        }
        if(oferta.getValorOferta()<=0){
           System.out.println("Error, valor invalido");
           return bandera;
        }
        listaOfertas.add(oferta);

        oferta.getComprador().getListaOfertas().remove(oferta);
        oferta.getInmueble().getListaOfertas().add(oferta);

        bandera = true;
        System.out.println("Oferta agregada correctamente para el inmueble " +
                oferta.getInmueble().getCodigo());
        return bandera;

    }

    @Override
    public void generarReporte() {
        System.out.println("Reporte de InmoSmart "+"\n"+
                "Total de Usuarios: "+ listaUsuarios.size()+"\n"+
                "Total de Inmuebles: "+ listaInmuebles.size()+"\n"+
                "Total de ofertas realizadas "+ listaOfertas.size()+"\n");


    }

}
