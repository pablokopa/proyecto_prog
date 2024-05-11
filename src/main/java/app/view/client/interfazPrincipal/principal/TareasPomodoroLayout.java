package app.view.client.interfazPrincipal.principal;

import app.view.services.Recursos;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TareasPomodoroLayout extends JFrame {
    private Recursos sRecursos;

    private JPanel panelArriba, panelIzquierda, panelCentral;
    private JLabel labelCerrarVentana;
    private JButton botonInicio, botonPerfil, botonAjustes, botonCerrarSesion, botonTareas, botonPomodoro, botonEstadisticas;

    TareasPomodoroLayout() {
        this.sRecursos = Recursos.getService();

        this.setSize(1100, 650);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(this);
        this.setUndecorated(false);
//        this.setUndecorated(true);
        this.setIconImage(sRecursos.getImagenLogo2().getImage());

        this.setLayout(new BorderLayout());


        crearPaneles();
        crearLabels();
        crearBotones();

        this.setVisible(true);

        redimensionarPaneles();
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

        botonInicio = new JButton("Inicio");
        botonInicio.setBackground(sRecursos.getGRANATE());
        botonInicio.setForeground(sRecursos.getBLANCO());
        botonInicio.setMaximumSize(new Dimension(getWidth(), 50));
        botonInicio.setFocusable(false);
        botonInicio.setCursor(sRecursos.getCursorMano());
        botonInicio.setFont(sRecursos.getMonserratBold(Recursos.SIZE_LETRA_BOTON));
        botonInicio.setBorder(null);
//        botonInicio.setContentAreaFilled(true);

        botonInicio.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (botonInicio.getModel().isPressed()) {
                    botonInicio.setBackground(sRecursos.getBLANCO());
                }
                if (botonInicio.getModel().isRollover()) {
                    botonInicio.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonInicio.setBackground(sRecursos.getGRANATE());
                }
            }
        });

        botonAjustes = new JButton("Ajustes");
        botonAjustes.setBackground(sRecursos.getGRANATE());
        botonAjustes.setForeground(sRecursos.getBLANCO());
        botonAjustes.setMaximumSize(new Dimension(getWidth(), 50));
        botonAjustes.setFocusable(false);
        botonAjustes.setCursor(sRecursos.getCursorMano());
        botonAjustes.setFont(sRecursos.getMonserratBold(Recursos.SIZE_LETRA_BOTON));
        botonAjustes.setBorder(null);
        botonAjustes.setContentAreaFilled(true);

        botonAjustes.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (botonAjustes.getModel().isPressed()) {
                    botonAjustes.setBackground(sRecursos.getBLANCO());
                }
                if (botonAjustes.getModel().isRollover()) {
                    botonAjustes.setBackground(sRecursos.getGRANATE_MID_LIGHT());
                } else {
                    botonAjustes.setBackground(sRecursos.getGRANATE());
                }
            }
        });

        panelIzquierda.add(botonInicio);
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
