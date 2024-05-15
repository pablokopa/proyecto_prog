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

    private JPanel panelArribaI, panelArribaD, panelAbajoI, panelAbajoD;
    private JPanel panelTituloArribaI, panelTituloArribaD, panelTituloAbajoI, panelTituloAbajoD;
    private JPanel panelTareasArribaI, panelTareasArribaD, panelTareasAbajoI, panelTareasAbajoD;

    private JLabel labelArribaI, labelArribaD, labelAbajoI, labelAbajoD;
    private JScrollPane scrollArribaI, scrollArribaD, scrollAbajoI, scrollAbajoD;

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
        panelArribaI = new JPanel();
        panelTituloArribaI = new JPanel();
        panelTareasArribaI = new JPanel();
        labelArribaI = new JLabel("No importante / No urgente");
        labelArribaI.setFont(sRecursos.getMonserratBold(20));

        // Configurar layout
        panelArribaI.setLayout(new BorderLayout());
        panelTituloArribaI.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Cambiar color de fondo
        panelTituloArribaI.setBackground(new Color(175,255,168));
        panelTareasArribaI.setBackground(new Color(175,255,168));

        // Añadir borde
        panelArribaI.setBorder(new MatteBorder(1, 10, 5, 5, sRecursos.getBLANCO()));

/* Panel Arriba Derecha */
        // Crear nuevos paneles
        panelArribaD = new JPanel();
        panelTituloArribaD = new JPanel();
        panelTareasArribaD = new JPanel();
        labelArribaD = new JLabel("No importante / Urgente");
        labelArribaD.setFont(sRecursos.getMonserratBold(20));

        // Indicar layout
        panelArribaD.setLayout(new BorderLayout());
        panelTituloArribaD.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Cambiar color de fondo
        panelTituloArribaD.setBackground(new Color(168,235,255));
        panelTareasArribaD.setBackground(new Color(168,235,255));

        // Añadir borde
        panelArribaD.setBorder(new MatteBorder(1, 5, 5, 10, sRecursos.getBLANCO()));

/* Panel Abajo Izquierda */
        // Crear nuevos paneles
        panelAbajoI = new JPanel();
        panelTituloAbajoI = new JPanel();
        panelTareasAbajoI = new JPanel();
        labelAbajoI = new JLabel("Importante / No urgente");
        labelAbajoI.setFont(sRecursos.getMonserratBold(20));

        // Indicar layout
        panelAbajoI.setLayout(new BorderLayout());
        panelTituloAbajoI.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Cambiar color de fondo
        panelTituloAbajoI.setBackground(new Color(255,221,122));
        panelTareasAbajoI.setBackground(new Color(255,221,122));

        // Añadir borde
        panelAbajoI.setBorder(new MatteBorder(5, 10, 10, 5, sRecursos.getBLANCO()));

/* Panel Abajo Derecha */
        // Crear nuevos paneles
        panelAbajoD = new JPanel();
        panelTituloAbajoD = new JPanel();
        panelTareasAbajoD = new JPanel();
        labelAbajoD = new JLabel("Importante / Urgente");
        labelAbajoD.setFont(sRecursos.getMonserratBold(20));

        // Indicar layout
        panelAbajoD.setLayout(new BorderLayout());
        panelTituloAbajoD.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Cambiar color de fondo
        panelTituloAbajoD.setBackground(new Color(255,149,149));
        panelTareasAbajoD.setBackground(new Color(255,149,149));

        // Añadir borde
        panelAbajoD.setBorder(new MatteBorder(5, 5, 10, 10, sRecursos.getBLANCO()));

/* AÑADIR ELEMENTOS */
        /*for (int i=0; i<100; i++){
            JLabel label = new JLabel("aaaa");
            panelTareasArribaI.add(label);
        }*/

        // Añadir elementos arriba izquierda
        panelTituloArribaI.add(labelArribaI);
        panelArribaI.add(panelTituloArribaI, BorderLayout.NORTH);
        panelArribaI.add(panelTareasArribaI, BorderLayout.CENTER);
        add(panelArribaI);

        // Añadir elementos arriba derecha
        panelTituloArribaD.add(labelArribaD);
        panelArribaD.add(panelTituloArribaD, BorderLayout.NORTH);
        panelArribaD.add(panelTareasArribaD, BorderLayout.CENTER);
        add(panelArribaD);

        // Añadir elementos abajo izquierda
        panelTituloAbajoI.add(labelAbajoI);
        panelAbajoI.add(panelTituloAbajoI, BorderLayout.SOUTH);
        panelAbajoI.add(panelTareasAbajoI, BorderLayout.CENTER);
        add(panelAbajoI);

        // Añadir elementos abajo derecha
        panelTituloAbajoD.add(labelAbajoD);
        panelAbajoD.add(panelTituloAbajoD, BorderLayout.SOUTH);
        panelAbajoD.add(panelTareasAbajoD, BorderLayout.CENTER);
        add(panelAbajoD);
    }
}