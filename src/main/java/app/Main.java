package app;

import app.controller.LoginController;
import app.model.basedatos.ConectarBD;
import app.view.client.login.LoginTemplate;
import app.model.usuarios.GestorUsuarios;

public class Main {
    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        LoginTemplate loginTemplate = new LoginTemplate(gestorUsuarios);
        ConectarBD.conectar();
        new LoginController(loginTemplate, gestorUsuarios);
    }
}