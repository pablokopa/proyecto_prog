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

    /**
     * Constructor de la clase
     * @param gestorTareas Gestor de tareas
     */
    public ControladorTareas(GestorTareas gestorTareas) {
        this.gestorTareas = gestorTareas;
    }

    /**
     * Comprueba el nombre y crea una tarea si es correcto
     * @param nombreT Nombre de la tarea
     * @param descripcionT Descripción de la tarea
     * @param nombreE Nombre del encargado
     * @return CodigoError constante
     */
    public int crearTarea(String nombreT, String descripcionT, String nombreE){
        int codigoError = comprobarNombreCrearTarea(nombreT);
        if (codigoError != CodigoError.SIN_ERROR){
            return codigoError;
        }
        Tarea tarea = new Tarea(nombreT, descripcionT, gestorTareas.getUsuario().getNombreU(), nombreE);
        codigoError = gestorTareas.crearTarea(tarea);
        return codigoError;
    }

    /**
     * Modifica una tarea
     * @param tareaSeleccionada Tarea seleccionada
     * @param tarea Tarea con los datos modificados
     * @return CodigoError constante
     */
    public int modificarTarea(Tarea tareaSeleccionada, Tarea tarea){
        int codigoError = comprobarDatosEditarTarea(tareaSeleccionada, tarea);
        if (codigoError != CodigoError.SIN_ERROR){
            return codigoError;
        }
        return gestorTareas.modificarTarea(tarea);
    }

    /**
     * Completar una tarea
     * @param tarea Tarea a completar
     * @return CodigoError constante
     */
    public int completarTarea(Tarea tarea){
        return gestorTareas.completarTarea(tarea);
    }

    /**
     * Elimina una tarea
     * @param tarea Tarea a eliminar
     * @return CodigoError constante
     */
    public int eliminarTarea(Tarea tarea) {
        return gestorTareas.eliminarTarea(tarea);
    }

    /**
     * Comprueba si el nombre de la tarea es correcto
     * @param nombreT Nombre de la tarea
     * @return CodigoError constante
     */
    private int comprobarNombreCrearTarea(String nombreT) {
        return gestorTareas.comprobarNombreCrearTarea(nombreT);
    }

    /**
     * Comprueba si los datos de la tarea editada son correctos
     * @param tareaOriginal Tarea original
     * @param tareaEditada Tarea editada
     * @return CodigoError constante
     */
    private int comprobarDatosEditarTarea(Tarea tareaOriginal, Tarea tareaEditada) {
        return gestorTareas.comprobarDatosEditarTarea(tareaOriginal, tareaEditada);
    }

    /**
     * Devuelve la última tarea desde la base para obtener todos los datos que se asignan automáticamente
     * @return Última tarea
     */
    public Tarea getUltimaTarea(){
        return gestorTareas.getUltimaTarea();
    }

    /**
     * Devuelve el usuario conectado
     * @return Usuario
     */
    public Usuario getUsuario(){
        return gestorTareas.getUsuario();
    }

    /**
     * Devuelve la lista de tareas
     * @return Lista de tareas
     */
    public ArrayList<Tarea> getListaTareas(){
        return gestorTareas.getListaTareas();
    }

}
