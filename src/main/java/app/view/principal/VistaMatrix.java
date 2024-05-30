package app.view.principal;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

import services.Recursos;

/**
 * Vista de la matriz de Eisenhower.
 */
public class VistaMatrix extends JPanel {
    private final Recursos sRecursos;

    private JPanel panelTareasArribaI, panelTareasArribaD, panelTareasAbajoI, panelTareasAbajoD;

    /**
     * Constructor de la clase
     */
    public VistaMatrix() {
        sRecursos = Recursos.getService();

        this.setLayout(new GridLayout(2, 2));

        crearMatrix();
    }

    /**
     * Método para obtener el panel de las tareas de "No importante / No urgente".
     * @return Paneles de las tareas.
     */
    public JPanel getPanelTareasArribaI() {
        return panelTareasArribaI;
    }

    /**
     * Método para obtener el panel de las tareas de "No importante / Urgente".
     * @return Paneles de las tareas.
     */
    public JPanel getPanelTareasArribaD() {
        return panelTareasArribaD;
    }

    /**
     * Método para obtener el panel de las tareas de "Importante / No urgente".
     * @return Paneles de las tareas.
     */
    public JPanel getPanelTareasAbajoI() {
        return panelTareasAbajoI;
    }

    /**
     * Método para obtener el panel de las tareas de "Importante / Urgente".
     * @return Paneles de las tareas.
     */
    public JPanel getPanelTareasAbajoD() {
        return panelTareasAbajoD;
    }

    /**
     * Método para crear la matriz de Eisenhower.
     */
    private void crearMatrix() {
        JPanel panelArribaI = crearPanelesMatrix(sRecursos.getColorVerde());
        this.panelTareasArribaI = crearPanelesTareas(panelArribaI, sRecursos.getColorVerde(), "No importante / No urgente");
        add(panelArribaI);

        JPanel panelArribaD = crearPanelesMatrix(sRecursos.getColorAzul());
        this.panelTareasArribaD = crearPanelesTareas(panelArribaD, sRecursos.getColorAzul(), "No importante / Urgente");
        add(panelArribaD);

        JPanel panelAbajoI = crearPanelesMatrix(sRecursos.getColorAmarillo());
        this.panelTareasAbajoI = crearPanelesTareas(panelAbajoI, sRecursos.getColorAmarillo(), "Importante / No urgente");
        add(panelAbajoI);

        JPanel panelAbajoD = crearPanelesMatrix(sRecursos.getColorRojo());
        this.panelTareasAbajoD = crearPanelesTareas(panelAbajoD, sRecursos.getColorRojo(), "Importante / Urgente");
        add(panelAbajoD);
    }

    /**
     * Método para crear los paneles de las tareas.
     * @param panel Panel donde se añadirán las tareas.
     * @param color Color del panel.
     * @param titulo Título del panel.
     * @return Panel creado.
     */
    private JPanel crearPanelesTareas(JPanel panel, Color color , String titulo){
        /* Crea el panel de tareas */
        JPanel panelTareas = new JPanel();
        panelTareas.setLayout(new BoxLayout(panelTareas, BoxLayout.Y_AXIS));
        panelTareas.setBackground(color);

        /* Añade el scroll al panel de tareas */
        JScrollPane scrollTareas = new JScrollPane(panelTareas);
        sRecursos.crearScrollModificado(scrollTareas, color, sRecursos.getBLANCO());
        panel.add(scrollTareas, BorderLayout.CENTER);

        /* Crea el panel del título y del botón añadir tareas */
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(color);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        /* Crea el label del título */
        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(sRecursos.getMontserratBold(20));
        panelTitulo.add(labelTitulo);

        /* Añade el panel del título en una disposición especifica según el panel de matrix */
        switch (titulo) {
            case "No importante / No urgente" -> {
                panel.add(panelTitulo, BorderLayout.NORTH);

                Border borderFuera = BorderFactory.createMatteBorder(0, 10, 5, 5, sRecursos.getBLANCO());
                Border borderDentro = BorderFactory.createMatteBorder(0, 3, 1, 3, color);
                panel.setBorder(BorderFactory.createCompoundBorder(borderFuera, borderDentro));
            }
            case "No importante / Urgente" -> {
                panel.add(panelTitulo, BorderLayout.NORTH);

                Border borderFuera = BorderFactory.createMatteBorder(0, 5, 5, 10, sRecursos.getBLANCO());
                Border borderDentro = BorderFactory.createMatteBorder(0, 3, 1, 3, color);
                panel.setBorder(BorderFactory.createCompoundBorder(borderFuera, borderDentro));
            }
            case "Importante / No urgente" -> {
                panel.add(panelTitulo, BorderLayout.SOUTH);

                Border borderFuera = BorderFactory.createMatteBorder(5, 10, 10, 5, sRecursos.getBLANCO());
                Border borderDentro = BorderFactory.createMatteBorder(1, 3, 0, 3, color);
                panel.setBorder(BorderFactory.createCompoundBorder(borderFuera, borderDentro));
            }
            case "Importante / Urgente" -> {
                panel.add(panelTitulo, BorderLayout.SOUTH);

                Border borderFuera = BorderFactory.createMatteBorder(5, 5, 10, 10, sRecursos.getBLANCO());
                Border borderDentro = BorderFactory.createMatteBorder(1, 3, 0, 3, color);
                panel.setBorder(BorderFactory.createCompoundBorder(borderFuera, borderDentro));
            }
        }
        panel.add(scrollTareas, BorderLayout.CENTER);

        return panelTareas;
    }

    /**
     * Método para crear los paneles de la matriz.
     * @param color Color del panel.
     * @return Panel creado.
     */
    private JPanel crearPanelesMatrix(Color color) {
        /* Crea el panel principal */
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(color);
        return panel;
    }

    /**
     * Método para actualizar la vista de la matriz.
     */
    public void actualizarVistaMatrix(){
        this.repaint();
        this.revalidate();
    }
}