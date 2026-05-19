package co.edu.uniquindio.poo.projecto_final.app;

import co.edu.uniquindio.poo.projecto_final.model.*;
import co.edu.uniquindio.poo.projecto_final.model.enums.*;

import javax.swing.*;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {

        // 1. Inicializar la inmobiliaria
        InmoSmart inmoSmart = new InmoSmart("Inmo Smart");

        // 2. Crear Usuarios (Corregido el orden: Nombre, Identificacion, Telefono, Correo)
        Comprador comprador = new Comprador("Juanito", "1234", "3111", "juan@gmail.com");
        Vendedor vendedor = new Vendedor("Pepe", "12222", "111", "pepe@gmail.com");

        // 3. Crear Inmueble (Corregido el orden: Codigo, Direccion, Ciudad, Area, Precio, Estado, Vendedor, Habitaciones)
        Apartamento apartamento = new Apartamento("1212", "Calle Falsa 123", "Armenia", 10f, 222f, Estado.DISPONIBLE, vendedor, 10);

        // 4. Crear Oferta
        Oferta oferta = new Oferta("111", comprador, apartamento, 1000f, LocalDate.now(), EstadoOferta.PENDIENTE, inmoSmart);

        // --- EJECUCIÓN DEL FLUJO ---
        System.out.println("=== INICIANDO PRUEBAS DEL SISTEMA ===");

        inmoSmart.registrarUsuario(comprador);
        inmoSmart.registrarUsuario(vendedor);

        inmoSmart.publicarInmueble(apartamento); // Vendedor suma +10 puntos
        inmoSmart.realizarOferta(oferta);       // Comprador suma +5 puntos

        // Generar reporte inicial (con la oferta pendiente)
        inmoSmart.generarReporte();

        // Aceptar oferta y cerrar negocio
        oferta.setEstadoOferta(EstadoOferta.ACEPTADA);
        inmoSmart.registrarTransaccion(oferta, TipoOperacion.VENTA); // Comprador +100, Vendedor +50

        // Prueba manual del switch que querías hacer
        String opcion = JOptionPane.showInputDialog("Ingrese la opción que desea realizar: " +
                "\n 1. Realizar oferta\n 2. Comprar \n 3. Completar transaccion");
        comprador.sumarPuntosReputacion(opcion);

        // --- RESULTADOS FINALES ---
        System.out.println("\n--- ESTADO FINAL POST-VENTA ---");
        System.out.println("Estado actual del inmueble: " + apartamento.getEstado());
        System.out.println("Reputación final de Pepe (Vendedor): " + vendedor.getPuntosReputacion() + " pts (" + vendedor.getRango() + ")");
        System.out.println("Reputación final de Juanito (Comprador): " + comprador.getPuntosReputacion() + " pts (" + comprador.getRango() + ")\n");
    }
}