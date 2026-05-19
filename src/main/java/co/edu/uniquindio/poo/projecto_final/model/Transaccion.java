package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.TipoOperacion;

import java.time.LocalDate;

public class Transaccion {

    private String codigoTransaccion;
    private Oferta oferta;
    private float valorFinal;
    private TipoOperacion tipoOperacion;
    private LocalDate fecha;

    public Transaccion(String codigoTransaccion, Oferta oferta, float valorFinal,
                       TipoOperacion tipoOperacion) {
        this.codigoTransaccion = codigoTransaccion;
        this.oferta = oferta;
        this.valorFinal = valorFinal;
        this.tipoOperacion = tipoOperacion;
        this.fecha = LocalDate.now();
    }

    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public float getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(float valorFinal) {
        this.valorFinal = valorFinal;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "codigoTransaccion='" + codigoTransaccion + '\'' +
                ", oferta=" + oferta +
                ", valorFinal=" + valorFinal +
                ", tipoOperacion=" + tipoOperacion +
                ", fecha=" + fecha +
                '}';
    }
}
