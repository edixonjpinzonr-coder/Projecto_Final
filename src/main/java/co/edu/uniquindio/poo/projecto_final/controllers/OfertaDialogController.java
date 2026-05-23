package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.Oferta;
import co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OfertaDialogController {

    @FXML private Label lblTitulo;
    @FXML private Label lblDetalleInmueble;
    @FXML private Label lblPrecioAnterior;
    @FXML private TextField txtNuevoPrecio;
    @FXML private Button btnEnviar;
    @FXML private Button btnAceptarCambio;

    private Oferta ofertaActual;
    private String rolUsuario;
    private Object usuarioLogueado;
    private Runnable callbackActualizar;


    public void inicializarDialogo(Oferta oferta, String rol, Object usuario, Runnable callback) {
        this.ofertaActual = oferta;
        this.rolUsuario = rol;
        this.usuarioLogueado = usuario;
        this.callbackActualizar = callback;

        lblDetalleInmueble.setText("Inmueble: " + oferta.getInmueble().getCodigo() + " | Precio Base: $" + oferta.getInmueble().getPrecio());
        btnAceptarCambio.setVisible(false);

        if (oferta.getEstadoOferta() == EstadoOferta.PENDIENTE && rol.equals("COMPRADOR")) {
            lblTitulo.setText("Crear Nueva Oferta");
            lblPrecioAnterior.setText("Precio sugerido por la plataforma.");
        }
        else if (oferta.getEstadoOferta() == EstadoOferta.EN_NEGOCIACION && rol.equals("COMPRADOR")) {
            lblTitulo.setText("Revisar Contrapropuesta del Vendedor");
            lblPrecioAnterior.setText("El Vendedor te pide: $" + oferta.getValorContrapropuesta());
            btnAceptarCambio.setVisible(true);
        }
        else if (rol.equals("VENDEDOR")) {
            lblTitulo.setText("Enviar Contrapropuesta al Comprador");
            lblPrecioAnterior.setText("El Comprador ofreció originalmente: $" + oferta.getValorOferta());
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
                ofertaActual.setValorOferta((float) precioIntroducido);
                ofertaActual.setEstadoOferta(EstadoOferta.PENDIENTE);

                if (!ModelFactoryService.getInstance().getInmoSmart().getListaOfertas().contains(ofertaActual)) {
                    ModelFactoryService.getInstance().getInmoSmart().realizarOferta(ofertaActual);
                }
                mostrarMensaje("Oferta enviada al vendedor con éxito.", Alert.AlertType.INFORMATION);

            } else if (rolUsuario.equals("VENDEDOR")) {
                ofertaActual.setValorContrapropuesta(precioIntroducido);
                ofertaActual.setEstadoOferta(EstadoOferta.EN_NEGOCIACION);
                mostrarMensaje("Contrapropuesta enviada al comprador.", Alert.AlertType.INFORMATION);
            }

            cerrarVentana();
        } catch (NumberFormatException e) {
            mostrarMensaje("Por favor, ingrese un número válido.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onAceptarCambioClick() {
        ofertaActual.setValorOferta((float) ofertaActual.getValorContrapropuesta());
        ofertaActual.setEstadoOferta(EstadoOferta.PENDIENTE);
        ofertaActual.setValorContrapropuesta(0);
        mostrarMensaje("Has aceptado el precio del vendedor. La oferta ha sido enviada de vuelta al vendedor para que formalice y cierre la venta.", Alert.AlertType.INFORMATION);
        cerrarVentana();
    }

    @FXML
    private void onCancelarClick() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        if (callbackActualizar != null) callbackActualizar.run(); // Refresca las tablas
        Stage stage = (Stage) txtNuevoPrecio.getScene().getWindow();
        stage.close();
    }

    private void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}