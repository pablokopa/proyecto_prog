package app;

import app.controller.LoginController;
import app.view.login.InterfazLogin;
import app.model.usuarios.GestorUsuarios;

public class Main {
    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        InterfazLogin interfazLogin = new InterfazLogin(gestorUsuarios);
        new LoginController(interfazLogin, gestorUsuarios);
    }
}