package tareas;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TareaToDo extends Tarea{
    private ZonedDateTime fechaCreacion;

    /**
     * Constructor, recibe un ID único desde la clase padre
     * @param nombreTarea nombre de la tarea
     * @param descripcionTarea descripción de la tarea
     */
    public TareaToDo(String nombreTarea, String descripcionTarea) {
        super(nombreTarea, descripcionTarea);
        this.fechaCreacion = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));      // añade la fecha de creación y hora basada en la zona horaria de España
    }


}
