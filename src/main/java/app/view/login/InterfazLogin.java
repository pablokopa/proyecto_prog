package app.view.login;

import app.controller.ControladorTareas;
import app.controller.ControladorUsuarios;
import app.model.CodigoError;
import app.model.tareas.GestorTareas;
import app.view.principal.InterfazPrincipal;
import services.Recursos;
import services.ObjGraficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Interfaz del login de la aplicación.
 */
public class InterfazLogin extends JFrame{
    private final ObjGraficos sObjGraficos;
    private final Recursos sRecursos;
    private final ControladorUsuarios controladorUsuarios;

    private int mouseX, x;
    private int mouseY, y;

    private JButton botonEntrar;
    private JLabel textoLogin, textoComprobacion, textoNumeroUsuarios;
    private JTextField cuadroUsuario;
    private JPasswordField cuadroPassword;
    private JPanel panelDerecha, panelIzquierda;

    /**
     * Constructor de la clase InterfazLogin.
     * Inicializa los componentes de la interfaz de usuario y configura la ventana.
     */
    public InterfazLogin(ControladorUsuarios controladorUsuarios){
        sObjGraficos = ObjGraficos.getService();
        sRecursos = Recursos.getService();

        this.controladorUsuarios = controladorUsuarios;

        this.moverVentana();
        this.crearJPanels();
        this.crearFieldsDatosUsuario();
        this.crearJButtons();
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

        /* Botón que se pulsará al presionar Enter */
        getRootPane().setDefaultButton(botonEntrar);

        this.actualizarContadorUsuarios();
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
     * Crea y configura los campos de texto para el nombre de usuario y la contraseña.
     */
    public void crearFieldsDatosUsuario(){
        cuadroUsuario = sObjGraficos.construirJTextField(null, (panelDerecha.getWidth() - 260) / 2, 150, 260, 40, sRecursos.getMontserratBold(13), Color.WHITE, Color.DARK_GRAY, sRecursos.getGRANATE(), sRecursos.getBordeGranate(), "c");
        panelDerecha.add(cuadroUsuario);

        cuadroPassword = sObjGraficos.construirJPasswordField((panelDerecha.getWidth() - 260) / 2, 220, 260, 40, Color.WHITE, Color.DARK_GRAY, sRecursos.getGRANATE(), sRecursos.getBordeGranate(), "c");
        panelDerecha.add(cuadroPassword);
    }

    /**
     * Método para crear los botones.
     * Crea y configura los botones para el logo, el botón de cerrar, el icono de usuario y el icono de contraseña.
     */
    public void crearJButtons(){

        JButton botonCerrar = construirBotonCerrar();
        botonCerrar.setBounds(360,5,35,35);
        panelDerecha.add(botonCerrar);

        /* Botón de registrarse */
        JButton botonRegistrar = sObjGraficos.construirJButton("Registrarse", (panelDerecha.getWidth() - 150) / 2, 370, 150, 40, sRecursos.getCursorMano(), null, sRecursos.getMontserratBold(14), sRecursos.getGRANATE(), Color.WHITE, null, "", true);
        panelDerecha.add(botonRegistrar);

        botonRegistrar.addActionListener(e -> {
            String nombreUsuario = cuadroUsuario.getText();
            String passwordUsuario = String.valueOf(cuadroPassword.getPassword());

            int codigoError = controladorUsuarios.registrarUsuario(nombreUsuario, passwordUsuario);
            if (codigoError != CodigoError.SIN_ERROR) {
                textoLogin.setText("Registro fallido..");
                textoComprobacion.setText(getMensajeError(codigoError));
                sRecursos.crearTimer(textoComprobacion);
            } else {
                textoComprobacion.setText("Usuario registrado correctamente");
                textoLogin.setText("Bienvenido, "+nombreUsuario+"!");
                cuadroUsuario.setText("");
                cuadroPassword.setText("");
                textoNumeroUsuarios.setText("Usuarios registrados: "+controladorUsuarios.contarUsuarios());
            }
        });

        /* Botón de entrar */
        botonEntrar = sObjGraficos.construirJButton("Entrar", (panelDerecha.getWidth() - 250) / 2, 300, 250, 45, sRecursos.getCursorMano(), null, sRecursos.getMontserratBold(14), sRecursos.getGRANATE(), Color.WHITE, null, "", true);
        panelDerecha.add(botonEntrar);

        botonEntrar.addActionListener(e -> {
            String nombreUsuario = cuadroUsuario.getText();
            String passwordUsuario = String.valueOf(cuadroPassword.getPassword());

            int codigoError = controladorUsuarios.conectarUsuario(nombreUsuario, passwordUsuario);
            if (codigoError != CodigoError.SIN_ERROR) {
                textoLogin.setText("Inicio de sesión fallido..");
                textoComprobacion.setText(getMensajeError(codigoError));
                sRecursos.crearTimer(textoComprobacion);
            } else {
                dispose();
                GestorTareas gestorTareas = new GestorTareas();
                ControladorTareas controladorTareas = new ControladorTareas(gestorTareas);
                new InterfazPrincipal(controladorTareas);
            }
        });
    }

    /**
     * Método para obtener el mensaje de error.
     * Devuelve un mensaje de error en función del código de error.
     * @param codigoError Código de error
     * @return Mensaje de error
     */
    private String getMensajeError(int codigoError) {
        String mensajeError = "";

        switch (codigoError) {
            case CodigoError.ERROR_SIN_CONEXION -> mensajeError = "No hay conexión";
            case CodigoError.ERROR_USUARIO_YA_EXISTE -> mensajeError = "El nombre de usuario ya existe";
            case CodigoError.ERROR_USUARIO_NOMBRE_CORTO -> mensajeError = "El nombre de usuario debe tener al menos 3 carácteres";
            case CodigoError.ERROR_USUARIO_NOMBRE_LARGO -> mensajeError = "El nombre de usuario no puede tener más de 40 carácteres";
            case CodigoError.ERROR_USUARIO_PASSWORD_CORTA -> mensajeError = "La contraseña debe tener al menos 4 carácteres";
            case CodigoError.ERROR_USUARIO_PASSWORD_LARGA -> mensajeError = "La contraseña debe tener menos de 50 carácteres";
            case CodigoError.ERROR_USUARIO_PASSWORD_CON_ESPACIOS -> mensajeError = "La contraseña no puede tener espacios en blanco";
            case CodigoError.ERROR_USUARIO_PASSWORD_CARACTERES_RAROS -> mensajeError = "La contraseña no puede tener carácteres extraños";
            case CodigoError.ERROR_USUARIO_NO_REGISTRADO -> mensajeError = "El usuario no está registrado";
            case CodigoError.ERROR_USUARIO_PASSWORD_INCORRECTA -> mensajeError = "La contraseña es incorrecta";
        }
        return mensajeError;
    }

    /**
     * Método para crear las etiquetas.
     * Crea y configura las etiquetas para el logo, el botón de cerrar, el icono de usuario y el icono de contraseña.
     */
    public void crearJLabels(){
        /* Imagen logo */
        JLabel labelLogo = sObjGraficos.construirJLabel(null, 50, 125, 550, 250, null, sRecursos.getImagenLogo(), null, null, null, null, "");
        panelIzquierda.add(labelLogo);

        /* Imagen candado password */
        JLabel labelPassword = sObjGraficos.construirJLabel(null, 25, 230, 32, 32, null, sRecursos.getImagenPassword(), null, null, null, null, "");
        panelDerecha.add(labelPassword);

        /* Imagen logo usuario */
        JLabel labelUsuario = sObjGraficos.construirJLabel(null, 25, 160, 32, 32, null, sRecursos.getImagenUsuario(), null, null, null, null, "");
        panelDerecha.add(labelUsuario);

        /* Texto login */
        textoLogin = sObjGraficos.construirJLabel("Iniciar Sesión", 0, 25, panelDerecha.getWidth(), 80, null, null, sRecursos.getMontserratBold(22), null, sRecursos.getGRANATE(), null, "c");
        panelDerecha.add(textoLogin);

        /* Label informativo; nombre y contraseña correctos */
        textoComprobacion = sObjGraficos.construirJLabel("", 0, 95, 400, 32, null, null, sRecursos.getMontserratItalic(13), null, sRecursos.getGRANATE(), null, "c");
        panelDerecha.add(textoComprobacion);

        /* Contador usuarios */
        textoNumeroUsuarios = sObjGraficos.construirJLabel("", -5, 450, panelDerecha.getWidth(), 80, null, null, sRecursos.getMontserratItalic(13), null, sRecursos.getGRANATE(), null, "r");
        panelDerecha.add(textoNumeroUsuarios);
    }

    /**
     * Método para actualizar el contador de usuarios.
     * Actualiza el contador de usuarios en función del número de usuarios registrados.
     */
    public void actualizarContadorUsuarios(){
        textoNumeroUsuarios.setText("Usuarios registrados: "+controladorUsuarios.contarUsuarios());
    }

    /**
     * Construye el JButton de cerrar la ventana.
     * @return Un JButton con un diseño personalizado.
     */
    public JButton construirBotonCerrar(){
        JButton boton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.clearRect(0,0,getWidth(),getHeight());
                super.paintComponent(g);
                g2.setColor(sRecursos.getGRANATE());
                g2.setStroke(new BasicStroke(3));
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawLine(8, 8,  getWidth()-8, getHeight()-8);
                g2.drawLine(getWidth()-8, 8, 8, getHeight()-8);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                g2.dispose();
            }
        };
        boton.setPreferredSize(new Dimension(40, 40));
        boton.setBackground(sRecursos.getBLANCO());

        boton.setCursor(sRecursos.getCursorMano());
        boton.setBorder(null);

        /* Listener para cerrar la ventana */
        boton.addActionListener(e -> dispose());

        return boton;
    }
}