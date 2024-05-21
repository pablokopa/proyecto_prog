package app.view.principal;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import services.Recursos;


public class VistaMatrix extends JPanel{
    private Recursos sRecursos;

    private Color colorGrisPrincipal = new Color(59,59,59);
    private Color colorGrisSecundario = new Color(220,220,220);
    private Color colorVerde = new Color(175,255,168);
    private Color colorAmarillo = new Color(255,255,168);
    private Color colorRojo = new Color(255,168,168);
    private Color colorAzul = new Color(168,235,255);

    private JPanel panelArribaI, panelArribaD, panelAbajoI, panelAbajoD;
    private JPanel panelTituloArribaI, panelTituloArribaD, panelTituloAbajoI, panelTituloAbajoD;
    private JPanel panelTareasArribaI, panelTareasArribaD, panelTareasAbajoI, panelTareasAbajoD;
    private JPanel panelTituloArribaIIzq, panelTituloArribaIDer, panelTituloArribaDIzq, panelTituloArribaDDer;
    private JPanel panelTituloAbajoIIzq, panelTituloAbajoIDer, panelTituloAbajoDIzq, panelTituloAbajoDDer;

    private JLabel labelArribaI, labelArribaD, labelAbajoI, labelAbajoD;

    private JScrollPane scrollArribaI, scrollArribaD, scrollAbajoI, scrollAbajoD;

    private JButton botonAddArribaD = new JButton("Añadir tarea");
    private JButton botonAddArribaI = new JButton("Añadir tarea");
    private JButton botonAddAbajoD = new JButton("Añadir tarea");
    private JButton botonAddAbajoI = new JButton("Añadir tarea");

    public VistaMatrix(){
        sRecursos = Recursos.getService();

        this.setLayout(new GridLayout(2,2));

        crearMatrix();
    }

    /**
     * Método para crear las tareas de los paneles.
     * @param color Color de fondo de los paneles.
     * @param lugar Posición en la que se añade el panel.
     * @param texto Texto que aparece (nombre de la tarea)
     */
    private void crearTareas(Color color, JPanel lugar, String texto){
        JPanel panel = new JPanel();
        JLabel label = new JLabel(texto);

        // Layout
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurar restricciones
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        // Dimensiones de los paneles
        panel.setMaximumSize(new Dimension(760,60));
        label.setHorizontalAlignment(JLabel.CENTER); // Alineación del texto

        // Tipo y color de fuente
        label.setFont(sRecursos.getMonserratBold(15));
        label.setForeground(colorGrisPrincipal); // Color de la letra

        // Borde
        Border bordeVisible = new MatteBorder(3,3,3,3, colorGrisPrincipal);
        Border bordeMargen = new MatteBorder(10,20,0,20, color);
        Border compound = BorderFactory.createCompoundBorder(bordeMargen,bordeVisible);
        panel.setBorder(compound);

        // Color de fondo
        panel.setBackground(colorGrisSecundario);

        // Añadir tarea
        panel.add(label, gbc);
        lugar.add(panel);
    }

    /**
     * Método para modificar el scrollbar.
     * @param zonaScroll Scroll que se modifica.
     */
    private void modificarScroll(JScrollPane zonaScroll){
        zonaScroll.setBorder(null);
        zonaScroll.getVerticalScrollBar().setUI(new ScrollBarBlanco());
        zonaScroll.getHorizontalScrollBar().setUI(new ScrollBarBlanco());
    }

    /**
     * Método para modificar el boton.
     * @param colorPanel Color del panel seleccionado.
     * @param boton Botón seleccionado para ser modificado.
     */
    private void modificarBoton(Color colorPanel, JButton boton){
        boton.setFont(sRecursos.getMonserratBold(14));
        boton.setBorder(new MatteBorder(5,5,5,5,colorGrisPrincipal));
        boton.setBackground(colorGrisPrincipal);
        boton.setForeground(colorPanel);
    }

