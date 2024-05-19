package app.view.templates;

import app.model.tareas.GestorTareas;
import app.model.tareas.Tarea;
import app.view.principal.VistaTareas;
import services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que crea un panel con la información de una tarea y le añade funciones.
 * Incluye el panel creado en la columna correspondiente de la VistaTareas.
 */
public class TemplatePanelTareaEspecifica extends JPanel {
    private final Recursos sRecursos = Recursos.getService();
    private final Tarea tarea;
    private final GestorTareas gestorTareas;
    private final VistaTareas vistaTareas;

    private final TemplatePanelTareaEspecifica esto;

    private JLabel labelImagen;
    private JPanel panelTarea;

    /**
     * Constructor de la clase.
     * Crea un panel con la información de una tarea y le añade funciones.
     * Incluye el panel creado en la columna correspondiente de la VistaTareas.
     * @param tarea tarea a mostrar
     * @param gestorTareas gestor de tareas
     * @param vistaTareas vista de tareas
     */
    public TemplatePanelTareaEspecifica(Tarea tarea, GestorTareas gestorTareas, VistaTareas vistaTareas) {
        this.tarea = tarea;
        this.gestorTareas = gestorTareas;
        this.vistaTareas = vistaTareas;

        this.esto = this;

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(Short.MAX_VALUE, 75));
        this.setMaximumSize(new Dimension(Short.MAX_VALUE, 75));
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        crearLabelImagen();
        crearPanelTarea();
    }

    /**
     * Crea un label con una imagen de un check y lo añade al panel.
     */
    private void crearLabelImagen() {
        this.labelImagen = new JLabel();

        /* Pone el check correspondiente cuando se abre la aplicación */
        if (tarea.getCompletadaT()){
            labelImagen.setIcon(sRecursos.getImagenCheck());
        } else {
            labelImagen.setIcon(sRecursos.getImagenCheckSinCheck());
        }

        /* Al iniciar la aplicación; si la tarea está completada la añade al panel de tareas completadas, si no, la añade al panel de tareas por hacer */
        if (tarea.getCompletadaT()){
            vistaTareas.addAColumnaCompletada(esto);
        } else {
            vistaTareas.addAColumnaToDo(esto);
        }

        /* Al hacer click en la tarea, se marca como completada o no y se cambia de columna */
        labelImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gestorTareas.completarTarea(tarea)){
                    if (tarea.getCompletadaT()){
                        labelImagen.setIcon(sRecursos.getImagenCheck());
                        vistaTareas.cambiarAColumnaCompletada(esto);
                    } else {
                        labelImagen.setIcon(sRecursos.getImagenCheckSinCheck());
                        vistaTareas.cambiarAColumnaToDo(esto);
                    }
                }
                vistaTareas.actualizarVistaTareas();
            }
        });

        add(labelImagen, BorderLayout.WEST);
    }

    /**
     * Crea un panel con el nombre de la tarea y lo añade al panel.
     */
    private void crearPanelTarea (){
        this.panelTarea = new JPanel();
        panelTarea.setLayout(new BorderLayout());

        JLabel labelTitulo = new JLabel();
        labelTitulo.setFont(sRecursos.getMonserratBold(16));
        labelTitulo.setText(tarea.getNombreT());

        panelTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vistaTareas.setCardTareaSeleccionada(tarea);
                vistaTareas.actualizarVistaTareas();
            }
        });

        panelTarea.add(labelTitulo, BorderLayout.CENTER);
        add(panelTarea, BorderLayout.CENTER);
    }
}
