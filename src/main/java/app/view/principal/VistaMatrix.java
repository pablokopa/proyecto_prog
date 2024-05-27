package app.view.principal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.border.MatteBorder;

import app.model.tareas.GestorTareas;
import app.view.templates.TemplatePanelTareaEspecifica;
import services.Recursos;


public class VistaMatrix extends JPanel {
    private final Recursos sRecursos;
    GestorTareas gestorTareas = new GestorTareas();

    private final Color colorGrisPrincipal = new Color(59, 59, 59);
    private final Color colorVerde = new Color(175, 255, 168);
    private final Color colorAmarillo = new Color(255, 255, 168);
    private final Color colorRojo = new Color(255, 168, 168);
    private final Color colorAzul = new Color(168, 235, 255);

    private InterfazPrincipal interfazPrincipal;

    private JPanel panelTareasArribaI, panelTareasArribaD, panelTareasAbajoI, panelTareasAbajoD;

    public VistaMatrix(InterfazPrincipal interfazPrincipal) {
        sRecursos = Recursos.getService();

        this.interfazPrincipal = interfazPrincipal;

        this.setLayout(new GridLayout(2, 2));

        crearMatrix();
        addTareasAMatrix();
    }


    public void addTareaArribaI(TemplatePanelTareaEspecifica panelTarea){

    }

    private void addTareasAMatrix(){

        for (TemplatePanelTareaEspecifica panelTarea : gestorTareas.getListaTareasToDo()) {

            panelTarea.getLabelImagen().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (gestorTareas.completarTarea(panelTarea.getTarea())){
                        if (panelTarea.getTarea().getCompletadaT()){
                            panelTarea.getLabelImagen().setIcon(sRecursos.getImagenCheck());
                            interfazPrincipal.cambiarAColumnaCompletada(panelTarea);
                            interfazPrincipal.actualizarVistaTareas();
                        } else {
                            panelTarea.getLabelImagen().setIcon(sRecursos.getImagenCheckSinCheck());
                            interfazPrincipal.cambiarAColumnaToDo(panelTarea);
                            interfazPrincipal.actualizarVistaTareas();
                        }
                    }

                }
            });

            switch (panelTarea.getTarea().getNombreE()) {
                case "No importante / No urgente" -> {
                    panelTareasArribaI.add(panelTarea);
                }
                case "No importante / Urgente" -> {
                    panelTareasArribaD.add(panelTarea);
                }
                case "Importante / No urgente" -> {
                    panelTareasAbajoI.add(panelTarea);
                }
                case "Importante / Urgente" -> {
                    panelTareasAbajoD.add(panelTarea);
                }
            }
        }

        revalidate();
        repaint();
    }

    /**
     * Método para crear la matriz de Eisenhower.
     */
    private void crearMatrix() {
        JPanel panelArribaI = crearPanelesMatrix(colorVerde);
        this.panelTareasArribaI = crearPanelesTareas(panelArribaI, colorVerde, "No importante / No urgente");
        add(panelArribaI);

        JPanel panelArribaD = crearPanelesMatrix(colorAzul);
        this.panelTareasArribaD = crearPanelesTareas(panelArribaD, colorAzul, "No importante / Urgente");
        add(panelArribaD);

        JPanel panelAbajoI = crearPanelesMatrix(colorAmarillo);
        this.panelTareasAbajoI = crearPanelesTareas(panelAbajoI, colorAmarillo, "Importante / No urgente");
        add(panelAbajoI);

        JPanel panelAbajoD = crearPanelesMatrix(colorRojo);
        this.panelTareasAbajoD = crearPanelesTareas(panelAbajoD, colorRojo, "Importante / Urgente");
        add(panelAbajoD);
    }

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
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.X_AXIS));
        panelTitulo.setBackground(color);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        /* Crea el label del título */
        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(sRecursos.getMontserratBold(20));

        /* Crea el botón de añadir tareas */
        JButton botonAddTareas = new JButton("Añadir tarea");
        botonAddTareas.setFont(sRecursos.getMontserratBold(14));
        botonAddTareas.setBorder(new MatteBorder(5, 5, 5, 5, colorGrisPrincipal));
        botonAddTareas.setBackground(colorGrisPrincipal);
        botonAddTareas.setForeground(color);
        botonAddTareas.setCursor(sRecursos.getCursorMano());

        /* Añade el panel del título en una disposición especifica según el panel de matrix */
        switch (titulo) {
            case "No importante / No urgente" -> {
                panel.add(panelTitulo, BorderLayout.NORTH);
                panelTitulo.add(labelTitulo);
                panelTitulo.add(Box.createHorizontalGlue());
                panelTitulo.add(botonAddTareas);

                panel.setBorder(new MatteBorder(0, 10, 5, 5, sRecursos.getBLANCO()));
            }
            case "No importante / Urgente" -> {
                panel.add(panelTitulo, BorderLayout.NORTH);
                panelTitulo.add(botonAddTareas);
                panelTitulo.add(Box.createHorizontalGlue());
                panelTitulo.add(labelTitulo);

                panel.setBorder(new MatteBorder(0, 5, 5, 10, sRecursos.getBLANCO()));
            }
            case "Importante / No urgente" -> {
                panel.add(panelTitulo, BorderLayout.SOUTH);
                panelTitulo.add(labelTitulo);
                panelTitulo.add(Box.createHorizontalGlue());
                panelTitulo.add(botonAddTareas);

                panel.setBorder(new MatteBorder(5, 10, 10, 5, sRecursos.getBLANCO()));
            }
            case "Importante / Urgente" -> {
                panel.add(panelTitulo, BorderLayout.SOUTH);
                panelTitulo.add(botonAddTareas);
                panelTitulo.add(Box.createHorizontalGlue());
                panelTitulo.add(labelTitulo);

                panel.setBorder(new MatteBorder(5, 5, 10, 10, sRecursos.getBLANCO()));
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
}