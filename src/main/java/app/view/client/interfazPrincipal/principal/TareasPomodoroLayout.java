package app.view.client.interfazPrincipal.principal;

import app.view.services.ObjGraficos;
import app.view.services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TareasPomodoroLayout extends JFrame {
    private Recursos sRecursos;

    private JPanel panelArriba, panelIzquierda, panelCentral;
    private JLabel labelCerrarVentana;
    private JButton botonInicio, botonPerfil, botonAjustes, botonCerrarSesion, botonTareas, botonPomodoro, botonEstadisticas;

    TareasPomodoroLayout() {
        sRecursos = Recursos.getService();

        this.setLayout(new BorderLayout());
        this.setSize(1100, 650);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(this);
        this.setUndecorated(false);
        this.setIconImage(sRecursos.getImagenLogo2().getImage());

        crearPaneles();
        crearLabels();
        crearBotones();
        redimensionarPaneles();

        this.setVisible(true);
    }

    private void crearPaneles(){
        panelIzquierda = new JPanel();
        panelIzquierda.setBackground(sRecursos.getGRANATE());
        panelIzquierda.setPreferredSize(new Dimension((int)(this.getWidth()*0.15), this.getHeight()));  //temporal setPreferredSize
        panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));

        panelCentral = new JPanel();
        panelCentral.setBackground(sRecursos.getGRIS_CLARO());
        panelCentral.setLayout(new BorderLayout());

        panelArriba = new JPanel();
        panelArriba.setBackground(sRecursos.getBLANCO());
        panelArriba.setPreferredSize(new Dimension(this.getWidth(), 50));  //temporal setPreferredSize

        this.add(panelIzquierda, BorderLayout.WEST);
        panelCentral.add(panelArriba, BorderLayout.NORTH);
        this.add(panelCentral, BorderLayout.CENTER);

    }

    private void crearLabels() {
        labelCerrarVentana = new JLabel();
        labelCerrarVentana.setIcon(sRecursos.getImagenCerrar());
        panelArriba.add(labelCerrarVentana);

    }

    private void crearBotones() {

        botonInicio = ObjGraficos.construirJButton(
                "Inicio",
                getWidth(),
                50,
                sRecursos.getBLANCO(),
                sRecursos.getGRANATE(),
                sRecursos.getMonserratBold(Recursos.SIZE_LETRA_BOTON),
                sRecursos.getCursorMano(),
                null,
                null
        );

        botonAjustes = ObjGraficos.construirJButton(
                "Ajustes",
                getWidth(),
                50,
                sRecursos.getGRANATE(),
                sRecursos.getBLANCO(),
                sRecursos.getMonserratBold(Recursos.SIZE_LETRA_BOTON),
                sRecursos.getCursorMano(),
                null,
                null
        );

        panelIzquierda.add(botonInicio);
        panelIzquierda.add(Box.createVerticalGlue());   //espacio entre botones
        panelIzquierda.add(botonAjustes);
    }

    private void redimensionarPaneles() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        panelIzquierda.setPreferredSize(new Dimension((int)(getWidth()*0.15), getHeight()));
                        panelIzquierda.revalidate();
                    }
                });
            }
        });
    }

    public static void main(String[] args) {
        new TareasPomodoroLayout();
    }
}
