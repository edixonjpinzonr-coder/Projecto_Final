package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.Comprador;
import co.edu.uniquindio.poo.projecto_final.model.Usuario;
import co.edu.uniquindio.poo.projecto_final.model.Vendedor;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class LoginController {
    @FXML
    private TextField txtIdentification;

    @FXML
    public void initialize(){
        System.out.println("LoginController inicializado correctamente.");
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
                abrirVentanaVendedor();
            }else if(usuario instanceof Comprador){
                mostrarMensaje("Acceso Consedido: \n"+ "Bienvenido Comprador: "+ usuario.getNombre(), Alert.AlertType.INFORMATION);
                abrirVentanaComprador();
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
    public void abrirVentanaVendedor(){
        System.out.println("Redirigiendo al apartado de Vendedor");



    }

    public void abrirVentanaComprador(){
        System.out.println("Redirigiendo al apartado de Comprador");



    }
}
