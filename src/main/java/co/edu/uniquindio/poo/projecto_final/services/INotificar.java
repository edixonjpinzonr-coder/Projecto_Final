package co.edu.uniquindio.poo.projecto_final.services;

import co.edu.uniquindio.poo.projecto_final.model.Alerta;
import co.edu.uniquindio.poo.projecto_final.model.Usuario;

public interface INotificar {
   void enviarNotificacion(Usuario usuario, Alerta alerta);

}
