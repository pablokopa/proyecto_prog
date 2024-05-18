package app;

import app.model.tareas.GestorTareas;
import app.view.login.InterfazLogin;
import app.model.usuarios.GestorUsuarios;
import app.view.principal.InterfazPrincipal;

public class Main {
    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
//        gestorUsuarios.conectarUsuario("pep", "pepe", null);
        InterfazLogin interfazLogin = new InterfazLogin(gestorUsuarios);
//        InterfazPrincipal interfazPrincipal = new InterfazPrincipal(new GestorTareas());
    }
}