package app.view.principal;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import services.ObjGraficos;
import services.Recursos;


public class VistaMatrix extends JPanel{
    private ObjGraficos sObjGraficos;
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
    private JLabel labelAddArribaI, labelAddArribaD, labelAddAbajoI, labelAddAbajoD;
    private JScrollPane scrollArribaI, scrollArribaD, scrollAbajoI, scrollAbajoD;

    private JButton botonAddArribaD = new JButton("Añadir tarea");
    private JButton botonAddArribaI = new JButton("Añadir tarea");
    private JButton botonAddAbajoD = new JButton("Añadir tarea");
    private JButton botonAddAbajoI = new JButton("Añadir tarea");

    public VistaMatrix(){
        sObjGraficos = ObjGraficos.getService();
        sRecursos = Recursos.getService();

        this.setLayout(new GridLayout(2,2));

        crearPaneles();
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

    private void crearPaneles() {
/* Panel Arriba Izquierda */
        // Crear nuevos paneles
        panelArribaI = new JPanel();
        panelTituloArribaI = new JPanel();
        panelTareasArribaI = new JPanel();
        panelTituloArribaIIzq = new JPanel();
        panelTituloArribaIDer = new JPanel();

        labelArribaI = new JLabel("No importante / No urgente");
        labelArribaI.setFont(sRecursos.getMonserratBold(20));

        // Configurar layout
        panelArribaI.setLayout(new BorderLayout());
        panelTituloArribaI.setLayout(new GridLayout(1,2));
        panelTituloArribaIIzq.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTituloArribaIDer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelTareasArribaI.setLayout(new BoxLayout(panelTareasArribaI, BoxLayout.Y_AXIS));

        // Crear y configurar scroll
        scrollArribaI = new JScrollPane(panelTareasArribaI);
        scrollArribaI.setBorder(null);
        scrollArribaI.getVerticalScrollBar().setUI(new WhiteScrollBarUI());
        scrollArribaI.getHorizontalScrollBar().setUI(new WhiteScrollBarUI());

        // Personalizar botón
        botonAddArribaI.setFont(sRecursos.getMonserratBold(12));
        botonAddArribaI.setBorder(new MatteBorder(5,5,5,5,colorGrisPrincipal));
        botonAddArribaI.setBackground(colorGrisPrincipal);
        botonAddArribaI.setForeground(colorVerde);

        // Cambiar color de fondo
        panelTituloArribaI.setBackground(colorVerde);
        panelTituloArribaIIzq.setBackground(colorVerde);
        panelTituloArribaIDer.setBackground(colorVerde);
        panelTareasArribaI.setBackground(colorVerde);

        // Añadir borde
        panelArribaI.setBorder(new MatteBorder(1, 10, 5, 5, sRecursos.getBLANCO()));

/* Panel Arriba Derecha */
        // Crear nuevos paneles
        panelArribaD = new JPanel();
        panelTituloArribaD = new JPanel();
        panelTareasArribaD = new JPanel();
        panelTituloArribaDIzq = new JPanel();
        panelTituloArribaDDer = new JPanel();

        labelArribaD = new JLabel("No importante / Urgente");
        labelArribaD.setFont(sRecursos.getMonserratBold(20));

        // Indicar layout
        panelArribaD.setLayout(new BorderLayout());
        panelTituloArribaD.setLayout(new GridLayout(1,2));
        panelTituloArribaDIzq.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTituloArribaDDer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelTareasArribaD.setLayout(new BoxLayout(panelTareasArribaD, BoxLayout.Y_AXIS));

        // Crear y configurar scroll
        scrollArribaD = new JScrollPane(panelTareasArribaD);
        scrollArribaD.setBorder(null);
        scrollArribaD.getVerticalScrollBar().setUI(new WhiteScrollBarUI());
        scrollArribaD.getHorizontalScrollBar().setUI(new WhiteScrollBarUI());

        // Personalizar botón
        botonAddArribaD.setFont(sRecursos.getMonserratBold(12));
        botonAddArribaD.setBorder(new MatteBorder(5,5,5,5,colorGrisPrincipal));
        botonAddArribaD.setBackground(colorGrisPrincipal);
        botonAddArribaD.setForeground(colorAzul);

        // Cambiar color de fondo
        panelTituloArribaD.setBackground(colorAzul);
        panelTituloArribaDIzq.setBackground(colorAzul);
        panelTituloArribaDDer.setBackground(colorAzul);
        panelTareasArribaD.setBackground(colorAzul);

        // Añadir borde
        panelArribaD.setBorder(new MatteBorder(1, 5, 5, 10, sRecursos.getBLANCO()));

/* Panel Abajo Izquierda */
        // Crear nuevos paneles
        panelAbajoI = new JPanel();
        panelTituloAbajoI = new JPanel();
        panelTareasAbajoI = new JPanel();
        panelTituloAbajoIIzq = new JPanel();
        panelTituloAbajoIDer = new JPanel();

        labelAbajoI = new JLabel("Importante / No urgente");
        labelAbajoI.setFont(sRecursos.getMonserratBold(20));

        // Indicar layout
        panelAbajoI.setLayout(new BorderLayout());
        panelTituloAbajoI.setLayout(new GridLayout(1,2));
        panelTituloAbajoIIzq.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTituloAbajoIDer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelTareasAbajoI.setLayout(new BoxLayout(panelTareasAbajoI, BoxLayout.Y_AXIS));

        // Crear y configurar scroll
        scrollAbajoI = new JScrollPane(panelTareasAbajoI);
        scrollAbajoI.setBorder(null);
        scrollAbajoI.getVerticalScrollBar().setUI(new WhiteScrollBarUI());
        scrollAbajoI.getHorizontalScrollBar().setUI(new WhiteScrollBarUI());

        // Personalizar botón
        botonAddAbajoI.setFont(sRecursos.getMonserratBold(12));
        botonAddAbajoI.setBorder(new MatteBorder(5,5,5,5,colorGrisPrincipal));
        botonAddAbajoI.setBackground(colorGrisPrincipal);
        botonAddAbajoI.setForeground(colorAmarillo);

        // Cambiar color de fondo
        panelTituloAbajoI.setBackground(colorAmarillo);
        panelTituloAbajoIIzq.setBackground(colorAmarillo);
        panelTituloAbajoIDer.setBackground(colorAmarillo);
        panelTareasAbajoI.setBackground(colorAmarillo);

        // Añadir borde
        panelAbajoI.setBorder(new MatteBorder(5, 10, 10, 5, sRecursos.getBLANCO()));

/* Panel Abajo Derecha */
        // Crear nuevos paneles
        panelAbajoD = new JPanel();
        panelTituloAbajoD = new JPanel();
        panelTareasAbajoD = new JPanel();
        panelTituloAbajoDIzq = new JPanel();
        panelTituloAbajoDDer = new JPanel();

        labelAbajoD = new JLabel("Importante / Urgente");
        labelAbajoD.setFont(sRecursos.getMonserratBold(20));

        // Indicar layout
        panelAbajoD.setLayout(new BorderLayout());
        panelTituloAbajoD.setLayout(new GridLayout(1,2));
        panelTituloAbajoDIzq.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTituloAbajoDDer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelTareasAbajoD.setLayout(new BoxLayout(panelTareasAbajoD, BoxLayout.Y_AXIS));

        // Crear y configurar scroll
        scrollAbajoD = new JScrollPane(panelTareasAbajoD);
        scrollAbajoD.setBorder(null);
        scrollAbajoD.getVerticalScrollBar().setUI(new WhiteScrollBarUI());
        scrollAbajoD.getHorizontalScrollBar().setUI(new WhiteScrollBarUI());

        // Personalizar botón
        botonAddAbajoD.setFont(sRecursos.getMonserratBold(12));
        botonAddAbajoD.setBorder(new MatteBorder(5,5,5,5,colorGrisPrincipal));
        botonAddAbajoD.setBackground(colorGrisPrincipal);
        botonAddAbajoD.setForeground(colorRojo);

        // Cambiar color de fondo
        panelTituloAbajoD.setBackground(colorRojo);
        panelTituloAbajoDIzq.setBackground(colorRojo);
        panelTituloAbajoDDer.setBackground(colorRojo);
        panelTareasAbajoD.setBackground(colorRojo);

        // Añadir borde
        panelAbajoD.setBorder(new MatteBorder(5, 5, 10, 10, sRecursos.getBLANCO()));

/* AÑADIR ELEMENTOS */
        /*for (int i=0; i<14; i++){
            crearTareas(colorVerde, panelTareasArribaI);
            crearTareas(colorAzul, panelTareasArribaD);
            crearTareas(colorAmarillo, panelTareasAbajoI);
            crearTareas(colorRojo, panelTareasAbajoD);
        }*/

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

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.add(new VistaMatrix());
        frame.setVisible(true);
    }
}

class WhiteScrollBarUI extends BasicScrollBarUI {
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