package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.*;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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


    private Vendedor vendedorLogueado;

    @FXML
    private void initialize() {

        if (comboTipoInmueble != null) {
            comboTipoInmueble.getItems().addAll("Casa", "Apartamento", "Local", "Terreno");
        }
        if (colCodigo != null) {
            colCodigo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("codigo"));
            colCiudad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("ciudad"));
            colArea.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("area"));
            colPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precio"));
        }

        if (tablaOfertas != null) {
            colOfInmueble.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getInmueble().getCodigo()));
            colOfComprador.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getComprador().getNombre()));
            colOfValor.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("valorOferta"));
            colOfEstado.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("estadoOferta"));
        }

        Usuario usuarioClase = ModelFactoryService.getInstance().getInmoSmart().buscarUsuario("4321");
        if (usuarioClase instanceof Vendedor) {
            this.vendedorLogueado = (Vendedor) usuarioClase;
        }

        actualizarTabla();
        actualizarTablaOfertas();
    }

    @FXML
    private void onPublicarButtonClick() {
        try {
            String codigo = txtCodigo.getText();
            String ciudad = txtCiudad.getText();
            String direccion = "Centro";
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

            Inmueble nuevoinmueble = null;
            if ("Casa".equals(tipo)) {
                nuevoinmueble = new Casa(codigo, direccion, ciudad,area, precio);
            } else if ("Apartamento".equals(tipo)) {
                nuevoinmueble = new Apartamento(codigo, direccion,ciudad, area, precio);
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
                mostrarMensaje("Éxito: Inmueble publicado correctamente", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarMensaje("Error: Ya existe un inmueble con ese código", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException exception) {
            mostrarMensaje("Error de datos: El precio y el área deben ser números.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onAceptarOfertaClick() {

    }

    @FXML
    private void onRechazarOfertaClick() {

    }

    @FXML
    private void onNegociarOfertaClick() {

    }

    private void simularNotificaciones(String destino, String mensaje) {

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
        alerta.showAndWait();
    }

    public void setVendedorLogueado(Vendedor vendedor) {
        this.vendedorLogueado = vendedor;
        this.lblBienvenida.setText("Bienvenido, " + vendedor.getNombre());
        actualizarTabla();
    }

    private void actualizarTablaOfertas() {
        if (tablaOfertas != null && vendedorLogueado != null) {
            java.util.List<Oferta> todasLasOfertas = ModelFactoryService.getInstance().getInmoSmart().getListaOfertas();
            java.util.List<Oferta> misOfertas = new java.util.ArrayList<>();
            for (Oferta of : todasLasOfertas) {
                if (of.getInmueble().getVendedor() != null &&
                        of.getInmueble().getVendedor().getIdentificacion().equals(vendedorLogueado.getIdentificacion())) {
                    misOfertas.add(of);
                }
            }
            tablaOfertas.setItems(FXCollections.observableArrayList(misOfertas));
        }
    }

}