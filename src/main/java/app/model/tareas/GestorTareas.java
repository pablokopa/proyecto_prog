package app.model.tareas;

import app.model.CodigoError;
import app.model.basedatos.ConectarBD;
import app.model.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;

/**
 * Gestor de tareas. Se utiliza desde el ControladorTareas
 */
public class GestorTareas {
    private final ConectarBD conectarBD = new ConectarBD();
    private final Usuario usuario;

    private final ArrayList<Tarea> listaTareas;

    /**
     * Constructor de la clase
     */
    public GestorTareas() {
        this.usuario = Usuario.getUsuarioConectado();
        this.listaTareas = new ArrayList<>();

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
    private void getTareasDeBase(){
        String sql = "select * from tarea where nombreu = ?";

        try (Connection conexion = conectarBD.conectar()){
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
                listaTareas.add(tarea);
            }
        } catch (SQLException e) {
            System.out.println("CATCH EN GestorTareas.getTareasDeBase()");
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la última tarea añadida a la base de datos.
     * @return la última tarea añadida o null si no hay tareas
     */
    public Tarea getUltimaTarea(){
        String sql = "SELECT * FROM tarea ORDER BY idT DESC LIMIT 1;";

        try (Connection conexion = conectarBD.conectar()){
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
                listaTareas.add(tarea);

                return tarea;
            }
        } catch (SQLException e) {
            System.out.println("CATCH EN GestorTareas.getTareasDeBase()");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Crea una tarea en la base de datos y en la lista de tareas.
     * @param tarea tarea a crear
     * @return CodigoError constante
     */
    public int crearTarea(Tarea tarea) {
        String sql = "INSERT INTO tarea (nombreT, descripcionT, fechaCreacionT, nombreU, nombreE) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = conectarBD.conectar()) {
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, tarea.getNombreT());
            prepare.setString(2, tarea.getDescripcionT());
            prepare.setTimestamp(3, tarea.getFechaCreacionT());
            prepare.setString(4, usuario.getNombreU());
            prepare.setString(5, tarea.getNombreE());

            prepare.executeUpdate();
        } catch (SQLException e) {
            System.out.println("CATCH EN crearTarea()");
            e.printStackTrace();
            return CodigoError.ERROR_SIN_CONEXION;
        }
        return CodigoError.SIN_ERROR;
    }

    /**
     * Completa o descompleta una tarea o devuelve un código de error
     * @param tarea tarea a completar
     * @return CodigoError constante
     */
    public int completarTarea(Tarea tarea){
        String sql = "UPDATE tarea SET completadaT = ?, fechaFinalizacionT = ? WHERE idT = ?";

        try (Connection conexion = conectarBD.conectar()){

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
            return CodigoError.ERROR_SIN_CONEXION;
        }
        return CodigoError.SIN_ERROR;
    }

    /**
     * Elimina una tarea de la base de datos y de la lista de tareas.
     * @param tarea tarea a eliminar
     * @return CodigoError constante
     */
    public int eliminarTarea(Tarea tarea){
        String sql = "delete from tarea where idt = ? and nombreu = ?";

        try(Connection conexion = conectarBD.conectar()){
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setInt(1, tarea.getIdT());
            prepare.setString(2, tarea.getNombreU());
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return CodigoError.ERROR_SIN_CONEXION;
        }
        return CodigoError.SIN_ERROR;
    }

    /**
     * Modifica una tarea en la base de datos y en la lista de tareas.
     * @param tarea tarea a modificar
     * @return CodigoError constante
     */
    public int modificarTarea(Tarea tarea){
        String sql = "UPDATE tarea SET nombret = ?, descripciont = ?, nombree = ? WHERE idt = ?";

        try(Connection conexion = conectarBD.conectar()){
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, tarea.getNombreT());
            prepare.setString(2, tarea.getDescripcionT());
            prepare.setString(3, tarea.getNombreE());
            prepare.setInt(4, tarea.getIdT());
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return CodigoError.ERROR_SIN_CONEXION;
        }
        return CodigoError.SIN_ERROR;
    }

    /**
     * Comprueba si el nombre de la tarea está vacío o tiene más de 35 caracteres; o si no ha habido cambios.
     * @param tareaOriginal tarea original
     * @param tareaEditada tarea editada
     * @return CodigoError constante
     */
    public int comprobarDatosEditarTarea(Tarea tareaOriginal, Tarea tareaEditada){
        if (tareaEditada.getNombreT().isBlank()){
            return CodigoError.ERROR_TAREA_NOMBRE_EN_BLANCO;
        }
        if (tareaEditada.getNombreT().length() > 35){
            return CodigoError.ERROR_TAREA_NOMBRE_LARGO;
        }
        if (tareaEditada.getNombreT().equals(tareaOriginal.getNombreT())
                && tareaEditada.getDescripcionT().equals(tareaOriginal.getDescripcionT())
                && tareaEditada.getNombreE().equals(tareaOriginal.getNombreE())){
            return CodigoError.ERROR_TAREA_SIN_CAMBIOS;
        }
        return CodigoError.SIN_ERROR;
    }

    /**
     * Comprueba si el nombre de la tarea está vacío o tiene más de 45 caracteres.
     * @param nombreTarea nombre de la tarea
     * @return CodigoError constante
     */
    public int comprobarNombreCrearTarea(String nombreTarea){
        if (nombreTarea.equals("Nombre de la tarea")){
            return CodigoError.ERROR_TAREA_SIN_NOMBRE;
        }
        if (nombreTarea.isBlank()){
            return CodigoError.ERROR_TAREA_NOMBRE_EN_BLANCO;
        }
        if (nombreTarea.length() > 35){
            return CodigoError.ERROR_TAREA_NOMBRE_LARGO;
        }
        return CodigoError.SIN_ERROR;
    }

    /**
     * Obtiene la lista de tareas
     * @return lista de tareas
     */
    public ArrayList<Tarea> getListaTareas() {
        return listaTareas;
    }
}