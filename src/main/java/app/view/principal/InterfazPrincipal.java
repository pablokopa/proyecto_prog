package app.view.principal;

import app.model.tareas.GestorTareas;
import app.model.usuarios.Usuario;
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

    private int xRaton, yRaton, xNuevo, yNuevo;

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
        crearBotones();
        redimensionarPaneles();
        moverVentana();
        botonesActionListener();
        botonesChangeListener();
        cursorBorde();

        this.setVisible(true);
    }

    /**
     * Crea los paneles principales de la interfaz.
     */
    private void crearPaneles(){
        panelMenu = ObjGraficos.construirPanelesPrincipales("menu", sRecursos.getGRANATE());
        panelCentral = ObjGraficos.construirPanelesPrincipales("central", sRecursos.getGRIS_CLARO());
        panelSuperior = ObjGraficos.construirPanelesPrincipales("superior", sRecursos.getBLANCO());
        panelPrincipal = ObjGraficos.construirPanelesPrincipales("principal", sRecursos.getGRIS_CLARO());
        this.panelTareas = new VistaTareas(gestorTareas);
        this.panelMatrix = new VistaMatrix();
        this.panelPomodoro = new VistaPomodoro();

        cardLayout = new CardLayout();
        panelPrincipal.setLayout(cardLayout);

        this.add(panelMenu, BorderLayout.WEST);
        panelCentral.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelTareas, "Tareas");
        panelPrincipal.add(panelPomodoro, "Pomodoro");
        panelPrincipal.add(panelMatrix, "Matrix");
        panelCentral.add(panelPrincipal, BorderLayout.CENTER);
        this.add(panelCentral, BorderLayout.CENTER);
    }

    /**
     * Crea los botones del menú principal y los de control de ventana (max, min, cerrar).
     */
    private void crearBotones() {
        botonMinimizar = ObjGraficos.construirBotonesVentana("minimizar", sRecursos.getBLANCO(), sRecursos.getGRANATE(), this);
        botonMaximizar = ObjGraficos.construirBotonesVentana("maximizar", sRecursos.getBLANCO(), sRecursos.getGRANATE(), this);
        botonCerrar = ObjGraficos.construirBotonesVentana("cerrar", sRecursos.getBLANCO(), sRecursos.getGRANATE(), this);

        panelSuperior.add(botonMinimizar);
        panelSuperior.add(botonMaximizar);
        panelSuperior.add(botonCerrar);

        botonInicio = ObjGraficos.construirBotonesMenu("Inicio", getWidth(), 50, sRecursos.getBLANCO(), sRecursos.getGRANATE());
        botonTareas = ObjGraficos.construirBotonesMenu("Tareas", getWidth(), 50, sRecursos.getGRANATE(), sRecursos.getBLANCO());
        botonMatrix = ObjGraficos.construirBotonesMenu("Matrix", getWidth(), 50, sRecursos.getGRANATE(), sRecursos.getBLANCO());
        botonPomodoro = ObjGraficos.construirBotonesMenu("Pomodoro", getWidth(), 50, sRecursos.getGRANATE(), sRecursos.getBLANCO());
        botonAjustes = ObjGraficos.construirBotonesMenu("Ajustes", getWidth(), 50, sRecursos.getGRANATE(), sRecursos.getBLANCO());
        botonCerrarSesion = ObjGraficos.construirBotonesMenu("Cerrar Sesión", getWidth(),50, sRecursos.getGRANATE(), sRecursos.getBLANCO());

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
     * Permite cambiar la vista al pulsar un botón del menú y cambia su tamaño.
     */
    private void botonesActionListener() {
        ArrayList<JButton> listaBotonesMenuConVista = new ArrayList<>();
        listaBotonesMenuConVista.add(botonInicio);
        listaBotonesMenuConVista.add(botonTareas);
        listaBotonesMenuConVista.add(botonMatrix);
        listaBotonesMenuConVista.add(botonPomodoro);
        listaBotonesMenuConVista.add(botonAjustes);

        for (JButton boton : listaBotonesMenuConVista){
            boton.addActionListener(e -> {
                String textoBoton = boton.getText();

                if (textoBoton.equals("Inicio")){
                    contraerBotones(listaBotonesMenuConVista);
                }
                else {
                    contraerBotones(listaBotonesMenuConVista);
                    boton.setPreferredSize(new Dimension(getWidth(), 75));
                }
                cardLayout.show(panelPrincipal, textoBoton);
                panelMenu.revalidate();

                // Guarda el texto del botón seleccionado para evitar que pierda el color 'seleccionado' por el ChangeListener
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
        ArrayList<JButton> listaBotonesMenuTodos = new ArrayList<>();
        listaBotonesMenuTodos.add(botonTareas);
        listaBotonesMenuTodos.add(botonMatrix);
        listaBotonesMenuTodos.add(botonPomodoro);
        listaBotonesMenuTodos.add(botonAjustes);
        listaBotonesMenuTodos.add(botonCerrarSesion);

        for (JButton boton : listaBotonesMenuTodos) {
            boton.addChangeListener(e -> {
                if (boton.getModel().isRollover()) {
                    boton.setBackground(sRecursos.getGRANATE().brighter());
                } else if (textoBotonActual.equals(boton.getText())) {
                    boton.setBackground(sRecursos.getGRANATE().brighter());
                } else {
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
                if (getExtendedState() == JFrame.MAXIMIZED_BOTH){
                    setExtendedState(JFrame.NORMAL);
                    xRaton = e.getX();
                } else {
                    xRaton = e.getX()+botonInicio.getWidth();
                }
                yRaton = e.getY();
            }
        });

        panelSuperior.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                xNuevo = e.getXOnScreen();
                yNuevo = e.getYOnScreen();
                setLocation(xNuevo - xRaton, yNuevo - yRaton);
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
}
