package app.controller;

import app.model.tareas.GestorTareas;
import app.view.principal.VistaTareas;

/**
 * Controlador de tareas
 */
public class ControladorTareas {
    private final GestorTareas gestorTareas;
    private final VistaTareas vistaTareas;

    public ControladorTareas(GestorTareas gestorTareas, VistaTareas vistaTareas) {
        this.gestorTareas = gestorTareas;
        this.vistaTareas = vistaTareas;

    }


}
