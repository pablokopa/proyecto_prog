package app;

import app.controller.ControladorUsuarios;
import app.view.login.InterfazLogin;
import app.model.usuarios.GestorUsuarios;

public class Main {
    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios(gestorUsuarios);
        new InterfazLogin(controladorUsuarios);
    }
}