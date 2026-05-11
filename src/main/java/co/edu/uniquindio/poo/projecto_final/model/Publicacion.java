package co.edu.uniquindio.poo.projecto_final.model;

import java.time.LocalDate;

public class Publicacion {
    private String codigo;
    private String descripcion;
    private LocalDate fechaPublicacion;
    private Inmueble inmueble;

    private InmoSmart ownedByInmoSmart;

    public Publicacion(String codigo, String descripcion, LocalDate fechaPublicacion,
                       Inmueble inmueble, InmoSmart ownedByInmoSmart) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.inmueble = inmueble;
        this.ownedByInmoSmart = ownedByInmoSmart;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public InmoSmart getOwnedByInmoSmart() {
        return ownedByInmoSmart;
    }

    public void setOwnedByInmoSmart(InmoSmart ownedByInmoSmart) {
        this.ownedByInmoSmart = ownedByInmoSmart;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaPublicacion=" + fechaPublicacion +
                ", inmueble=" + inmueble +
                ", ownedByInmoSmart=" + ownedByInmoSmart +
                '}';
    }
}
