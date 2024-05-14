package app.view.principal;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.MatteBorder;

import services.ObjGraficos;
import services.Recursos;


public class VistaMatrix extends JPanel{
    private ObjGraficos sObjGraficos;
    private Recursos sRecursos;

    private Color colorGrisPrincipal;
    private JPanel panelArribaIzq;
    private JPanel panelArribaDer;
    private JPanel panelAbajoIzq;
    private JPanel panelAbajoDer;
    private JLabel labelArribaIzq, labelArribaDer, labelAbajoIzq, labelAbajoDer;

    public VistaMatrix(){
        sObjGraficos = ObjGraficos.getService();
        sRecursos = Recursos.getService();

        this.setLayout(new GridLayout(2,2));

        crearPaneles();
    }

    private void crearPaneles() {
        colorGrisPrincipal = new Color(59,59,59);

        // Panel Arriba Izquierda
        panelArribaIzq = new JPanel();
        panelArribaIzq.setLayout(new BorderLayout());
        panelArribaIzq.setBackground(new Color(175,255,168));
        add(panelArribaIzq);
        labelArribaIzq = sObjGraficos.construirJLabel(
                "TODO",
                0,
                0,
                550,
                250,
                null,
                null,
                sRecursos.getMonserratBold(20),
                null,
                colorGrisPrincipal,
                null,
                "");
        panelArribaIzq.add(labelArribaIzq, BorderLayout.BEFORE_FIRST_LINE);

        // Panel Arriba Derecha
        panelArribaDer = new JPanel();
        panelArribaDer.setLayout(new BorderLayout());
        panelArribaDer.setBackground(new Color(168,235,255));
        panelArribaDer.setBorder(new MatteBorder(0, 2, 0, 0, colorGrisPrincipal));
        add(panelArribaDer);
        labelArribaDer = sObjGraficos.construirJLabel(
                "TODO",
                0,
                0,
                0,
                0,
                null,
                null,
                sRecursos.getMonserratBold(20),
                null,
                colorGrisPrincipal,
                null,
                "");
        panelArribaDer.add(labelArribaDer, BorderLayout.BEFORE_FIRST_LINE);

        // Panel Abajo Izquierda
        panelAbajoIzq = new JPanel();
        panelAbajoIzq.setLayout(new BorderLayout());
        panelAbajoIzq.setBackground(new Color(255,221,122));
        panelAbajoIzq.setBorder(new MatteBorder(2, 0, 0, 0, colorGrisPrincipal));
        add(panelAbajoIzq);
        labelAbajoIzq = sObjGraficos.construirJLabel(
                "TODO",
                0,
                0,
                550,
                250,
                null,
                null,
                sRecursos.getMonserratBold(20),
                null,
                colorGrisPrincipal,
                null,
                "");
        panelAbajoIzq.add(labelAbajoIzq, BorderLayout.AFTER_LAST_LINE);

        // Panel Abajo Derecha
        panelAbajoDer = new JPanel();
        panelAbajoDer.setLayout(new BorderLayout());
        panelAbajoDer.setBackground(new Color(255,149,149));
        panelAbajoDer.setBorder(new MatteBorder(2, 2, 0, 0, colorGrisPrincipal));
        add(panelAbajoDer);
        labelAbajoDer = sObjGraficos.construirJLabel(
                "TODO",
                0,
                0,
                550,
                250,
                null,
                null,
                sRecursos.getMonserratBold(20),
                null,
                colorGrisPrincipal,
                null,
                "");
        panelAbajoDer.add(labelAbajoDer, BorderLayout.SOUTH);
    }
}