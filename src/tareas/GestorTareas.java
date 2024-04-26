package tareas;

import java.util.ArrayList;

public class GestorTareas {
    private final ArrayList<TareaToDo> listaTareasToDo;
    private final ArrayList<TareaCompletada> listaTareasCompletadas;

    public GestorTareas(){
        this.listaTareasToDo = new ArrayList<>();
        this.listaTareasCompletadas = new ArrayList<>();
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
}
