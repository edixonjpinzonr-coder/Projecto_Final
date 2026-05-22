package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.*;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class VendedorController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtArea;
    @FXML private TextField txtPrecio;

    @FXML private ComboBox<String> comboTipoInmueble;
    @FXML private TableView<Inmueble> tablaInmuebles;
    @FXML private Label lblBienvenida;



    @FXML
    private void initialize() {
        if(comboTipoInmueble != null) {
            comboTipoInmueble.getItems().addAll("Casa", "Apartamento", "Local", "Terreno");
        }
    }

    @FXML
    private void onPublicarButtonClick() {
        try{
            String codigo = txtCodigo.getText();
            String direccion = txtDireccion.getText();
            float area= Float.parseFloat(txtArea.getText());
            float precio= Float.parseFloat(txtPrecio.getText());
            String tipo= comboTipoInmueble.getValue();

            Inmueble nuevoinmueble = null;
            if("Casa".equals(tipo)){
                nuevoinmueble= new Casa(codigo, direccion, area, precio);
            }else if("Apartamento".equals(tipo)){
                nuevoinmueble= new Apartamento(codigo, direccion, area, precio);
            }else if("Local".equals(tipo)){
                nuevoinmueble= new Local(codigo, direccion, area, precio);
            }else if("Terreno".equals(tipo)){
                nuevoinmueble= new Terreno(codigo, direccion, area, precio);
            }
            boolean exito= ModelFactoryService.getInstance().getInmoSmart().publicarInmueble(nuevoinmueble);
            if(exito){
                mostrarMensaje("Exito: Inmueble publicado correctamente", Alert.AlertType.INFORMATION);
                    limpiarCampos();
                }else{
                    mostrarMensaje("Error: Ya existe un inmueble con ese codigo", Alert.AlertType.ERROR);
                }
        }catch (NumberFormatException exception){
            mostrarMensaje("Error de datos: El precio y el área deben ser números.", Alert.AlertType.ERROR);
        }
    }

    public void setNombreVendedor(String nombre) {
        lblBienvenida.setText("Bienvenido, " + nombre);
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtDireccion.clear();
        txtArea.clear();
        txtPrecio.clear();
    }

    public void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


}
