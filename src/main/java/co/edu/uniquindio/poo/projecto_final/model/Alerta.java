package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.TipoAlerta;

import java.time.LocalDate;

public class Alerta {
    private TipoAlerta tipoAlerta;
    private LocalDate fecha;
    private Inmueble inmuebleAsociado;

    private InmoSmart ownedByInmoSmart;

    public Alerta(TipoAlerta tipoAlerta, Inmueble inmuebleAsociado, InmoSmart ownedByInmoSmart) {
        this.tipoAlerta = tipoAlerta;
        this.fecha = LocalDate.now();
        this.inmuebleAsociado = inmuebleAsociado;
        this.ownedByInmoSmart = ownedByInmoSmart;
    }

    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Inmueble getInmuebleAsociado() {
        return inmuebleAsociado;
    }

    public void setInmuebleAsociado(Inmueble inmuebleAsociado) {
        this.inmuebleAsociado = inmuebleAsociado;
    }

    public InmoSmart getOwnedByInmoSmart() {
        return ownedByInmoSmart;
    }

    public void setOwnedByInmoSmart(InmoSmart ownedByInmoSmart) {
        this.ownedByInmoSmart = ownedByInmoSmart;
    }

    @Override
    public String toString() {
        return "Alerta{" +
                "tipoAlerta=" + tipoAlerta +
                ", fecha=" + fecha +
                ", inmuebleAsociado=" + inmuebleAsociado +
                ", ownedByInmoSmart=" + ownedByInmoSmart +
                '}';
    }
}
