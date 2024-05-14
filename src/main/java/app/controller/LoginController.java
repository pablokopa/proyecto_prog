package app.controller;

import app.model.usuarios.GestorUsuarios;
import app.model.usuarios.UsuarioConectado;
import app.view.login.InterfazLogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    InterfazLogin interfazLogin;
    GestorUsuarios gestorUsuarios;
    UsuarioConectado usuarioConectado;

    public LoginController(InterfazLogin interfazLogin, GestorUsuarios gestorUsuarios){
        this.interfazLogin = interfazLogin;
        this.gestorUsuarios = gestorUsuarios;

        ponerAccionBotones();
    }

    private void ponerAccionBotones(){

        /* Acción del botón registrar */
        interfazLogin.getBotonRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = interfazLogin.getCuadroUsuario().getText();
                String passwordUsuario = String.valueOf(interfazLogin.getCuadroPassword().getPassword());

                if (gestorUsuarios.registrarUsuario(nombreUsuario, passwordUsuario, interfazLogin.getTextoComprobacion(), interfazLogin.getTextoLogin())){  // Se intenta registrar el usuario, si fue registrado correctamente devuelve true
                    interfazLogin.getTextoComprobacion().setText("Usuario registrado correctamente");
                    interfazLogin.getTextoLogin().setText("Bienvenido, "+ interfazLogin.getCuadroUsuario().getText()+"!");
                    interfazLogin.getCuadroUsuario().setText("");
                    interfazLogin.getCuadroPassword().setText("");
                    interfazLogin.getTextoNumeroUsuarios().setText("Nº Usuarios: "+gestorUsuarios.contarUsuarios());
                }else{
                    System.out.println("El usuario no ha sido registrado por alguna razón desconocida");
                }
            }
        });

        /* Acción del botón entrar */
        interfazLogin.getBotonEntrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = interfazLogin.getCuadroUsuario().getText();
                String passwordUsuario = String.valueOf(interfazLogin.getCuadroPassword().getPassword());

                if (gestorUsuarios.conectarUsuario(nombreUsuario, passwordUsuario, interfazLogin.getTextoComprobacion(), interfazLogin.getTextoLogin())) {   // Se intenta conectar al usuario; si no se conectó, se cambia el textoLogin
                    /* cambiar de ventana */
                    // *** pruebas ***
                    interfazLogin.getFrameLoginTemplate().dispose();
                    System.out.println("Conectado");
//                    MenuPrincipalConsolaTemporal menuPrincipal = new MenuPrincipalConsolaTemporal(usuarioTemporal);
//                    menuPrincipal.elegirEnMenu();
                }else{
                    interfazLogin.getTextoLogin().setText("Inicio de sesión fallido..");
                }
            }
        });
    }
}