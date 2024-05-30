package app.model.usuarios;

import app.model.basedatos.ConectarBD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorUsuariosTest {
    private GestorUsuarios gestorUsuarios;
    private ConectarBD conectarBD;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        /* Mockear las dependencias */
        conectarBD = mock(ConectarBD.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        /* Crear una instancia de GestorUsuarios */
        gestorUsuarios = new GestorUsuarios();

        /* Configurar como se comportan los mocks */
        when(conectarBD.conectar()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        /* Reemplazar la instancia ConectarBD de GestorUsuarios con el mock para que funcione correctamente */
        try {
            Field conectarBdField = GestorUsuarios.class.getDeclaredField("conectarBD");
            conectarBdField.setAccessible(true);
            conectarBdField.set(gestorUsuarios, conectarBD);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void comprobarNombreUsuarioCorrecto() throws SQLException {
        when(resultSet.next()).thenReturn(false); // Simular que el usuario no existe
        JLabel label = new JLabel();
        assertTrue(gestorUsuarios.comprobarNombreUsuario("nombre", label));
    }

    @Test
    public void comprobarNombreUsuarioExistente() throws SQLException {
        when(resultSet.next()).thenReturn(true); // Simular que el usuario ya existe
        JLabel label = new JLabel();
        assertFalse(gestorUsuarios.comprobarNombreUsuario("nombre", label));
    }

    @Test
    public void comprobarNombreUsuarioCorto() throws SQLException {
        JLabel label = new JLabel();
        assertFalse(gestorUsuarios.comprobarNombreUsuario("no", label));
    }

    @Test
    public void comprobarNombreUsuarioLargo() throws SQLException {
        JLabel label = new JLabel();
        assertFalse(gestorUsuarios.comprobarNombreUsuario("nombreDeUsuarioConMasDeCincuentaCaracteres", label));
    }

    @Test
    public void registrarUsuarioCorrectamente() throws SQLException {
        when(resultSet.next()).thenReturn(false); // Simular que el usuario no existe
        JLabel textoComprobacion = new JLabel();
        JLabel textoLogin = new JLabel();

        boolean result = gestorUsuarios.registrarUsuario("nuevoUsuario", "passwordSeguro", textoComprobacion, textoLogin);
        assertTrue(result);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void registrarUsuario_nombreIncorrecto() {
        JLabel textoComprobacion = new JLabel();
        JLabel textoLogin = new JLabel();

        boolean result = gestorUsuarios.registrarUsuario("", "passwordSeguro", textoComprobacion, textoLogin);
        assertFalse(result);
    }

    @Test
    public void registrarUsuario_passwordIncorrecta() {
        JLabel textoComprobacion = new JLabel();
        JLabel textoLogin = new JLabel();

        boolean result = gestorUsuarios.registrarUsuario("usuarioValido", "123", textoComprobacion, textoLogin);
        assertFalse(result);
    }

    @Test
    public void testContarUsuarios() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(5);

        int count = gestorUsuarios.contarUsuarios();
        assertEquals(5, count);
    }

    @Test
    public void conectarUsuarioCorrectamente() throws SQLException {
        JLabel textoComprobacion = new JLabel();

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("passwordu")).thenReturn(BCrypt.hashpw("password", BCrypt.gensalt()));

        boolean result = gestorUsuarios.conectarUsuario("usuario", "password", textoComprobacion);

        assertTrue(result);
    }

    @Test
    public void conectarUsuario_usuarioNoRegistrado() throws SQLException {
        JLabel textoComprobacion = new JLabel();

        when(resultSet.next()).thenReturn(false);

        boolean result = gestorUsuarios.conectarUsuario("usuario", "password", textoComprobacion);

        assertFalse(result);
    }

    @Test
    public void conectarUsuario_passwordIncorrecta() throws SQLException {
        JLabel textoComprobacion = new JLabel();

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("passwordu")).thenReturn(BCrypt.hashpw("password", BCrypt.gensalt()));

        boolean result = gestorUsuarios.conectarUsuario("usuario", "passwordIncorrecta", textoComprobacion);

        assertFalse(result);
    }
}
