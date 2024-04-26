package tareas;

import java.util.ArrayList;

public class GestorTareas {
    private final ArrayList<TareaToDo> listaTareasToDo;
    private final ArrayList<TareaEnProgreso> listaTareasEnProgreso;
    private final ArrayList<TareaCompletada> listaTareasCompletadas;

    public GestorTareas(){
        this.listaTareasToDo = new ArrayList<>();
        this.listaTareasEnProgreso = new ArrayList<>();
        this.listaTareasCompletadas = new ArrayList<>();
    }

    // Getters
    public ArrayList<TareaToDo> getListaTareasToDo() {
        return listaTareasToDo;
    }
    public ArrayList<TareaEnProgreso> getListaTareasEnProgreso() {
        return listaTareasEnProgreso;
    }
    public ArrayList<TareaCompletada> getListaTareasCompletadas() {
        return listaTareasCompletadas;
    }

    //
    /**
     * Agrega una tarea a la lista de tareas por hacer
     * @param tarea tarea a agregar
     * @return true si fue agregada correctamente
     */
    public boolean agregarTarea(TareaToDo tarea){
        return this.listaTareasToDo.add(tarea);
    }

    /**
     * Elimina una tarea de la lista de tareas
     * @param tarea tarea a eliminar. Debe ser una instancia de TareaToDo, TareaEnProgreso o TareaCompletada
     * @return true si fue eliminada correctamente
     */
    public boolean eliminarTarea(Tarea tarea){
        if (tarea instanceof TareaToDo){
            return this.listaTareasToDo.remove((TareaToDo) tarea);
        }
        if (tarea instanceof TareaEnProgreso){
            return this.listaTareasEnProgreso.remove(((TareaEnProgreso) tarea));
        }
        if (tarea instanceof TareaCompletada){
            return this.listaTareasCompletadas.remove(((TareaCompletada) tarea));
        }
        return false;
    }
}
