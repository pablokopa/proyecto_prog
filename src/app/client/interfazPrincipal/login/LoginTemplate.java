package app.client.interfazPrincipal.login;

import app.services.ObjGraficos;
import app.services.Recursos;

import usuarios.GestorUsuarios;
import usuarios.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Esta clase representa la interfaz de inicio de sesión y registro de la aplicación.
 * Extiende de JFrame para proporcionar la funcionalidad de la ventana de la interfaz.
 * Contiene métodos para crear y configurar componentes de la interfaz.
 */
public class LoginTemplate extends JFrame{
    private ObjGraficos sObjGraficos;
    private Recursos sRecursos;

    private int mouseX, x;
    private int mouseY, y;

    private JLabel textoLogin;
    private JLabel labelLogo, labelCerrar, labelUsuario, labelPassword;

    private JLabel textoComprobacion;

    private JTextField cuadroUsuario;
    private JPasswordField cuadroPassword;
    private JButton botonEntrar, botonRegistrar;
    private JPanel panelDerecha, panelIzquierda;

    /**
     * Constructor de la clase LoginTemplate.
     * Inicializa los componentes de la interfaz de usuario y configura la ventana.
     * @param gestorUsuarios Gestor de usuarios para manejar la lógica de inicio de sesión y registro.
     */
    public LoginTemplate(GestorUsuarios gestorUsuarios){
        sObjGraficos = ObjGraficos.getService();
        sRecursos = Recursos.getService();

        this.moverVentana();
        this.crearJPanels();
        this.crearJTextFields();
        this.crearJPasswordField();
        this.crearJButtons(gestorUsuarios);
        this.crearJLabels();

        /* Configuración */
        setTitle("BLOOM - Login");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(this);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
    }

    /**
     * Método para permitir el movimiento de la ventana.
     */
    public void moverVentana(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getXOnScreen();
                y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }
        });
    }

    /**
     * Método para crear los paneles de la interfaz.
     * Crea y configura los paneles izquierdo y derecho de la ventana.
     */
    public void crearJPanels(){
        /* Panel Izquierda */
        panelIzquierda = sObjGraficos.construirJPanel(0, 0, 600, 500, sRecursos.getGRANATE(), null);
        this.add(panelIzquierda);

        /* Panel Derecha */
        panelDerecha = sObjGraficos.construirJPanel(600, 0, 400, 500, Color.WHITE, null);
        this.add(panelDerecha);
    }

    /**
     * Método para crear los campos de texto de la interfaz.
     * Crea y configura el campo de texto para el nombre de usuario.
     */
    public void crearJTextFields(){
        cuadroUsuario = sObjGraficos.construirJTextField(null, (panelDerecha.getWidth() - 260) / 2, 150, 260, 40, sRecursos.getFontArialBold(13), Color.WHITE, Color.DARK_GRAY, sRecursos.getGRANATE(), sRecursos.getBordeGranate(), "c");
        panelDerecha.add(cuadroUsuario);
    }

    /**
     * Método para crear el campo de contraseña de la interfaz.
     * Crea y configura el campo de contraseña para la contraseña del usuario.
     */
    public void crearJPasswordField(){
        cuadroPassword = sObjGraficos.construirJPasswordField(null, (panelDerecha.getWidth() - 260) / 2, 220, 260, 40, sRecursos.getFontArialBold(13), Color.WHITE, Color.DARK_GRAY, sRecursos.getGRANATE(), sRecursos.getBordeGranate(), "c");
        panelDerecha.add(cuadroPassword);
    }

    /**
     * Método para crear los botones.
     * Crea y configura los botones para el logo, el botón de cerrar, el icono de usuario y el icono de contraseña.
     */
    public void crearJButtons(GestorUsuarios gestorUsuarios){
        /* Botón de entrar */
        botonEntrar = sObjGraficos.construirJButton("Entrar", (panelDerecha.getWidth() - 250) / 2, 300, 250, 45, sRecursos.getCursorMano(), null, sRecursos.getFontTArialDefault(14), sRecursos.getGRANATE(), Color.WHITE, null, "center", true);
        panelDerecha.add(botonEntrar);

        /* Botón de registrarse */
        botonRegistrar = sObjGraficos.construirJButton("Registrarse", (panelDerecha.getWidth() - botonEntrar.getWidth())-25, 370, 150, 40, sRecursos.getCursorMano(), null, sRecursos.getFontTArialDefault(14), sRecursos.getGRANATE(), Color.WHITE, null, "center", true);

        // *** Se puede hacer un método externo ***
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se comprueba si el nombre de usuario ya existe y se le pasa el label textoComprobacion para que pueda cambiar el texto
                if (!gestorUsuarios.comprobarNombreUsuario(cuadroUsuario.getText(), textoComprobacion)){
                    return;                         // si comprobarNombre no devuelve true, se corta el flujo de la acción del botón y no se registra el usuario
                }
                // *** en JPasswordField, getText() esta deprecado, hay que cambiarlo por getPassword() que devuelve un char[] ***
                Usuario usuarioTemporal = new Usuario(cuadroUsuario.getText(),cuadroPassword.getText());
                if (gestorUsuarios.registrarUsuario(usuarioTemporal)){
                    System.out.println("Usuario registrado correctamente");
                }else{
                    System.out.println("El usuario no ha sido registrado");
                }
                textoComprobacion.setText("Usuario registrado correctamente");
                cuadroUsuario.setText("");
                cuadroPassword.setText("");
            }
        });
        panelDerecha.add(botonRegistrar);
    }

    /**
     * Método para crear las etiquetas.
     * Crea y configura las etiquetas para el logo, el botón de cerrar, el icono de usuario y el icono de contraseña.
     */
    public void crearJLabels(){
        /* Texto login*/
        textoLogin = sObjGraficos.construirJLabel("Iniciar Sesión", 0, 0, panelDerecha.getWidth(), 80, null, null, sRecursos.getFontArialBold(Recursos.TITTLE_SIZE), null, sRecursos.getGRANATE(), null, "c");
        panelDerecha.add(textoLogin);

        /* Label informativo; nombre y contraseña correctos */
        textoComprobacion = sObjGraficos.construirJLabel("", 0, 90, 400, 32, null, null, sRecursos.getFontArialItalic(13), null, sRecursos.getGRANATE(), null, "c");
        panelDerecha.add(textoComprobacion);

        /* Imagen logo */
        labelLogo = sObjGraficos.construirJLabel(null, 50, 125, 550, 250, null, sRecursos.getImagenLogo(), null, null, null, null, "center");
        panelIzquierda.add(labelLogo);

        /* Imagen candado password */
        labelPassword = sObjGraficos.construirJLabel(null, 25, 230, 32, 32, null, sRecursos.getImagenPassword(), null, null, null, null, "center");
        panelDerecha.add(labelPassword);

        /* Imagen logo usuario */
        labelUsuario = sObjGraficos.construirJLabel(null, 25, 160, 32, 32, null, sRecursos.getImagenUsuario(), null, null, null, null, "center");
        panelDerecha.add(labelUsuario);

        /* Imagen cruz cerrar */
        labelCerrar = sObjGraficos.construirJLabel(null, 360, 5, 40, 40, sRecursos.getCursorMano(), sRecursos.getImagenCerrar(), null, null, null, null, "center");
        labelCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Cuando se haga click
                dispose(); // Cerrar la ventana
            }
        });
        panelDerecha.add(labelCerrar);
    }
}