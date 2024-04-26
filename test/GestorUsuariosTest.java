import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import usuarios.GestorUsuarios;
import usuarios.Usuario;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GestorUsuariosTest {

    @Test
    @DisplayName("Usuario registrado correctamente")
    public void usuarioRegistradoCorrectamente(){
        GestorUsuarios gu = new GestorUsuarios();
        assertTrue(gu.registrarUsuario(new Usuario("nombre", "contra")));
    }

    @Test
    @DisplayName("Usuario ya registrado")
    public void usuarioYaRegistrado(){
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        gestorUsuarios.registrarUsuario(new Usuario("nombre", "contra"));
        assertFalse(gestorUsuarios.registrarUsuario(new Usuario("nombre", "otraContra")));
    }

    @Test
    @DisplayName("Usuario eliminado correctamente")
    public void usuarioEliminadoCorrectamente(){
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        Usuario usuario = new Usuario("nombre", "contra");
        gestorUsuarios.registrarUsuario(usuario);
        assertTrue(gestorUsuarios.eliminarUsuario(usuario));
    }

    @Test
    @DisplayName("No se puede eliminar un usuario no registrado")
    public void usuarioNoSePuedeEliminar(){
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        assertFalse(gestorUsuarios.eliminarUsuario(new Usuario("nombre", "contra")));
    }
}
