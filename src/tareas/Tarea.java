package tareas;

import java.util.Objects;
import java.util.UUID;

public class Tarea {
    private String nombreTarea;
    private String descripcionTarea;
    private final UUID idTarea;

    public Tarea(String nombreTarea, String descripcionTarea){
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;
        this.idTarea = UUID.randomUUID();       // añade un identificador único por cada instancia de la clase
    }

    public UUID getIdTarea() {
        return idTarea;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        if (obj == this) return true;
        Tarea tareaObj = (Tarea) obj;
        return Objects.equals(this.idTarea, tareaObj.idTarea);
    }

    /*
    se utiliza junto al equals , dado que se espera que dos objetos iguales tengan el mismo hashcode
     */
    @Override
    public int hashCode() {
        return idTarea.hashCode();
    }

    @Override
    public String toString() {
        return this.nombreTarea + ": " + this.descripcionTarea + " - " + this.idTarea;
    }
}
