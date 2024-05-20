package app.view.principal;

import app.model.tareas.GestorTareas;
import app.model.usuarios.GestorUsuarios;
import app.model.usuarios.Usuario;
import app.view.login.InterfazLogin;
import services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Interfaz fija de la aplicación. Contiene el menú principal y la barra supeior.
 * En ella aparecen el resto de las vistas del programa.
 */
public class InterfazPrincipal extends JFrame {
    private final Recursos sRecursos;

    private final GestorTareas gestorTareas;

    private int xRelativoFrame, yRelativoFrame, xRelativoPantalla, yRelativoPantalla;

    private JPanel panelMenu, panelCentral, panelSuperior, panelPrincipal;
    private JButton botonInicio, botonAjustes, botonCerrarSesion, botonTareas, botonPomodoro, botonMatrix;
    private JButton botonCerrar, botonMinimizar, botonMaximizar;
    private VistaTareas panelTareas;
    private VistaMatrix panelMatrix;
    private VistaPomodoro panelPomodoro;

    private CardLayout cardLayout;
    private ArrayList<JButton> listaBotonesMenu;
    private final Dimension dimensionPantallaCompleta, dimensionPantallaNormal;

    private String textoBotonActual = "";


    public InterfazPrincipal(GestorTareas gestorTareas) {
        this.sRecursos = Recursos.getService();
        this.gestorTareas = gestorTareas;

        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        dimensionPantallaCompleta = new Dimension(screenSize.width, screenSize.height-1);
        dimensionPantallaNormal = new Dimension(1100, 650);

        this.setLayout(new BorderLayout());
        this.setSize(dimensionPantallaNormal);              // Debe empezar en dimension pequeña para evitar errores y posteriormente redimensionar a completa
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setIconImage(sRecursos.getImagenLogo2().getImage());

        crearPaneles();

        crearBotonesVentana();
        crearBotonesMenu();
        botonesActionListener();
        botonesChangeListener();

        redimensionarPaneles();
        moverVentana();

        this.setVisible(true);

        setSize(dimensionPantallaCompleta);     // Línea necesaria para redimensionar sin errores
    }

    /**
     * Crea los paneles principales de la interfaz.
     */
    private void crearPaneles(){
        panelMenu = templatePanelesPrincipales("menu");
        panelCentral = templatePanelesPrincipales("central");
        panelSuperior = templatePanelesPrincipales("superior");
        panelPrincipal = templatePanelesPrincipales("principal");
        this.panelTareas = new VistaTareas(gestorTareas);
        this.panelMatrix = new VistaMatrix();
        this.panelPomodoro = new VistaPomodoro();

        cardLayout = new CardLayout();
        panelPrincipal.setLayout(cardLayout);

        this.add(panelMenu, BorderLayout.WEST);
        this.add(panelCentral, BorderLayout.CENTER);
        panelCentral.add(panelSuperior, BorderLayout.NORTH);
        panelCentral.add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.add(panelTareas, "Tareas");
        panelPrincipal.add(panelPomodoro, "Pomodoro");
        panelPrincipal.add(panelMatrix, "Matrix");
    }

    /**
     * Crea los botones de control de la ventana principal con el método estático construirBotonesVentana y los añade
     */
    private void crearBotonesVentana() {
        botonMinimizar = construirBotonesVentana("minimizar");
        botonMaximizar = construirBotonesVentana("maximizar");
        botonCerrar = construirBotonesVentana("cerrar");

        panelSuperior.add(botonMinimizar);
        panelSuperior.add(botonMaximizar);
        panelSuperior.add(botonCerrar);
    }

    /**
     * Crea los botones del menú principal con el método templateBotonesMEnu y los añade
     */
    private void crearBotonesMenu() {
        botonInicio = templateBotonesMenu("Inicio");
        botonTareas = templateBotonesMenu("Tareas");
        botonMatrix = templateBotonesMenu("Matrix");
        botonPomodoro = templateBotonesMenu("Pomodoro");
        botonAjustes = templateBotonesMenu("Ajustes");
        botonCerrarSesion = templateBotonesMenu("Cerrar Sesión");

        panelMenu.add(botonInicio);
        panelMenu.add(botonTareas);
        panelMenu.add(botonMatrix);
        panelMenu.add(botonPomodoro);
        panelMenu.add(Box.createVerticalGlue());   //espacio entre botones
        panelMenu.add(botonAjustes);
        panelMenu.add(botonCerrarSesion);

        addBotonesALista();
    }

