package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.Oferta;
import co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class OfertaDialogController {

    @FXML private Label lblTitulo;
    @FXML private Label lblDetalleInmueble;
    @FXML private Label lblPrecioAnterior;
    @FXML private TextField txtNuevoPrecio;
    @FXML private Button btnEnviar;
    @FXML private Button btnAceptarCambio;
    @FXML private ComboBox<String> comboTipoOperacion;

    private Oferta ofertaActual;
    private String rolUsuario;
    private Object usuarioLogueado;
    private Runnable callbackActualizar;


    public void inicializarDialogo(Oferta oferta, String rol, Object usuario, Runnable callback) {
        this.ofertaActual = oferta;
        this.rolUsuario = rol;
        this.usuarioLogueado = usuario;
        this.callbackActualizar = callback;
        comboTipoOperacion.setItems(FXCollections.<String>observableArrayList("VENTA", "ARRIENDO"));
        lblDetalleInmueble.setText("Inmueble: " + oferta.getInmueble().getCodigo() + " | Precio Base: $" + oferta.getInmueble().getPrecio());
        btnAceptarCambio.setVisible(false);

        if ("VENDEDOR".equalsIgnoreCase(rol)) {
            comboTipoOperacion.setVisible(false);
            lblTitulo.setText("Enviar Contrapropuesta al Comprador");
            lblPrecioAnterior.setText("El Comprador ofreció originalmente: $" + oferta.getValorOferta());
        }
        else if ("COMPRADOR".equalsIgnoreCase(rol)) {
            comboTipoOperacion.setVisible(true);
            if (oferta.getEstadoOferta() == EstadoOferta.PENDIENTE) {
                lblTitulo.setText("Crear Nueva Oferta");
                lblPrecioAnterior.setText("Precio sugerido por la plataforma.");
            }
            else if (oferta.getEstadoOferta() == EstadoOferta.EN_NEGOCIACION) {
                lblTitulo.setText("Revisar Contrapropuesta del Vendedor");
                lblPrecioAnterior.setText("El Vendedor te pide: $" + oferta.getValorContrapropuesta());
                btnAceptarCambio.setVisible(true);
            }
        }
    }

    @FXML
    private void onEnviarClick() {
        try {
            double precioIntroducido = Double.parseDouble(txtNuevoPrecio.getText());
            if (precioIntroducido <= 0) {
                mostrarMensaje("El valor debe ser mayor a cero.", Alert.AlertType.WARNING);
                return;
            }
            if (rolUsuario.equals("COMPRADOR")) {
                if (comboTipoOperacion.getValue() == null) {
                    mostrarMensaje("Debe seleccionar VENTA o ARRIENDO.", Alert.AlertType.WARNING);
                    return;
                }
                String tipoSeleccionado = comboTipoOperacion.getValue();
                co.edu.uniquindio.poo.projecto_final.model.enums.TipoOperacion tipoEnum =
                        co.edu.uniquindio.poo.projecto_final.model.enums.TipoOperacion.valueOf(tipoSeleccionado);
                ofertaActual.setTipoOperacion(tipoEnum);
                ofertaActual.setValorOferta((float) precioIntroducido);
                ofertaActual.setEstadoOferta(EstadoOferta.PENDIENTE);

                if (!ModelFactoryService.getInstance().getInmoSmart().getListaOfertas().contains(ofertaActual)) {
                    ModelFactoryService.getInstance().getInmoSmart().realizarOferta(ofertaActual);
                }
                co.edu.uniquindio.poo.projecto_final.model.Vendedor vendedorDueno = ofertaActual.getInmueble().getVendedor();
                if (vendedorDueno != null) {
                    co.edu.uniquindio.poo.projecto_final.model.Alerta alertaNueva = new co.edu.uniquindio.poo.projecto_final.model.Alerta(
                            co.edu.uniquindio.poo.projecto_final.model.enums.TipoAlerta.OFERTA_RECIBIDA,
                            ofertaActual.getInmueble(),
                            ModelFactoryService.getInstance().getInmoSmart()
                    );
                    vendedorDueno.agregarAlerta(alertaNueva);
                }
                mostrarMensaje("Oferta enviada al vendedor con éxito.", Alert.AlertType.INFORMATION);

            } else if (rolUsuario.equals("VENDEDOR")) {
                ofertaActual.setValorContrapropuesta((float)precioIntroducido);
                ofertaActual.setEstadoOferta(EstadoOferta.EN_NEGOCIACION);
                mostrarMensaje("Contrapropuesta enviada al comprador.", Alert.AlertType.INFORMATION);
            }

            cerrarVentana();
        } catch (NumberFormatException e) {
            mostrarMensaje("Por favor, ingrese un número válido.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error al procesar el tipo de operación.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onAceptarCambioClick() {
        if (ofertaActual != null && rolUsuario.equals("COMPRADOR")) {
            ofertaActual.setValorOferta((float) ofertaActual.getValorContrapropuesta());
            ofertaActual.setEstadoOferta(EstadoOferta.ACEPTADA_POR_COMPRADOR);

            co.edu.uniquindio.poo.projecto_final.model.Vendedor vendedorDueno = ofertaActual.getInmueble().getVendedor();
            if (vendedorDueno != null) {
                co.edu.uniquindio.poo.projecto_final.model.Alerta alertaAceptada = new co.edu.uniquindio.poo.projecto_final.model.Alerta(
                        co.edu.uniquindio.poo.projecto_final.model.enums.TipoAlerta.CONTRAPROPUESTA_ACEPTADA,
                        ofertaActual.getInmueble(),
                        ModelFactoryService.getInstance().getInmoSmart()
                );
                vendedorDueno.agregarAlerta(alertaAceptada);
            }

            mostrarMensaje("Has aceptado la contrapropuesta. Ahora el vendedor puede proceder al cierre de la transacción.", Alert.AlertType.INFORMATION);
            if (callbackActualizar != null) {
                callbackActualizar.run();
            }
            Stage stage = (Stage) btnAceptarCambio.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void onCancelarClick() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        if (callbackActualizar != null) callbackActualizar.run();
        Stage stage = (Stage) txtNuevoPrecio.getScene().getWindow();
        stage.close();
    }

    public void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.getDialogPane().setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        alerta.getDialogPane().setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);
        alerta.showAndWait();
    }
}
