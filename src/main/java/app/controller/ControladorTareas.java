package app.controller;

import app.model.CodigoError;
import app.model.tareas.GestorTareas;
import app.model.tareas.Tarea;
import app.model.usuarios.Usuario;

import java.util.ArrayList;

/**
 * Controlador de tareas
 */
public class ControladorTareas {
    private final GestorTareas gestorTareas;

    public ControladorTareas(GestorTareas gestorTareas) {
        this.gestorTareas = gestorTareas;

    }

    private int comprobarNombreCrearTarea(String nombreT) {
        return gestorTareas.comprobarNombreCrearTarea(nombreT);
    }

    private int comprobarDatosEditarTarea(Tarea tareaOriginal, Tarea tareaEditada) {
        return gestorTareas.comprobarDatosEditarTarea(tareaOriginal, tareaEditada);
    }

    public int crearTarea(String nombreT, String descripcionT, String nombreE){
        int codigoError = comprobarNombreCrearTarea(nombreT);
        if (codigoError != CodigoError.SIN_ERROR){
            return codigoError;
        }
        Tarea tarea = new Tarea(nombreT, descripcionT, gestorTareas.getUsuario().getNombreU(), nombreE);
        codigoError = gestorTareas.crearTarea(tarea);
        return codigoError;
    }

    public int modificarTarea(Tarea tareaSeleccionada, Tarea tarea){
        int codigoError = comprobarDatosEditarTarea(tareaSeleccionada, tarea);
        if (codigoError != CodigoError.SIN_ERROR){
            return codigoError;
        }
        return gestorTareas.modificarTarea(tarea);
    }

    public int completarTarea(Tarea tarea){
        return gestorTareas.completarTarea(tarea);
    }

    public int eliminarTarea(Tarea tarea) {
        return gestorTareas.eliminarTarea(tarea);
    }

    public Tarea getUltimaTarea(){
        return gestorTareas.getUltimaTarea();
    }

    public Usuario getUsuario(){
        return gestorTareas.getUsuario();
    }

    public ArrayList<Tarea> getListaTareas(){
        return gestorTareas.getListaTareas();
    }

}
