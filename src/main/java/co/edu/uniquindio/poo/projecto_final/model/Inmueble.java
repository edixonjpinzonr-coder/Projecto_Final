package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.Estado;

import java.util.ArrayList;
import java.util.List;

public abstract class Inmueble {

    private String codigo;
    private String direccion;
    private String ciudad;
    private float area;
    private float precio;
    private Estado estado;
    private Vendedor vendedor;
    private List<Oferta> listaOfertas;

    public Inmueble(String codigo, String direccion, String ciudad,
                    float area, float precio, Estado estado, Vendedor vendedor) {
        this.codigo = codigo;
        this.direccion = direccion;
        this.ciudad=ciudad;
        this.area = area;
        this.precio = precio;
        this.estado = estado;
        this.vendedor=vendedor;
        this.listaOfertas=new ArrayList<>();
    }
    public Inmueble(String codigo, String direccion, String ciudad, float area, float precio){
        this.codigo = codigo;
        this.direccion = direccion;
        this.ciudad=ciudad;
        this.area = area;
        this.precio = precio;
        this.estado = Estado.DISPONIBLE;
        this.vendedor=vendedor;
        this.listaOfertas=new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Vendedor getVendedor() {return vendedor;}

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public List<Oferta> getListaOfertas() {return listaOfertas;}

    @Override
    public String toString() {
        return "Inmueble{" +
                "codigo='" + codigo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", area='" + area + '\'' +
                ", precio=" + precio +
                ", estado=" + estado +
                '}';
    }

}
