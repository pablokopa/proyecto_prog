package app.model.tareas;

import app.model.basedatos.ConectarBD;
import app.model.usuarios.Usuario;
import app.view.principal.InterfazPrincipal;
import app.view.principal.VistaMatrix;
import app.view.principal.VistaTareas;
import app.view.templates.TemplatePanelMatrix;
import app.view.templates.TemplatePanelTareas;
import services.Recursos;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class GestorTareas {
    private final ArrayList<Tarea> listaTareas;
    private final Usuario usuario;
    private final Recursos sRecursos = Recursos.getService();

    private ArrayList<TemplatePanelTareas> listaTareasToDo, listaTareasCompletadas;
    private ArrayList<TemplatePanelMatrix> listaTareasArribaI, listaTareasArribaD, listaTareasAbajoI, listaTareasAbajoD;

    private InterfazPrincipal interfazPrincipal;
    private VistaTareas vistaTareas;
    private VistaMatrix vistaMatrix;

    public GestorTareas(InterfazPrincipal interfazPrincipal, VistaTareas vistaTareas, VistaMatrix vistaMatrix) {
        this.usuario = Usuario.getUsuarioConectado();

        this.interfazPrincipal = interfazPrincipal;
        this.vistaTareas = vistaTareas;
        this.vistaMatrix = vistaMatrix;


        this.listaTareas = new ArrayList<>();

        this.listaTareasToDo = new ArrayList<>();
        this.listaTareasCompletadas = new ArrayList<>();
        this.listaTareasArribaI = new ArrayList<>();
        this.listaTareasArribaD = new ArrayList<>();
        this.listaTareasAbajoI = new ArrayList<>();
        this.listaTareasAbajoD = new ArrayList<>();

        getTareasDeBase();
    }

    /**
     * Obtiene el usuario conectado.
     * @return usuario conectado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Obtiene las tareas del usuario registrado de la base de datos y las añade a una lista para reducir el número de consultas.
     */
    public void getTareasDeBase(){
        String sql = "select * from tarea where nombreu = ?";

        try (Connection conexion = ConectarBD.conectar()){
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, usuario.getNombreU());

            prepare.executeQuery();
            ResultSet resultado = prepare.getResultSet();

            /* Obtiene los resultados y añade las tareas a la lista */
            while (resultado.next()){
                int idT = resultado.getInt("idt");
                String nombreT = resultado.getString("nombret");
                String descripcionT = resultado.getString("descripciont");
                Timestamp fechaCreacionT = resultado.getTimestamp("fechacreaciont");
                Timestamp fechaFinalizacionT = resultado.getTimestamp("fechafinalizaciont");
                Boolean completadaT = resultado.getBoolean("completadat");
                String nombreE = resultado.getString("nombree");

                Tarea tarea = new Tarea(idT, nombreT, descripcionT, fechaCreacionT, fechaFinalizacionT, completadaT, usuario.getNombreU(), nombreE);
                TemplatePanelTareas panelTareas = new TemplatePanelTareas(tarea, this, interfazPrincipal, vistaMatrix);
                TemplatePanelMatrix panelMatrix = new TemplatePanelMatrix(tarea, this, interfazPrincipal, vistaTareas);

                if (completadaT){
                    listaTareasCompletadas.add(panelTareas);
                } else {
                    listaTareasToDo.add(panelTareas);

                    switch (panelTareas.getTarea().getNombreE()){
                        case "No importante / No urgente" -> listaTareasArribaI.add(panelMatrix);
                        case "No importante / Urgente" -> listaTareasArribaD.add(panelMatrix);
                        case "Importante / No urgente" -> listaTareasAbajoI.add(panelMatrix);
                        case "Importante / Urgente" -> listaTareasAbajoD.add(panelMatrix);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("CATCH EN GestorTareas.getTareasDeBase()");
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la última tarea añadida a la base de datos.
     * @return la última tarea añadida
     */
    public Tarea getUltimaTarea(){
        String sql = "SELECT * FROM tarea ORDER BY idT DESC LIMIT 1;";

        try (Connection conexion = ConectarBD.conectar()){
            PreparedStatement prepare = conexion.prepareStatement(sql);

            prepare.executeQuery();
            ResultSet resultado = prepare.getResultSet();

            /* Obtiene los resultados y añade las tareas a la lista */
            if (resultado.next()){
                int idT = resultado.getInt("idt");
                String nombreT = resultado.getString("nombret");
                String descripcionT = resultado.getString("descripciont");
                Timestamp fechaCreacionT = resultado.getTimestamp("fechacreaciont");
                Timestamp fechaFinalizacionT = resultado.getTimestamp("fechafinalizaciont");
                Boolean completadaT = resultado.getBoolean("completadat");
                String nombreE = resultado.getString("nombree");

                Tarea tarea = new Tarea(idT, nombreT, descripcionT, fechaCreacionT, fechaFinalizacionT, completadaT, usuario.getNombreU(), nombreE);
                TemplatePanelTareas panelTareas = new TemplatePanelTareas(tarea, this, interfazPrincipal, vistaMatrix);
                TemplatePanelMatrix panelMatrix = new TemplatePanelMatrix(tarea, this, interfazPrincipal, vistaTareas);

                if (completadaT){
                    listaTareasCompletadas.add(panelTareas);
                } else {
                    listaTareasToDo.add(panelTareas);

                    switch (panelTareas.getTarea().getNombreE()){
                        case "No importante / No urgente" -> listaTareasArribaI.add(panelMatrix);
                        case "No importante / Urgente" -> listaTareasArribaD.add(panelMatrix);
                        case "Importante / No urgente" -> listaTareasAbajoI.add(panelMatrix);
                        case "Importante / Urgente" -> listaTareasAbajoD.add(panelMatrix);
                    }
                }

                return tarea;
            }
        } catch (SQLException e) {
            System.out.println("CATCH EN GestorTareas.getTareasDeBase()");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Añade una tarea a la base de datos y a la lista de tareas.
     * @param tarea tarea a añadir
     */
    public void crearTarea(Tarea tarea) {
        String sql = "INSERT INTO tarea (nombreT, descripcionT, fechaCreacionT, nombreU, nombreE) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConectarBD.conectar()) {
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, tarea.getNombreT());
            prepare.setString(2, tarea.getDescripcionT());
            prepare.setTimestamp(3, tarea.getFechaCreacionT());
            prepare.setString(4, usuario.getNombreU());
            prepare.setString(5, tarea.getNombreE());

            prepare.executeUpdate();
            this.listaTareas.add(tarea);
        } catch (SQLException e) {
            System.out.println("CATCH EN crearTarea()");
            e.printStackTrace();
        }
    }

    /**
     * Completa o descompleta una tarea.
     * @param tarea tarea a completar
     * @return true si se ha completado correctamente
     */
    public boolean completarTarea(Tarea tarea){
        String sql = "UPDATE tarea SET completadaT = ?, fechaFinalizacionT = ? WHERE idT = ?";

        try (Connection conexion = ConectarBD.conectar()){

            if (tarea.getCompletadaT()){
                tarea.setCompletadaT(false);
                tarea.setFechaFinalizacionT(null);
            } else {
                tarea.setCompletadaT(true);
                tarea.setFechaFinalizacionT(new Timestamp(System.currentTimeMillis()));
            }

            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setBoolean(1, tarea.getCompletadaT());
            prepare.setTimestamp(2, tarea.getFechaFinalizacionT());
            prepare.setInt(3, tarea.getIdT());

            prepare.executeUpdate();
        } catch (SQLException e) {
            System.out.println("CATCH EN completarTarea()");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Elimina una tarea de la base de datos y de la lista de tareas.
     * @param tarea tarea a eliminar
     * @return true si se ha eliminado correctamente
     */
    public boolean eliminarTarea(Tarea tarea){
        String sql = "delete from tarea where idt = ? and nombreu = ?";

        try(Connection conexion = ConectarBD.conectar()){
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setInt(1, tarea.getIdT());
            prepare.setString(2, tarea.getNombreU());
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Modifica una tarea en la base de datos y en la lista de tareas.
     * @param tarea tarea a modificar
     * @return true si se ha modificado correctamente
     */
    public boolean modificarTarea(Tarea tarea){
        String sql = "UPDATE tarea SET nombret = ?, descripciont = ?, nombree = ? WHERE idt = ?";

        try(Connection conexion = ConectarBD.conectar()){
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, tarea.getNombreT());
            prepare.setString(2, tarea.getDescripcionT());
            prepare.setString(3, tarea.getNombreE());
            prepare.setInt(4, tarea.getIdT());
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Comprueba si el nombre de la tarea está vacío o tiene más de 45 caracteres o si no ha habido cambios.
     * @param tareaOriginal tarea original
     * @param tareaEditada tarea editada
     * @param label label para mostrar mensajes de error
     * @return true si es correcto
     */
    public boolean comprobarNombreEditarTarea(Tarea tareaOriginal, Tarea tareaEditada, JLabel label){
        if (tareaEditada.getNombreT().isBlank()){
            label.setText("El nombre no puede estar vacío");
            sRecursos.crearTimer(label);
            return false;
        }
        if (tareaEditada.getNombreT().length() > 35){
            label.setText("El nombre no puede tener más de 35 caracteres");
            sRecursos.crearTimer(label);
            return false;
        }
        if (tareaEditada.getNombreT().equals(tareaOriginal.getNombreT())
                && tareaEditada.getDescripcionT().equals(tareaOriginal.getDescripcionT())
                && tareaEditada.getNombreE().equals(tareaOriginal.getNombreE())){
            label.setText("No se ha modificado ningún campo");
            sRecursos.crearTimer(label);
            return false;
        }
        return true;
    }

    /**
     * Comprueba si el nombre de la tarea está vacío o tiene más de 45 caracteres.
     * @param nombreTarea nombre de la tarea
     * @param label label para mostrar mensajes de error
     * @return true si es correcto
     */
    public boolean comprobarNombreCrearTarea(String nombreTarea, JLabel label){
        if (nombreTarea.equals("Nombre de la tarea")){
            label.setText("Introduce un nombre");
            sRecursos.crearTimer(label);
            return false;
        }
        if (nombreTarea.isBlank()){
            label.setText("El nombre no puede estar vacío");
            sRecursos.crearTimer(label);
            return false;
        }
        if (nombreTarea.length() > 35){
            label.setText("El nombre no puede tener más de 35 caracteres");
            sRecursos.crearTimer(label);
            return false;
        }
        return true;
    }

    /**
     * Devuelve la lista de tareas.
     * @return lista de tareas
     */
    public ArrayList<Tarea> getListaTareas(){
        return this.listaTareas;
    }

    public ArrayList<TemplatePanelTareas> getListaTareasToDo() {
        return listaTareasToDo;
    }

    public ArrayList<TemplatePanelTareas> getListaTareasCompletadas() {
        return listaTareasCompletadas;
    }

    public ArrayList<TemplatePanelMatrix> getListaTareasArribaI() {
        return listaTareasArribaI;
    }

    public ArrayList<TemplatePanelMatrix> getListaTareasArribaD() {
        return listaTareasArribaD;
    }

    public ArrayList<TemplatePanelMatrix> getListaTareasAbajoI() {
        return listaTareasAbajoI;
    }

    public ArrayList<TemplatePanelMatrix> getListaTareasAbajoD() {
        return listaTareasAbajoD;
    }
}