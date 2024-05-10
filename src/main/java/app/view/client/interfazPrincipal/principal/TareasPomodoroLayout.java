package app.view.client.interfazPrincipal.principal;

import app.view.services.Recursos;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TareasPomodoroLayout extends JFrame {
    private Recursos sRecursos;

    TareasPomodoroLayout(){
        this.sRecursos = Recursos.getService();

        this.setSize(1100, 650);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(this);
        this.setUndecorated(false);
//        this.setUndecorated(true);
        this.setIconImage(sRecursos.getImagenLogo2().getImage());

        this.setLayout(new BorderLayout());

        crearPaneles();
        crearLabels();

        this.setVisible(true);
    }

    private void crearPaneles(){
        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setBackground(sRecursos.getGRANATE());
        panelIzquierda.setPreferredSize(new Dimension(200, this.getHeight()));  //temporal setPreferredSize
//        panelIzquierda.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(sRecursos.getGRIS_CLARO());
        panelCentral.setLayout(new BorderLayout());

        JPanel panelArriba = new JPanel();
        panelArriba.setBackground(sRecursos.getBLANCO());
        panelArriba.setPreferredSize(new Dimension(this.getWidth(), 50));  //temporal setPreferredSize

        this.add(panelIzquierda, BorderLayout.WEST);
        panelCentral.add(panelArriba, BorderLayout.NORTH);
        this.add(panelCentral, BorderLayout.CENTER);

    }

    private void crearLabels() {
        JLabel labelCerrarVentana = new JLabel();
        labelCerrarVentana.setIcon(sRecursos.getImagenCerrar());
        panelArriba.add(labelCerrarVentana);

    }

    public static void main(String[] args) {
        new TareasPomodoroLayout();
    }
}
