package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.model.*;
import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class HistorialController {

    @FXML private Label lblTituloHistorial;
    @FXML private TableView<Transaccion> tablaHistorial;

    @FXML private TableColumn<Transaccion, String> colTrComprador;
    @FXML private TableColumn<Transaccion, String> colTrFecha;
    @FXML private TableColumn<Transaccion, String> colTrInmueble;
    @FXML private TableColumn<Transaccion, Double> colTrPrecio;

    public void inicializarHistorial(Usuario usuarioLogueado) {
        java.util.List<Transaccion> todas = ModelFactoryService.getInstance().getInmoSmart().getListaTransacciones();
        java.util.List<Transaccion> filtradas = new java.util.ArrayList<>();

        if (usuarioLogueado instanceof Vendedor) {
            Vendedor v = (Vendedor) usuarioLogueado;
            colTrComprador.setText("Comprador");
            lblTituloHistorial.setText("Historial de Propiedades Vendidas / Arrendadas");

            colTrComprador.setCellValueFactory(cellData -> {
                if (cellData.getValue().getOferta() != null && cellData.getValue().getOferta().getComprador() != null) {
                    return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOferta().getComprador().getNombre());
                }
                return new javafx.beans.property.SimpleStringProperty("N/A");
            });

            for (Transaccion tr : todas) {
                if (tr.getOferta() != null && tr.getOferta().getInmueble().getVendedor() != null &&
                        tr.getOferta().getInmueble().getVendedor().getIdentificacion().equals(v.getIdentificacion())) {
                    filtradas.add(tr);
                }
            }
        }
        else if (usuarioLogueado instanceof Comprador) {
            Comprador c = (Comprador) usuarioLogueado;

            colTrComprador.setText("Vendedor");
            lblTituloHistorial.setText("Historial de mis Propiedades Adquiridas");
            colTrComprador.setCellValueFactory(cellData -> {
                if (cellData.getValue().getOferta() != null &&
                        cellData.getValue().getOferta().getInmueble() != null &&
                        cellData.getValue().getOferta().getInmueble().getVendedor() != null) {
                    return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOferta().getInmueble().getVendedor().getNombre());
                }
                return new javafx.beans.property.SimpleStringProperty("N/A");
            });

            for (Transaccion tr : todas) {
                if (tr.getOferta() != null && tr.getOferta().getComprador() != null &&
                        tr.getOferta().getComprador().getIdentificacion().equals(c.getIdentificacion())) {
                    filtradas.add(tr);
                }
            }
        }
        colTrInmueble.setCellValueFactory(cellData -> {
            if (cellData.getValue().getOferta() != null && cellData.getValue().getOferta().getInmueble() != null) {
                return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOferta().getInmueble().getCodigo());
            }
            return new javafx.beans.property.SimpleStringProperty("N/A");
        });
        colTrFecha.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFecha().toString()));
        colTrPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("valorFinal"));
        tablaHistorial.setItems(FXCollections.observableArrayList(filtradas));
    }


    @FXML
    private void onVolverClick() {
        Stage stage = (Stage) tablaHistorial.getScene().getWindow();
        stage.close();
    }
}
