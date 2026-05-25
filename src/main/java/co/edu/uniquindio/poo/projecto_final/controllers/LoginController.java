package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.Comprador;
import co.edu.uniquindio.poo.projecto_final.model.Usuario;
import co.edu.uniquindio.poo.projecto_final.model.Vendedor;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtIdentification;

    @FXML
    public void initialize(){
        System.out.println("LoginController inicializado correctamente.");
    }

    @FXML
    private void onIrARegistroClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/RegistroView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtIdentification.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("InmoSmart - Crear Cuenta");
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error al abrir la pantalla de registro", Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void onLoginButtonClick() {
        String identificacion= txtIdentification.getText();
        if(identificacion== null || identificacion.trim().isEmpty()){
            mostrarMensaje("Campo vacio, por favor llene este espacio", Alert.AlertType.WARNING);
            return;
        }

        Usuario usuario = ModelFactoryService.getInstance().getInmoSmart().buscarUsuario(identificacion);

        if(usuario!= null){
            if(usuario instanceof Vendedor){
                mostrarMensaje("Acceso Consedido\n" +"Bienvenido Vendedor: "+ usuario.getNombre(), Alert.AlertType.INFORMATION);
                abrirVentanaVendedor((Vendedor) usuario);
            }else if(usuario instanceof Comprador){
                mostrarMensaje("Acceso Consedido: \n"+ "Bienvenido Comprador: "+ usuario.getNombre(), Alert.AlertType.INFORMATION);
                abrirVentanaComprador((Comprador) usuario);
            }
        }else{
            mostrarMensaje("Usuario no encontrado: "+ "identificacion no registrada", Alert.AlertType.ERROR);
        }
    }
    public void mostrarMensaje(String mensaje, Alert.AlertType tipo){
        Alert alerta= new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    public void abrirVentanaVendedor(Vendedor vendedor){
        System.out.println("Redirigiendo al apartado de Vendedor");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/VendedorView.fxml"));
            Parent root = loader.load();
            VendedorController controller = loader.getController();
            controller.setVendedorLogueado(vendedor);

            Stage stage = (Stage) txtIdentification.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("InmoSmart - Panel de Vendedor");
            stage.show();

        } catch (IOException e) {
            mostrarMensaje("Error al abrir la interfaz de Vendedor: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onVerReportesClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/ReportesView.fxml"));
            Parent root = loader.load();
            ReportesController controller = loader.getController();
            controller.inicializarReportes();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Reportes InmoSmart");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void abrirVentanaComprador(Comprador comprador){
        System.out.println("Redirigiendo al apartado de Comprador");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/CompradorView.fxml"));
            Parent root = loader.load();
            CompradorController controller = loader.getController();
            controller.setCompradorLogueado(comprador);

            Stage stage = (Stage) txtIdentification.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("InmoSmart - Panel de Comprador");
            stage.show();

        } catch (IOException e) {
            mostrarMensaje("Error al abrir la interfaz de Comprador: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
