package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.Comprador;
import co.edu.uniquindio.poo.projecto_final.model.Vendedor;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtIdentificacion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;

    @FXML
    private void onRegistrarVendedorClick() {
        registrar("Vendedor");
    }

    @FXML
    private void onRegistrarCompradorClick() {
        registrar("Comprador");
    }

    private void registrar(String tipo) {
        String nombre = txtNombre.getText();
        String id = txtIdentificacion.getText();
        String tel = txtTelefono.getText();
        String correo = txtCorreo.getText();

        if (nombre.isEmpty() || id.isEmpty() || tel.isEmpty() || correo.isEmpty()) {
            mostrarMensaje("Error: Todos los campos son obligatorios", Alert.AlertType.WARNING);
            return;
        }

        boolean exito;
        if (tipo.equals("Vendedor")) {
            Vendedor nuevoVendedor = new Vendedor(nombre, id, tel, correo);
            exito = ModelFactoryService.getInstance().getInmoSmart().registrarUsuario(nuevoVendedor);

            if (exito) {
                mostrarMensaje("Registro exitoso como Vendedor", Alert.AlertType.INFORMATION);
                irAInterfazVendedor(nuevoVendedor); // Redirección directa
            } else {
                mostrarMensaje("Error: Ya existe un usuario con esa identificación", Alert.AlertType.ERROR);
            }

        } else {
            Comprador nuevoComprador = new Comprador(nombre, id, tel, correo);
            exito = ModelFactoryService.getInstance().getInmoSmart().registrarUsuario(nuevoComprador);

            if (exito) {
                mostrarMensaje("Registro exitoso como Comprador", Alert.AlertType.INFORMATION);
                irAInterfazComprador(nuevoComprador);
            } else {
                mostrarMensaje("Error: Ya existe un usuario con esa identificación", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void onVolverClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("InmoSmart - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void irAInterfazVendedor(Vendedor vendedor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/VendedorView.fxml"));
            Parent root = loader.load();
            VendedorController controller = loader.getController();
            controller.setVendedorLogueado(vendedor);

            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("InmoSmart - Panel de Vendedor");
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error al abrir la interfaz de Vendedor", Alert.AlertType.ERROR);
        }
    }

    private void irAInterfazComprador(Comprador comprador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/CompradorView.fxml"));
            Parent root = loader.load();
            CompradorController controller = loader.getController();
            controller.setCompradorLogueado(comprador);

            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("InmoSmart - Panel de Comprador");
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error al abrir la interfaz de Comprador", Alert.AlertType.ERROR);
        }
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