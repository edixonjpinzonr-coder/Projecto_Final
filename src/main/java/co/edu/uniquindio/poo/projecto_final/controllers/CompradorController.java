package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.Comprador;
import co.edu.uniquindio.poo.projecto_final.model.Inmueble;
import co.edu.uniquindio.poo.projecto_final.model.Usuario;
import co.edu.uniquindio.poo.projecto_final.model.enums.TipoInmueble;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CompradorController {

    @FXML private TextField txtFiltroCiudad;
    @FXML private TextField txtPrecioMin;
    @FXML private TextField txtPrecioMax;
    @FXML private TextField txtAreaMinima;
    @FXML private ComboBox<String> comboTipoInmueble;
    @FXML private Label lblBienvenida;

    @FXML private TableView<Inmueble> tablaBusqueda;
    @FXML private TableColumn<Inmueble, String> colBusqCodigo;
    @FXML private TableColumn<Inmueble, String> colBusqCiudad;
    @FXML private TableColumn<Inmueble, Float> colBusqArea;
    @FXML private TableColumn<Inmueble, Float> colBusqPrecio;

    @FXML private TableView<Inmueble> tablaSugerencias;
    @FXML private TableColumn<Inmueble, String> colSugCodigo;
    @FXML private TableColumn<Inmueble, String> colSugCiudad;
    @FXML private TableColumn<Inmueble, Float> colSugArea;
    @FXML private TableColumn<Inmueble, Float> colSugPrecio;



    private Comprador compradorLogueado;

    @FXML
    private void initialize() {
        if (comboTipoInmueble != null) {
            comboTipoInmueble.getItems().addAll("Todos", "Casa", "Apartamento", "Local", "Terreno");
            comboTipoInmueble.setValue("Todos");
        }
        if (colBusqCodigo != null) {
            colBusqCodigo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("codigo"));
            colBusqCiudad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("Ciudad"));
            colBusqArea.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("area"));
            colBusqPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precio"));

        }

        if (colSugCodigo != null) {
            colSugCodigo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("codigo"));
            colSugCiudad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("Ciudad"));
            colSugArea.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("area"));
            colSugPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precio"));

        }

            Usuario usuarioClase = ModelFactoryService.getInstance().getInmoSmart().buscarUsuario("1234");
            if (usuarioClase instanceof Comprador) {
                this.compradorLogueado = (Comprador) usuarioClase;
            }

        cargarInmueblesIniciales();
        cargarSugerencias();

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
                tablaBusqueda.setItems(javafx.collections.FXCollections.observableArrayList());
                mostrarMensaje("Sin resultados No se encontraron inmuebles que cumplan con esos filtros.", Alert.AlertType.INFORMATION);
            } else {
                tablaBusqueda.setItems(javafx.collections.FXCollections.observableArrayList(resultados));
                mostrarMensaje("Búsqueda exitosa Se encontraron " +resultados.size()+ " inmuebles.", Alert.AlertType.INFORMATION);
            }
            cargarSugerencias();

        } catch (NumberFormatException e) {
            mostrarMensaje("Error de formato Los precios y el área deben ser valores numéricos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCerrarSesionClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblBienvenida.getScene().getWindow(); // Usamos el label para capturar la ventana
            stage.setScene(new Scene(root));
            stage.setTitle("InmoSmart - Iniciar Sesión");
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error al regresar al Login", Alert.AlertType.ERROR);
        }
    }

    private void cargarInmueblesIniciales() {
        if (tablaBusqueda != null) {
            List<Inmueble> todosLosInmuebles = ModelFactoryService.getInstance().getInmoSmart().getListaInmuebles();
            List<Inmueble> disponibles = todosLosInmuebles.stream()
                    .filter(inm -> inm.getEstado() == co.edu.uniquindio.poo.projecto_final.model.enums.Estado.DISPONIBLE)
                    .toList();
            tablaBusqueda.setItems(javafx.collections.FXCollections.observableArrayList(disponibles));
        }
    }

    private void cargarSugerencias() {
        if (tablaSugerencias != null && compradorLogueado != null) {
            List<Inmueble> recomendados = ModelFactoryService.getInstance().getInmoSmart().recomendarInmuebles(compradorLogueado);

            if (recomendados.isEmpty()) {
                var todos = ModelFactoryService.getInstance().getInmoSmart().getListaInmuebles();
                tablaSugerencias.setItems(javafx.collections.FXCollections.observableArrayList(todos));
            } else {
                tablaSugerencias.setItems(javafx.collections.FXCollections.observableArrayList(recomendados));
            }
        }
    }

    public void setNombreVendedor(String nombre) {
        lblBienvenida.setText("Bienvenido, " + nombre);
    }

    public void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setCompradorLogueado(Comprador comprador) {
        this.compradorLogueado = comprador;
        this.lblBienvenida.setText("Bienvenido, " + comprador.getNombre());
        cargarInmueblesIniciales();
        cargarSugerencias();
    }
}

