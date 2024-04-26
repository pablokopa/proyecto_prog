import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import tareas.GestorTareas;
import tareas.TareaCompletada;
import tareas.TareaToDo;

import static org.junit.Assert.*;

public class GestorTareasTest {
    private GestorTareas gestorTareas;

    @Before
    public void setUp(){
        gestorTareas = new GestorTareas();
    }

    @Test
    @DisplayName("Agregar tarea correctamente")
    public void agregarTareaCorrectamente(){
        assertTrue(gestorTareas.agregarTarea(new TareaToDo("nombre", "descripcion")));
    }

    @Test
    @DisplayName("Completar tarea correctamente")
    public void completarTareaCorrectamente(){
        TareaToDo tarea = new TareaToDo("nombre", "descripcion");
        gestorTareas.agregarTarea(tarea);
        assertTrue(gestorTareas.completarTarea(tarea));
    }

    @Test
    @DisplayName("Tarea Completada Es Instancia de TareaCompletada")
    public void completarTareaInstanciaTareaCompletada(){
        TareaToDo tarea = new TareaToDo("nombre", "descripcion");
        gestorTareas.agregarTarea(tarea);
        gestorTareas.completarTarea(tarea);
        assertFalse(gestorTareas.getListaTareasCompletadas().isEmpty());
    }

    @Test
    @DisplayName("Eliminar tarea to-do correctamente")
    public void eliminarTareaToDoCorrectamente(){
        TareaToDo tarea = new TareaToDo("nombre", "descripcion");
        gestorTareas.agregarTarea(tarea);
        assertTrue(gestorTareas.eliminarTarea(tarea));
    }

    @Test
    @DisplayName("Eliminar tarea completada correctamente")
    public void eliminarTareaCompletadaCorrectamente(){
        TareaToDo tareaToDo = new TareaToDo("nombre", "descripcion");
        gestorTareas.completarTarea(tareaToDo);
        assertTrue(gestorTareas.eliminarTarea(gestorTareas.getListaTareasCompletadas().getFirst()));
    }
}
