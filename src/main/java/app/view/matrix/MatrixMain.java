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
        panelArribaIzq.setBackground(new Color(175,255,168));
        panelMatrix.add(panelArribaIzq);

        // Panel Arriba Derecha
        panelArribaDer = new JPanel();
        panelArribaDer.setBackground(new Color(168,235,255));
        panelArribaDer.setBorder(new MatteBorder(0, 2, 0, 0, colorBorder));
        panelMatrix.add(panelArribaDer);

        // Panel Abajo Izquierda
        panelAbajoIzq = new JPanel();
        panelAbajoIzq.setBackground(new Color(255,221,122));
        panelAbajoIzq.setBorder(new MatteBorder(2, 0, 0, 0, colorBorder));
        panelMatrix.add(panelAbajoIzq);

        // Panel Abajo Izquierda
        panelAbajoDer = new JPanel();
        panelAbajoDer.setBackground(new Color(255,149,149));
        panelAbajoDer.setBorder(new MatteBorder(2, 2, 0, 0, colorBorder));
        panelMatrix.add(panelAbajoDer);

    }

    public static void main(String[] args) {
        new MatrixMain(new InterfazFija());
    }
}