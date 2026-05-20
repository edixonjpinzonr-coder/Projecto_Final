package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.Comprador;
import co.edu.uniquindio.poo.projecto_final.model.Inmueble;
import co.edu.uniquindio.poo.projecto_final.model.enums.TipoInmueble;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class CompradorController {

    @FXML private TextField txtFiltroCiudad;
    @FXML private TextField txtPrecioMin;
    @FXML private TextField txtPrecioMax;
    @FXML private TextField txtAreaMinima;
    @FXML private ComboBox<String> comboTipoInmueble;
    @FXML private TableView<Inmueble> tablaBusqueda;


    private Comprador compradorLogueado;

    @FXML
    private void initialize() {
        if (comboTipoInmueble!=null) {
            comboTipoInmueble.getItems().addAll("Todos", "Casa", "Apartamento", "Local", "Terreno");
            comboTipoInmueble.setValue("Todos");
        }
        this.compradorLogueado = (Comprador) ModelFactoryService.getInstance().getInmoSmart().buscarUsuario("1234");
    }

    @FXML
    public void onBuscarButtonClick() {
        try {
            String ciudad = txtFiltroCiudad.getText();

            float precioMin = txtPrecioMin.getText().isEmpty()? 0 :Float.parseFloat(txtPrecioMin.getText());
            float precioMax = txtPrecioMax.getText().isEmpty()? Float.MAX_VALUE: Float.parseFloat(txtPrecioMax.getText());
            float areaMinima = txtAreaMinima.getText().isEmpty()? 0 :Float.parseFloat(txtAreaMinima.getText());
            String seleccionTipo = comboTipoInmueble.getValue();
            TipoInmueble tipoEnum = null;

            if (!"Todos".equals(seleccionTipo)) {
                tipoEnum = TipoInmueble.valueOf(seleccionTipo.toUpperCase());
            }
            List<Inmueble> resultados = ModelFactoryService.getInstance().getInmoSmart()
                    .buscarInmueblesConFiltro(compradorLogueado, ciudad, tipoEnum, precioMin, precioMax, areaMinima);

            if (resultados.isEmpty()) {
                mostrarMensaje("Sin resultados No se encontraron inmuebles que cumplan con esos filtros.", Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje("Búsqueda exitosa Se encontraron " + resultados.size() + " inmuebles.", Alert.AlertType.INFORMATION);
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error de formato Los precios y el área deben ser valores numéricos.", Alert.AlertType.ERROR);
        }
    }

    public void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

