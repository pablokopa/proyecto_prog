package app.view.principal;

import services.ObjGraficos;
import services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazFija extends JFrame {
    private Recursos sRecursos;

    int xRaton, yRaton, xNuevo, yNuevo;

    private JPanel panelMenu, panelCentral, panelSuperior, panelPrincipal;
    private JButton botonInicio, botonAjustes, botonCerrarSesion, botonTareas, botonPomodoro, botonMatrix;
    private JButton botonCerrar, botonMinimizar, botonMaximizar;

    public InterfazFija() {
        sRecursos = Recursos.getService();

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

        this.setVisible(true);
    }

    private void crearPaneles(){
        panelMenu = ObjGraficos.construirPanelesPrincipales("menu", sRecursos.getGRANATE());
        panelCentral = ObjGraficos.construirPanelesPrincipales("central", sRecursos.getGRIS_CLARO());
        panelSuperior = ObjGraficos.construirPanelesPrincipales("superior", sRecursos.getBLANCO());
        panelPrincipal = ObjGraficos.construirPanelesPrincipales("central", sRecursos.getGRIS_CLARO());

        this.add(panelMenu, BorderLayout.WEST);
        panelCentral.add(panelPrincipal, BorderLayout.CENTER);
        panelCentral.add(panelSuperior, BorderLayout.NORTH);
        this.add(panelCentral, BorderLayout.CENTER);
    }

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
        botonCerrarSesion = ObjGraficos.construirBotonesMenu("Cerrar Sesión", getWidth(), 50, sRecursos.getGRANATE(), sRecursos.getBLANCO());

        panelMenu.add(botonInicio);
        panelMenu.add(botonTareas);
        panelMenu.add(botonMatrix);
        panelMenu.add(botonPomodoro);
        panelMenu.add(Box.createVerticalGlue());   //espacio entre botones
        panelMenu.add(botonAjustes);
        panelMenu.add(botonCerrarSesion);
    }

    public void redimensionarPaneles() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        panelMenu.setPreferredSize(new Dimension((int)(getWidth()*0.15), getHeight()));

                        panelMenu.revalidate();

                        System.out.println("panel principal ancho: " + panelPrincipal.getWidth());
                        System.out.println("panel principal alto: " + panelPrincipal.getHeight());

                        System.out.println("ancho: " + getWidth());
                        System.out.println("alto: " + getWidth());
                    }
                });
            }
        });
    }

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

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public static void main(String[] args) {
        new InterfazFija();
    }
}
