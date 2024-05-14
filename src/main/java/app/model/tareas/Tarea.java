package app.model.tareas;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

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

    // Getters
    /**
     * @return nombre de la tarea
     */
    public String getNombreTarea() {
        return nombreTarea;
    }
    /**
     * @return descripción de la tarea
     */
    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    // Setters
    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }
    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }


    /**
     * Comprueba si dos objetos (Tarea) tienen el mismo ID
     * @param obj Tarea (o herencia) a comparar
     * @return true si son iguales
     */
 /*   @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        if (obj == this) return true;
        Tarea tareaObj = (Tarea) obj;
        return Objects.equals(this.idTarea, tareaObj.idTarea);
    }*/

    /**
     * Método toString
     * @return nombre de la tarea y descripción
     */
    @Override
    public String toString() {
        return this.nombreTarea + ": " + this.descripcionTarea;      // añade id tarea temporalmente para pruebas
    }
}
