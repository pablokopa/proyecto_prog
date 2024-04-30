package app.model.tareas;

import java.util.Objects;
import java.util.UUID;

/**
 * Clase que representa una tarea
 */
public class Tarea {

    // Constantes de prioridad
    /**
     * (1) Prioridad importante
     */
    public static final int PRIORIDAD_IMPORTANTE = 1;
    /**
     * (2) Prioridad media
     */
    public static final int PRIORIDAD_MEDIA = 2;
    /**
     * (3) Prioridad baja
     */
    public static final int PRIORIDAD_BAJA = 3;

    // Atributos
    private String nombreTarea;
    private String descripcionTarea;
    private final UUID idTarea;
    private int prioridadTarea;

    /**
     * Constructor
     * @param nombreTarea nombre de la tarea
     * @param descripcionTarea descripción de la tarea
     */
    public Tarea(String nombreTarea, String descripcionTarea){
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;
        this.idTarea = UUID.randomUUID();
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

    // Setters

    /**
     * Establece la prioridad de la tarea
     * @param prioridadTarea Constante de prioridad de Tarea
     */
    public void setPrioridadTarea(int prioridadTarea) {
        this.prioridadTarea = prioridadTarea;
    }

    /**
     * Comprueba si dos objetos (Tarea) tienen el mismo ID
     * @param obj Tarea (o herencia) a comparar
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        if (obj == this) return true;
        Tarea tareaObj = (Tarea) obj;
        return Objects.equals(this.idTarea, tareaObj.idTarea);
    }

    /**
     * Se utiliza automáticamente en el caso de utilizar HashSet, HashMap o HashTable,
     * (No utilizado actualmente en el proyecto)
     * @return hashCode de idTarea
     */
    @Override
    public int hashCode() {
        return idTarea.hashCode();
    }

    /**
     * Método toString
     * @return nombre de la tarea y descripción
     */
    @Override
    public String toString() {
        return this.nombreTarea + ": " + this.descripcionTarea;      // añade id tarea temporalmente para pruebas
    }
}
