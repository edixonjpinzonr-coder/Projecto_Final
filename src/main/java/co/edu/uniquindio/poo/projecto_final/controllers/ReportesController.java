package co.edu.uniquindio.poo.projecto_final.controllers;

import co.edu.uniquindio.poo.projecto_final.services.ModelFactoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReportesController {

    @FXML private Label lblTotalInmuebles;
    @FXML private Label lblTotalCompradores;
    @FXML private Label lblTotalVendedores;
    @FXML private Label lblInmuebleMasVendido;
    @FXML private Label lblCiudadDemanda;
    @FXML private Label lblCompradorActivo;
    @FXML private Label lblMejorVendedor;

    public void inicializarReportes() {
        var inmo = ModelFactoryService.getInstance().getInmoSmart();

        lblTotalInmuebles.setText(String.valueOf(inmo.getListaInmuebles().size()));
        lblTotalCompradores.setText(String.valueOf(inmo.calcularTotalCompradores()));
        lblTotalVendedores.setText(String.valueOf(inmo.calcularTotalVendedores()));

        lblInmuebleMasVendido.setText(inmo.obtenerTipoInmuebleMasVendido());
        lblCiudadDemanda.setText(inmo.obtenerCiudadMasDemandada());
        lblCompradorActivo.setText(inmo.obtenerCompradorMasActivo());
        lblMejorVendedor.setText(inmo.obtenerVendedorConMasPropiedades());
    }

    @FXML
    private void onCerrarClick() {
        Stage stage = (Stage) lblTotalInmuebles.getScene().getWindow();
        stage.close();
    }
}