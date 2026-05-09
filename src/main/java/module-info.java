module co.edu.uniquindio.poo.projecto_final {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.projecto_final to javafx.fxml;
    exports co.edu.uniquindio.poo.projecto_final;
    exports co.edu.uniquindio.poo.projecto_final.app;
    opens co.edu.uniquindio.poo.projecto_final.app to javafx.fxml;
}