package app.controller;

import app.model.tareas.GestorTareas;
import app.model.tareas.Tarea;
import app.view.principal.VistaTareas;
import app.view.templates.TemplatePanelTareaEspecifica;

/**
 * Controlador de tareas de prueba
 */
public class ControladorTareas {
    private final GestorTareas gestorTareas;
    private final VistaTareas vistaTareas;

    public ControladorTareas(GestorTareas gestorTareas, VistaTareas vistaTareas) {
        this.gestorTareas = gestorTareas;
        this.vistaTareas = vistaTareas;

//        gestorTareas.addObserver(vistaTareas);
    }

    public void crearTarea(String nombreT, String descripcionT) {
        if (nombreT.isBlank()){
            System.out.println("Tarea sin nombre no posible");
            return;
        }
        Tarea tarea = new Tarea(nombreT, descripcionT, gestorTareas.getUsuario().getNombreU());
        gestorTareas.crearTarea(tarea);
        new TemplatePanelTareaEspecifica(tarea, gestorTareas, vistaTareas);
        vistaTareas.actualizarVistaTareas();
    }
}
