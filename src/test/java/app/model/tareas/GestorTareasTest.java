package app.model.tareas;

import app.model.CodigoError;
import app.model.basedatos.ConectarBD;
import app.model.usuarios.Usuario;
import app.view.principal.InterfazPrincipal;
import app.view.principal.VistaMatrix;
import app.view.principal.VistaTareas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorTareasTest {
    private GestorTareas gestorTareas;
    private ConectarBD conectarBD;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Usuario usuario;
    private InterfazPrincipal interfazPrincipal;
    private VistaTareas vistaTareas;
    private VistaMatrix vistaMatrix;

    @BeforeEach
    public void setUp() {
        /* Mockear las dependencias */
        conectarBD = mock(ConectarBD.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        usuario = mock(Usuario.class);
        interfazPrincipal = mock(InterfazPrincipal.class);
        vistaTareas = mock(VistaTareas.class);
        vistaMatrix = mock(VistaMatrix.class);

        /* Configurar como se comportan los mocks */
        when(usuario.getNombreU()).thenReturn("nombreU");
        try {
            when(conectarBD.conectar()).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error setting up the test due to SQLException");
        }

        /* Mockear Usuario.getUsuarioConectado() para devolver el usuario mockeado */
        try (MockedStatic<Usuario> mockedUsuario = mockStatic(Usuario.class)) {
            mockedUsuario.when(Usuario::getUsuarioConectado).thenReturn(usuario);

            /* Crear una instancia de GestorTareas */
            gestorTareas = new GestorTareas();
        }

        /* Reemplazar la instancia ConectarBD de GestorTareas con el mock para que funcione correctamente */
        try {
            Field conectarBdField = GestorTareas.class.getDeclaredField("conectarBD");
            conectarBdField.setAccessible(true);
            conectarBdField.set(gestorTareas, conectarBD);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Error setting up the test due to reflection issues");
        }
    }

    @Test
    public void testCrearTarea() throws SQLException {
        Tarea tarea = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        int result = gestorTareas.crearTarea(tarea);
        assertEquals(CodigoError.SIN_ERROR, result);
        verify(preparedStatement, times(1)).executeUpdate(); // Verificar que se llamó al método executeUpdate() una vez
    }

    @Test
    public void testCompletarTarea() throws SQLException {
        Tarea tarea = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        int result = gestorTareas.completarTarea(tarea);
        assertEquals(CodigoError.SIN_ERROR, result);
        verify(preparedStatement, times(1)).executeUpdate(); // Verify that the executeUpdate() method was called once
    }

    @Test
    public void testEliminarTarea() throws SQLException {
        Tarea tarea = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        int result = gestorTareas.eliminarTarea(tarea);
        assertEquals(CodigoError.SIN_ERROR, result);
        verify(preparedStatement, times(1)).executeUpdate(); // Verificar que se llamó al método executeUpdate() una vez
    }

    @Test
    public void testModificarTarea() throws SQLException {
        Tarea tarea = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        int result = gestorTareas.modificarTarea(tarea);
        assertEquals(CodigoError.SIN_ERROR, result);
        verify(preparedStatement, times(1)).executeUpdate(); // Verificar que se llamó al método executeUpdate() una vez
    }

    @Test
    public void testComprobarNombreCrearTarea_NombreVacio() throws SQLException {
        int result = gestorTareas.comprobarNombreCrearTarea("");
        assertEquals(CodigoError.ERROR_TAREA_NOMBRE_EN_BLANCO, result);
    }

    @Test
    public void testComprobarNombreCrearTarea_NombreLargo() throws SQLException {
        String nombreLargo = "nombreDeTareaConMasDeTreintaYCincoCaracteres";
        int result = gestorTareas.comprobarNombreCrearTarea(nombreLargo);
        assertEquals(CodigoError.ERROR_TAREA_NOMBRE_LARGO, result);
    }

    @Test
    public void testComprobarNombreCrearTarea_NombreCorrecto() throws SQLException {
        int result = gestorTareas.comprobarNombreCrearTarea("nombreTarea");
        assertEquals(CodigoError.SIN_ERROR, result);
    }

    @Test
    public void testComprobarDatosEditarTarea_NombreVacio() {
        Tarea tareaOriginal = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        Tarea tareaEditada = new Tarea(1, "", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        int result = gestorTareas.comprobarDatosEditarTarea(tareaOriginal, tareaEditada);
        assertEquals(CodigoError.ERROR_TAREA_NOMBRE_EN_BLANCO, result);
    }

    @Test
    public void testComprobarDatosEditarTarea_NombreLargo() {
        Tarea tareaOriginal = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        Tarea tareaEditada = new Tarea(1, "nombreDeTareaConMasDeTreintaYCincoCaracteres", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        int result = gestorTareas.comprobarDatosEditarTarea(tareaOriginal, tareaEditada);
        assertEquals(CodigoError.ERROR_TAREA_NOMBRE_LARGO, result);
    }

    @Test
    public void testComprobarDatosEditarTarea_SinCambios() {
        Tarea tareaOriginal = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        Tarea tareaEditada = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        int result = gestorTareas.comprobarDatosEditarTarea(tareaOriginal, tareaEditada);
        assertEquals(CodigoError.ERROR_TAREA_SIN_CAMBIOS, result);
    }

    @Test
    public void testComprobarDatosEditarTarea_Correcto() {
        Tarea tareaOriginal = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        Tarea tareaEditada = new Tarea(1, "nombreT2", "descripcionT2", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        int result = gestorTareas.comprobarDatosEditarTarea(tareaOriginal, tareaEditada);
        assertEquals(CodigoError.SIN_ERROR, result);
    }
}