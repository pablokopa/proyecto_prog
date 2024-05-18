package app.view.templates;

import app.model.tareas.Tarea;
import services.Recursos;

import javax.swing.*;
import java.awt.*;

public class TemplatePanelTareaEspecifica extends JPanel {
    Recursos sRecursos = Recursos.getService();

    Tarea tarea;

    JLabel labelImagen;
    JPanel panelTarea;

    public TemplatePanelTareaEspecifica(Tarea tarea) {
        this.tarea = tarea;

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(Short.MAX_VALUE, 75));
        this.setMaximumSize(new Dimension(Short.MAX_VALUE, 75));
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        crearLabelImagen(tarea);
        crearPanelTarea(tarea);
    }

    private void crearLabelImagen(Tarea tarea) {
        this.labelImagen = new JLabel();

        /* Pone el check correspondiente cuando se abre la aplicación */
        if (tarea.getCompletadaT()){
            labelImagen.setIcon(sRecursos.getImagenCheck());
        } else {
            labelImagen.setIcon(sRecursos.getImagenCheckSinCheck());
        }

        add(labelImagen, BorderLayout.WEST);
    }

    private void crearPanelTarea (Tarea tarea){
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
}
