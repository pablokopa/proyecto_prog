package app.controller;

import app.model.CodigoError;
import app.model.usuarios.GestorUsuarios;

public class ControladorUsuarios {
    private final GestorUsuarios gestorUsuarios;

    public ControladorUsuarios(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
    }

    public int conectarUsuario (String nombreUsuario, String passwordUsuario){
        return gestorUsuarios.conectarUsuario(nombreUsuario, passwordUsuario);
    }

    public int registrarUsuario(String nombreUsuario, String passwordUsuario) {
        int codigoError = comprobarDatosUsuario(nombreUsuario, passwordUsuario);
        if (codigoError != CodigoError.SIN_ERROR){
            return codigoError;
        }
        return gestorUsuarios.registrarUsuario(nombreUsuario, passwordUsuario);
    }

    private int comprobarDatosUsuario(String nombreUsuario, String passwordUsuario) {
        int codigoError = gestorUsuarios.comprobarNombreUsuario(nombreUsuario);
        if (codigoError != CodigoError.SIN_ERROR){
            return codigoError;
        }
        codigoError = gestorUsuarios.comprobarPasswordUsuario(passwordUsuario);
        return codigoError;
    }

    public int contarUsuarios(){
        return gestorUsuarios.contarUsuarios();
    }
}
