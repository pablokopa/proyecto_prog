package app;

import app.controller.LoginController;
import app.view.client.login.LoginTemplate;
import app.model.usuarios.GestorUsuarios;

public class Main {
    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        LoginTemplate loginTemplate = new LoginTemplate(gestorUsuarios);
        new LoginController(loginTemplate, gestorUsuarios);
    }
}