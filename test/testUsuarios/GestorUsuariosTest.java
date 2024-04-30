package testUsuarios;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import usuarios.GestorUsuarios;
import usuarios.Usuario;
import javax.swing.JLabel;

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
        char[] contraUsuario = {};
        assertTrue(gestorUsuarios.registrarUsuario(new Usuario("nombre", contraUsuario)));
    }

    /**
     * Test para verificar que no se puede registrar un usuario con el mismo nombre
     */
    @Test
    @DisplayName("Usuario ya registrado")
    public void usuarioYaRegistrado(){
        char[] contraUsuario = {};
        gestorUsuarios.registrarUsuario(new Usuario("nombre", contraUsuario));
        assertFalse(gestorUsuarios.registrarUsuario(new Usuario("nombre", contraUsuario)));
    }

    /**
     * Test para verificar que se puede eliminar un usuario correctamente
     */
    @Test
    @DisplayName("Usuario eliminado correctamente")
    public void usuarioEliminadoCorrectamente(){
        Usuario usuario = new Usuario("nombre", null);
        gestorUsuarios.registrarUsuario(usuario);
        assertTrue(gestorUsuarios.eliminarUsuario(usuario));
    }

    /**
     * Test para verificar que no se puede eliminar un usuario no registrado
     */
    @Test
    @DisplayName("No se puede eliminar un usuario no registrado")
    public void usuarioNoSePuedeEliminar(){
        assertFalse(gestorUsuarios.eliminarUsuario(new Usuario("nombre", null)));
    }

    /**
     * Test para verificar que un nombre correcto es permitido
     */
    @Test
    @DisplayName("Comprobar nombre de usuario correcto")
    public void comprobarNombreUsuarioCumpleLimitaciones(){
        String nombre = "nombre";
        assertTrue(gestorUsuarios.comprobarNombreUsuario(nombre, null));
    }

    /**
     * Test para verificar que un nombre con menos de 4 letras no es permitido
     */
    @Test
    @DisplayName("Comprobar nombre de usuario con menos de 3 letras")
    public void comprobarNombreUsuarioConMenosDe4Letras(){
        String nombre = "no";
        JLabel label = new JLabel();
        assertFalse(gestorUsuarios.comprobarNombreUsuario(nombre, label));
    }
}