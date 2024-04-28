package usuarios;

import tareas.Tarea;
import tareas.TareaCompletada;
import tareas.TareaToDo;

import java.util.ArrayList;

/**
 * Clase que representa un usuario conectado. Singleton.
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

    public void verTareas(){

        if (this.listaTareasToDo.isEmpty()){
            System.out.println("Todavía no hay tareas por hacer.");
        } else {
            System.out.println("Tareas por hacer: ");
            int index = 1;
            for (TareaToDo tarea : this.listaTareasToDo) {
                System.out.println(index+". "+tarea);
                index++;
            }
        }

        if (this.listaTareasCompletadas.isEmpty()){
            System.out.println("Todavía no hay tareas completadas.");
        } else {
            System.out.println("Tareas completadas: ");
            int index=1;
            for (TareaCompletada tarea : this.listaTareasCompletadas) {
                System.out.println(index+". "+tarea);
                index++;
            }
        }
    }

}
