package co.edu.uniquindio.poo.projecto_final.app;

import co.edu.uniquindio.poo.projecto_final.model.*;
import co.edu.uniquindio.poo.projecto_final.model.enums.Estado;
import co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta;

import javax.swing.*;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {

        InmoSmart inmoSmart= new InmoSmart("Inmo Smart");
        Comprador comprador= new Comprador("1234", "juan","3111","juanito");
        Vendedor vendedor= new Vendedor("jaja","12222", "111", "pepe");

        Apartamento apartamento= new Apartamento("1212","nose","noroeste",
                "por ahi",222f, Estado.DISPONIBLE, vendedor,10);

        Oferta oferta= new Oferta("111",comprador,apartamento,1000f,
                LocalDate.now(), EstadoOferta.PENDIENTE,inmoSmart);


        inmoSmart.registrarUsuario(comprador);
        inmoSmart.registrarUsuario(vendedor);

        inmoSmart.publicarInmueble(apartamento);
        inmoSmart.realizarOferta(oferta);

        inmoSmart.generarReporte();


        String opcion= JOptionPane.showInputDialog("ingrese la opcion que desea realizar: " +
                "\n 1. realizar oferta\n  2. comprar \n 3.  completar transaccion");
        comprador.sumarPuntosReputacion(opcion);

    }
}
