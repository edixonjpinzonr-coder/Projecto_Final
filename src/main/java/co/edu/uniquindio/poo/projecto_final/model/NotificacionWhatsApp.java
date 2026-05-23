package co.edu.uniquindio.poo.projecto_final.model;

import co.edu.uniquindio.poo.projecto_final.services.INotificar;

public class NotificacionWhatsApp implements INotificar {
    @Override
    public void enviarNotificacion(Usuario usuario, Alerta alerta) {
        if(usuario != null && alerta != null) {
            System.out.println("Mensaje enviado a: "+ usuario.getNombre());
            System.out.println("Mensaje: hola "+ usuario.getNombre()+ ", se le notifica de lo siguiente: \n"
                    + alerta.getTipoAlerta()+ "\n"+ alerta.getFecha()+ "\n"+ alerta.getInmuebleAsociado());
        }
    }
}
