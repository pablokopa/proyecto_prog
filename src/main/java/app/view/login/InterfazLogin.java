package app.view.login;

import app.view.principal.InterfazPrincipal;
import services.Recursos;
import app.model.usuarios.GestorUsuarios;
import services.ObjGraficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Interfaz del login de la aplicación.
 */
public class InterfazLogin extends JFrame{
    private ObjGraficos sObjGraficos;
    private Recursos sRecursos;
    private GestorUsuarios gestorUsuarios;

    private int mouseX, x;
    private int mouseY, y;

    private JLabel textoLogin, textoComprobacion, textoNumeroUsuarios;
    private JLabel labelLogo, labelUsuario, labelPassword;

    private JTextField cuadroUsuario;
    private JPasswordField cuadroPassword;
    private JButton botonEntrar, botonRegistrar, botonCerrar;
    private JPanel panelDerecha, panelIzquierda;

    /**
     * Constructor de la clase InterfazLogin.
     * Inicializa los componentes de la interfaz de usuario y configura la ventana.
     * @param gestorUsuarios Gestor de usuarios para manejar la lógica de inicio de sesión y registro.
     */
    public InterfazLogin(GestorUsuarios gestorUsuarios){
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
        setIconImage(sRecursos.getImagenLogo2().getImage());
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

        botonCerrar = ObjGraficos.construirBotonesVentana("cerrar", sRecursos.getBLANCO(), sRecursos.getGRANATE(), this);
        botonCerrar.setBounds(360,5,40,40);
        panelDerecha.add(botonCerrar);

        /* Botón de registrarse */
        botonRegistrar = sObjGraficos.construirJButton("Registrarse", (panelDerecha.getWidth() - 150) / 2, 370, 150, 40, sRecursos.getCursorMano(), null, sRecursos.getMonserratBold(14), sRecursos.getGRANATE(), Color.WHITE, null, "", true);
        panelDerecha.add(botonRegistrar);

        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = cuadroUsuario.getText();
                String passwordUsuario = String.valueOf(cuadroPassword.getPassword());

                if (gestorUsuarios.registrarUsuario(nombreUsuario, passwordUsuario, textoComprobacion, textoLogin)){  // Se intenta registrar el usuario, si fue registrado correctamente devuelve true
                    textoComprobacion.setText("Usuario registrado correctamente");
                    textoLogin.setText("Bienvenido, "+ cuadroUsuario.getText()+"!");
                    cuadroUsuario.setText("");
                    cuadroPassword.setText("");
                    textoNumeroUsuarios.setText("Nº Usuarios: "+gestorUsuarios.contarUsuarios());
                }
            }
        });

        /* Botón de entrar */
        botonEntrar = sObjGraficos.construirJButton("Entrar", (panelDerecha.getWidth() - 250) / 2, 300, 250, 45, sRecursos.getCursorMano(), null, sRecursos.getMonserratBold(14), sRecursos.getGRANATE(), Color.WHITE, null, "", true);
        panelDerecha.add(botonEntrar);

        botonEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = cuadroUsuario.getText();
                String passwordUsuario = String.valueOf(cuadroPassword.getPassword());

                if (gestorUsuarios.conectarUsuario(nombreUsuario, passwordUsuario, textoComprobacion, textoLogin)) {   // Se intenta conectar al usuario; si no se conectó, se cambia el textoLogin
                    dispose();
                    new InterfazPrincipal();
                }else{
                    textoLogin.setText("Inicio de sesión fallido..");
                }
            }
        });
    }

    /**
     * Método para crear las etiquetas.
     * Crea y configura las etiquetas para el logo, el botón de cerrar, el icono de usuario y el icono de contraseña.
     */
    public void crearJLabels(){
        /* Imagen logo */
        labelLogo = sObjGraficos.construirJLabel(null, 50, 125, 550, 250, null, sRecursos.getImagenLogo(), null, null, null, null, "");
        panelIzquierda.add(labelLogo);

        /* Imagen candado password */
        labelPassword = sObjGraficos.construirJLabel(null, 25, 230, 32, 32, null, sRecursos.getImagenPassword(), null, null, null, null, "");
        panelDerecha.add(labelPassword);

        /* Imagen logo usuario */
        labelUsuario = sObjGraficos.construirJLabel(null, 25, 160, 32, 32, null, sRecursos.getImagenUsuario(), null, null, null, null, "");
        panelDerecha.add(labelUsuario);

        /* Contador usuarios */
        textoNumeroUsuarios = sObjGraficos.construirJLabel("Nº Usuarios: "+gestorUsuarios.contarUsuarios(), -5, 450, panelDerecha.getWidth(), 80, null, null, sRecursos.getMontserratPlain(10), null, sRecursos.getGRANATE(), null, "r");
        panelDerecha.add(textoNumeroUsuarios);

        /* Texto login */
        textoLogin = sObjGraficos.construirJLabel("Iniciar Sesión", 0, 25, panelDerecha.getWidth(), 80, null, null, sRecursos.getMonserratBold(22), null, sRecursos.getGRANATE(), null, "c");
        panelDerecha.add(textoLogin);

        /* Label informativo; nombre y contraseña correctos */
        textoComprobacion = sObjGraficos.construirJLabel("", 0, 95, 400, 32, null, null, sRecursos.getMonserratItalic(13), null, sRecursos.getGRANATE(), null, "c");
        panelDerecha.add(textoComprobacion);
    }
}