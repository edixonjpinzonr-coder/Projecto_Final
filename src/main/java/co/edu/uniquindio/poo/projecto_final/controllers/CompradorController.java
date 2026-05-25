package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.*;
import co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta;
import co.edu.uniquindio.poo.projecto_final.model.enums.TipoInmueble;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.collections.FXCollections;
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

    @FXML private TableView<Oferta> tablaMisOfertas;
    @FXML private TableColumn<Oferta, String> colOfInmueble;
    @FXML private TableColumn<Oferta, Double> colOfValorOriginal;
    @FXML private TableColumn<Oferta, Double> colOfContrapropuesta;
    @FXML private TableColumn<Oferta, String> colOfEstado;

    @FXML private Label lblPuntos;
    @FXML private Label lblRango;

    private Comprador compradorLogueado;
    @FXML
    private void initialize() {
        if (comboTipoInmueble != null) {
            comboTipoInmueble.getItems().addAll("Todos", "Casa", "Apartamento", "Local", "Terreno");
            comboTipoInmueble.setValue("Todos");
        }
        if (colBusqCodigo != null) {
            colBusqCodigo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("codigo"));
            colBusqCiudad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("ciudad"));
            colBusqArea.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("area"));
            colBusqPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precio"));
        }
        if (colSugCodigo != null) {
            colSugCodigo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("codigo"));
            colSugCiudad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("ciudad"));
            colSugArea.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("area"));
            colSugPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precio"));
        }
        if (tablaMisOfertas != null) {
            colOfInmueble.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getInmueble().getCodigo()));
            colOfValorOriginal.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("valorOferta"));
            colOfContrapropuesta.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("valorContrapropuesta"));
            colOfEstado.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("estadoOferta"));
        }
    }

    @FXML
    public void onBuscarButtonClick() {
        try {
            String ciudad = txtFiltroCiudad.getText();
            String seleccionTipo = comboTipoInmueble.getValue();

            float precioMin = txtPrecioMin.getText().isEmpty()? 0 :Float.parseFloat(txtPrecioMin.getText());
            float precioMax = txtPrecioMax.getText().isEmpty()? Float.MAX_VALUE: Float.parseFloat(txtPrecioMax.getText());
            float areaMinima = txtAreaMinima.getText().isEmpty()? 0 :Float.parseFloat(txtAreaMinima.getText());

            TipoInmueble tipoEnum = null;
            if (seleccionTipo != null && !"Todos".equals(seleccionTipo)) {
                tipoEnum = TipoInmueble.valueOf(seleccionTipo.toUpperCase());
            }
            if (compradorLogueado != null) {
                compradorLogueado.setUltimaCiudadBuscada(ciudad);
                compradorLogueado.setUltimoTipoBuscado(tipoEnum);
                compradorLogueado.setUltimoTipoBuscadoStr(seleccionTipo);
            }
            List<Inmueble> resultados = ModelFactoryService.getInstance().getInmoSmart()
                    .buscarInmueblesConFiltro(compradorLogueado, ciudad, tipoEnum, precioMin, precioMax, areaMinima);

            if (resultados.isEmpty()) {
                tablaBusqueda.setItems(FXCollections.observableArrayList());
                mostrarMensaje("Sin resultados: No se encontraron inmuebles que cumplan con esos filtros.", Alert.AlertType.INFORMATION);
            } else {
                tablaBusqueda.setItems(FXCollections.observableArrayList(resultados));
                mostrarMensaje("Búsqueda exitosa: Se encontraron " + resultados.size() + " inmuebles.", Alert.AlertType.INFORMATION);
            }
            cargarSugerencias();
        } catch (NumberFormatException e) {
            mostrarMensaje("Error de formato: Los precios y el área deben ser valores numéricos.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: Tipo de inmueble no reconocido.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onRealizarOfertaClick() {
        Inmueble seleccionado = tablaBusqueda.getSelectionModel().getSelectedItem();
        if (seleccionado == null && tablaSugerencias != null) {
            seleccionado = tablaSugerencias.getSelectionModel().getSelectedItem();
        }
        if (seleccionado == null) {
            mostrarMensaje("Por favor, seleccione un inmueble de la tabla de búsqueda o de sugerencias para ofertar.", Alert.AlertType.WARNING);
            return;
        }
        String idOferta = "OF-" + (ModelFactoryService.getInstance().getInmoSmart().getListaOfertas().size() + 1);
        Oferta nueva = new Oferta(
                idOferta,
                compradorLogueado,
                seleccionado,
                0,
                java.time.LocalDate.now(),
                co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.PENDIENTE,
                ModelFactoryService.getInstance().getInmoSmart()
        );
        abrirModalOferta(nueva, "COMPRADOR");
        actualizarDatosPerfil();
    }

    @FXML
    private void onResponderNegociacionClick() {
        Oferta seleccionada = tablaMisOfertas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Por favor, seleccione una oferta de su lista.", Alert.AlertType.WARNING);
            return;
        }

        if (seleccionada.getEstadoOferta() != co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.EN_NEGOCIACION) {
            mostrarMensaje("Solo puedes modificar ofertas que estén en estado 'EN_NEGOCIACION'.", Alert.AlertType.WARNING);
            return;
        }
        abrirModalOferta(seleccionada, "COMPRADOR");
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

    private void actualizarTablaMisOfertas() {
        if (tablaMisOfertas != null && compradorLogueado != null) {
            java.util.List<Oferta> todasLasOfertas = ModelFactoryService.getInstance().getInmoSmart().getListaOfertas();
            java.util.List<Oferta> misOfertasPropias = new java.util.ArrayList<>();
            for (Oferta of : todasLasOfertas) {
                if (of.getComprador() != null &&
                        of.getComprador().getIdentificacion().equals(compradorLogueado.getIdentificacion())) {
                    if (of.getEstadoOferta() == co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.PENDIENTE ||
                            of.getEstadoOferta() == co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.EN_NEGOCIACION ||
                            of.getEstadoOferta() == co.edu.uniquindio.poo.projecto_final.model.enums.EstadoOferta.ACEPTADA_POR_COMPRADOR) {

                        misOfertasPropias.add(of);
                    }
                }
            }
            tablaMisOfertas.setItems(FXCollections.observableArrayList(misOfertasPropias));
        }
    }

    private void abrirModalOferta(Oferta oferta, String rol) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/projecto_final/OfertaDialogView.fxml"));
            Parent root = loader.load();
            OfertaDialogController controller = loader.getController();
            controller.inicializarDialogo(oferta, rol, compradorLogueado, () -> {
                actualizarTablaMisOfertas();
                actualizarDatosPerfil();
            });
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Negociación de Inmueble");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error al abrir la interfaz de ofertas.", Alert.AlertType.ERROR);
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
                recomendados = ModelFactoryService.getInstance().getInmoSmart().getListaInmuebles();

            }
            List<Inmueble> recomendadosDisponibles = recomendados.stream()
                    .filter(inm -> inm.getEstado() == co.edu.uniquindio.poo.projecto_final.model.enums.Estado.DISPONIBLE)
                    .toList();
            tablaSugerencias.setItems(javafx.collections.FXCollections.observableArrayList(recomendadosDisponibles));
        }
    }

    private void actualizarDatosPerfil() {
        if (compradorLogueado != null) {
            lblPuntos.setText("Puntos: " + compradorLogueado.getPuntosReputacion());
            lblRango.setText("Rango: " + compradorLogueado.obtenerRango().toString());
        }
    }

    public void setNombreVendedor(String nombre) {
        lblBienvenida.setText("Bienvenido, " + nombre);
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
            controller.inicializarHistorial(compradorLogueado);

            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("InmoSmart - Historial");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.show();
        } catch (java.io.IOException e) {
            mostrarMensaje("Error al abrir el historial.", Alert.AlertType.ERROR);
        }
    }

    public void setCompradorLogueado(Comprador comprador) {
        this.compradorLogueado = comprador;
        if (comprador != null) {
            this.lblBienvenida.setText("Bienvenido, " + comprador.getNombre());
            cargarInmueblesIniciales();
            cargarSugerencias();
            actualizarTablaMisOfertas();
            actualizarDatosPerfil();

            if (!comprador.getListaAlertas().isEmpty()) {
                StringBuilder mensajeAcumulado = new StringBuilder("¡Tienes una nueva Notificación! \n\n");
                for (Alerta alerta : comprador.getListaAlertas()) {
                    mensajeAcumulado.append("• ").append(alerta.getTipoAlerta())
                            .append("\n  Inmueble: ").append(alerta.getInmuebleAsociado().getCodigo())
                            .append("\n  Fecha: ").append(alerta.getFecha()).append("\n\n");
                }
                mostrarMensaje(mensajeAcumulado.toString(), Alert.AlertType.INFORMATION);
                comprador.getListaAlertas().clear();
            }
        }
    }
}

