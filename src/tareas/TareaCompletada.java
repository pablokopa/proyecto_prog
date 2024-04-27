package tareas;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Clase que representa una tarea completada
 * Hereda de Tarea
 */
public class TareaCompletada extends Tarea{
    /**
     * Fecha de finalización de la tarea. Establece la hora y día basado en la zona horaria de España
     */
    private ZonedDateTime dataFinalizacion;

    /**
     * Constructor. La tarea recibe un ID único desde la clase padre
     * @param nombreTarea nombre de la tarea
     * @param descripcionTarea descripción de la tarea
     */
    public TareaCompletada(String nombreTarea, String descripcionTarea) {
        super(nombreTarea, descripcionTarea);
        this.dataFinalizacion = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
    }
}
