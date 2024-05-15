package app.model.tareas;

import app.model.basedatos.ConectarBD;
import app.model.usuarios.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestorTareas {
    private ArrayList<Tarea> listaTareas;
    private Usuario usuario;

    public GestorTareas(Usuario usuario) {
//        this.usuario = usuario.getUsuario();
    }

    private void getTareasDeBase(){
        listaTareas = new ArrayList<>();

        String sql = "select * from tarea where nombreu = ?";

        try (Connection conexion = ConectarBD.conectar()){
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, usuario.nombreUsuario());

        } catch (SQLException e) {
            System.out.println("eo");
        }
    }
}
