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
}
