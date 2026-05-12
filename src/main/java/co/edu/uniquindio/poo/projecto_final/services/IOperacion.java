package co.edu.uniquindio.poo.projecto_final.services;

import co.edu.uniquindio.poo.projecto_final.model.Inmueble;
import co.edu.uniquindio.poo.projecto_final.model.Oferta;
import co.edu.uniquindio.poo.projecto_final.model.Usuario;

import java.util.ArrayList;

public interface IOperacion {
    boolean registrarUsuario(Usuario usuario);
    Inmueble buscarinmueble(String Codigo);
    boolean publicarInmueble(Inmueble inmueble);
    boolean realizarOferta(Oferta oferta);
    void generarReporte();
}
