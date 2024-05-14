package app.view.principal;

import services.ObjGraficos;
import services.Recursos;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Interfaz fija de la aplicación. Contiene el menú principal y la barra supeior.
 * En ella aparecen el resto de las vistas del programa.
 */
public class InterfazPrincipal extends JFrame {
    private Recursos sRecursos;

    private int xRaton, yRaton, xNuevo, yNuevo;

    private JPanel panelMenu, panelCentral, panelSuperior, panelPrincipal;
    private JPanel panelMatrix, panelPomodoro;
    private JButton botonInicio, botonAjustes, botonCerrarSesion, botonTareas, botonPomodoro, botonMatrix;
    private JButton botonCerrar, botonMinimizar, botonMaximizar;

    private CardLayout cardLayout;

    private ArrayList<JButton> listaBotonesMenuTodos;
    private ArrayList<JButton> listaBotonesMenuConVista;
    private String textoBotonActual = "";


    public InterfazPrincipal() {
        sRecursos = Recursos.getService();

        this.setLayout(new BorderLayout());
        this.setSize(1100, 650);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(this);
        this.setUndecorated(true);
        this.setIconImage(sRecursos.getImagenLogo2().getImage());

        this.listaBotonesMenuTodos = new ArrayList<JButton>();
        this.listaBotonesMenuConVista = new ArrayList<JButton>();

        crearPaneles();
        crearBotones();
        redimensionarPaneles();
        moverVentana();
        botonesActionListener();
        botonesChangeListener();

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
        this.panelMatrix = new VistaMatrix();
        this.panelPomodoro = new VistaPomodoro();

        cardLayout = new CardLayout();
        panelPrincipal.setLayout(cardLayout);

        this.add(panelMenu, BorderLayout.WEST);
        panelCentral.add(panelSuperior, BorderLayout.NORTH);
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
        botonCerrarSesion = ObjGraficos.construirBotonesMenu("Cerrar Sesión", getWidth(),50, sRecursos.getGRANATE(), sRecursos.getBLANCO());;

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
        listaBotonesMenuConVista.add(botonInicio);
        listaBotonesMenuConVista.add(botonTareas);
        listaBotonesMenuConVista.add(botonMatrix);
        listaBotonesMenuConVista.add(botonPomodoro);
        listaBotonesMenuConVista.add(botonAjustes);

        for (JButton boton : listaBotonesMenuConVista){
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String textoBoton = boton.getText();

                    if (textoBoton.equals("Inicio")){
                        contraerBotones();
                        cardLayout.show(panelPrincipal, textoBoton);
                        panelMenu.revalidate();
                    }
                    else {
                        contraerBotones();
                        cardLayout.show(panelPrincipal, textoBoton);
                        boton.setPreferredSize(new Dimension(getWidth(), 75));
                        panelMenu.revalidate();
                    }

                    textoBotonActual = textoBoton;
                }
            });
        }
    }

    /**
     * Utilizado en botonesActionListener para contraer los botones que ya no están seleccionados.
     */
    private void contraerBotones() {
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
        listaBotonesMenuTodos.add(botonTareas);
        listaBotonesMenuTodos.add(botonMatrix);
        listaBotonesMenuTodos.add(botonPomodoro);
        listaBotonesMenuTodos.add(botonAjustes);
        listaBotonesMenuTodos.add(botonCerrarSesion);

        for (JButton boton : listaBotonesMenuTodos) {
            boton.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (boton.getModel().isRollover()) {
                        boton.setBackground(sRecursos.getGRANATE().brighter());
                    } else if (textoBotonActual.equals(boton.getText())) {
                        boton.setBackground(sRecursos.getGRANATE().brighter());
                    } else {
                        boton.setBackground(sRecursos.getGRANATE());
                    }
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

    public static void main(String[] args) {
        new InterfazPrincipal();
    }
}
