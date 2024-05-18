package app.view.principal;

import app.model.tareas.GestorTareas;
import app.model.usuarios.GestorUsuarios;
import app.model.usuarios.Usuario;
import app.view.login.InterfazLogin;
import services.ObjGraficos;
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

    GestorTareas gestorTareas;
    Usuario usuario;

    private int xRelativoFrame, yRelativoFrame, xRelativoPantalla, yRelativoPantalla;

    private JPanel panelMenu, panelCentral, panelSuperior, panelPrincipal;
    private JPanel panelTareas, panelMatrix, panelPomodoro;
    private JButton botonInicio, botonAjustes, botonCerrarSesion, botonTareas, botonPomodoro, botonMatrix;
    private JButton botonCerrar, botonMinimizar, botonMaximizar;

    private CardLayout cardLayout;

    private String textoBotonActual = "";


    public InterfazPrincipal(GestorTareas gestorTareas) {
        this.sRecursos = Recursos.getService();

        this.gestorTareas = gestorTareas;
        this.usuario = gestorTareas.getUsuario();

        this.setLayout(new BorderLayout());
        this.setSize(1100, 650);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(this);
        this.setUndecorated(true);
        this.setIconImage(sRecursos.getImagenLogo2().getImage());

        crearPaneles();

        crearBotonesVentana();
        crearBotonesMenu();
        botonesActionListener();
        botonesChangeListener();

        redimensionarPaneles();
        moverVentana();
        cursorBorde();

        this.setVisible(true);
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
     * Crea los botones del menú principal y los de control de ventana (max, min, cerrar).
     */
    private void crearBotonesVentana() {
        botonMinimizar = ObjGraficos.construirBotonesVentana("minimizar", sRecursos.getBLANCO(), sRecursos.getGRANATE(), this);
        botonMaximizar = ObjGraficos.construirBotonesVentana("maximizar", sRecursos.getBLANCO(), sRecursos.getGRANATE(), this);
        botonCerrar = ObjGraficos.construirBotonesVentana("cerrar", sRecursos.getBLANCO(), sRecursos.getGRANATE(), this);

        panelSuperior.add(botonMinimizar);
        panelSuperior.add(botonMaximizar);
        panelSuperior.add(botonCerrar);
    }

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
    }

    /**
     * Añade ActionListener a los botones.
     * Permite cambiar la vista al pulsar un botón del menú y cambia su tamaño o desconecta al usuario.
     */
    private void botonesActionListener() {
        /* Añade los botones necesarios a un ArrayList temporal para reducir la cantidad de código */
        ArrayList<JButton> listaBotonesMenuConVista = new ArrayList<>();
        listaBotonesMenuConVista.add(botonInicio);
        listaBotonesMenuConVista.add(botonTareas);
        listaBotonesMenuConVista.add(botonMatrix);
        listaBotonesMenuConVista.add(botonPomodoro);
        listaBotonesMenuConVista.add(botonAjustes);
        listaBotonesMenuConVista.add(botonCerrarSesion);

        for (JButton boton : listaBotonesMenuConVista){
            boton.addActionListener(e -> {
                String textoBoton = boton.getText();

                /* Si el botón es Cerrar Sesión desconecta al usuario y vuelve a la ventana de login. Los botones no cambian de tamaño porque no es una vista */
                if (textoBoton.equals("Cerrar Sesión")) {
                    System.out.println("cerrar");
                    dispose();
                    Usuario.desconectarUsuario();
                    new InterfazLogin(new GestorUsuarios());
                    return;
                }
                /* Si el botón es Inicio se cambia a la vista inicio y contrae todos los botones. Inicio no se expande por estética */
                else if (textoBoton.equals("Inicio")){
                    System.out.println("inicio");
                    contraerBotones(listaBotonesMenuConVista);
                }
                /* Si es cualquier otro botón, se cambia a la vista seleccionada, expande el botón seleccionado y contrae el resto de botones */
                else {
                    System.out.println("otro");
                    contraerBotones(listaBotonesMenuConVista);
                    boton.setPreferredSize(new Dimension(getWidth(), 75));
                }
                cardLayout.show(panelPrincipal, textoBoton);        // Muestra la ventana del botón seleccionado
                panelMenu.revalidate();

                /* Guarda el texto del botón seleccionado para evitar que pierda el color 'seleccionado' por el ChangeListener */
                textoBotonActual = textoBoton;
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
        /* Añade los botones necesarios a un ArrayList temporal para reducir la cantidad de código */
        ArrayList<JButton> listaBotonesMenuTodos = new ArrayList<>();
        listaBotonesMenuTodos.add(botonTareas);
        listaBotonesMenuTodos.add(botonMatrix);
        listaBotonesMenuTodos.add(botonPomodoro);
        listaBotonesMenuTodos.add(botonAjustes);
        listaBotonesMenuTodos.add(botonCerrarSesion);

        for (JButton boton : listaBotonesMenuTodos) {
            boton.addChangeListener(e -> {
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
     * Método para poder mover la ventana.
     * Si la ventana está maximizada, vuelve a su estado normal antes de moverla.
     */
    private void moverVentana(){
        panelSuperior.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xRelativoFrame = e.getX() + (int)(getWidth()*0.15);
                yRelativoFrame = e.getY();
            }
        });

        panelSuperior.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (getExtendedState()  == JFrame.MAXIMIZED_BOTH) {
                    int anchoAntes = getWidth();
                    setExtendedState(JFrame.NORMAL);
                    double proporcionDiferencia = 1.0*anchoAntes/getWidth();
                    xRelativoFrame =(int) Math.round((e.getX() + getWidth()*0.15) / proporcionDiferencia);
                    System.out.println(xRelativoFrame);
                }
                xRelativoPantalla = e.getXOnScreen();
                yRelativoPantalla = e.getYOnScreen();
                setLocation(xRelativoPantalla - xRelativoFrame, yRelativoPantalla - yRelativoFrame);
            }
        });
    }

    private void cursorBorde () {
        this.addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                super.mouseDragged(e);
//            }

            @Override
            public void mouseMoved(MouseEvent e) {
//                super.mouseMoved(e);
                if (e.getX() == getWidth()) {
                    System.out.println("borde derecho");
                }
                if (e.getX() == 0){
                    System.out.println("borde izquierdo");
//                    panelMenu.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                }
                if (e.getY() == getHeight()) {
                    System.out.println("borde inferior");
                }
                if (e.getY() == 0) {
                    System.out.println("borde superior");
                }
            }
        });
    }

    /**
     * Construye los paneles principales de la interfaz fija (menú, central, superior y principal).
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
}