package app.view.matrix;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class MatrixMain extends JPanel{
    private Color colorBorder;
    private JPanel panelArribaIzq, panelArribaDer, panelAbajoIzq, panelAbajoDer;

    public MatrixMain(){
        this.setLayout(new GridLayout(2,2));

        crearPaneles();
    }

    private void crearPaneles() {
        colorBorder = new Color(    59, 59, 59);

        // Panel Arriba Izquierda
        panelArribaIzq = new JPanel();
        panelArribaIzq.setBackground(new Color(   49, 203, 36 ));
        add(panelArribaIzq);

        // Panel Arriba Derecha
        panelArribaDer = new JPanel();
        panelArribaDer.setBackground(new Color( 62, 177, 234 ));
        panelArribaDer.setBorder(new MatteBorder(0, 2, 0, 0, colorBorder));
        add(panelArribaDer);

        // Panel Abajo Izquierda
        panelAbajoIzq = new JPanel();
        panelAbajoIzq.setBackground(new Color( 255, 207, 28));
        panelAbajoIzq.setBorder(new MatteBorder(2, 0, 0, 0, colorBorder));
        add(panelAbajoIzq);

        // Panel Abajo Izquierda
        panelAbajoDer = new JPanel();
        panelAbajoDer.setBackground(new Color(  236, 42, 42));
        panelAbajoDer.setBorder(new MatteBorder(2, 2, 0, 0, colorBorder));
        add(panelAbajoDer);

    }
}