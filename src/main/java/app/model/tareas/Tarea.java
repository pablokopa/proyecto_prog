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

    public Tarea(String nombreT, String descripcionT, String nombreU, String nombreE){
        this.nombreT = nombreT;
        this.descripcionT = descripcionT;
        this.nombreU = nombreU;
        this.nombreE = nombreE;

        this.fechaCreacionT = new Timestamp(System.currentTimeMillis());

        this.fechaFinalizacionT = null;
        this.completadaT = false;
    }

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

    public void setNombreT(String nombreT) {
        this.nombreT = nombreT;
    }

    public void setDescripcionT(String descripcionT) {
        this.descripcionT = descripcionT;
    }

    public void setNombreE(String nombreE) {
        this.nombreE = nombreE;
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

    public String getFechaCreacionFormat() {
        return simpleDateFormat.format(fechaCreacionT);
    }

    public String getFechaFinalizacionFormat() {
        if (fechaFinalizacionT != null) {
            return simpleDateFormat.format(fechaFinalizacionT);
        } else {
            return null;
        }
    }

    public boolean getCompletadaT() {
        return completadaT;
    }

    public String getNombreU() {
        return nombreU;
    }

    public String getNombreE() {
        return nombreE;
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