    /**
     * Método para modificar el color del fondo de los paneles.
     * @param colorPanel Color del panel seleccionado.
     * @param panelTitulo Panel del titulo a modificar.
     * @param panelTituloIzq Lado izquierdo del panel del titulo a modificar.
     * @param panelTituloDer Lado derecho del panel del titulo a modificar.
     * @param panelTareas Panel de tareas a modificar.
     */
    private void modificarBackground(Color colorPanel, JPanel panelTitulo, JPanel panelTituloIzq, JPanel panelTituloDer , JPanel panelTareas){
        panelTitulo.setBackground(colorPanel);
        panelTituloIzq.setBackground(colorPanel);
        panelTituloDer.setBackground(colorPanel);
        panelTareas.setBackground(colorPanel);
    }

    private void crearMatrix() {
/* Panel Arriba Izquierda */
        // Definir elementos
        panelArribaI = new JPanel();
        panelTituloArribaI = new JPanel();
        panelTareasArribaI = new JPanel();
        panelTituloArribaIIzq = new JPanel();
        panelTituloArribaIDer = new JPanel();
        scrollArribaI = new JScrollPane(panelTareasArribaI);
        labelArribaI = new JLabel("No importante / No urgente");

        // Configurar layout
        panelArribaI.setLayout(new BorderLayout());
        panelTituloArribaI.setLayout(new GridLayout(1,2));
        panelTituloArribaIIzq.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTituloArribaIDer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelTareasArribaI.setLayout(new BoxLayout(panelTareasArribaI, BoxLayout.Y_AXIS));

        // Personalizar paneles
        labelArribaI.setFont(sRecursos.getMonserratBold(20));
        modificarScroll(scrollArribaI);
        modificarBoton(colorVerde, botonAddArribaI);
        modificarBackground(colorVerde, panelTituloArribaI, panelTituloArribaIIzq, panelTituloArribaIDer ,panelTareasArribaI);
        panelArribaI.setBorder(new MatteBorder(1, 10, 5, 5, sRecursos.getBLANCO()));

/* Panel Arriba Derecha */
        // Definir elementos
        panelArribaD = new JPanel();
        panelTituloArribaD = new JPanel();
        panelTareasArribaD = new JPanel();
        panelTituloArribaDIzq = new JPanel();
        panelTituloArribaDDer = new JPanel();
        scrollArribaD = new JScrollPane(panelTareasArribaD);
        labelArribaD = new JLabel("No importante / Urgente");

        // Indicar layout
        panelArribaD.setLayout(new BorderLayout());
        panelTituloArribaD.setLayout(new GridLayout(1,2));
        panelTituloArribaDIzq.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTituloArribaDDer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelTareasArribaD.setLayout(new BoxLayout(panelTareasArribaD, BoxLayout.Y_AXIS));

        // Personalizar paneles
        labelArribaD.setFont(sRecursos.getMonserratBold(20));
        modificarScroll(scrollArribaD);
        modificarBoton(colorAzul, botonAddArribaD);
        modificarBackground(colorAzul, panelTituloArribaD, panelTituloArribaDIzq, panelTituloArribaDDer, panelTareasArribaD);
        panelArribaD.setBorder(new MatteBorder(1, 5, 5, 10, sRecursos.getBLANCO()));

/* Panel Abajo Izquierda */
        // Definir elementos
        panelAbajoI = new JPanel();
        panelTituloAbajoI = new JPanel();
        panelTareasAbajoI = new JPanel();
        panelTituloAbajoIIzq = new JPanel();
        panelTituloAbajoIDer = new JPanel();
        scrollAbajoI = new JScrollPane(panelTareasAbajoI);
        labelAbajoI = new JLabel("Importante / No urgente");

        // Indicar layout
        panelAbajoI.setLayout(new BorderLayout());
        panelTituloAbajoI.setLayout(new GridLayout(1,2));
        panelTituloAbajoIIzq.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTituloAbajoIDer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelTareasAbajoI.setLayout(new BoxLayout(panelTareasAbajoI, BoxLayout.Y_AXIS));

        // Personalizar paneles
        labelAbajoI.setFont(sRecursos.getMonserratBold(20));
        modificarScroll(scrollAbajoI);
        modificarBoton(colorAmarillo, botonAddAbajoI);
        modificarBackground(colorAmarillo, panelTituloAbajoI, panelTituloAbajoIIzq, panelTituloAbajoIDer, panelTareasAbajoI);
        panelAbajoI.setBorder(new MatteBorder(5, 10, 10, 5, sRecursos.getBLANCO()));

/* Panel Abajo Derecha */
        // Definir elementos
        panelAbajoD = new JPanel();
        panelTituloAbajoD = new JPanel();
        panelTareasAbajoD = new JPanel();
        panelTituloAbajoDIzq = new JPanel();
        panelTituloAbajoDDer = new JPanel();
        scrollAbajoD = new JScrollPane(panelTareasAbajoD);
        labelAbajoD = new JLabel("Importante / Urgente");

        // Indicar layout
        panelAbajoD.setLayout(new BorderLayout());
        panelTituloAbajoD.setLayout(new GridLayout(1,2));
        panelTituloAbajoDIzq.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTituloAbajoDDer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelTareasAbajoD.setLayout(new BoxLayout(panelTareasAbajoD, BoxLayout.Y_AXIS));

        // Personalizar paneles
        labelAbajoD.setFont(sRecursos.getMonserratBold(20));
        modificarScroll(scrollAbajoD);
        modificarBoton(colorRojo, botonAddAbajoD);
        modificarBackground(colorRojo, panelTituloAbajoD, panelTituloAbajoDIzq, panelTituloAbajoDDer, panelTareasAbajoD);
        panelAbajoD.setBorder(new MatteBorder(5, 5, 10, 10, sRecursos.getBLANCO()));

/* AÑADIR ELEMENTOS */
        // Añadir elementos arriba izquierda
        panelTituloArribaI.add(panelTituloArribaIIzq, BorderLayout.EAST);
        panelTituloArribaI.add(panelTituloArribaIDer, BorderLayout.WEST);
        panelTituloArribaIIzq.add(labelArribaI, BorderLayout.EAST);
        panelTituloArribaIDer.add(botonAddArribaI, BorderLayout.WEST);
        panelArribaI.add(panelTituloArribaI, BorderLayout.NORTH);
        panelArribaI.add(scrollArribaI, BorderLayout.CENTER);
        add(panelArribaI);

        // Añadir elementos arriba derecha
        panelTituloArribaD.add(panelTituloArribaDIzq, BorderLayout.EAST);
        panelTituloArribaD.add(panelTituloArribaDDer, BorderLayout.WEST);
        panelTituloArribaDDer.add(labelArribaD, BorderLayout.WEST);
        panelTituloArribaDIzq.add(botonAddArribaD, BorderLayout.EAST);
        panelArribaD.add(panelTituloArribaD, BorderLayout.NORTH);
        panelArribaD.add(scrollArribaD, BorderLayout.CENTER);
        add(panelArribaD);

        // Añadir elementos abajo izquierda
        panelTituloAbajoI.add(panelTituloAbajoIIzq, BorderLayout.EAST);
        panelTituloAbajoI.add(panelTituloAbajoIDer, BorderLayout.WEST);
        panelTituloAbajoIIzq.add(labelAbajoI, BorderLayout.EAST);
        panelTituloAbajoIDer.add(botonAddAbajoI, BorderLayout.WEST);
        panelAbajoI.add(panelTituloAbajoI, BorderLayout.SOUTH);
        panelAbajoI.add(scrollAbajoI, BorderLayout.CENTER);
        add(panelAbajoI);

        // Añadir elementos abajo derecha
        panelTituloAbajoD.add(panelTituloAbajoDIzq, BorderLayout.EAST);
        panelTituloAbajoD.add(panelTituloAbajoDDer, BorderLayout.WEST);
        panelTituloAbajoDDer.add(labelAbajoD, BorderLayout.WEST);
        panelTituloAbajoDIzq.add(botonAddAbajoD, BorderLayout.EAST);
        panelAbajoD.add(panelTituloAbajoD, BorderLayout.SOUTH);
        panelAbajoD.add(scrollAbajoD, BorderLayout.CENTER);
        add(panelAbajoD);
    }
}

class ScrollBarBlanco extends BasicScrollBarUI {
    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(59,59,59);
        this.trackColor = new Color(220, 220, 220);
        this.thumbDarkShadowColor = new Color(0, 0, 0, 0);
        this.thumbHighlightColor = new Color(0, 0, 0, 0);
        this.thumbLightShadowColor = new Color(0, 0, 0, 0);
    }
}