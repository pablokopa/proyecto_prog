package app.controller;

import app.model.CodigoError;
import app.model.usuarios.GestorUsuarios;

/**
 * Controlador de GestorUsuarios
 */
public class ControladorUsuarios {
    private final GestorUsuarios gestorUsuarios;

    /**
     * Constructor de la clase
     * @param gestorUsuarios Gestor de usuarios
     */
    public ControladorUsuarios(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
    }

    /**
     * Conectar un usuario
     * @param nombreUsuario Nombre del usuario
     * @param passwordUsuario Contraseña del usuario
     * @return CodigoError constante
     */
    public int conectarUsuario (String nombreUsuario, String passwordUsuario){
        return gestorUsuarios.conectarUsuario(nombreUsuario, passwordUsuario);
    }

    /**
     * Registrar un usuario
     * @param nombreUsuario Nombre del usuario
     * @param passwordUsuario Contraseña del usuario
     * @return CodigoError constante
     */
    public int registrarUsuario(String nombreUsuario, String passwordUsuario) {
        int codigoError = comprobarDatosUsuario(nombreUsuario, passwordUsuario);
        if (codigoError != CodigoError.SIN_ERROR){
            return codigoError;
        }
        return gestorUsuarios.registrarUsuario(nombreUsuario, passwordUsuario);
    }

    /**
     * Comprobar los datos de un usuario
     * @param nombreUsuario Nombre del usuario
     * @param passwordUsuario Contraseña del usuario
     * @return CodigoError constante
     */
    private int comprobarDatosUsuario(String nombreUsuario, String passwordUsuario) {
        int codigoError = gestorUsuarios.comprobarNombreUsuario(nombreUsuario);
        if (codigoError != CodigoError.SIN_ERROR){
            return codigoError;
        }
        codigoError = gestorUsuarios.comprobarPasswordUsuario(passwordUsuario);
        return codigoError;
    }

    /**
     * Contar los usuarios
     * @return Número de usuarios
     */
    public int contarUsuarios(){
        return gestorUsuarios.contarUsuarios();
    }
}
