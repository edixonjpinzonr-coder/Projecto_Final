package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta;
import co.edu.uniquindio.poo.projecto_final.model.enums.TipoOperacion;

import java.time.LocalDate;

public class Oferta {
    private String codigo;
    private Comprador comprador;
    private Inmueble inmueble;
    private float valorOferta;
    private LocalDate fechaoferta;
    private double valorContrapropuesta;
    private EstadoOferta estadoOferta;
    private TipoOperacion tipoOperacion;
    private InmoSmart ownedByInmoSmart;

    public Oferta(String codigo, Comprador comprador, Inmueble inmueble, float valorOferta,
                  LocalDate fechaoferta, EstadoOferta estadoOferta, InmoSmart ownedByInmoSmart) {
        this.codigo = codigo;
        this.comprador = comprador;
        this.inmueble = inmueble;
        this.valorOferta = valorOferta;
        this.fechaoferta = fechaoferta;
        this.valorContrapropuesta = 0;
        this.tipoOperacion = tipoOperacion;
        this.estadoOferta = estadoOferta;
        this.ownedByInmoSmart = ownedByInmoSmart;
    }

    public TipoOperacion getTipoOperacion() { return tipoOperacion; }
    public void setTipoOperacion(TipoOperacion tipoTransaccion) { this.tipoOperacion = tipoTransaccion; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Comprador getComprador() { return comprador; }
    public void setComprador(Comprador comprador) { this.comprador = comprador; }

    public Inmueble getInmueble() { return inmueble; }
    public void setInmueble(Inmueble inmueble) { this.inmueble = inmueble; }

    public float getValorOferta() { return valorOferta; }
    public void setValorOferta(float valorOferta) { this.valorOferta = valorOferta; }

    public LocalDate getFechaoferta() { return fechaoferta; }
    public void setFechaoferta(LocalDate fechaoferta) { this.fechaoferta = fechaoferta; }

    public double getValorContrapropuesta() { return valorContrapropuesta; }

    public void setValorContrapropuesta(double valorContrapropuesta) {
        this.valorContrapropuesta = valorContrapropuesta;
    }

    public EstadoOferta getEstadoOferta() { return estadoOferta; }
    public void setEstadoOferta(EstadoOferta estadoOferta) { this.estadoOferta = estadoOferta; }

    public InmoSmart getOwnedByInmoSmart() { return ownedByInmoSmart; }
    public void setOwnedByInmoSmart(InmoSmart ownedByInmoSmart) { this.ownedByInmoSmart = ownedByInmoSmart; }

    @Override
    public String toString() {
        return "Oferta{" + "codigo='" + codigo + '\'' + ", valorOferta=" + valorOferta +
                ", estadoOferta=" + estadoOferta + '}';
    }

}
