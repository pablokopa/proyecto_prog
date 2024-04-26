package tareas;

import java.util.Objects;
import java.util.UUID;

public class Tarea {
    /**
     *     Constantes de clase.
     *     Están en Tarea y no en una interfaz porque no se comparten con otras clases
     *     que no sean heredadas de Tarea
     */
    public static final int PRIORIDAD_IMPORTANTE = 1;
    public static final int PRIORIDAD_MEDIA = 2;
    public static final int PRIORIDAD_BAJA = 3;

    private String nombreTarea;
    private String descripcionTarea;
    private final UUID idTarea;
    private int prioridadTarea;

    public Tarea(String nombreTarea, String descripcionTarea){
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;
        this.idTarea = UUID.randomUUID();       // añade un identificador único por cada instancia de la clase
    }

    // Getters
    public UUID getIdTarea() {
        return idTarea;
    }
    public String getNombreTarea() {
        return nombreTarea;
    }
    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    // Setters
    public void setPrioridadTarea(int prioridadTarea) {
        this.prioridadTarea = prioridadTarea;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        if (obj == this) return true;
        Tarea tareaObj = (Tarea) obj;
        return Objects.equals(this.idTarea, tareaObj.idTarea);
    }

    // Se utiliza junto al equals, dado que se espera que dos objetos iguales tengan el mismo hashcode
    @Override
    public int hashCode() {
        return idTarea.hashCode();
    }

    @Override
    public String toString() {
        return this.nombreTarea + ": " + this.descripcionTarea + " - " + this.idTarea;
    }
}
