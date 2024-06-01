package app;

import app.controller.ControladorUsuarios;
import app.view.login.InterfazLogin;
import app.model.usuarios.GestorUsuarios;

/**
 * Clase principal de la aplicación.
 *
 * Se encarga de instanciar el gestor de usuarios, el controlador de usuarios y
 * la interfaz de login.
 */
public class Main {
    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios(gestorUsuarios);
        new InterfazLogin(controladorUsuarios);
    }
}