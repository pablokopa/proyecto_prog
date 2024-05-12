package app.controller;

import app.model.usuarios.GestorUsuarios;
import app.model.usuarios.Usuario;
import app.model.usuarios.UsuarioConectado;
import app.view.interfazPrincipal.principal.MenuPrincipalConsolaTemporal;
import app.view.login.LoginTemplate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    LoginTemplate loginTemplate;
    GestorUsuarios gestorUsuarios;
    UsuarioConectado usuarioConectado;

    public LoginController(LoginTemplate loginTemplate, GestorUsuarios gestorUsuarios){
        this.loginTemplate = loginTemplate;
        this.gestorUsuarios = gestorUsuarios;

        ponerAccionBotones();
    }

    private void ponerAccionBotones(){

        /* Acción del botón registrar */
        loginTemplate.getBotonRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = loginTemplate.getCuadroUsuario().getText();
                char[] passwordUsuario = loginTemplate.getCuadroPassword().getPassword();

                /* Se comprueba si el nombre de usuario ya existe y se le pasa el label textoComprobacion para que pueda cambiar el texto y
                   Se comprueba si la contraseña tiene al menos 4 caracteres y no tiene espacios en blanco, además de ciertos carácteres extraños */
                if (!gestorUsuarios.comprobarNombreUsuario(nombreUsuario, loginTemplate.getTextoComprobacion())
                        || !gestorUsuarios.comprobarPasswordUsuario(passwordUsuario, loginTemplate.getTextoComprobacion())){
                    loginTemplate.getTextoLogin().setText("Registro fallido..");
                    return; // si comprobarNombre no devuelve true, se corta el flujo de la acción del botón y no se registra el usuario
                }

                Usuario usuarioTemporal = new Usuario(nombreUsuario, passwordUsuario);
                if (gestorUsuarios.registrarUsuario(usuarioTemporal)){  // Se intenta registrar el usuario, si fue registrado correctamente devuelve true

                    gestorUsuarios.insertarUsuario(usuarioTemporal.getNombreUsuario(), usuarioTemporal.getContraUsuario());

                    loginTemplate.getTextoComprobacion().setText("Usuario registrado correctamente");
                    loginTemplate.getTextoLogin().setText("Bienvenido, "+loginTemplate.getCuadroUsuario().getText()+"!");
                    loginTemplate.getCuadroUsuario().setText("");
                    loginTemplate.getCuadroPassword().setText("");
                    loginTemplate.getTextoNumeroUsuarios().setText("Nº Usuarios: "+gestorUsuarios.contarUsuarios());
                }else{
                    System.out.println("El usuario no ha sido registrado por alguna razón desconocida");
                }
            }
        });

        /* Acción del botón entrar */
        loginTemplate.getBotonEntrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuarioTemporal = new Usuario(loginTemplate.getCuadroUsuario().getText(), loginTemplate.getCuadroPassword().getPassword());
                if (gestorUsuarios.conectarUsuario(usuarioTemporal, loginTemplate.getTextoComprobacion())) {   // Se intenta conectar al usuario; si no se conectó, se cambia el textoLogin
                    /* cambiar de ventana */
                    // *** pruebas ***
                    loginTemplate.getFrameLoginTemplate().dispose();
                    MenuPrincipalConsolaTemporal menuPrincipal = new MenuPrincipalConsolaTemporal(usuarioTemporal);
                    menuPrincipal.elegirEnMenu();
                }else{
                    loginTemplate.getTextoLogin().setText("Inicio de sesión fallido..");
                }
            }
        });
    }
}