    /**
     * Añade ActionListener a los botones.
     * Permite cambiar la vista al pulsar un botón del menú y cambia su tamaño o desconecta al usuario.
     */
    private void botonesActionListener() {
        for (JButton boton : listaBotonesMenu){
            boton.addActionListener(e -> {
                String textoBoton = boton.getText();

                switch (textoBoton) {
                    case "Cerrar Sesión":   // Si el botón es Cerrar Sesión, se desconecta al usuario y vuelve a la ventana de login
                        dispose();
                        Usuario.desconectarUsuario();
                        new InterfazLogin(new GestorUsuarios());
                        return;
                    case "Inicio":      // Si el botón es Inicio, contrae el resto de botones
                        contraerBotones(listaBotonesMenu);
                        break;
                    case "Tareas":      // Si el botón es Tareas, se cambia el card de la columna Información Extra de la VistaTareas y continua a default
                        panelTareas.setCardGeneral();
                    default:            // Si es cualquier otro botón, expande el botón seleccionado y contrae el resto de botones
                        contraerBotones(listaBotonesMenu);
                        boton.setPreferredSize(new Dimension(getWidth(), 75));
                }
                cardLayout.show(panelPrincipal, textoBoton);        // Muestra la ventana del botón seleccionado en el menú principal
                panelMenu.revalidate();

                textoBotonActual = textoBoton;    // Guarda el texto del botón seleccionado para mantenerlo con el color 'seleccionado'
            });
        }
    }

    /**
     * Utilizado en botonesActionListener para contraer los botones que ya no están seleccionados.
     */
    private void contraerBotones(ArrayList<JButton> listaBotonesMenuConVista) {
        for (JButton boton : listaBotonesMenuConVista) {
            boton.setPreferredSize(new Dimension(getWidth(), 50));
            if (boton.getText().equals("Inicio")){
                continue;
            }
            boton.setBackground(sRecursos.getGRANATE());
        }
    }

    /**
     * Añade ChangeListener a los botones.
     * Cambia el color de fondo del botón al pasar el ratón por encima y permite que el botón seleccionado mantenga ese color.
     */
    private void botonesChangeListener() {
        for (JButton boton : listaBotonesMenu) {
            boton.addChangeListener(e -> {
                /* Si el botón es Inicio, no cambia de color */
                if (boton.getText().equals("Inicio")) {
                    return;
                }
                /* Cambia el color del botón al pasar el ratón por encima */
                if (boton.getModel().isRollover()) {
                    boton.setBackground(sRecursos.getGRANATE().brighter());
                }
                /* Mantiene el color del botón seleccionado */
                else if (textoBotonActual.equals(boton.getText())) {
                    boton.setBackground(sRecursos.getGRANATE().brighter());
                }
                /* Restaura el color del botón al sacar el ratón de encima */
                else {
                    boton.setBackground(sRecursos.getGRANATE());
                }
            });
        }
    }

