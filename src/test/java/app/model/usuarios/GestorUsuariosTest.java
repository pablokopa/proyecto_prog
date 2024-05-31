package app.model.usuarios;

import app.model.CodigoError;
import app.model.basedatos.ConectarBD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase que contiene los tests unitarios de la clase GestorUsuarios.
 */
class GestorUsuariosTest {
    private GestorUsuarios gestorUsuarios;
    private ConectarBD conectarBD;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    /**
     * Método que se ejecuta antes de cada test.
     * @throws SQLException
     */
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

    /**
     * Test para el método comprobarNombreUsuario() de la clase GestorUsuarios. Debe devolver SIN_ERROR si el nombre de usuario es correcto y no existe.
     * @throws SQLException
     */
    @Test
    public void comprobarNombreUsuarioCorrecto() throws SQLException {
        when(resultSet.next()).thenReturn(false); // Simular que el usuario no existe

        int result = gestorUsuarios.comprobarNombreUsuario("nombre");
        assertEquals(CodigoError.SIN_ERROR, result);
        verify(preparedStatement, times(1)).executeQuery(); // Verificar que se llamó al método executeQuery() una vez
    }

    /**
     * Test para el método comprobarNombreUsuario() de la clase GestorUsuarios. Debe devolver ERROR_USUARIO_YA_EXISTE si el nombre de usuario ya existe.
     * @throws SQLException
     */
    @Test
    public void comprobarNombreUsuarioExistente() throws SQLException {
        when(resultSet.next()).thenReturn(true); // Simular que el usuario ya existe

        int result = gestorUsuarios.comprobarNombreUsuario("nombre");
        assertEquals(CodigoError.ERROR_USUARIO_YA_EXISTE, result);
        verify(preparedStatement, times(1)).executeQuery(); // Verificar que se llamó al método executeUpdate() una vez
    }

    /**
     * Test para el método comprobarNombreUsuario() de la clase GestorUsuarios. Debe devolver ERROR_USUARIO_NOMBRE_CORTO si el nombre de usuario es muy corto.
     * @throws SQLException
     */
    @Test
    public void comprobarNombreUsuarioCorto() throws SQLException {
        int result = gestorUsuarios.comprobarNombreUsuario("no");
        assertEquals(CodigoError.ERROR_USUARIO_NOMBRE_CORTO, result);
        verify(preparedStatement, times(1)).executeQuery(); // Verificar que se llamó al método executeUpdate() una vez
    }

    /**
     * Test para el método comprobarNombreUsuario() de la clase GestorUsuarios. Debe devolver ERROR_USUARIO_NOMBRE_LARGO si el nombre de usuario es muy largo.
     * @throws SQLException
     */
    @Test
    public void comprobarNombreUsuarioLargo() throws SQLException {
        int result = gestorUsuarios.comprobarNombreUsuario("nombre con más de cincuenta caracteres no permitido");
        assertEquals(CodigoError.ERROR_USUARIO_NOMBRE_LARGO, result);
        verify(preparedStatement, times(1)).executeQuery(); // Verificar que se llamó al método executeUpdate() una vez

    }

    /**
     * Test para el método registrarUsuario() de la clase GestorUsuarios. Debe devolver SIN_ERROR si el usuario se registró correctamente.
     * @throws SQLException
     */
    @Test
    public void registrarUsuarioCorrectamente() throws SQLException {
        when(resultSet.next()).thenReturn(false); // Simular que el usuario no existe

        int result = gestorUsuarios.registrarUsuario("nuevoUsuario", "password");
        assertEquals(CodigoError.SIN_ERROR, result);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    /**
     * Test para el método contarUsuarios() de la clase GestorUsuarios. Debe devolver el número de usuarios registrados.
     * @throws SQLException
     */
    @Test
    public void testContarUsuarios() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(5);

        int count = gestorUsuarios.contarUsuarios();
        assertEquals(5, count);
        verify(preparedStatement, times(1)).executeQuery(); // Verificar que se llamó al método executeUpdate() una vez
    }

    /**
     * Test para el método conectarUsuario() de la clase GestorUsuarios. Debe devolver SIN_ERROR si el usuario se conectó correctamente.
     * @throws SQLException
     */
    @Test
    public void conectarUsuarioCorrectamente() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("passwordu")).thenReturn(BCrypt.hashpw("password", BCrypt.gensalt()));

        int result = gestorUsuarios.conectarUsuario("usuario", "password");
        assertEquals(CodigoError.SIN_ERROR, result);
        verify(preparedStatement, times(1)).executeQuery(); // Verificar que se llamó al método executeQuery() una vez
    }

    /**
     * Test para el método conectarUsuario() de la clase GestorUsuarios. Debe devolver ERROR_USUARIO_NO_REGISTRADO si el usuario no está registrado.
     * @throws SQLException
     */
    @Test
    public void conectarUsuario_usuarioNoRegistrado() throws SQLException {
        when(resultSet.next()).thenReturn(false); // Simular que el usuario no está registrado

        int result = gestorUsuarios.conectarUsuario("usuarioNoRegistrado", "password");
        assertEquals(CodigoError.ERROR_USUARIO_NO_REGISTRADO, result);
        verify(preparedStatement, times(1)).executeQuery(); // Verificar que se llamó al método executeQuery() una vez
    }

    /**
     * Test para el método conectarUsuario() de la clase GestorUsuarios. Debe devolver ERROR_USUARIO_PASSWORD_INCORRECTA si la contraseña es incorrecta.
     * @throws SQLException
     */
    @Test
    public void conectarUsuario_passwordIncorrecta() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("passwordu")).thenReturn(BCrypt.hashpw("password", BCrypt.gensalt()));

        int result = gestorUsuarios.conectarUsuario("usuario", "passwordIncorrecta");
        assertEquals(CodigoError.ERROR_USUARIO_PASSWORD_INCORRECTA, result);
        verify(preparedStatement, times(1)).executeQuery(); // Verificar que se llamó al método executeQuery() una vez
    }
}
