package testTareas;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import tareas.GestorTareas;
import tareas.TareaToDo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class GestorTareasTest {
    private GestorTareas gestorTareas;

    /**
     * Inicializa el gestor de tareas
     */
    @Before
    public void setUp(){
        gestorTareas = new GestorTareas();
    }

    /**
     * Test para verificar que se puede agregar una tarea correctamente
     */
    @Test
    @DisplayName("Agregar tarea correctamente")
    public void agregarTareaCorrectamente(){
        assertTrue(gestorTareas.agregarTarea(new TareaToDo("nombre", "descripcion")));
    }

    /**
     * Test para verificar que se puede completar una tarea correctamente
     */
    @Test
    @DisplayName("Completar tarea correctamente")
    public void completarTareaCorrectamente(){
        TareaToDo tarea = new TareaToDo("nombre", "descripcion");
        gestorTareas.agregarTarea(tarea);
        assertTrue(gestorTareas.completarTarea(tarea));
    }

    /**
     * Test para verificar que una tarea completada se elimina y se instancia en TareaCompletada
     */
    @Test
    @DisplayName("Tarea Completada Es Instancia de TareaCompletada")
    public void completarTareaInstanciaTareaCompletada(){
        TareaToDo tarea = new TareaToDo("nombre", "descripcion");
        gestorTareas.agregarTarea(tarea);
        gestorTareas.completarTarea(tarea);
        assertFalse(gestorTareas.getListaTareasCompletadas().isEmpty());
    }

    /**
     * Test para verificar que se elimina la TareaToDo correctamente
     */
    @Test
    @DisplayName("Eliminar tarea to-do correctamente")
    public void eliminarTareaToDoCorrectamente(){
        TareaToDo tarea = new TareaToDo("nombre", "descripcion");
        gestorTareas.agregarTarea(tarea);
        assertTrue(gestorTareas.eliminarTarea(tarea));
    }

    /**
     * Test para verificar que se puede eliminar una TareaCompletada correctamente
     */
    @Test
    @DisplayName("Eliminar tarea completada correctamente")
    public void eliminarTareaCompletadaCorrectamente(){
        TareaToDo tareaToDo = new TareaToDo("nombre", "descripcion");
        gestorTareas.completarTarea(tareaToDo);
        assertTrue(gestorTareas.eliminarTarea(gestorTareas.getListaTareasCompletadas().getFirst()));
    }
}
