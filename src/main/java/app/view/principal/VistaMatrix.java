package app.view.principal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import app.model.tareas.GestorTareas;
import app.view.templates.TemplatePanelTareaEspecifica;
import services.Recursos;


public class VistaMatrix extends JPanel {
    private Recursos sRecursos;
    GestorTareas gestorTareas = new GestorTareas();
    VistaTareas tareas = new VistaTareas(gestorTareas);

    private Color colorGrisPrincipal = new Color(59, 59, 59);
//    private Color colorGrisSecundario = new Color(220, 220, 220);
    private Color colorVerde = new Color(175, 255, 168);
    private Color colorAmarillo = new Color(255, 255, 168);
    private Color colorRojo = new Color(255, 168, 168);
    private Color colorAzul = new Color(168, 235, 255);

    public VistaMatrix() {
        sRecursos = Recursos.getService();
        gestorTareas.getTareasDeBase();

        this.setLayout(new GridLayout(2, 2));

        crearMatrix();
    }

    /**
     * Método para modificar el scrollbar.
     * @param zonaScroll Scroll que se modifica.
     */
    private void modificarScroll(JScrollPane zonaScroll) {
        zonaScroll.setBorder(null);
        zonaScroll.getVerticalScrollBar().setUI(new ScrollBarBlanco());
        zonaScroll.getHorizontalScrollBar().setUI(new ScrollBarBlanco());
    }

    private JPanel crearPanelesMatrix(Color color, String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(color);

        JPanel panelTareas = new JPanel();
        panelTareas.setLayout(new BoxLayout(panelTareas, BoxLayout.Y_AXIS));
        panelTareas.setBackground(color);
        panel.add(panelTareas, BorderLayout.CENTER);

        JScrollPane scrollTareas = new JScrollPane(panelTareas);
        modificarScroll(scrollTareas);
        panel.add(scrollTareas);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.X_AXIS));
        panelTitulo.setBackground(color);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(panelTitulo, BorderLayout.NORTH);

        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(sRecursos.getMonserratBold(20));
        panelTitulo.add(labelTitulo);
        panelTitulo.add(Box.createHorizontalGlue());

        JButton botonAddTareas = new JButton("Añadir tarea");
        botonAddTareas.setFont(sRecursos.getMonserratBold(14));
        botonAddTareas.setBorder(new MatteBorder(5, 5, 5, 5, colorGrisPrincipal));
        botonAddTareas.setBackground(colorGrisPrincipal);
        botonAddTareas.setForeground(color);
        botonAddTareas.setCursor(sRecursos.getCursorMano());

        botonAddTareas.addActionListener(e -> {
            // Obtener los paneles de tareas por hacer
            ArrayList<TemplatePanelTareaEspecifica> panelesTareasToDo = tareas.getListaPanelesTareasToDo();

            // Añadir cada panel de tarea al panel
            for (TemplatePanelTareaEspecifica panelTarea : panelesTareasToDo) {
                panelTareas.add(panelTarea);
            }

            // Actualizar el panel
            panelTareas.revalidate();
            panelTareas.repaint();
        });

        panelTitulo.add(botonAddTareas);

        return panel;
    }

    private void crearMatrix() {
        JPanel panelArribaI = crearPanelesMatrix(colorVerde, "No importante / No urgente");
        panelArribaI.setBorder(new MatteBorder(1, 10, 5, 5, sRecursos.getBLANCO()));
        add(panelArribaI);

        JPanel panelArribaD = crearPanelesMatrix(colorAzul, "No importante / Urgente");
        panelArribaD.setBorder(new MatteBorder(1, 5, 5, 10, sRecursos.getBLANCO()));
        add(panelArribaD);

        JPanel panelAbajoI = crearPanelesMatrix(colorAmarillo, "Importante / No urgente");
        panelAbajoI.setBorder(new MatteBorder(5, 10, 10, 5, sRecursos.getBLANCO()));
        add(panelAbajoI);

        JPanel panelAbajoD = crearPanelesMatrix(colorRojo, "Importante / Urgente");
        panelAbajoD.setBorder(new MatteBorder(5, 5, 10, 10, sRecursos.getBLANCO()));
        add(panelAbajoD);
    }

    static class ScrollBarBlanco extends BasicScrollBarUI {
        @Override
        protected JButton createDecreaseButton(int orientation) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
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
}