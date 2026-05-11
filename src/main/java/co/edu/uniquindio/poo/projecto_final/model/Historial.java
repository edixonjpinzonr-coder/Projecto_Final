package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.TipoInmueble;

import java.time.LocalDate;

public class Historial {

    private String ciudadBusqueda;
    private TipoInmueble tipoinmueblebusqueda;
    private float precioMinimo;
    private float precioMaximo;
    private float areaMinima;
    private LocalDate fechaBusqueda;

    public Historial(String ciudadBusqueda, TipoInmueble tipoinmueblebusqueda,
                     float precioMinimo, float precioMaximo, float areaMinima,
                     LocalDate fechaBusqueda) {
        this.ciudadBusqueda = ciudadBusqueda;
        this.tipoinmueblebusqueda = tipoinmueblebusqueda;
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.areaMinima = areaMinima;
        this.fechaBusqueda = fechaBusqueda;
    }

    public String getCiudadBusqueda() {
        return ciudadBusqueda;
    }

    public void setCiudadBusqueda(String ciudadBusqueda) {
        this.ciudadBusqueda = ciudadBusqueda;
    }

    public TipoInmueble getTipoinmueblebusqueda() {
        return tipoinmueblebusqueda;
    }

    public void setTipoinmueblebusqueda(TipoInmueble tipoinmueblebusqueda) {
        this.tipoinmueblebusqueda = tipoinmueblebusqueda;
    }

    public float getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(float precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public float getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(float precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public float getAreaMinima() {
        return areaMinima;
    }

    public void setAreaMinima(float areaMinima) {
        this.areaMinima = areaMinima;
    }

    public LocalDate getFechaBusqueda() {
        return fechaBusqueda;
    }

    public void setFechaBusqueda(LocalDate fechaBusqueda) {
        this.fechaBusqueda = fechaBusqueda;
    }

    @Override
    public String toString() {
        return "Historial{" +
                "ciudadBusqueda='" + ciudadBusqueda + '\'' +
                ", tipoinmueblebusqueda=" + tipoinmueblebusqueda +
                ", precioMinimo=" + precioMinimo +
                ", precioMaximo=" + precioMaximo +
                ", areaMinima=" + areaMinima +
                ", fechaBusqueda=" + fechaBusqueda +
                '}';
    }
}
