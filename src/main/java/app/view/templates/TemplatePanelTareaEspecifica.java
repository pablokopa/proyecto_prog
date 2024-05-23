package app.view.templates;

import app.model.tareas.Tarea;
import services.Recursos;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que crea un panel con la información de una tarea y le añade funciones.
 * Incluye el panel creado en la columna correspondiente de la VistaTareas.
 */
public class TemplatePanelTareaEspecifica extends JPanel {
    private final Recursos sRecursos = Recursos.getService();
    private final Tarea tarea;

    private JLabel labelImagen;
    private JPanel panelTarea;

    private final int idT;

    /**
     * Constructor de la clase.
     * Crea un panel con la información de una tarea y le añade funciones.
     * Incluye el panel creado en la columna correspondiente de la VistaTareas.
     * @param tarea tarea a mostrar
     */
    public TemplatePanelTareaEspecifica(Tarea tarea) {
        this.tarea = tarea;

        this.idT = tarea.getIdT();

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

        panelTarea.add(labelTitulo, BorderLayout.CENTER);
        add(panelTarea, BorderLayout.CENTER);
    }

    public JLabel getLabelImagen() {
        return labelImagen;
    }

    public JPanel getPanelTarea() {
        return panelTarea;
    }

    public Tarea getTarea() {
        return tarea;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)    return true;
        if (obj == null || obj.getClass() != this.getClass())   return false;
        TemplatePanelTareaEspecifica template = (TemplatePanelTareaEspecifica) obj;
        return this.idT == template.idT;
    }
}
