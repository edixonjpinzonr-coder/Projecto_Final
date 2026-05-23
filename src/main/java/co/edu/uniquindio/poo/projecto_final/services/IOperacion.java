package co.edu.uniquindio.poo.projecto_final.services;

import co.edu.uniquindio.poo.projecto_final.model.Comprador;
import co.edu.uniquindio.poo.projecto_final.model.Inmueble;
import co.edu.uniquindio.poo.projecto_final.model.Oferta;
import co.edu.uniquindio.poo.projecto_final.model.Usuario;
import co.edu.uniquindio.poo.projecto_final.model.enums.TipoInmueble;
import co.edu.uniquindio.poo.projecto_final.model.enums.TipoOperacion;

import java.util.List;

public interface IOperacion {
    boolean registrarUsuario(Usuario usuario);
    Inmueble buscarinmueble(String Codigo);
    boolean publicarInmueble(Inmueble inmueble);
    boolean realizarOferta(Oferta oferta);
    void generarReporte();
    boolean registrarTransaccion(Oferta oferta, TipoOperacion tipoOperacion);
    List<Inmueble> buscarInmueblesConFiltro(Comprador comprador, String ciudad, TipoInmueble tipo, float precioMin, float precioMax, float areaMinima);
    List<Inmueble> recomendarInmuebles(Comprador comprador);
    boolean rechazarOferta(Oferta oferta);
    boolean actualizarPrecioInmueble(Inmueble inmueble, float nuevoPrecio);
}
