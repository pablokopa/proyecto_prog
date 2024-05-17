package app.model.tareas;

import java.sql.Timestamp;

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
    private String nombreU;

    public Tarea(String nombreT, String descripcionT, String nombreU){
        this.nombreT = nombreT;
        this.descripcionT = descripcionT;
        this.nombreU = nombreU;

        this.fechaCreacionT = new Timestamp(System.currentTimeMillis());

        this.fechaFinalizacionT = null;
        this.completadaT = false;
    }

    public Tarea(int idT, String nombreT, String descripcionT, Timestamp fechaCreacionT, Timestamp fechaFinalizacionT, Boolean completadaT, String nombreU){
        this.idT = idT;
        this.nombreT = nombreT;
        this.descripcionT = descripcionT;
        this.fechaCreacionT = fechaCreacionT;
        this.fechaFinalizacionT = fechaFinalizacionT;
        this.completadaT = completadaT;
        this.nombreU = nombreU;
    }

    public int getIdT() {
        return idT;
    }

    public String getNombreT() {
        return nombreT;
    }

    public String getDescripcionT() {
        return descripcionT;
    }

    public Timestamp getFechaCreacionT() {
        return fechaCreacionT;
    }

    public Timestamp getFechaFinalizacionT() {
        return fechaFinalizacionT;
    }

    public boolean getCompletadaT() {
        return completadaT;
    }

    public void setCompletadaT(boolean completadaT) {
        this.completadaT = completadaT;
    }

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
