package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.Estado;
import co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta;
import co.edu.uniquindio.poo.projecto_final.model.enums.TipoInmueble;
import co.edu.uniquindio.poo.projecto_final.model.enums.TipoOperacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


import static org.junit.jupiter.api.Assertions.*;



public class InmoSmartTest {
    /**
     * Clase para probar metodos del projecto final
     * @author Jefrey Pinzon
     * @since 23/05/2026
     *
     * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE)
     */
    private InmoSmart inmoSmart;
    private Vendedor vendedorTest;
    private Comprador compradorTest;
    private Inmueble casaTest;

    @BeforeEach
    public void setUp() {
        inmoSmart = new InmoSmart("InmoSmart Quindío");
        vendedorTest = new Vendedor("Carlos Vendedor","4321" , "3001609090","carlos@email.com");
        compradorTest = new Comprador("Jefrey Comprador","1234" ,"3156907676" ,"jeffrey@email.com");
        casaTest = new Casa("1111", "Centro","Cali", 20 ,150000000,2);
        inmoSmart.registrarUsuario(vendedorTest);
        inmoSmart.registrarUsuario(compradorTest);
        inmoSmart.publicarInmueble(casaTest);
    }

    @Test
    public void verificarPublicarInmuebleExitoso() {
        System.out.println("Inicio de prueba: verificar Publicar Inmuebles Exitosamente");
        Inmueble apto = new Apartamento("apartamento 2", "Calarcá",
                "Medellin" ,20f,100000000,4);
        boolean exito = inmoSmart.publicarInmueble(apto);

        assertTrue(exito);
        assertTrue(inmoSmart.getListaInmuebles().contains(apto));
        System.out.println("Fin de prueba: verificar Publicar Inmueble Exitosamente\n");
    }

    @Test
    public void verificarBuscarInmueble() {
        System.out.println("Inicio de prueba: verificar Buscar Inmuebles");
        Inmueble encontrado = inmoSmart.buscarinmueble("1111");
        assertNotNull(encontrado);
        assertEquals("Cali", encontrado.getCiudad());
        Inmueble noExiste = inmoSmart.buscarinmueble("1212");
        assertNull(noExiste);
        System.out.println("Fin de prueba: verificar Buscar Inmuebles\n");
    }

    @Test
    public void verificarActualizarPrecioInmuebleValido() {
        System.out.println("Inicio de prueba: verificar Actualizar Precio de Inmueble Valido");
        boolean exito = inmoSmart.actualizarPrecioInmueble(casaTest, 140000000);
        assertTrue(exito);
        assertEquals(140000000, casaTest.getPrecio());
        boolean fallo = inmoSmart.actualizarPrecioInmueble(casaTest, -5000);
        assertFalse(fallo);
        System.out.println("Fin de prueba: verificar Actualizar Precio de Inmueble Valido\n");
    }

    @Test
    public void verificarCalcularTotalInmuebles() {
        System.out.println("Inicio de prueba: verificar el Total de inmuebles");
        assertEquals(1, inmoSmart.calcularTotalInmuebles());
        Inmueble local = new Local("1289","Calle 12","Circasia",30f ,90000000,3);
        inmoSmart.publicarInmueble(local);
        assertEquals(2, inmoSmart.calcularTotalInmuebles());
        System.out.println("Fin de prueba: verificar el Total de inmuebles\n");
    }

    @Test
    public void  verificarRealizarOfertaExitosa() {
        System.out.println("Inicio de prueba: verificar Realizar Oferta");
        Oferta oferta = new Oferta("11112",compradorTest, casaTest, 145000000,
                LocalDate.now(), EstadoOferta.ACEPTADA,inmoSmart);
        boolean resultado = inmoSmart.realizarOferta(oferta);
        assertTrue(resultado);
        assertTrue(inmoSmart.getListaOfertas().contains(oferta));
        assertTrue(compradorTest.getListaOfertas().contains(oferta));
        System.out.println("Fin de prueba: verificar Realizar Oferta\n");
    }

    @Test
    public void verificarRegistrarTransaccionCierre() {
        System.out.println("Inicio de prueba: verificar Registrar Transaccion");
        Oferta oferta = new Oferta("11113",compradorTest, casaTest, 145000000,
                LocalDate.now(), EstadoOferta.ACEPTADA,inmoSmart);
        oferta.setEstadoOferta(EstadoOferta.PENDIENTE);
        inmoSmart.realizarOferta(oferta);
        boolean exitoTransaccion = inmoSmart.registrarTransaccion(oferta, TipoOperacion.VENTA);
        assertTrue(exitoTransaccion);
        assertEquals(EstadoOferta.ACEPTADA, oferta.getEstadoOferta());
        assertEquals(Estado.VENDIDO, casaTest.getEstado());
        System.out.println("Fin de prueba: verificar Registrar Transaccion\n");
    }

    @Test
    public void verificarRechazarOferta() {
        System.out.println("Inicio de prueba: verificar Rechazar Oferta");
        Oferta oferta = new Oferta("11112",compradorTest, casaTest, 145000000,
                LocalDate.now(), EstadoOferta.ACEPTADA,inmoSmart);
        oferta.setEstadoOferta(EstadoOferta.PENDIENTE);
        inmoSmart.realizarOferta(oferta);
        boolean exitoRechazo = inmoSmart.rechazarOferta(oferta);
        assertTrue(exitoRechazo);
        assertEquals(EstadoOferta.RECHAZADA, oferta.getEstadoOferta());
        System.out.println("Fin de prueba: verificar Rechazar Oferta\n");
    }
    @Test
    public void VerificarBuscarUsuarioPorId() {
        System.out.println("Inicio de prueba: verificar Buscar Usuario por ID");
        Usuario encontrado = inmoSmart.buscarUsuario("1234");
        assertNotNull(encontrado);
        assertEquals("Jefrey Comprador", encontrado.getNombre());
        System.out.println("Fin de prueba: verificar Buscar Usuario por ID\n");
    }

    @Test
    public void verificarBuscarInmueblesConFiltro() {
        System.out.println("Inicio de prueba: verificar  Buscar Inmuebles por Filtro");
        casaTest.setEstado(Estado.DISPONIBLE);
        List<Inmueble> filtrados = inmoSmart.buscarInmueblesConFiltro(
                compradorTest, "Cali", TipoInmueble.CASA, 100000000, 200000000, 10
        );
        assertNotNull(filtrados);
        assertFalse(filtrados.isEmpty());
        assertEquals("1111", filtrados.get(0).getCodigo());
        System.out.println("Fin de prueba: verificar Buscar Inmuebles por Filtro\n");
    }

    @Test
    public void verificarRecomendarInmueblesPorBusqueda() {
        System.out.println("Inicio de prueba: verificar recomendaciones por Busqueda");
        casaTest.setEstado(Estado.DISPONIBLE);
        compradorTest.setUltimaCiudadBuscada("Cali");
        compradorTest.setUltimoTipoBuscadoStr("Casa");
        List<Inmueble> recomendaciones = inmoSmart.recomendarInmuebles(compradorTest);
        assertNotNull(recomendaciones);
        assertFalse(recomendaciones.isEmpty());
        assertEquals("1111", recomendaciones.get(0).getCodigo());
        System.out.println("Fin de prueba: verificar recomendaciones por Busqueda\n");
    }

}

