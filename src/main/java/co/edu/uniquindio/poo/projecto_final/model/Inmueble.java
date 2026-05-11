package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.Estado;

public abstract class Inmueble {

    private String codigo;
    private String direccion;
    private String ciudad;
    private String area;
    private float precio;
    private Estado estado;
    private Vendedor vendedor;

    public Inmueble(String codigo, String direccion, String ciudad,
                    String area, float precio, Estado estado, Vendedor vendedor) {
        this.codigo = codigo;
        this.direccion = direccion;
        this.ciudad=ciudad;
        this.area = area;
        this.precio = precio;
        this.estado = estado;
        this.vendedor=vendedor;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
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
