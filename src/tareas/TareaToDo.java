package tareas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una tarea por hacer.
 * Hereda de Tarea
 */
public class TareaToDo extends Tarea{
    /**
     * Establece el día de creación según el día del SO
     */
    private LocalDate diaCreacion;
    /**
     * Establece la hora de creación según la hora del SO
     */
    private LocalTime horaCreacion;

    /**
     * Constructor. La tarea recibe un ID único desde la clase padre
     * @param nombreTarea nombre de la tarea
     * @param descripcionTarea descripción de la tarea
     */
    public TareaToDo(String nombreTarea, String descripcionTarea) {
        super(nombreTarea, descripcionTarea);
        this.diaCreacion = LocalDate.now();
        this.horaCreacion = LocalTime.now();
    }

    /**
     * Día de creación de la tarea
     * @return día de creación con formato dd-MM-yyyy
     */
    public String getDiaCreacion() {
        DateTimeFormatter diaFormato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return diaCreacion.format(diaFormato);
    }

    /**
     * Hora de creación de la tarea
     * @return hora de creación con formato HH:mm:ss
     */
    public String getHoraCreacion() {
        DateTimeFormatter horaFormato = DateTimeFormatter.ofPattern("HH:mm:ss");
        return horaCreacion.format(horaFormato);
    }

    /**
     * Nombre, descripción, día y hora de creación de la tarea
     * @return nombre: descripción - dd-MM-yyyy, HH:mm:ss
     */
    @Override
    public String toString() {
        return super.toString() + " - "+getDiaCreacion()+", "+getHoraCreacion();
    }
}
