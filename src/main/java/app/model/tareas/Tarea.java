package app.model.tareas;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Clase que representa una tarea
 */
public class Tarea {
    private int idT;
    private String nombreT;
    private String descripcionT;
    private Timestamp fechaCreacionT;
    private Timestamp fechaFinalizacionT;
    private boolean completadaT;
    private final String nombreU;
    private String nombreE;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Constructor de la clase
     * @param nombreT nombre de la tarea
     * @param descripcionT descripción de la tarea
     * @param nombreU nombre del usuario
     * @param nombreE nombre del equipo
     */
    public Tarea(String nombreT, String descripcionT, String nombreU, String nombreE){
        this.nombreT = nombreT;
        this.descripcionT = descripcionT;
        this.nombreU = nombreU;
        this.nombreE = nombreE;

        this.fechaCreacionT = new Timestamp(System.currentTimeMillis());

        this.fechaFinalizacionT = null;
        this.completadaT = false;
    }

    /**
     * Constructor de la clase
     * @param idT id de la tarea
     * @param nombreT nombre de la tarea
     * @param descripcionT descripción de la tarea
     * @param fechaCreacionT fecha de creación de la tarea
     * @param fechaFinalizacionT fecha de finalización de la tarea
     * @param completadaT si la tarea está completada
     * @param nombreU nombre del usuario
     * @param nombreE nombre del equipo
     */
    public Tarea(int idT, String nombreT, String descripcionT, Timestamp fechaCreacionT, Timestamp fechaFinalizacionT, Boolean completadaT, String nombreU, String nombreE){
        this.idT = idT;
        this.nombreT = nombreT;
        this.descripcionT = descripcionT;
        this.fechaCreacionT = fechaCreacionT;
        this.fechaFinalizacionT = fechaFinalizacionT;
        this.completadaT = completadaT;
        this.nombreU = nombreU;
        this.nombreE = nombreE;
    }

    /**
     * Estable el nombre de la tarea
     * @param nombreT nombre de la tarea
     */
    public void setNombreT(String nombreT) {
        this.nombreT = nombreT;
    }

    /**
     * Establece la descripción de la tarea
     * @param descripcionT descripción de la tarea
     */
    public void setDescripcionT(String descripcionT) {
        this.descripcionT = descripcionT;
    }

    /**
     * Establece el nombre de la etiqueta
     * @param nombreE nombre de la etiqueta
     */
    public void setNombreE(String nombreE) {
        this.nombreE = nombreE;
    }

    /**
     * Obtiene el id de la tarea
     * @return id de la tarea
     */
    public int getIdT() {
        return idT;
    }

    /**
     * Obtiene el nombre de la tarea
     * @return nombre de la tarea
     */
    public String getNombreT() {
        return nombreT;
    }

    /**
     * Obtiene la descripción de la tarea
     * @return descripción de la tarea
     */
    public String getDescripcionT() {
        return descripcionT;
    }

    /**
     * Obtiene la fecha de creación de la tarea
     * @return fecha de creación de la tarea
     */
    public Timestamp getFechaCreacionT() {
        return fechaCreacionT;
    }

    /**
     * Obtiene la fecha de finalización de la tarea
     * @return fecha de finalización de la tarea
     */
    public Timestamp getFechaFinalizacionT() {
        return fechaFinalizacionT;
    }

    /**
     * Obtiene la fecha de creación de la tarea en formato dd/MM/yyyy
     * @return fecha de creación de la tarea en formato dd/MM/yyyy
     */
    public String getFechaCreacionFormat() {
        return simpleDateFormat.format(fechaCreacionT);
    }

    /**
     * Obtiene la fecha de finalización de la tarea en formato dd/MM/yyyy
     * @return fecha de finalización de la tarea en formato dd/MM/yyyy
     */
    public String getFechaFinalizacionFormat() {
        if (fechaFinalizacionT != null) {
            return simpleDateFormat.format(fechaFinalizacionT);
        } else {
            return null;
        }
    }

    /**
     * Obtiene si la tarea está completada
     * @return true si la tarea está completada
     */
    public boolean getCompletadaT() {
        return completadaT;
    }

    /**
     * Obtiene el nombre del usuario
     * @return nombre del usuario
     */
    public String getNombreU() {
        return nombreU;
    }

    /**
     * Obtiene el nombre de la etiqueta
     * @return nombre de la etiqueta
     */
    public String getNombreE() {
        return nombreE;
    }

    /**
     * Establece si la tarea está completada
     * @param completadaT true si la tarea está completada
     */
    public void setCompletadaT(boolean completadaT) {
        this.completadaT = completadaT;
    }

    /**
     * Establece la fecha de finalización de la tarea
     * @param fechaFinalizacionT fecha de finalización de la tarea
     */
    public void setFechaFinalizacionT(Timestamp fechaFinalizacionT) {
        this.fechaFinalizacionT = fechaFinalizacionT;
    }

    /**
     * Método toString
     * @return nombre de la tarea y descripción
     */
    @Override
    public String toString() {
        return "tarea: "+ idT + " - " + nombreT +" - "+ descripcionT +" - "+ fechaCreacionT +" - "+ fechaFinalizacionT +" - "+ completadaT+" - "+ nombreU ;
    }
}
