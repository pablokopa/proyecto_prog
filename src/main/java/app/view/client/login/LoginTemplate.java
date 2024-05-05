package app.view.client.login;

import app.view.services.Recursos;
import app.model.usuarios.GestorUsuarios;
import app.view.services.ObjGraficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Esta clase representa la interfaz de inicio de sesión y registro de la aplicación.
 * Extiende de JFrame para proporcionar la funcionalidad de la ventana de la interfaz.
 * Contiene métodos para crear y configurar componentes de la interfaz.
 */
public class LoginTemplate extends JFrame{
    private ObjGraficos sObjGraficos;
    private Recursos sRecursos;
    private GestorUsuarios gestorUsuarios;

    private int mouseX, x;
    private int mouseY, y;

    private JLabel textoLogin;
    private JLabel labelLogo, labelCerrar, labelUsuario, labelPassword;
    private JLabel textoNumeroUsuarios;
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
        this.gestorUsuarios = gestorUsuarios;

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
        cuadroUsuario = sObjGraficos.construirJTextField(null, (panelDerecha.getWidth() - 260) / 2, 150, 260, 40, sRecursos.getMonserratBold(13), Color.WHITE, Color.DARK_GRAY, sRecursos.getGRANATE(), sRecursos.getBordeGranate(), "c");
        panelDerecha.add(cuadroUsuario);
    }

    /**
     * Método para crear el campo de contraseña de la interfaz.
     * Crea y configura el campo de contraseña para la contraseña del usuario.
     */
    public void crearJPasswordField(){
        cuadroPassword = sObjGraficos.construirJPasswordField(null, (panelDerecha.getWidth() - 260) / 2, 220, 260, 40, sRecursos.getMonserratBold(13), Color.WHITE, Color.DARK_GRAY, sRecursos.getGRANATE(), sRecursos.getBordeGranate(), "c");
        panelDerecha.add(cuadroPassword);
    }

    /**
     * Método para crear los botones.
     * Crea y configura los botones para el logo, el botón de cerrar, el icono de usuario y el icono de contraseña.
     */
    public void crearJButtons(GestorUsuarios gestorUsuarios){

        /* Botón de registrarse */
        botonRegistrar = sObjGraficos.construirJButton("Registrarse", (panelDerecha.getWidth() - 150) / 2, 370, 150, 40, sRecursos.getCursorMano(), null, sRecursos.getMonserratBold(14), sRecursos.getGRANATE(), Color.WHITE, null, "center", true);
        panelDerecha.add(botonRegistrar);

        /* Botón de entrar */
        botonEntrar = sObjGraficos.construirJButton("Entrar", (panelDerecha.getWidth() - 250) / 2, 300, 250, 45, sRecursos.getCursorMano(), null, sRecursos.getMonserratBold(14), sRecursos.getGRANATE(), Color.WHITE, null, "center", true);
        panelDerecha.add(botonEntrar);

    }

    /**
     * Método para crear las etiquetas.
     * Crea y configura las etiquetas para el logo, el botón de cerrar, el icono de usuario y el icono de contraseña.
     */
    public void crearJLabels(){
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

        /* Contador usuarios */
        textoNumeroUsuarios = sObjGraficos.construirJLabel("Nº Usuarios: 0", -5, 450, panelDerecha.getWidth(), 80, null, null, sRecursos.getMontserratPlain(10), null, sRecursos.getGRANATE(), null, "r");
        panelDerecha.add(textoNumeroUsuarios);

        /* Texto login */
        textoLogin = sObjGraficos.construirJLabel("Iniciar Sesión", 0, 25, panelDerecha.getWidth(), 80, null, null, sRecursos.getMonserratBold(22), null, sRecursos.getGRANATE(), null, "c");
        panelDerecha.add(textoLogin);

        /* Label informativo; nombre y contraseña correctos */
        textoComprobacion = sObjGraficos.construirJLabel("", 0, 95, 400, 32, null, null, sRecursos.getMonserratItalic(13), null, sRecursos.getGRANATE(), null, "c");
        panelDerecha.add(textoComprobacion);
    }

    // Getters para utilizar los componentes en el controlador
    public JButton getBotonRegistrar(){
        return botonRegistrar;
    }
    public JButton getBotonEntrar(){
        return botonEntrar;
    }
    public JTextField getCuadroUsuario(){
        return cuadroUsuario;
    }
    public JPasswordField getCuadroPassword(){
        return cuadroPassword;
    }
    public JLabel getTextoLogin(){
        return textoLogin;
    }
    public JLabel getTextoComprobacion(){
        return textoComprobacion;
    }
    public Frame getFrameLoginTemplate(){
        return this;
    }

    public JLabel getTextoNumeroUsuarios() {
        return textoNumeroUsuarios;
    }
}