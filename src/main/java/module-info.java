module co.edu.uniquindio.poo.projecto_final {

    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.poo.projecto_final.app to javafx.fxml;
    opens co.edu.uniquindio.poo.projecto_final.controllers to javafx.fxml;

    exports co.edu.uniquindio.poo.projecto_final.app;
    exports co.edu.uniquindio.poo.projecto_final.controllers;
    exports co.edu.uniquindio.poo.projecto_final.model;
    exports co.edu.uniquindio.poo.projecto_final.services;
}