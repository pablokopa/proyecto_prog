package app.model.tareas;

import app.model.basedatos.ConectarBD;
import app.model.usuarios.Usuario;
import app.view.principal.InterfazPrincipal;
import app.view.principal.VistaMatrix;
import app.view.principal.VistaTareas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
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
    public void setUp() throws SQLException {
        /* Mockear las dependencias */
        conectarBD = mock(ConectarBD.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        usuario = mock(Usuario.class);
        interfazPrincipal = mock(InterfazPrincipal.class);
        vistaTareas = mock(VistaTareas.class);
        vistaMatrix = mock(VistaMatrix.class);
        usuario = mock(Usuario.class);


        /* Configurar como se comportan los mocks */
        when(usuario.getNombreU()).thenReturn("nombreU");
        when(conectarBD.conectar()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        /* Crear una instancia de GestorTareasTestable */
        gestorTareas = new GestorTareasTestable(usuario, interfazPrincipal, vistaTareas, vistaMatrix);

        /* Reemplazar la instancia ConectarBD de GestorTareas con el mock para que funcione correctamente */
        try {
            Field conectarBdField = GestorTareas.class.getDeclaredField("conectarBD");
            conectarBdField.setAccessible(true);
            conectarBdField.set(gestorTareas, conectarBD);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCrearTarea() throws SQLException {
        Tarea tarea = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        gestorTareas.crearTarea(tarea);
        verify(preparedStatement, times(1)).executeUpdate(); // Verificar que se llamó al método executeUpdate() una vez
    }

    @Test
    public void testCompletarTarea() throws SQLException {
        Tarea tarea = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        boolean result = gestorTareas.completarTarea(tarea);
        assertTrue(result);
        verify(preparedStatement, times(1)).executeUpdate(); // Verificar que se llamó al método executeUpdate() una vez
    }

    @Test
    public void testEliminarTarea() throws SQLException {
        Tarea tarea = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        boolean result = gestorTareas.eliminarTarea(tarea);
        assertTrue(result);
        verify(preparedStatement, times(1)).executeUpdate(); // Verificar que se llamó al método executeUpdate() una vez
    }

    @Test
    public void testModificarTarea() throws SQLException {
        Tarea tarea = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        boolean result = gestorTareas.modificarTarea(tarea);
        assertTrue(result);
        verify(preparedStatement, times(1)).executeUpdate(); // Verificar que se llamó al método executeUpdate() una vez
    }

    @Test
    public void testComprobarNombreCrearTarea_NombreVacio() throws SQLException {
        JLabel label = new JLabel();
        assertFalse(gestorTareas.comprobarNombreCrearTarea("", label));
        assertEquals("El nombre no puede estar vacío", label.getText());
    }

    @Test
    public void testComprobarNombreCrearTarea_NombreLargo() throws SQLException {
        JLabel label = new JLabel();
        String nombreLargo = "nombreDeTareaConMasDeTreintaYCincoCaracteres";
        assertFalse(gestorTareas.comprobarNombreCrearTarea(nombreLargo, label));
        assertEquals("El nombre no puede tener más de 35 caracteres", label.getText());
    }

    @Test
    public void testComprobarNombreCrearTarea_NombreCorrecto() throws SQLException {
        JLabel label = new JLabel();
        assertTrue(gestorTareas.comprobarNombreCrearTarea("nombreTarea", label));
    }

    @Test
    public void testComprobarDatosEditarTarea_NombreVacio() {
        Tarea tareaOriginal = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        Tarea tareaEditada = new Tarea(1, "", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        JLabel label = new JLabel();
        assertFalse(gestorTareas.comprobarDatosEditarTarea(tareaOriginal, tareaEditada, label));
        assertEquals("El nombre no puede estar vacío", label.getText());
    }

    @Test
    public void testComprobarDatosEditarTarea_NombreLargo() {
        Tarea tareaOriginal = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        Tarea tareaEditada = new Tarea(1, "nombreDeTareaConMasDeTreintaYCincoCaracteres", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        JLabel label = new JLabel();
        assertFalse(gestorTareas.comprobarDatosEditarTarea(tareaOriginal, tareaEditada, label));
        assertEquals("El nombre no puede tener más de 35 caracteres", label.getText());
    }

    @Test
    public void testComprobarDatosEditarTarea_SinCambios() {
        Tarea tareaOriginal = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        Tarea tareaEditada = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        JLabel label = new JLabel();
        assertFalse(gestorTareas.comprobarDatosEditarTarea(tareaOriginal, tareaEditada, label));
        assertEquals("No se ha modificado ningún campo", label.getText());
    }

    @Test
    public void testComprobarDatosEditarTarea_Correcto() {
        Tarea tareaOriginal = new Tarea(1, "nombreT", "descripcionT", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        Tarea tareaEditada = new Tarea(1, "nombreT2", "descripcionT2", new Timestamp(System.currentTimeMillis()), null, false, "nombreU", "nombreE");
        JLabel label = new JLabel();
        assertTrue(gestorTareas.comprobarDatosEditarTarea(tareaOriginal, tareaEditada, label));
    }
}