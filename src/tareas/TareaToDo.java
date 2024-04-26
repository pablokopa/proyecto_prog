package tareas;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Clase que representa una tarea por hacer.
 * Hereda de Tarea
 */
public class TareaToDo extends Tarea{
    /**
     * Fecha de creación de la tarea. Establece la hora y día basado en la zona horaria de España
     */
    private ZonedDateTime fechaCreacion;  // fecha de creación de la tarea

    /**
     * Constructor. La tarea recibe un ID único desde la clase padre
     * @param nombreTarea nombre de la tarea
     * @param descripcionTarea descripción de la tarea
     */
    public TareaToDo(String nombreTarea, String descripcionTarea) {
        super(nombreTarea, descripcionTarea);
        this.fechaCreacion = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));      // añade la fecha de creación y hora basada en la zona horaria de España
    }


}
