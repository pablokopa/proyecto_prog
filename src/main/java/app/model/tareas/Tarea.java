package app.model.tareas;

import java.sql.Timestamp;

/**
 * Clase que representa una tarea
 */
public class Tarea {

    // Atributos
    private String nombreTarea;
    private String descripcionTarea;
    private Timestamp fechaCreacion;
    private Timestamp fechaFinalizacion;
    private boolean completada;

    /**
     * Constructor
     * @param nombreTarea nombre de la tarea
     * @param descripcionTarea descripción de la tarea
     */
    public Tarea(String nombreTarea, String descripcionTarea){
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;
        this.fechaCreacion = new Timestamp(System.currentTimeMillis());

        this.fechaFinalizacion = null;
        this.completada = false;
    }

    public Tarea(String nombreTarea, String descripcionTarea, Timestamp fechaCreacion, Timestamp fechaFinalizacion, Boolean completada){
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;
        this.fechaCreacion = fechaCreacion;
        this.fechaFinalizacion = fechaFinalizacion;
        this.completada = completada;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Método toString
     * @return nombre de la tarea y descripción
     */
    @Override
    public String toString() {
        return this.nombreTarea + ": " + this.descripcionTarea + " - " + this.fechaCreacion;      // añade id tarea temporalmente para pruebas
    }
}
