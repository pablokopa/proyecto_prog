package app.model.usuarios;

import app.model.tareas.Tarea;
import app.model.tareas.TareaCompletada;
import app.model.tareas.TareaToDo;

import java.util.ArrayList;

/**
 * Clase Singleton que representa un usuario conectado.
 * Actúa como un gestor de tareas del propio usuario.
 */
public class UsuarioConectado extends Usuario{
    private static UsuarioConectado usuarioConectado = null;

    private final ArrayList<TareaToDo> listaTareasToDo;                 // Lista de tareas to-do
    private final ArrayList<TareaCompletada> listaTareasCompletadas;    // Lista de tareas completadas

    public UsuarioConectado(Usuario usuario) {
        super(usuario.getNombreUsuario(), usuario.getContraUsuario());
        this.listaTareasToDo = new ArrayList<>();
        this.listaTareasCompletadas = new ArrayList<>();
    }

    /**
     * Obtiene la instancia única de UsuarioConectado
     * @param usuario usuario a conectar
     * @return instancia de UsuarioConectado
     */
    public static UsuarioConectado getUsuarioConectado(Usuario usuario){
        if (usuarioConectado == null){
            usuarioConectado = new UsuarioConectado(usuario);
        }
        return usuarioConectado;
    }

    // Getters
    public ArrayList<TareaToDo> getListaTareasToDo() {
        return listaTareasToDo;
    }
    public ArrayList<TareaCompletada> getListaTareasCompletadas() {
        return this.listaTareasCompletadas;
    }


    /**
     * Agrega una tarea a la lista de tareas por hacer
     * @param tarea tarea a agregar
     * @return true si fue agregada correctamente
     */
    public boolean agregarTarea(TareaToDo tarea){
        return this.listaTareasToDo.add(tarea);
    }

    /**
     * Completa una tarea y la agrega a la lista de tareas completadas
     * @param tarea tarea a completar
     * @return true si fue completada correctamente
     */
    public boolean completarTarea(TareaToDo tarea){
        TareaCompletada tareaCompletada = new TareaCompletada(tarea.getNombreTarea(), tarea.getDescripcionTarea());
        return listaTareasCompletadas.add(tareaCompletada) && eliminarTarea(tarea);
    }

    /**
     * Elimina una tarea de la lista de tareas
     * @param tarea tarea a eliminar. Debe ser una instancia de TareaToDo o TareaCompletada
     * @return true si fue eliminada correctamente
     */
    public boolean eliminarTarea(Tarea tarea){
        if (tarea instanceof TareaToDo){
            return this.listaTareasToDo.remove((TareaToDo) tarea);
        }
        if (tarea instanceof TareaCompletada){
            return this.listaTareasCompletadas.remove(((TareaCompletada) tarea));
        }
        return false;
    }

    /**
     * Muestra las tareas por hacer
     */
    public void verTareasToDo() {
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
    }

    /**
     * Muestra las tareas completadas
     */
    public void verTareasCompletadas(){
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
    }

    /**
     * Muestra todas las tareas
     */
    public void verTareas(){
        verTareasToDo();
        verTareasCompletadas();
    }

    public void modificarTarea(Tarea tarea, String nombre, String descripcion) {
        if (tarea instanceof TareaToDo){
            tarea.setNombreTarea(nombre);
            tarea.setDescripcionTarea(descripcion);
        }
        if (tarea instanceof TareaCompletada){
            tarea.setNombreTarea(nombre);
            tarea.setDescripcionTarea(descripcion);
        }
    }

}
