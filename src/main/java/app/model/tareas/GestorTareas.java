package app.model.tareas;

import app.model.basedatos.ConectarBD;
import app.model.usuarios.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class GestorTareas {
    private final ArrayList<Tarea> listaTareas;
    private final Usuario usuario;

    public GestorTareas() {
        this.usuario = Usuario.getUsuarioConectado();
        this.listaTareas = new ArrayList<>();
    }

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
                this.listaTareas.add(tarea);
            }
        } catch (SQLException e) {
            System.out.println("CATCH EN GestorTareas.getTareasDeBase()");
            e.printStackTrace();
        }
    }

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
                this.listaTareas.add(tarea);
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

    private void crearTimer(JLabel label){
        Timer timer = new Timer(3500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public boolean comprobarNombreEditarTarea(Tarea tareaOriginal, Tarea tareaEditada, JLabel label){
        if (tareaEditada.getNombreT().isBlank()){
            label.setText("El nombre no puede estar vacío");
            crearTimer(label);
            return false;
        }
        if (tareaEditada.getNombreT().length() > 45){
            label.setText("El nombre no puede tener más de 45 caracteres");
            crearTimer(label);
            return false;
        }
        if (tareaEditada.getNombreT().equals(tareaOriginal.getNombreT()) && tareaEditada.getDescripcionT().equals(tareaOriginal.getDescripcionT()) && tareaEditada.getNombreE().equals(tareaOriginal.getNombreE())){
            label.setText("No se ha modificado ningún campo");
            crearTimer(label);
            return false;
        }
        return true;
    }

    public boolean comprobarNombreCrearTarea(String nombreTarea, JLabel label){
        if (nombreTarea.equals("Nombre de la tarea")){
            label.setText("Introduce un nombre");
            return false;
        }
        if (nombreTarea.isBlank()){
            label.setText("El nombre no puede estar vacío");
            return false;
        }
        if (nombreTarea.length() > 45){
            label.setText("El nombre no puede tener más de 45 caracteres");
            return false;
        }
        crearTimer(label);
        return true;
    }


    public ArrayList<Tarea> getListaTareas(){
        return this.listaTareas;
    }
}