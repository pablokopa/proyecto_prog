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

    private JLabel labelArribaI, labelArribaD, labelAbajoI, labelAbajoD;
    private JLabel labelAddArribaI, labelAddArribaD, labelAddAbajoI, labelAddAbajoD;
    private JScrollPane scrollArribaI, scrollArribaD, scrollAbajoI, scrollAbajoD;

    public VistaMatrix(){
        sObjGraficos = ObjGraficos.getService();
        sRecursos = Recursos.getService();

        this.setLayout(new GridLayout(2,2));

        crearPaneles();
    }

    /**
     * Método para crear las tareas de los paneles.
     * @param color Color de fondo de los paneles.
     */
    private void crearTareas(Color color, JPanel lugar){
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Tarea");

        // Ajustar layout y posición de label
        panel.setLayout(new BorderLayout());
        Dimension panelSize = new Dimension(60, 60);
        panel.setPreferredSize(panelSize);
        label.setHorizontalAlignment(JLabel.CENTER); // Alineación del texto

        // Tipo de fuente
        label.setFont(sRecursos.getMonserratBold(15));
        label.setForeground(colorGrisPrincipal); // Color de la letra

        // Borde
        Border bordeVisible = new MatteBorder(3,3,3,3, colorGrisPrincipal);
        Border bordeMargen = new MatteBorder(5,20,5,20, color);
        Border compound = BorderFactory.createCompoundBorder(bordeMargen,bordeVisible);
        panel.setBorder(compound);

        // Color de fondo
        panel.setBackground(colorGrisSecundario);

        // Añadir tarea
        panel.add(label, BorderLayout.CENTER);
        lugar.add(panel);
    }

    private void crearPaneles() {
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
        panelTareasArribaI.setLayout(new BoxLayout(panelTareasArribaI, BoxLayout.Y_AXIS));

        // Crear y configurar scroll
        scrollArribaI = new JScrollPane(panelTareasArribaI);
        scrollArribaI.setBorder(null);
        scrollArribaI.getVerticalScrollBar().setUI(new WhiteScrollBarUI());
        scrollArribaI.getHorizontalScrollBar().setUI(new WhiteScrollBarUI());

        // Cambiar color de fondo
        panelTituloArribaI.setBackground(colorVerde);
        panelTareasArribaI.setBackground(colorVerde);

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
        panelTareasArribaD.setLayout(new BoxLayout(panelTareasArribaD, BoxLayout.Y_AXIS));

        // Crear y configurar scroll
        scrollArribaD = new JScrollPane(panelTareasArribaD);
        scrollArribaD.setBorder(null);
        scrollArribaD.getVerticalScrollBar().setUI(new WhiteScrollBarUI());
        scrollArribaD.getHorizontalScrollBar().setUI(new WhiteScrollBarUI());

        // Cambiar color de fondo
        panelTituloArribaD.setBackground(colorAzul);
        panelTareasArribaD.setBackground(colorAzul);

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
        panelTareasAbajoI.setLayout(new BoxLayout(panelTareasAbajoI, BoxLayout.Y_AXIS));

        // Crear y configurar scroll
        scrollAbajoI = new JScrollPane(panelTareasAbajoI);
        scrollAbajoI.setBorder(null);
        scrollAbajoI.getVerticalScrollBar().setUI(new WhiteScrollBarUI());
        scrollAbajoI.getHorizontalScrollBar().setUI(new WhiteScrollBarUI());

        // Cambiar color de fondo
        panelTituloAbajoI.setBackground(colorAmarillo);
        panelTareasAbajoI.setBackground(colorAmarillo);

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
        panelTareasAbajoD.setLayout(new BoxLayout(panelTareasAbajoD, BoxLayout.Y_AXIS));

        // Crear y configurar scroll
        scrollAbajoD = new JScrollPane(panelTareasAbajoD);
        scrollAbajoD.setBorder(null);
        scrollAbajoD.getVerticalScrollBar().setUI(new WhiteScrollBarUI());
        scrollAbajoD.getHorizontalScrollBar().setUI(new WhiteScrollBarUI());

        // Cambiar color de fondo
        panelTituloAbajoD.setBackground(colorRojo);
        panelTareasAbajoD.setBackground(colorRojo);

        // Añadir borde
        panelAbajoD.setBorder(new MatteBorder(5, 5, 10, 10, sRecursos.getBLANCO()));

/* AÑADIR ELEMENTOS */
        for (int i=0; i<3; i++){
            crearTareas(colorVerde, panelTareasArribaI);
            crearTareas(colorAzul, panelTareasArribaD);
            crearTareas(colorAmarillo, panelTareasAbajoI);
            crearTareas(colorRojo, panelTareasAbajoD);
        }

        // Añadir elementos arriba izquierda
        panelTituloArribaI.add(labelArribaI);
        panelArribaI.add(panelTituloArribaI, BorderLayout.NORTH);
        panelArribaI.add(scrollArribaI, BorderLayout.CENTER);
        add(panelArribaI);

        // Añadir elementos arriba derecha
        panelTituloArribaD.add(labelArribaD);
        panelArribaD.add(panelTituloArribaD, BorderLayout.NORTH);
        panelArribaD.add(scrollArribaD, BorderLayout.CENTER);
        add(panelArribaD);

        // Añadir elementos abajo izquierda
        panelTituloAbajoI.add(labelAbajoI);
        panelAbajoI.add(panelTituloAbajoI, BorderLayout.SOUTH);
        panelAbajoI.add(scrollAbajoI, BorderLayout.CENTER);
        add(panelAbajoI);

        // Añadir elementos abajo derecha
        panelTituloAbajoD.add(labelAbajoD);
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