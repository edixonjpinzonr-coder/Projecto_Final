package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta;

import java.time.LocalDate;

public class Oferta {
    private String codigo;
    private Comprador comprador;
    private Inmueble inmueble;
    private float valorOferta;
    private LocalDate fechaoferta;
    private EstadoOferta estadoOferta;

    public Oferta(String codigo, Comprador comprador, Inmueble inmueble, float valorOferta,
                  LocalDate fechaoferta, EstadoOferta estadoOferta) {
        this.codigo = codigo;
        this.comprador = comprador;
        this.inmueble = inmueble;
        this.valorOferta = valorOferta;
        this.fechaoferta = fechaoferta;
        this.estadoOferta = estadoOferta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public float getValorOferta() {
        return valorOferta;
    }

    public void setValorOferta(float valorOferta) {
        this.valorOferta = valorOferta;
    }

    public LocalDate getFechaoferta() {
        return fechaoferta;
    }

    public void setFechaoferta(LocalDate fechaoferta) {
        this.fechaoferta = fechaoferta;
    }

    public EstadoOferta getEstadoOferta() {
        return estadoOferta;
    }

    public void setEstadoOferta(EstadoOferta estadoOferta) {
        this.estadoOferta = estadoOferta;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "codigo='" + codigo + '\'' +
                ", comprador=" + comprador +
                ", inmueble=" + inmueble +
                ", valorOferta=" + valorOferta +
                ", fechaoferta=" + fechaoferta +
                ", estadoOferta=" + estadoOferta +
                '}';
    }

}
