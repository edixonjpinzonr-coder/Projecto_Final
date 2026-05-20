package co.edu.uniquindio.poo.projecto_final.services;

import co.edu.uniquindio.poo.projecto_final.model.Comprador;
import co.edu.uniquindio.poo.projecto_final.model.InmoSmart;
import co.edu.uniquindio.poo.projecto_final.model.Vendedor;

public class ModelFactoryService {
    private static ModelFactoryService instance;
    private final InmoSmart inmoSmart;

    private ModelFactoryService() {
        this.inmoSmart= new InmoSmart("InmoSmart Uniquindio");
        inicializarDatosPrueba();
    }

    public static ModelFactoryService getInstance() {
        if (instance == null) {
            instance = new ModelFactoryService();
        }
        return instance;
    }

    public InmoSmart getInmoSmart() {
        return inmoSmart;
    }
    public  void inicializarDatosPrueba(){
        Comprador juanito= new Comprador("juan", "12345","315","juan@gmail.com");
        Vendedor pepito= new Vendedor("pepito", "11111", "314","pepito@gmail.com");

        inmoSmart.registrarUsuario(juanito);
        inmoSmart.registrarUsuario(pepito);
    }
}
