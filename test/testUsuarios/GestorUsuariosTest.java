package testUsuarios;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import usuarios.GestorUsuarios;
import usuarios.Usuario;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GestorUsuariosTest {
    private GestorUsuarios gestorUsuarios;

    /**
     * Inicializa el gestor de usuarios
     */
    @Before
    public void setUp(){
        gestorUsuarios = new GestorUsuarios();
    }

    /**
     * Test para verificar que se puede registrar un usuario correctamente
     */
    @Test
    @DisplayName("Usuario registrado correctamente")
    public void usuarioRegistradoCorrectamente(){
        assertTrue(gestorUsuarios.registrarUsuario(new Usuario("nombre", "contra")));
    }

    /**
     * Test para verificar que no se puede registrar un usuario con el mismo nombre
     */
    @Test
    @DisplayName("Usuario ya registrado")
    public void usuarioYaRegistrado(){
        gestorUsuarios.registrarUsuario(new Usuario("nombre", "contra"));
        assertFalse(gestorUsuarios.registrarUsuario(new Usuario("nombre", "otraContra")));
    }

    /**
     * Test para verificar que se puede eliminar un usuario correctamente
     */
    @Test
    @DisplayName("Usuario eliminado correctamente")
    public void usuarioEliminadoCorrectamente(){
        Usuario usuario = new Usuario("nombre", "contra");
        gestorUsuarios.registrarUsuario(usuario);
        assertTrue(gestorUsuarios.eliminarUsuario(usuario));
    }

    /**
     * Test para verificar que no se puede eliminar un usuario no registrado
     */
    @Test
    @DisplayName("No se puede eliminar un usuario no registrado")
    public void usuarioNoSePuedeEliminar(){
        assertFalse(gestorUsuarios.eliminarUsuario(new Usuario("nombre", "contra")));
    }
}
