package app.view.templates;

import app.model.tareas.GestorTareas;
import app.model.tareas.Tarea;
import app.view.principal.VistaTareas;
import services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TemplatePanelTareaEspecifica extends JPanel {
    Recursos sRecursos = Recursos.getService();

    Tarea tarea;
    GestorTareas gestorTareas;

    VistaTareas vistaTareas;

    JLabel labelImagen;
    JPanel panelTarea;

    TemplatePanelTareaEspecifica esto;

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
            vistaTareas.añadirAColumnaCompletada(esto);
        } else {
            vistaTareas.añadirAColumnaToDo(esto);
        }

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

    private void crearPanelTarea (){
        this.panelTarea = new JPanel();
        panelTarea.setLayout(new BorderLayout());

        JLabel labelTitulo = new JLabel();
        labelTitulo.setFont(sRecursos.getMonserratBold(16));
        labelTitulo.setText(tarea.getNombreT());

        panelTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vistaTareas.getCardLayout().show(vistaTareas.getPanelColumnaInformacionExtra(), "InfoSeleccionada");
            }
        });

        panelTarea.add(labelTitulo, BorderLayout.CENTER);
        add(panelTarea, BorderLayout.CENTER);
    }
}
