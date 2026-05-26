package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.model.enums.*;
import co.edu.uniquindio.poo.projecto_final.services.INotificar;
import co.edu.uniquindio.poo.projecto_final.services.IOperacion;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InmoSmart implements IOperacion {
    private String nombre;

    private List<Usuario> listaUsuarios;
    private List<Inmueble> listaInmuebles;
    private List<Publicacion> listaPublicaciones;
    private List<Transaccion> listaTransacciones;
    private List<Alerta> listaAlertas;
    private List<INotificar> listaNotificaciones;
    private List<Oferta> listaOfertas;

    public InmoSmart(String nombre) {
        this.nombre = nombre;
        this.listaUsuarios = new ArrayList<>();
        this.listaInmuebles = new ArrayList<>();
        this.listaPublicaciones = new ArrayList<>();
        this.listaTransacciones = new ArrayList<>();
        this.listaAlertas = new ArrayList<>();
        this.listaNotificaciones = new ArrayList<>();
        this.listaOfertas = new ArrayList<>();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Inmueble> getListaInmuebles() {
        return listaInmuebles;
    }

    public void setListaInmuebles(List<Inmueble> listaInmuebles) {
        this.listaInmuebles = listaInmuebles;
    }

    public List<Publicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaPublicaciones(List<Publicacion> listaPublicaciones) {
        this.listaPublicaciones = listaPublicaciones;
    }

    public List<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }

    public void setListaTransacciones(List<Transaccion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }

    public List<Alerta> getListaAlertas() {
        return listaAlertas;
    }

    public void setListaAlertas(List<Alerta> listaAlertas) {
        this.listaAlertas = listaAlertas;
    }

    public List<INotificar> getListaNotificaciones() {
        return listaNotificaciones;
    }

    public void setListaNotificaciones(List<INotificar> listaNotificaciones) {
        this.listaNotificaciones = listaNotificaciones;
    }

    public List<Oferta> getListaOfertas() {
        return listaOfertas;
    }

    @Override
    public String toString() {
        return "InmoSmart{" +
                "nombre='" + nombre + '\'' +
                ", listaUsuarios=" + listaUsuarios +
                ", listaInmuebles=" + listaInmuebles +
                ", listaPublicaciones=" + listaPublicaciones +
                ", listaTransacciones=" + listaTransacciones +
                ", listaAlertas=" + listaAlertas +
                ", listaNotificaciones=" + listaNotificaciones +
                ", listaOfertas=" + listaOfertas +
                '}';
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        boolean bandera = false;
        Usuario usuarioEncontrado = buscarUsuario(usuario.getIdentificacion());

        if(usuarioEncontrado == null){
            listaUsuarios.add(usuario);
            bandera = true;
        }
        return bandera;
    }

    public Usuario buscarUsuario(String id) {
        return listaUsuarios.stream()
                .filter(u -> u.getIdentificacion().equals(id))
                .findFirst()
                .orElse(null);
        }

    @Override
    public Inmueble buscarinmueble(String codigo) {
        for(Inmueble inmueble : listaInmuebles){
            if(inmueble.getCodigo().equals(codigo)){
                return inmueble;
            }
        }
        return null;
    }


    @Override
    public boolean publicarInmueble(Inmueble inmueble) {
        boolean bandera = false;
        Inmueble inmueble1 = buscarinmueble(inmueble.getCodigo());
        if(inmueble1 == null){
            listaInmuebles.add(inmueble);
            if (inmueble.getVendedor() != null) {
                inmueble.getVendedor().sumarPuntosReputacion("1");
                inmueble.getVendedor().getListaInmuebles().add(inmueble);
            }
            bandera = true;
        }
        return bandera;
    }

    @Override
    public boolean realizarOferta(Oferta oferta) {
        if (oferta == null || oferta.getComprador() == null || oferta.getInmueble() == null) {
            return false;
        }
        if (oferta.getValorOferta() <= 0) {
            System.out.println("Error, valor inválido");
            return false;
        }
        Estado estadoInmueble = oferta.getInmueble().getEstado();
        if (estadoInmueble == Estado.VENDIDO || estadoInmueble == Estado.RESERVADO) {
            System.out.println("Error: No se pueden realizar ofertas sobre un inmueble que ya no está disponible.");
            return false;
        }
        listaOfertas.add(oferta);
        oferta.getComprador().getListaOfertas().add(oferta);
        oferta.getComprador().sumarPuntosReputacion("1");
        oferta.getInmueble().getListaOfertas().add(oferta);
        System.out.println("Oferta agregó correctamente para el inmueble " +
                oferta.getInmueble().getCodigo());

        return true;
    }

    @Override
    public boolean registrarTransaccion(Oferta oferta, TipoOperacion tipoOperacion) {
        boolean bandera = false;
        if (oferta != null && oferta.getInmueble() != null) {
            Estado estadoActual = oferta.getInmueble().getEstado();
            if (estadoActual == Estado.VENDIDO || estadoActual == Estado.RESERVADO) {
                System.out.println("Error: No se puede registrar la transacción. El inmueble ya no está disponible.");
                return false;
            }
        }
        if (oferta != null && (oferta.getEstadoOferta() == EstadoOferta.PENDIENTE ||
                oferta.getEstadoOferta() == EstadoOferta.ACEPTADA_POR_COMPRADOR)) {
            oferta.setEstadoOferta(EstadoOferta.ACEPTADA);

            String codigo = "TR-" + (listaTransacciones.size() + 1);
            Transaccion nuevaTransaccion = new Transaccion(
                    codigo,
                    oferta,
                    oferta.getValorOferta(),
                    tipoOperacion
            );
            listaTransacciones.add(nuevaTransaccion);
            if (tipoOperacion == TipoOperacion.ARRIENDO) {
                oferta.getInmueble().setEstado(Estado.RESERVADO);
            } else {
                oferta.getInmueble().setEstado(Estado.VENDIDO);
            }
            oferta.getComprador().sumarPuntosReputacion("3");
            if (oferta.getInmueble().getVendedor() != null) {
                oferta.getInmueble().getVendedor().sumarPuntosReputacion("3");
            }
            Alerta alertaNegocio = new Alerta(TipoAlerta.OFERTA_ACEPTADA, oferta.getInmueble(), this);
            INotificar canal = new NotificacionWhatsApp();
            canal.enviarNotificacion(oferta.getComprador(), alertaNegocio);

            bandera = true;
            System.out.println("Transacción " + codigo + " registrada con éxito para el inmueble " +
                    oferta.getInmueble().getCodigo());
        } else {
            System.out.println("Error: La oferta debe estar en un estado válido para concretar el cierre del trato.");
        }

        return bandera;
    }

    @Override
    public List<Inmueble> buscarInmueblesConFiltro(Comprador comprador, String ciudad,
                                                   TipoInmueble tipo, float precioMin,
                                                   float precioMax, float areaMinima) {

        if (comprador!= null) {
            Historial busquedaActual= new Historial(ciudad, tipo, precioMin, precioMax,
                    areaMinima, LocalDate.now());
            comprador.getListaHistorial().add(busquedaActual);
        }
        List<Inmueble> resultados= new ArrayList<>();

        for (Inmueble inmueble: listaInmuebles) {
            if (inmueble.getEstado()== Estado.DISPONIBLE) {

                boolean cumpleCiudad= (ciudad==null || ciudad.isEmpty() || inmueble.getCiudad().equalsIgnoreCase(ciudad));
                boolean cumplePrecio= (inmueble.getPrecio()>= precioMin && inmueble.getPrecio()<= precioMax);
                boolean cumpleArea = (inmueble.getArea() >= areaMinima);

                boolean cumpleTipo = false;
                if (tipo== null) {
                    cumpleTipo = true;
                }else if(tipo == TipoInmueble.CASA && inmueble instanceof Casa) {
                    cumpleTipo = true;
                }else if(tipo == TipoInmueble.APARTAMENTO && inmueble instanceof Apartamento) {
                    cumpleTipo = true;
                }else if(tipo == TipoInmueble.LOCAL && inmueble instanceof Local) {
                    cumpleTipo = true;
                }else if(tipo == TipoInmueble.TERRENO && inmueble instanceof Terreno) {
                    cumpleTipo = true;
                }

                if (cumpleCiudad && cumplePrecio && cumpleArea && cumpleTipo) {
                    resultados.add(inmueble);
                }
            }
        }
        return resultados;
    }

    @Override
    public List<Inmueble> recomendarInmuebles(Comprador comprador) {
        List<Inmueble> recomendaciones = new ArrayList<>();
        if (comprador == null) return recomendaciones;
        Transaccion ultimaTransaccion = null;
        for (int i = listaTransacciones.size() - 1; i >= 0; i--) {
            Transaccion tr = listaTransacciones.get(i);
            if (tr.getOferta() != null && tr.getOferta().getComprador() != null &&
                    tr.getOferta().getComprador().getIdentificacion().equals(comprador.getIdentificacion())) {
                ultimaTransaccion = tr;
                break;
            }
        }
        for (Inmueble inmueble : listaInmuebles) {

            if (inmueble.getEstado() == Estado.DISPONIBLE) {
                boolean coincideConHistorial = false;

                if (ultimaTransaccion != null && ultimaTransaccion.getOferta().getInmueble() != null) {
                    Inmueble comprado = ultimaTransaccion.getOferta().getInmueble();
                    boolean coincideCiudad = inmueble.getCiudad().equalsIgnoreCase(comprado.getCiudad());
                    boolean coincidePrecio = inmueble.getPrecio() >= (comprado.getPrecio() * 0.7) &&
                            inmueble.getPrecio() <= (comprado.getPrecio() * 1.3);
                    coincideConHistorial = coincideCiudad && coincidePrecio && !inmueble.getCodigo().equals(comprado.getCodigo());
                }
                boolean coincideBusqueda = false;
                boolean coincideCiudadBusqueda = (comprador.getUltimaCiudadBuscada() != null && !comprador.getUltimaCiudadBuscada().isEmpty())
                        && inmueble.getCiudad().equalsIgnoreCase(comprador.getUltimaCiudadBuscada());
                boolean coincideTipoBusqueda = false;

                if (comprador.getUltimoTipoBuscadoStr() != null && !comprador.getUltimoTipoBuscadoStr().equals("Todos")) {
                    String tipo = comprador.getUltimoTipoBuscadoStr();
                    if (tipo.equals("Casa") && inmueble instanceof Casa) coincideTipoBusqueda = true;
                    else if (tipo.equals("Apartamento") && inmueble instanceof Apartamento) coincideTipoBusqueda = true;
                    else if (tipo.equals("Local") && inmueble instanceof Local) coincideTipoBusqueda = true;
                    else if (tipo.equals("Terreno") && inmueble instanceof Terreno) coincideTipoBusqueda = true;
                }
                coincideBusqueda = coincideCiudadBusqueda || coincideTipoBusqueda;

                if ((coincideConHistorial || coincideBusqueda) && !recomendaciones.contains(inmueble)) {
                    recomendaciones.add(inmueble);
                }
            }
        }
        return recomendaciones;
    }

    @Override
    public boolean rechazarOferta(Oferta oferta) {
            if (oferta != null && oferta.getEstadoOferta() == EstadoOferta.PENDIENTE) {
                oferta.setEstadoOferta(EstadoOferta.RECHAZADA);
                Alerta alertaRechazada = new Alerta(TipoAlerta.OFERTA_RECHAZADA, oferta.getInmueble(), this);
                INotificar canalSms = new NotificacionSMS();
                canalSms.enviarNotificacion(oferta.getComprador(), alertaRechazada);
                System.out.println("Oferta " + oferta.getCodigo() + " ha sido rechazada.");
                return true;
            }
            return false;
    }

    @Override
    public boolean actualizarPrecioInmueble(Inmueble inmueble, float nuevoPrecio) {
        if (inmueble != null && nuevoPrecio > 0) {
            inmueble.setPrecio(nuevoPrecio);
            Alerta alertaPrecio = new Alerta(TipoAlerta.CAMBIO_PRECIO, inmueble, this);
            INotificar canalCorreo = new NotificacionCorreo();
            for (Oferta of : inmueble.getListaOfertas()) {
                canalCorreo.enviarNotificacion(of.getComprador(), alertaPrecio);
            }

            System.out.println("Precio del inmueble " + inmueble.getCodigo() + " actualizado a $" + nuevoPrecio);
            return true;
        }
        return false;
    }

    @Override
    public void generarReporte() {
        System.out.println("\nREPORTES DE INMOSMART");
        System.out.println("Total de Usuarios registrados: " + listaUsuarios.size());
        System.out.println("Total de Inmuebles en plataforma: " + listaInmuebles.size());
        System.out.println("Total de Ofertas procesadas: " + listaOfertas.size());
        System.out.println("Total de Transacciones cerradas: " + listaTransacciones.size());

        Map<String, Integer> demandaCiudades=new HashMap<>();
        for (Oferta o : listaOfertas) {
            String ciudad = o.getInmueble().getCiudad();
            demandaCiudades.put(ciudad, demandaCiudades.getOrDefault(ciudad, 0) + 1);
        }
        System.out.println("Demanda por Ciudades: "+demandaCiudades);

        Comprador masActivo = null;
        int maxOfertas = -1;
        for (Usuario usuario : listaUsuarios) {
            if (usuario instanceof Comprador comp) {
                if (comp.getListaOfertas().size() > maxOfertas) {
                    maxOfertas = comp.getListaOfertas().size();
                    masActivo = comp;
                }
            }
        }
        if (masActivo != null) {
            System.out.println("Comprador más activo: "+masActivo.getNombre());
        }

        int casas =0, apartamentos = 0, locales = 0, terrenos = 0;
        for (Transaccion transaccion : listaTransacciones) {
            Inmueble i = transaccion.getOferta().getInmueble();
            if (i instanceof Casa) casas++;
            else if (i instanceof Apartamento) apartamentos++;
            else if (i instanceof Local) locales++;
            else if (i instanceof Terreno) terrenos++;
        }
        System.out.println("Tipos de Inmuebles vendidos: Casas: "+casas+ "\n Apartamentos: " +
                apartamentos+ "\nLocales: " +locales+ "\n Terrenos: " +terrenos);

        Vendedor mejorVendedor = null;
        int maxPropiedades = -1;
        for (Usuario usuario : listaUsuarios) {
            if (usuario instanceof Vendedor vend) {
                int conteo= 0;
                for(Inmueble inm : listaInmuebles) {
                    if(inm.getVendedor() != null && inm.getVendedor().getIdentificacion().equals(vend.getIdentificacion())) {
                        conteo++;
                    }
                }
                if (conteo> maxPropiedades) {
                    maxPropiedades= conteo;
                    mejorVendedor= vend;
                }
            }
        }
        if (mejorVendedor != null) {
            System.out.println("Vendedor con más propiedades: " +mejorVendedor.getNombre() + " (" + maxPropiedades + " propiedades)\n");
        }
    }

    public int calcularTotalInmuebles() {
        return listaInmuebles.size();
    }

    public int calcularTotalCompradores() {
        return (int) listaUsuarios.stream().filter(u -> u instanceof Comprador).count();
    }


    public int calcularTotalVendedores() {
        return (int) listaUsuarios.stream().filter(u -> u instanceof Vendedor).count();
    }

    public String obtenerTipoInmuebleMasVendido() {
        return listaTransacciones.stream()
                .map(t -> t.getOferta().getInmueble().getClass().getSimpleName()) // Obtiene "Casa", "Apartamento", etc.
                .collect(Collectors.groupingBy(tipo -> tipo, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + " (" + e.getValue() + " ventas)").orElse("Sin ventas");
    }

    public String obtenerCiudadMasDemandada() {
        return listaInmuebles.stream()
                .collect(Collectors.groupingBy(Inmueble::getCiudad, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("N/A");
    }

    public String obtenerCompradorMasActivo() {
        return listaOfertas.stream()
                .collect(Collectors.groupingBy(o -> o.getComprador().getNombre(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("N/A");
    }


    public String obtenerVendedorConMasPropiedades() {
        return listaInmuebles.stream()
                .filter(i -> i.getVendedor() != null)
                .collect(Collectors.groupingBy(i -> i.getVendedor().getNombre(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("N/A");
    }

}
