package app.view.templates;

import app.controller.ControladorTareas;
import app.model.CodigoError;
import app.model.tareas.Tarea;
import app.view.principal.InterfazPrincipal;
import services.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que crea un panel con la información de una tarea y le añade funciones.
 */
public class TemplatePanelTareas extends JPanel {
    private final Recursos sRecursos = Recursos.getService();
    private final Tarea tarea;

    private JLabel labelImagen;
    private JPanel panelTarea;
    private JLabel labelTitulo;

    private final int idT;

    private final ControladorTareas controladorTareas;
    private final InterfazPrincipal interfazPrincipal;

    /**
     * Constructor de la clase
     * Crea un panel con la información de una tarea y le añade funciones.
     * @param tarea tarea a mostrar
     * @param controladorTareas controlador de tareas
     * @param interfazPrincipal interfaz principal
     */
    public TemplatePanelTareas(Tarea tarea, ControladorTareas controladorTareas, InterfazPrincipal interfazPrincipal) {
        this.tarea = tarea;
        this.controladorTareas = controladorTareas;
        this.interfazPrincipal = interfazPrincipal;

        this.idT = tarea.getIdT();

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(Short.MAX_VALUE, 65));
        this.setMaximumSize(new Dimension(Short.MAX_VALUE, 65));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, sRecursos.getBLANCO()));

        crearLabelImagen();
        crearPanelTarea();
        addActionListeners();
    }

    /**
     * Añade un action listener al panel para marcar la tarea como completada y cambiar a los paneles convenientes en todos las vistas
     */
    public void addActionListeners(){

        labelImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int codigoError = controladorTareas.completarTarea(tarea);
                if (codigoError == CodigoError.SIN_ERROR) {
                    if (tarea.getCompletadaT()) {
                        labelImagen.setIcon(sRecursos.getImagenCheck());
                        interfazPrincipal.cambiarAColumnaCompletada(tarea);
                    } else {
                        labelImagen.setIcon(sRecursos.getImagenCheckSinCheck());
                        interfazPrincipal.cambiarAColumnaToDo(tarea);
                    }
                    interfazPrincipal.completarEnMatrix(tarea);
                } else {
                    interfazPrincipal.getLabelMensajesDeErrorGeneral().setText("Sin conexión");
                    sRecursos.crearTimer(interfazPrincipal.getLabelMensajesDeErrorGeneral());
                }

                interfazPrincipal.actualizarVistaTareas();
                interfazPrincipal.actualizarVistaMatrix();
            }
        });

        panelTarea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                interfazPrincipal.setCardTareaSeleccionada(TemplatePanelTareas.this);
            }
        });


    }

    /**
     *  Pone el check correspondiente cuando se abre la aplicación
     */
    private void setImagenDelLabel(){
        if (tarea.getCompletadaT()) {
            labelImagen.setIcon(sRecursos.getImagenCheck());
        } else {
            labelImagen.setIcon(sRecursos.getImagenCheckSinCheck());
        }
    }

    /**
     * Crea un label con una imagen de un check y lo añade al panel.
     */
    private void crearLabelImagen() {
        this.labelImagen = new JLabel();
        labelImagen.setCursor(sRecursos.getCursorMano());
        labelImagen.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        setImagenDelLabel();
        add(labelImagen, BorderLayout.WEST);
    }

    /**
     * Crea un panel con el nombre de la tarea y lo añade al panel.
     */
    private void crearPanelTarea (){
        this.panelTarea = new JPanel();
        panelTarea.setLayout(new BorderLayout());

        this.labelTitulo = new JLabel();
        labelTitulo.setFont(sRecursos.getMontserratMedium(16));
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        labelTitulo.setText(tarea.getNombreT());

        panelTarea.add(labelTitulo, BorderLayout.CENTER);
        add(panelTarea, BorderLayout.CENTER);
    }

    /**
     * Establece el texto del label con el nombre de la tarea.
     * @param titulo nombre de la tarea
     */
    public void setLabelTituloText(String titulo) {
        labelTitulo.setText(titulo);
    }

    /**
     * Obtiene la tarea del panel
     * @return tarea del panel
     */
    public Tarea getTarea() {
        return tarea;
    }

    /**
     * Sobreesribe el método equals para comparar dos objetos de la clase por su id.
     * @param obj objeto a comparar
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)    return true;
        if (obj == null || obj.getClass() != this.getClass())   return false;
        TemplatePanelTareas template = (TemplatePanelTareas) obj;
        return this.idT == template.idT;
    }
}
