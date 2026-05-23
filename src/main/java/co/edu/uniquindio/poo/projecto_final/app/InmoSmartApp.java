package co.edu.uniquindio.poo.projecto_final.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InmoSmartApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/co/edu/uniquindio/poo/projecto_final/LoginView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 450, 450);

        stage.setTitle("InmoSmart - Iniciar Sesión");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}