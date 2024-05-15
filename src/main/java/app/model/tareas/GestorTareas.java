package app.model.tareas;

import app.model.basedatos.ConectarBD;
import app.model.usuarios.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class GestorTareas {
    private ArrayList<Tarea> listaTareas;
    private Usuario usuario;

    public GestorTareas() {
        this.usuario = Usuario.getUsuario();
        this.listaTareas = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void getTareasDeBase(){
        String sql = "select * from tarea where nombreu = ?";

        try (Connection conexion = ConectarBD.conectar()){
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, usuario.getNombreUsuario());

            prepare.executeQuery();
            ResultSet resultado = prepare.getResultSet();
            int i = 0;
            while (resultado.next()){
                String nombreT = resultado.getString("nombret");
                String descripcionT = resultado.getString("descripciont");
                Timestamp fechaCreacionT = resultado.getTimestamp("fechacreaciont");
                Timestamp fechaFinalizacionT = resultado.getTimestamp("fechafinalizaciont");
                Boolean completadaT = resultado.getBoolean("completadat");

                Tarea tarea = new Tarea(nombreT, descripcionT, fechaCreacionT, fechaFinalizacionT, completadaT);
                this.listaTareas.add(tarea);

                i++;
                System.out.println(i + "tarea añadida a la lista");
            }
        } catch (SQLException e) {
            System.out.println("CATCH EN GestorTareas.getTareasDeBase()");
            e.printStackTrace();
        }
    }

    public ArrayList<Tarea> getListaTareas(){
//        getTareasDeBase();
        return this.listaTareas;
    }

}
