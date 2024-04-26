package tareas;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TareaCompletada extends Tarea{
    private ZonedDateTime dataFinalizacion;

    public TareaCompletada(String nombreTarea, String descripcionTarea) {
        super(nombreTarea, descripcionTarea);
        this.dataFinalizacion = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
    }
}
