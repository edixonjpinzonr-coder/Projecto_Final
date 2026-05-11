module co.edu.uniquindio.poo.projecto_final {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    // Abrir y exportar el paquete de la aplicación (donde está el Launcher y el Main)
    opens co.edu.uniquindio.poo.projecto_final.app to javafx.fxml, javafx.graphics;
    exports co.edu.uniquindio.poo.projecto_final.app;

    // Abrir y exportar los controladores para que JavaFX pueda conectar los botones y eventos
    opens co.edu.uniquindio.poo.projecto_final.controllers to javafx.fxml;
    exports co.edu.uniquindio.poo.projecto_final.controllers;

    // Abrir el modelo para que las tablas de JavaFX puedan leer los datos de tus clases
    opens co.edu.uniquindio.poo.projecto_final.model to javafx.base;
    exports co.edu.uniquindio.poo.projecto_final.model;

    // Exportar los enums y servicios por si necesitas usarlos en otros módulos
    exports co.edu.uniquindio.poo.projecto_final.model.enums;
    exports co.edu.uniquindio.poo.projecto_final.services;
}