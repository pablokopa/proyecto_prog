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
    private JPanel panelArribaIzq, panelArribaDer, panelAbajoIzq, panelAbajoDer;
    private JPanel panelTituloArribaI, panelTareasArribaI, panelTituloArribaD, panelTareasArribaD;
    private JPanel panelTituloAbajoI, panelTareasAbajoI, panelTituloAbajoD, panelTareasAbajoD;
    private JLabel labelArribaIzq, labelArribaDer, labelAbajoIzq, labelAbajoDer;

    public VistaMatrix(){
        sObjGraficos = ObjGraficos.getService();
        sRecursos = Recursos.getService();

        this.setLayout(new GridLayout(2,2));

        crearPaneles();
    }

    private void crearPaneles() {
        colorGrisPrincipal = new Color(59,59,59);

/* Panel Arriba Izquierda */
        // Crear nuevos paneles
        panelArribaIzq = new JPanel();
        panelTituloArribaI = new JPanel();
        panelTareasArribaI = new JPanel();
        labelArribaIzq = new JLabel("No importante / No urgente");
        labelArribaIzq.setFont(sRecursos.getMonserratBold(20));

        // Indicar layout
        panelArribaIzq.setLayout(new BorderLayout());
        panelTituloArribaI.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Cambiar color de fondo
        panelTituloArribaI.setBackground(new Color(175,255,168));
        panelTareasArribaI.setBackground(new Color(175,255,168));

        // Añadir borde
        panelArribaIzq.setBorder(new MatteBorder(1, 10, 5, 5, sRecursos.getBLANCO()));

        // Añadir elementos
        panelTituloArribaI.add(labelArribaIzq);
        panelArribaIzq.add(panelTituloArribaI, BorderLayout.NORTH);
        panelArribaIzq.add(panelTareasArribaI, BorderLayout.CENTER);
        add(panelArribaIzq);

/* Panel Arriba Derecha */
        // Crear nuevos paneles
        panelArribaDer = new JPanel();
        panelTituloArribaD = new JPanel();
        panelTareasArribaD = new JPanel();
        labelArribaDer = new JLabel("No importante / Urgente");
        labelArribaDer.setFont(sRecursos.getMonserratBold(20));

        // Indicar layout
        panelArribaDer.setLayout(new BorderLayout());
        panelTituloArribaD.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Cambiar color de fondo
        panelTituloArribaD.setBackground(new Color(168,235,255));
        panelTareasArribaD.setBackground(new Color(168,235,255));

        // Añadir borde
        panelArribaDer.setBorder(new MatteBorder(1, 5, 5, 10, sRecursos.getBLANCO()));

        // Añadir elementos
        panelTituloArribaD.add(labelArribaDer);
        panelArribaDer.add(panelTituloArribaD, BorderLayout.NORTH);
        panelArribaDer.add(panelTareasArribaD, BorderLayout.CENTER);
        add(panelArribaDer);

/* Panel Abajo Izquierda */
        // Crear nuevos paneles
        panelAbajoIzq = new JPanel();
        panelTituloAbajoI = new JPanel();
        panelTareasAbajoI = new JPanel();
        labelAbajoIzq = new JLabel("Importante / No urgente");
        labelAbajoIzq.setFont(sRecursos.getMonserratBold(20));

        // Indicar layout
        panelAbajoIzq.setLayout(new BorderLayout());
        panelTituloAbajoI.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Cambiar color de fondo
        panelTituloAbajoI.setBackground(new Color(255,221,122));
        panelTareasAbajoI.setBackground(new Color(255,221,122));

        // Añadir borde
        panelAbajoIzq.setBorder(new MatteBorder(5, 10, 10, 5, sRecursos.getBLANCO()));

        // Añadir elementos
        panelTituloAbajoI.add(labelAbajoIzq);
        panelAbajoIzq.add(panelTituloAbajoI, BorderLayout.SOUTH);
        panelAbajoIzq.add(panelTareasAbajoI, BorderLayout.CENTER);
        add(panelAbajoIzq);

/* Panel Abajo Derecha */
        // Crear nuevos paneles
        panelAbajoDer = new JPanel();
        panelTituloAbajoD = new JPanel();
        panelTareasAbajoD = new JPanel();
        labelAbajoDer = new JLabel("Importante / Urgente");
        labelAbajoDer.setFont(sRecursos.getMonserratBold(20));

        // Indicar layout
        panelAbajoDer.setLayout(new BorderLayout());
        panelTituloAbajoD.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Cambiar color de fondo
        panelTituloAbajoD.setBackground(new Color(255,149,149));
        panelTareasAbajoD.setBackground(new Color(255,149,149));

        // Añadir borde
        panelAbajoDer.setBorder(new MatteBorder(5, 5, 10, 10, sRecursos.getBLANCO()));

        // Añadir elementos
        panelTituloAbajoD.add(labelAbajoDer);
        panelAbajoDer.add(panelTituloAbajoD, BorderLayout.SOUTH);
        panelAbajoDer.add(panelTareasAbajoD, BorderLayout.CENTER);
        add(panelAbajoDer);
    }
}