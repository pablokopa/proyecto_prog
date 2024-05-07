package app.view.client.interfazPrincipal.principal;

import app.view.services.ObjGraficos;
import app.view.services.Recursos;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TareasPomodoroTemporal extends JFrame {
    private ObjGraficos sObjGraficos;
    private Recursos sRecursos;

    private int x, y, mouseX, mouseY;

    private JPanel panelOpciones, panelVistaPrincipal, panelSuperior;
    private JLabel labelCerrarVentana, labelTiempoConcentracion, labelTiempoDescanso;
    private JButton botonPlay, botonPause, botonDetener, botonCambiarTiempo;

    TareasPomodoroTemporal() {
        this.sObjGraficos = ObjGraficos.getService();
        this.sRecursos = Recursos.getService();

        /* Configuración de la ventana */
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
        setLayout(null);
        setUndecorated(true);
        setIconImage(sRecursos.getImagenLogo2().getImage());

        /* Generar los componentes */
        crearPaneles();
        crearLabels();
        crearBotones();
        moverVentana();

        /* Hacer visible la ventana */
        setVisible(true);
    }

    private void crearPaneles(){
        /* Panel opciones izquierda */
        panelOpciones = sObjGraficos.construirJPanel(0,0,250,650, sRecursos.getGRANATE(), null);
        add(panelOpciones);

        /* Panel superior */
        panelSuperior = sObjGraficos.construirJPanel(250,0, 850, 50, sRecursos.getBLANCO(), null);
        add(panelSuperior);

        /* Panel principal */
        panelVistaPrincipal = sObjGraficos.construirJPanel(250,50, 850, 600, sRecursos.getGRIS_CLARO(), null);
        add(panelVistaPrincipal);
    }

    private void crearLabels(){
        /* Label cerrar ventana */
        labelCerrarVentana = sObjGraficos.construirJLabel(
                null,
                panelVistaPrincipal.getWidth()-50,
                5,
                40,
                40,
                sRecursos.getCursorMano(),
                sRecursos.getImagenCerrar(),
                null,
                null,
                null,
                null,
                "r"
        );
        labelCerrarVentana.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        panelSuperior.add(labelCerrarVentana);

        labelTiempoConcentracion = sObjGraficos.construirJLabel(
                "25:00",
                0,
                75,
                panelVistaPrincipal.getWidth(),
                175,
                null,
                null,
                sRecursos.getMonserratBold(175),
                sRecursos.getGRIS_CLARO(),
                sRecursos.getGRANATE(),
                null,
                "c"
        );
        panelVistaPrincipal.add(labelTiempoConcentracion);

        labelTiempoDescanso = sObjGraficos.construirJLabel(
                "5:00",
                20,
                labelTiempoConcentracion.getHeight()+100,
                panelVistaPrincipal.getWidth()-40,
                195,
                null,
                null,
                sRecursos.getMonserratBold(75),
                sRecursos.getGRIS_CLARO(),
                new Color(82,0,0,250),
                sRecursos.getBordeGranate(),
                "t"
        );
        panelVistaPrincipal.add(labelTiempoDescanso);
    }

    /**
     * Método para crear los botones de la vista principal.
     */
    private void crearBotones(){

        /* Boton play */
        botonPlay = sObjGraficos.construirJButton(
                null,
                (panelVistaPrincipal.getWidth()-620)/2,
                panelVistaPrincipal.getHeight()-100-20,
                200,
                50,
                sRecursos.getCursorMano(),
                sRecursos.getImagenPlay(),
                null,
                sRecursos.getGRANATE(),
                null,
                null,
                "",
                true
        );
        botonPlay.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (botonPlay.getModel().isPressed()) {
                    botonPlay.setBackground(sRecursos.getBLANCO());
                }
                if (botonPlay.getModel().isRollover()) {
                    botonPlay.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonPlay.setBackground(sRecursos.getGRANATE());
                }
            }
        });
        panelVistaPrincipal.add(botonPlay);

        /* Boton pausa */
        botonPause = sObjGraficos.construirJButton(
                null,
                botonPlay.getX()+botonPlay.getWidth()+10,
                panelVistaPrincipal.getHeight()-100-20,
                200,
                50,
                sRecursos.getCursorMano(),
                sRecursos.getImagenPause(),
                null,
                sRecursos.getGRANATE(),
                null,
                null,
                "",
                true
        );
        botonPause.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (botonPause.getModel().isRollover()) {
                    botonPause.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonPause.setBackground(sRecursos.getGRANATE());
                }
            }
        });
        panelVistaPrincipal.add(botonPause);

        /* Boton detener */
        botonDetener = sObjGraficos.construirJButton(
                null,
                botonPause.getX()+botonPause.getWidth()+10,
                panelVistaPrincipal.getHeight()-100-20,
                200,
                50,
                sRecursos.getCursorMano(),
                new ImageIcon(sRecursos.getImagenStop().getImage().getScaledInstance(36,36, Image.SCALE_DEFAULT)),
                null,
                sRecursos.getGRANATE(),
                null,
                null,
                "",
                true
        );
        botonDetener.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (botonDetener.getModel().isRollover()) {
                    botonDetener.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonDetener.setBackground(sRecursos.getGRANATE());
                }
            }
        });
        panelVistaPrincipal.add(botonDetener);

        /* Boton cambiar tiempos de concentración y descanso */
        botonCambiarTiempo = sObjGraficos.construirJButton(
                "Cambiar los parámetros",
                botonPlay.getX(),
                panelVistaPrincipal.getHeight()-50-10,
                botonPlay.getWidth()*3+20,
                50,
                sRecursos.getCursorMano(),
                null,
                sRecursos.getMonserratBold(Recursos.SIZE_LETRA_BOTON),
                sRecursos.getGRANATE(),
                sRecursos.getBLANCO(),
                null,
                "",
                true
        );
        botonCambiarTiempo.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (botonCambiarTiempo.getModel().isRollover()) {
                    botonCambiarTiempo.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonCambiarTiempo.setBackground(sRecursos.getGRANATE());
                }
            }
        });
        panelVistaPrincipal.add(botonCambiarTiempo);
    }

    public void moverVentana(){
        panelSuperior.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX()+250;      // +250 porque es la posición x de panelSuperior
                mouseY = e.getY();
            }
        });

        panelSuperior.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getXOnScreen();
                y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }
        });
    }

    public static void main(String[] args) {
        new TareasPomodoroTemporal();
    }
}
