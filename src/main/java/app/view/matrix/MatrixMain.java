package app.view.matrix;

import app.view.principal.InterfazFija;
import app.view.pruebas.NuevaPruebaPomo;
import services.ObjGraficos;
import services.Recursos;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MatrixMain {
    private final Recursos sRecursos;
    private final InterfazFija interfazFija;

    private Color colorBorder;
    private JPanel panelPrincipal;
    private JPanel panelMatrix;
    private JPanel panelArribaIzq, panelArribaDer, panelAbajoIzq, panelAbajoDer;

    MatrixMain(InterfazFija interfazFija){
        this.sRecursos = Recursos.getService();
        this.interfazFija = interfazFija;
        panelPrincipal = interfazFija.getPanelPrincipal();
        panelPrincipal.setLayout(new BorderLayout());

        crearPaneles();
    }

    private void crearPaneles() {
        colorBorder = new Color(    59, 59, 59);

        // Panel Matrix (principal)
        panelMatrix = new JPanel();
        panelMatrix.setLayout(new GridLayout(2, 2));
        panelPrincipal.add(panelMatrix, BorderLayout.CENTER);

        // Panel Arriba Izquierda
        panelArribaIzq = new JPanel();
        panelArribaIzq.setBackground(new Color(   49, 203, 36 ));
        panelMatrix.add(panelArribaIzq, BorderLayout.NORTH);

        // Panel Arriba Derecha
        panelArribaDer = new JPanel();
        panelArribaDer.setBackground(new Color( 62, 177, 234 ));
        panelArribaDer.setBorder(new MatteBorder(0, 2, 0, 0, colorBorder));
        panelMatrix.add(panelArribaDer, BorderLayout.WEST);

        // Panel Abajo Izquierda
        panelAbajoIzq = new JPanel();
        panelAbajoIzq.setBackground(new Color( 255, 207, 28));
        panelAbajoIzq.setBorder(new MatteBorder(2, 0, 0, 0, colorBorder));
        panelMatrix.add(panelAbajoIzq, BorderLayout.SOUTH);

        // Panel Abajo Izquierda
        panelAbajoDer = new JPanel();
        panelAbajoDer.setBackground(new Color(  236, 42, 42));
        panelAbajoDer.setBorder(new MatteBorder(2, 2, 0, 0, colorBorder));
        panelMatrix.add(panelAbajoDer, BorderLayout.EAST);

    }

    public static void main(String[] args) {
        new MatrixMain(new InterfazFija());
    }
}