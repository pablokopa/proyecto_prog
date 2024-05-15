package app.model.usuarios;

import app.model.basedatos.ConectarBD;
import app.model.tareas.Tarea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Clase Singleton que representa un usuario conectado.
 * Actúa como un gestor de tareas del propio usuario.
 */
public class Usuario {
    private static Usuario usuario;
    private String nombreUsuario;

    private Usuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public static Usuario setUsuarioConectado(String nombreUsuario){
        if (usuario == null){
            usuario = new Usuario(nombreUsuario);
        }
        return usuario;
    }

    public static Usuario getUsuario(){
        return usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public boolean agregarTarea(Tarea tarea) {
        String sql = "INSERT INTO tarea (nombreT, descripcionT, fechaFinalizacionT, nombreU) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConectarBD.conectar()) {
            PreparedStatement prepare = conexion.prepareStatement(sql);
//            prepare.setString(1, tarea.getNombreTarea());
//            prepare.setString(2, tarea.getDescripcionTarea());
//            prepare.setTimestamp(3, tarea.getFechaCreacion());
//            prepare.setString(4, this.nombreUsuario());

            prepare.executeUpdate();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("CATCH EN agregarTarea()");
            return false;
        }
        return true;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        if (obj == this) return true;
        Usuario userObj = (Usuario) obj;
        return Objects.equals(this.nombreUsuario, userObj.nombreUsuario);
    }

    /**
     * Método toString
     *
     * @return nombre de usuario
     */
    @Override
    public String toString() {
        return this.nombreUsuario;
    }

    /**
     * Obtiene la instancia única de Usuario
     * @param usuario usuario a conectar
     * @return instancia de Usuario
     */
/*    public static Usuario getUsuarioConectado(Usuario usuario){
        if (usuario == null){
            usuario = new Usuario(usuario);
        }
        return usuario;
    }*/

    /**
     * Agrega una tarea a la lista de tareas por hacer
     * @param tarea tarea a agregar
     * @return true si fue agregada correctamente
     */


    /**
     * Completa una tarea y la agrega a la lista de tareas completadas
     * @param tarea tarea a completar
     * @return true si fue completada correctamente
     */
/*    public boolean completarTarea(TareaToDo tarea){
        TareaCompletada tareaCompletada = new TareaCompletada(tarea.getNombreTarea(), tarea.getDescripcionTarea());
        return listaTareasCompletadas.add(tareaCompletada) && eliminarTarea(tarea);
    }*/

    /**
     * Elimina una tarea de la lista de tareas
     * @param tarea tarea a eliminar. Debe ser una instancia de TareaToDo o TareaCompletada
     * @return true si fue eliminada correctamente
     */
/*    public boolean eliminarTarea(Tarea tarea){
        if (tarea instanceof TareaToDo){
            return this.listaTareasToDo.remove((TareaToDo) tarea);
        }
        if (tarea instanceof TareaCompletada){
            return this.listaTareasCompletadas.remove(((TareaCompletada) tarea));
        }
        return false;
    }*/

    /**
     * Muestra las tareas por hacer
     */
/*    public void verTareasToDo() {
        if (this.listaTareasToDo.isEmpty()) {
            System.out.println("Todavía no hay tareas por hacer.");
        } else {
            System.out.println("Tareas por hacer: ");
            int index = 1;
            for (TareaToDo tarea : this.listaTareasToDo) {
                System.out.println(index + ". " + tarea);
                index++;
            }
        }
    }*/

    /**
     * Muestra las tareas completadas
     */
 /*   public void verTareasCompletadas(){
        if (this.listaTareasCompletadas.isEmpty()){
            System.out.println("Todavía no hay tareas completadas.");
        } else {
            System.out.println("Tareas completadas: ");
            int index = 1;
            for (TareaCompletada tarea : this.listaTareasCompletadas) {
                System.out.println(index + ". " + tarea);
                index++;
            }
        }
    }*/

    /**
     * Muestra todas las tareas
     */
/*    public void verTareas(){
        verTareasToDo();
        verTareasCompletadas();
    }*/

/*    public void modificarTarea(Tarea tarea, String nombre, String descripcion) {
        if (tarea instanceof TareaToDo){
            tarea.setNombreTarea(nombre);
            tarea.setDescripcionTarea(descripcion);
        }
        if (tarea instanceof TareaCompletada){
            tarea.setNombreTarea(nombre);
            tarea.setDescripcionTarea(descripcion);
        }
    }*/

}
