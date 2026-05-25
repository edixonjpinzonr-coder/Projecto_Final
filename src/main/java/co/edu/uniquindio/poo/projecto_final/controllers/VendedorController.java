package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.*;
import co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta;
import co.edu.uniquindio.poo.projecto_final.model.enums.TipoAlerta;
import co.edu.uniquindio.poo.projecto_final.services.INotificar;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class VendedorController {
    @FXML private TextField txtCodigo;
    @FXML private TextField txtCiudad;
    @FXML private TextField txtArea;
    @FXML private TextField txtPrecio;
    @FXML private ComboBox<String> comboTipoInmueble;
    @FXML private TableView<Inmueble> tablaInmuebles;

    @FXML private Label lblBienvenida;
    @FXML private TableColumn<Inmueble, String> colCodigo;
    @FXML private TableColumn<Inmueble, String> colCiudad;
    @FXML private TableColumn<Inmueble, Float> colArea;
    @FXML private TableColumn<Inmueble, Float> colPrecio;

    @FXML private TableView<Oferta> tablaOfertas;
    @FXML private TableColumn<Oferta, String> colOfInmueble;
    @FXML private TableColumn<Oferta, String> colOfComprador;
    @FXML private TableColumn<Oferta, Double> colOfValor;
    @FXML private TableColumn<Oferta, String> colOfEstado;

    @FXML private Button btnAceptarOferta;
    @FXML private Label lblPuntos;
    @FXML private Label lblRango;

    private Vendedor vendedorLogueado;
    INotificar notificadorWhatsApp = new NotificacionWhatsApp();
    INotificar notificadorSMS = new NotificacionSMS();

    @FXML
    private void initialize() {
        // 1. Configurar las opciones del ComboBox
        if (comboTipoInmueble != null) {
            comboTipoInmueble.getItems().addAll("Casa", "Apartamento", "Local", "Terreno");
        }

        // 2. Vincular las columnas de la tabla de Inmuebles con los atributos del modelo
        if (colCodigo != null) {
            colCodigo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("codigo"));
            colCiudad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("ciudad"));
            colArea.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("area"));
            colPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precio"));
        }

        // 3. Vincular las columnas de la tabla de Ofertas
        if (tablaOfertas != null) {
            colOfInmueble.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getInmueble().getCodigo()));
            colOfComprador.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getComprador().getNombre()));
            colOfValor.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("valorOferta"));
            colOfEstado.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("estadoOferta"));
        }

        // 4. Listener de selección para habilitar/deshabilitar el botón de aceptar ofertas
        if (tablaOfertas != null) {
            tablaOfertas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta estado = newSelection.getEstadoOferta();
                    btnAceptarOferta.setDisable(!(estado == co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.PENDIENTE ||
                            estado == co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.ACEPTADA_POR_COMPRADOR));
                } else {
                    btnAceptarOferta.setDisable(true);
                }
            });
        }

        if (btnAceptarOferta != null) {
            btnAceptarOferta.setDisable(true);
        }
    }

    @FXML
    private void onPublicarButtonClick() {
        try {
            String codigo = txtCodigo.getText();
            String ciudad = txtCiudad.getText();
            String direccion = "Centro";
            if (txtArea.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
                mostrarMensaje("Error: Los campos de Área y Precio son obligatorios.", Alert.AlertType.WARNING);
                return;
            }
            float area = Float.parseFloat(txtArea.getText());
            float precio = Float.parseFloat(txtPrecio.getText());
            String tipo = comboTipoInmueble.getValue();

            if (tipo == null) {
                mostrarMensaje("Error: Debe seleccionar un tipo de inmueble.", Alert.AlertType.WARNING);
                return;
            }
            if (ciudad == null || ciudad.trim().isEmpty()) {
                mostrarMensaje("Error: El campo Ciudad no puede estar vacío.", Alert.AlertType.WARNING);
                return;
            }
            if (codigo == null || codigo.trim().isEmpty()) {
                mostrarMensaje("Error: El campo Código no puede estar vacío.", Alert.AlertType.WARNING);
                return;
            }
            Inmueble nuevoinmueble = null;
            if ("Casa".equals(tipo)) {
                nuevoinmueble = new Casa(codigo, direccion, ciudad, area, precio);
            } else if ("Apartamento".equals(tipo)) {
                nuevoinmueble = new Apartamento(codigo, direccion, ciudad, area, precio);
            } else if ("Local".equals(tipo)) {
                nuevoinmueble = new Local(codigo, direccion, ciudad, area, precio);
            } else if ("Terreno".equals(tipo)) {
                nuevoinmueble = new Terreno(codigo, direccion, ciudad, area, precio);
            }
            if (nuevoinmueble != null) {
                nuevoinmueble.setVendedor(vendedorLogueado);
                nuevoinmueble.setEstado(co.edu.uniquindio.poo.projecto_final.model.enums.Estado.DISPONIBLE);
            }
            boolean exito = ModelFactoryService.getInstance().getInmoSmart().publicarInmueble(nuevoinmueble);
            if (exito) {
                if (vendedorLogueado != null && !vendedorLogueado.getListaInmuebles().contains(nuevoinmueble)) {
                    vendedorLogueado.getListaInmuebles().add(nuevoinmueble);
                }

                mostrarMensaje("Éxito: Inmueble publicado correctamente", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
                actualizarDatosPerfil();
            } else {
                mostrarMensaje("Error: Ya existe un inmueble con ese código en el sistema general.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException exception) {
            mostrarMensaje("Error de datos: El precio y el área deben ser números válidos.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void onAceptarOfertaClick() {
        Oferta seleccionada = tablaOfertas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Por favor, seleccione una oferta de la tabla.", Alert.AlertType.WARNING);
            return;
        }
        co.edu.uniquindio.poo.projecto_final.model.enums.TipoOperacion tipo = seleccionada.getTipoOperacion();
        if (tipo == null) {
            mostrarMensaje("Error: Esta oferta no tiene definido el tipo de operación (Venta/Arriendo).", Alert.AlertType.ERROR);
            return;
        }
        boolean exitoTr = ModelFactoryService.getInstance().getInmoSmart().registrarTransaccion(seleccionada, tipo);
        if (exitoTr) {
            Comprador comprador = seleccionada.getComprador();
            Alerta alerta = new Alerta(
                    co.edu.uniquindio.poo.projecto_final.model.enums.TipoAlerta.OFERTA_ACEPTADA,
                    seleccionada.getInmueble(),ModelFactoryService.getInstance().getInmoSmart());
            notificadorWhatsApp.enviarNotificacion(comprador, alerta);
            comprador.agregarAlerta(alerta);

            mostrarMensaje("¡Éxito! Oferta aceptada y transacción de " + tipo + " generada exitosamente.", Alert.AlertType.INFORMATION);
            simularNotificaciones(seleccionada.getComprador(),
                    co.edu.uniquindio.poo.projecto_final.model.enums.TipoAlerta.OFERTA_ACEPTADA,
                    seleccionada.getInmueble());
            actualizarTabla();
            actualizarTablaOfertas();
            actualizarDatosPerfil();
        } else {
            mostrarMensaje("Error al procesar la transacción en la base de datos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onRechazarOfertaClick() {
        Oferta seleccionada = tablaOfertas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Por favor, seleccione una oferta de la tabla.", Alert.AlertType.WARNING);
            return;
        }
        seleccionada.setEstadoOferta(co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.RECHAZADA);
        mostrarMensaje("La oferta ha sido rechazada.", Alert.AlertType.INFORMATION);
        Comprador comprador = seleccionada.getComprador();
        Alerta alerta = new Alerta(
                co.edu.uniquindio.poo.projecto_final.model.enums.TipoAlerta.OFERTA_RECHAZADA,
                seleccionada.getInmueble(),
                ModelFactoryService.getInstance().getInmoSmart()
        );
        notificadorSMS.enviarNotificacion(comprador, alerta);
        comprador.agregarAlerta(alerta);
        String mensajeNotif = "Lo sentimos. Tu oferta para el inmueble " + seleccionada.getInmueble().getCodigo() + " ha sido RECHAZADA por el vendedor.";
        simularNotificaciones(seleccionada.getComprador(), co.edu.uniquindio.poo.projecto_final.model.enums.TipoAlerta.OFERTA_RECHAZADA, seleccionada.getInmueble());

        actualizarTablaOfertas();

    }

    @FXML
    private void onNegociarOfertaClick() {
        Oferta seleccionada = tablaOfertas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Por favor, seleccione una oferta de la tabla.", Alert.AlertType.WARNING);
            return;
        }
        abrirModalOferta(seleccionada, "VENDEDOR");
    }
    @FXML
    private void onActualizarPrecioClick() {
        Inmueble inmuebleSeleccionado = tablaInmuebles.getSelectionModel().getSelectedItem();
        if (inmuebleSeleccionado != null) {
            TextInputDialog dialog = new TextInputDialog(String.valueOf(inmuebleSeleccionado.getPrecio()));
            dialog.setTitle("Actualizar Precio");
            dialog.setHeaderText("Modificar valor del inmueble: " + inmuebleSeleccionado.getCodigo());
            dialog.setContentText("Ingrese el nuevo precio ($):");
            dialog.showAndWait().ifPresent(nuevoPrecioStr -> {
                try {
                    float nuevoPrecio = Float.parseFloat(nuevoPrecioStr);
                    if (nuevoPrecio <= 0) {
                        mostrarMensaje("Error: El precio debe ser un valor mayor a cero.", Alert.AlertType.ERROR);
                        return;
                    }

                    boolean exito = ModelFactoryService.getInstance().getInmoSmart()
                            .actualizarPrecioInmueble(inmuebleSeleccionado, nuevoPrecio);
                    if (exito) {
                        tablaInmuebles.refresh();
                        mostrarMensaje("Éxito: El precio ha sido actualizado y se envió un correo de alerta a los interesados.", Alert.AlertType.INFORMATION);
                    }
                } catch (NumberFormatException e) {
                    mostrarMensaje("Error: Por favor ingrese un valor numérico válido.", Alert.AlertType.ERROR);
                }
            });
        } else {
            mostrarMensaje("Atención: Por favor, seleccione un inmueble de la tabla para modificar su precio.", Alert.AlertType.WARNING);
        }
    }

    private void abrirModalOferta(Oferta oferta, String rol) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/OfertaDialogView.fxml"));
            Parent root = loader.load();

            OfertaDialogController controller = loader.getController();
            controller.inicializarDialogo(oferta, rol, vendedorLogueado, () -> actualizarTablaOfertas());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Contrapropuesta Comercial");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error al abrir la interfaz de ofertas.", Alert.AlertType.ERROR);
        }
    }

    private void simularNotificaciones(Comprador comprador, co.edu.uniquindio.poo.projecto_final.model.enums.TipoAlerta tipo, Inmueble inmueble) {
            System.out.println("\n--- DISPARANDO CENTRO DE NOTIFICACIONES ---");
            Alerta alerta = new Alerta(
                    tipo,
                    inmueble,
                    ModelFactoryService.getInstance().getInmoSmart()
            );
            var servicios = ModelFactoryService.getInstance().getInmoSmart().getListaNotificaciones();

            if (servicios == null || servicios.isEmpty()) {
                System.out.println("[Simulación Consola] Notificando a " + comprador.getNombre() +
                        " sobre el inmueble " + inmueble.getCodigo() + " con alerta tipo: " + tipo);
            } else {
                for (co.edu.uniquindio.poo.projecto_final.services.INotificar canal : servicios) {
                    canal.enviarNotificacion(comprador, alerta);
                    System.out.println("Notificación enviada con éxito por el canal: " + canal.getClass().getSimpleName());
                }
            }

    }

    @FXML
    private void onCerrarSesionClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblBienvenida.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("InmoSmart - Iniciar Sesión");
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error al regresar al Login", Alert.AlertType.ERROR);
        }
    }

    private void actualizarDatosPerfil() {
        if (vendedorLogueado != null) {
            lblPuntos.setText("Puntos: " + vendedorLogueado.getPuntosReputacion());
            lblRango.setText("Rango: " + vendedorLogueado.obtenerRango().toString());
        }
    }

    private void actualizarTabla() {
        if (tablaInmuebles != null && vendedorLogueado != null) {
            tablaInmuebles.setItems(FXCollections.observableArrayList(vendedorLogueado.getListaInmuebles()));
        }
    }

    public void setNombreVendedor(String nombre) {
        lblBienvenida.setText("Bienvenido, " + nombre);
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtCiudad.clear();
        txtArea.clear();
        txtPrecio.clear();
    }

    public void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.getDialogPane().setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        alerta.getDialogPane().setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);

        alerta.showAndWait();
    }

    @FXML
    private void onVerHistorialClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/HistorialView.fxml"));
            javafx.scene.Parent root = loader.load();

            HistorialController controller = loader.getController();
            controller.inicializarHistorial(vendedorLogueado);

            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("InmoSmart - Historial");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL); // Hace que sea una ventana emergente obligatoria
            stage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al abrir el historial.", Alert.AlertType.ERROR);
        }
    }

    public void setVendedorLogueado(Vendedor vendedor) {
        this.vendedorLogueado = vendedor;
        if (vendedor != null) {
            this.lblBienvenida.setText("Bienvenido, " + vendedor.getNombre());
            actualizarTabla();
            actualizarTablaOfertas();
            actualizarDatosPerfil();

            if (!vendedor.getListaAlertas().isEmpty()) {
                StringBuilder mensajeAcumulado = new StringBuilder("Novedades en tus Publicaciones\n\n");
                for (Alerta alerta : vendedor.getListaAlertas()) {
                    mensajeAcumulado.append("• Acción: ").append(alerta.getTipoAlerta())
                            .append("\n  Inmueble afectado: ").append(alerta.getInmuebleAsociado().getCodigo())
                            .append("\n  Fecha: ").append(alerta.getFecha()).append("\n\n");
                }
                mostrarMensaje(mensajeAcumulado.toString(), Alert.AlertType.INFORMATION);
                vendedor.getListaAlertas().clear();
            }
        }
    }
    private void actualizarTablaOfertas() {
        if (tablaOfertas != null && vendedorLogueado != null) {
            java.util.List<Oferta> todasLasOfertas = ModelFactoryService.getInstance().getInmoSmart().getListaOfertas();
            java.util.List<Oferta> misOfertas = new java.util.ArrayList<>();
            for (Oferta of : todasLasOfertas) {
                if (of.getInmueble().getVendedor() != null &&
                        of.getInmueble().getVendedor().getIdentificacion().equals(vendedorLogueado.getIdentificacion())) {
                    if (of.getEstadoOferta() == co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.PENDIENTE ||
                            of.getEstadoOferta() == co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.EN_NEGOCIACION ||
                            of.getEstadoOferta() == co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.ACEPTADA_POR_COMPRADOR) {
                        misOfertas.add(of);
                    }
                }
            }
            tablaOfertas.setItems(FXCollections.observableArrayList(misOfertas));
        }
    }

}
