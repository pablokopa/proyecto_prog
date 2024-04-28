package testUsuarios;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import tareas.TareaToDo;
import usuarios.Usuario;
import usuarios.UsuarioConectado;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class UsuarioConectadoTest {
    private UsuarioConectado usuarioConectado;

    /**
     * Inicializa el gestor de tareas
     */
    @Before
    public void setUp(){
        usuarioConectado = UsuarioConectado.getUsuarioConectado(new Usuario("Admin", new char[0]));
    }

    /**
     * Test para verificar que se puede agregar una tarea correctamente
     */
    @Test
    @DisplayName("Agregar tarea correctamente")
    public void agregarTareaCorrectamente(){
        assertTrue(usuarioConectado.agregarTarea(new TareaToDo("nombre", "descripcion")));
    }

    /**
     * Test para verificar que se puede completar una tarea correctamente
     */
    @Test
    @DisplayName("Completar tarea correctamente")
    public void completarTareaCorrectamente(){
        TareaToDo tarea = new TareaToDo("nombre", "descripcion");
        usuarioConectado.agregarTarea(tarea);
        assertTrue(usuarioConectado.completarTarea(tarea));
    }

    /**
     * Test para verificar que una tarea completada se elimina y se instancia en TareaCompletada
     */
    @Test
    @DisplayName("Tarea Completada Es Instancia de TareaCompletada")
    public void completarTareaInstanciaTareaCompletada(){
        TareaToDo tarea = new TareaToDo("nombre", "descripcion");
        usuarioConectado.agregarTarea(tarea);
        usuarioConectado.completarTarea(tarea);
        assertFalse(usuarioConectado.getListaTareasCompletadas().isEmpty());
    }

    /**
     * Test para verificar que se elimina la TareaToDo correctamente
     */
    @Test
    @DisplayName("Eliminar tarea to-do correctamente")
    public void eliminarTareaToDoCorrectamente(){
        TareaToDo tarea = new TareaToDo("nombre", "descripcion");
        usuarioConectado.agregarTarea(tarea);
        assertTrue(usuarioConectado.eliminarTarea(tarea));
    }

    /**
     * Test para verificar que se puede eliminar una TareaCompletada correctamente
     */
    @Test
    @DisplayName("Eliminar tarea completada correctamente")
    public void eliminarTareaCompletadaCorrectamente(){
        TareaToDo tareaToDo = new TareaToDo("nombre", "descripcion");
        usuarioConectado.completarTarea(tareaToDo);
        assertTrue(usuarioConectado.eliminarTarea(usuarioConectado.getListaTareasCompletadas().getFirst()));
    }
}