    /**
     * Método para redimensionar el panelMenu y que se ajusten el resto en consecuencia.
     * Utiliza invokeLater para que se ejecute tras el resto de operaciones.
     */
    private void redimensionarPaneles() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        panelMenu.setPreferredSize(new Dimension((int)(getWidth()*0.15), getHeight()));
                        panelMenu.revalidate();
                    }
                });
            }
        });
    }

    /**
     * Método para mover y maximizar la ventana.
     */
    private void moverVentana(){
        panelSuperior.addMouseListener(new MouseAdapter() {

            /* Permite maximizar o minimizar la ventana dando doble click sobre el panel superior */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    if (getSize().getWidth() == dimensionPantallaCompleta.width && getSize().getHeight() == dimensionPantallaCompleta.height) {
                        setSize(dimensionPantallaNormal);
                        setLocationRelativeTo(null);
                    } else {
                        setSize(dimensionPantallaCompleta);
                        setLocation(0,0);
                    }
                }
            }

            /* Obtiene la posición del ratón relativa al frame */
            @Override
            public void mousePressed(MouseEvent e) {
                xRelativoFrame = e.getX() + (int)(getWidth()*0.15);     // Suma el tamaño del menú para obtener la posición x relativa al frame real
                yRelativoFrame = e.getY();
            }
        });

        panelSuperior.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (getSize().getWidth() == dimensionPantallaCompleta.width && getSize().getHeight() == dimensionPantallaCompleta.height) {
                    int anchoAntes = getWidth();
                    setSize(dimensionPantallaNormal);
                    double proporcionDiferencia = 1.0*anchoAntes/getWidth();            // La diferencia entre la ventana maximizada y no, aprox: 1920/1100 = 1.7
                    xRelativoFrame =(int) Math.round((e.getX() + getWidth()*0.15) / proporcionDiferencia);  // Divide la xRelativoFrame entre la proporción para obtener la nueva posición de x al cambiar de tamaño la ventana
                }
                xRelativoPantalla = e.getXOnScreen();
                yRelativoPantalla = e.getYOnScreen();
                setLocation(xRelativoPantalla - xRelativoFrame, yRelativoPantalla - yRelativoFrame);
            }
        });
    }

    /**
     * Construye los paneles principales (menú, central, superior y principal).
     * @param tipo El tipo de panel a construir. Puede ser "menu", "central", "principal" o "superior".
     * @return El panel construido.
     */
    private JPanel templatePanelesPrincipales(String tipo){
        JPanel panel = new JPanel();
        panel.setCursor(sRecursos.getCursorNormal());
        switch (tipo){
            case "menu":
                panel.setBackground(sRecursos.getGRANATE());
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setPreferredSize(new Dimension((int)(getWidth()*0.15), getHeight()));
                break;
            case "central":
                panel.setLayout(new BorderLayout());
                break;
            case "principal":
                break;
            case "superior":
                panel.setBackground(sRecursos.getBLANCO());
                panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                break;
        }
        return panel;
    }

    /**
     * Construye los JButton del Menú con el diseño requerido.
     * @param texto El texto del botón.
     * @return Un JButton con el diseño personalizado.
     */
    private JButton templateBotonesMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(getWidth(), 50));
        boton.setMaximumSize(new Dimension(getWidth(), 50));
        boton.setFocusable(false);
        boton.setCursor(sRecursos.getCursorMano());
        boton.setFont(sRecursos.getMonserratBold(Recursos.SIZE_LETRA_BOTON));
        if (texto.equals("Inicio")){
            boton.setBackground(sRecursos.getBLANCO());
            boton.setForeground(sRecursos.getGRANATE());
        } else {
            boton.setBackground(sRecursos.getGRANATE());
            boton.setForeground(sRecursos.getBLANCO());
        }
        boton.setBorder(null);

        return boton;
    }

    /**
     * Construye los JButton de las acciones de la ventana (maximizar, minimizar, cerrar).
     * El botón puede ser de tipo "minimizar", "maximizar" o "cerrar", y cambia su diseño en consecuencia.
     *
     * @param tipo El tipo de botón a construir. Puede ser "minimizar", "maximizar" o "cerrar".
     * @return Un JButton con un diseño personalizado según el tipo especificado.
     */
    private JButton construirBotonesVentana(String tipo) {
        JButton boton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.clearRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
                g2.setColor(sRecursos.getGRANATE());
                g2.setStroke(new BasicStroke(3));
                switch (tipo) {
                    case "minimizar":
                        g2.drawLine(10, getHeight() / 2 + 3, getWidth() - 10, getHeight() / 2 + 3);
                        break;
                    case "maximizar":
                        g2.drawLine(11, 9, 11, 6);
                        g2.drawLine(11, 6, getWidth() - 9, 6);
                        g2.drawLine(getWidth() - 8, 6, getWidth() - 8, getHeight() - 11);
                        g2.drawLine(getWidth() - 9, getHeight() - 11, getWidth() - 14, getHeight() - 11);
                        g2.drawRect(6, 12, getWidth() - 20, getHeight() - 18);
                        break;
                    case "cerrar":
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.drawLine(8, 8, getWidth() - 8, getHeight() - 8);
                        g2.drawLine(getWidth() - 8, 8, 8, getHeight() - 8);
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                        break;
                }
                g2.dispose();
            }
        };
        boton.setPreferredSize(new Dimension(40, 40));
        boton.setBackground(sRecursos.getBLANCO());
        boton.setCursor(sRecursos.getCursorMano());
        boton.setBorder(null);

        addActionListenerBotonesVentana(boton, tipo);

        return boton;
    }

    /**
     * Añade ActionListener a los botones de la ventana.
     * Permite minimizar, maximizar o cerrar la ventana.
     *
     * @param boton El botón al que añadir el ActionListener.
     * @param tipo El tipo de botón. Puede ser "minimizar", "maximizar" o "cerrar".
     */
    private void addActionListenerBotonesVentana(JButton boton, String tipo) {
        boton.addActionListener(e -> {
            switch (tipo){
                case "minimizar":
                    setState(JFrame.ICONIFIED);
                    break;
                case "maximizar":
                    if (getSize().getWidth() == dimensionPantallaCompleta.width && getSize().getHeight() == dimensionPantallaCompleta.height) {
                        setSize(dimensionPantallaNormal);
                        setLocationRelativeTo(null);
                    } else {
                        setSize(dimensionPantallaCompleta);
                        setLocation(0,0);
                    }
                    break;
                case "cerrar":
                    dispose();
                    break;
            }
        });
    }

    /**
     * Añade los botones del menú a una lista para poder aplicarle las funciones.
     */
    private void addBotonesALista(){
        this.listaBotonesMenu = new ArrayList<>();
        listaBotonesMenu.add(botonInicio);
        listaBotonesMenu.add(botonTareas);
        listaBotonesMenu.add(botonMatrix);
        listaBotonesMenu.add(botonPomodoro);
        listaBotonesMenu.add(botonAjustes);
        listaBotonesMenu.add(botonCerrarSesion);
    }
}