package app.model.tareas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una tarea completada
 * Hereda de Tarea
 */
public class TareaCompletada extends Tarea{
    /**
     * Establece el día de creación según el día del SO
     */
    private LocalDate diaFinalizacion;
    /**
     * Establece la hora de creación según la hora del SO
     */
    private LocalTime horaFinalizacion;

    /**
     * Constructor. La tarea recibe un ID único desde la clase padre
     * @param nombreTarea nombre de la tarea
     * @param descripcionTarea descripción de la tarea
     */
    public TareaCompletada(String nombreTarea, String descripcionTarea) {
        super(nombreTarea, descripcionTarea);
        this.diaFinalizacion = LocalDate.now();
        this.horaFinalizacion = LocalTime.now();
    }

    /**
     * Día de finalización de la tarea
     * @return día de finalización con formato dd-MM-yyyy
     */
    public String getDiaFinalizacion() {
        DateTimeFormatter diaFormato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return diaFinalizacion.format(diaFormato);
    }

    /**
     * Hora de finalización de la tarea
     * @return hora de finalización con formato HH:mm:ss
     */
    public String getHoraFinalizacion() {
        DateTimeFormatter horaFormato = DateTimeFormatter.ofPattern("HH:mm:ss");
        return horaFinalizacion.format(horaFormato);
    }

    /**
     * Nombre, descripción, día y hora de creación de la tarea
     * @return nombre: descripción - dd-MM-yyyy, HH:mm:ss
     */
    @Override
    public String toString() {
        return super.toString() + " - "+ getDiaFinalizacion()+", "+ getHoraFinalizacion();
    }
}
