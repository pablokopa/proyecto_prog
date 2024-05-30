package app.view.login;

import app.controller.ControladorUsuarios;
import app.model.CodigoError;
import app.view.principal.InterfazPrincipal;
import services.Recursos;
import app.model.usuarios.GestorUsuarios;
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
    private final GestorUsuarios gestorUsuarios;
    private final ControladorUsuarios controladorUsuarios;

    private int mouseX, x;
    private int mouseY, y;

    private JButton botonCerrar, botonRegistrar, botonEntrar;
    private JLabel labelLogo, labelUsuario, labelPassword;
    private JLabel textoLogin, textoComprobacion, textoNumeroUsuarios;
    private JTextField cuadroUsuario;
    private JPasswordField cuadroPassword;
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
        this.controladorUsuarios = new ControladorUsuarios(gestorUsuarios);

        this.moverVentana();
        this.crearJPanels();
        this.crearFieldsDatosUsuario();
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
    public void crearJButtons(GestorUsuarios gestorUsuarios){

        botonCerrar = construirBotonCerrar();
        botonCerrar.setBounds(360,5,35,35);
        panelDerecha.add(botonCerrar);

        /* Botón de registrarse */
        botonRegistrar = sObjGraficos.construirJButton("Registrarse", (panelDerecha.getWidth() - 150) / 2, 370, 150, 40, sRecursos.getCursorMano(), null, sRecursos.getMontserratBold(14), sRecursos.getGRANATE(), Color.WHITE, null, "", true);
        panelDerecha.add(botonRegistrar);

        botonRegistrar.addActionListener(e -> {
            String nombreUsuario = cuadroUsuario.getText();
            String passwordUsuario = String.valueOf(cuadroPassword.getPassword());

            int codigoError = controladorUsuarios.registrarUsuario(nombreUsuario, passwordUsuario);
            if (codigoError != CodigoError.SIN_ERROR) {
                textoLogin.setText("Registro fallido..");
                String mensajeError = getMensajeError(codigoError);
                textoComprobacion.setText(mensajeError);
                sRecursos.crearTimer(textoComprobacion);
            } else {
                textoComprobacion.setText("Usuario registrado correctamente");
                textoLogin.setText("Bienvenido, "+ cuadroUsuario.getText()+"!");
                cuadroUsuario.setText("");
                cuadroPassword.setText("");
                textoNumeroUsuarios.setText("Nº Usuarios: "+gestorUsuarios.contarUsuarios());
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
                String mensajeError = getMensajeError(codigoError);
                textoComprobacion.setText(mensajeError);
                sRecursos.crearTimer(textoComprobacion);
            } else {
                dispose();
                new InterfazPrincipal();
            }
        });
    }

    private static String getMensajeError(int codigoError) {
        String mensajeError = "";

        switch (codigoError) {
            case CodigoError.ERROR_SIN_CONEXION -> mensajeError = "No hay conexión";
            case CodigoError.ERROR_USUARIO_YA_EXISTE -> mensajeError = "El nombre de usuario ya existe";
            case CodigoError.ERROR_NOMBRE_CORTO -> mensajeError = "El nombre de usuario debe tener al menos 3 carácteres";
            case CodigoError.ERROR_NOMBRE_LARGO -> mensajeError = "El nombre de usuario no puede tener más de 40 carácteres";
            case CodigoError.ERROR_PASSWORD_CORTA -> mensajeError = "La contraseña debe tener al menos 4 carácteres";
            case CodigoError.ERROR_PASSWORD_LARGA -> mensajeError = "La contraseña debe tener menos de 50 carácteres";
            case CodigoError.ERROR_PASSWORD_CON_ESPACIOS -> mensajeError = "La contraseña no puede tener espacios en blanco";
            case CodigoError.ERROR_PASSWORD_CARACTERES_RAROS -> mensajeError = "La contraseña no puede tener carácteres extraños";
            case CodigoError.ERROR_USUARIO_NO_REGISTRADO -> mensajeError = "El usuario no está registrado";
            case CodigoError.ERROR_PASSWORD_INCORRECTA -> mensajeError = "La contraseña es incorrecta";
        }
        return mensajeError;
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

        /* Texto login */
        textoLogin = sObjGraficos.construirJLabel("Iniciar Sesión", 0, 25, panelDerecha.getWidth(), 80, null, null, sRecursos.getMontserratBold(22), null, sRecursos.getGRANATE(), null, "c");
        panelDerecha.add(textoLogin);

        /* Label informativo; nombre y contraseña correctos */
        textoComprobacion = sObjGraficos.construirJLabel("", 0, 95, 400, 32, null, null, sRecursos.getMontserratItalic(13), null, sRecursos.getGRANATE(), null, "c");
        panelDerecha.add(textoComprobacion);

        /* Contador usuarios */
        textoNumeroUsuarios = sObjGraficos.construirJLabel("", -5, 450, panelDerecha.getWidth(), 80, null, null, sRecursos.getMontserratPlain(10), null, sRecursos.getGRANATE(), null, "r");
        panelDerecha.add(textoNumeroUsuarios);
    }

    public void actualizarContadorUsuarios(){
        textoNumeroUsuarios.setText("Nº Usuarios: "+gestorUsuarios.contarUsuarios());
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