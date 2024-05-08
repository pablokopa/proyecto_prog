package app.model.usuarios;
import app.model.basedatos.ConectarBD;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase que gestiona los usuarios
 */
public class GestorUsuarios {
    private final ArrayList<Usuario> listaUsuarios; // Lista de usuarios

    /**
     * Constructor
     */
    public GestorUsuarios(){
        this.listaUsuarios = new ArrayList<>();
    }

    // Getters
    /**
     * @return lista de usuarios
     */
    public ArrayList<Usuario> getListaUsuarios() {
        return this.listaUsuarios;
    }

    /**
     * Crea un usuario si el nombre de usuario no existe
     * @param usuario (Usuario)
     * @return true si fue creado correctamente
     */
    public boolean registrarUsuario(Usuario usuario){
        if (this.listaUsuarios.contains(usuario)){
            return false;
        }
        return this.listaUsuarios.add(usuario);
    }

    /**
     * Elimina un usuario si se encuentra en la lista de usuarios
     * @param usuario (Usuario)
     * @return true si fue eliminado correctamente
     */
    public boolean eliminarUsuario (Usuario usuario){
        return this.listaUsuarios.remove(usuario);
    }

    /**
     * Conecta un usuario si se encuentra en la lista y no está conectado
     * @param usuario (Usuario)
     * @return true si fue conectado correctamente
     */
    public boolean conectarUsuario (Usuario usuario, JLabel textoComprobacion){
        if (this.listaUsuarios.contains(usuario)){
            int index = this.listaUsuarios.indexOf(usuario);
            if (Arrays.equals(this.listaUsuarios.get(index).getContraUsuario(), usuario.getContraUsuario())){
                System.out.println("Usuario conectado");
                UsuarioConectado.getUsuarioConectado(usuario);
                return true;
            }
            textoComprobacion.setText("Contraseña incorrecta");
            return false;
        }
        textoComprobacion.setText("Usuario no registrado");
        return false;
    }

    /**
     * Desconecta un usuario si se encuentra en la lista y está conectado
     * @param usuario (Usuario)
     * @return true si fue desconectado correctamente
     */
    public boolean desconectarUsuario (Usuario usuario){
        return false;
    }

    public boolean comprobarNombreUsuario(String nombreUsuario, JLabel textoComprobacion){
        // Consulta SQL para buscar un nombre de usuario
        String sql = "SELECT nombre FROM usuarios WHERE nombre = ?";

        try (
            // Establecer la conexión a la base de datos
            Connection conexion = ConectarBD.conectar();
            // Preparar la consulta SQL
            PreparedStatement pstmt = conexion.prepareStatement(sql)
        ) {
            // Establecer el valor del parámetro de la consulta SQL
            pstmt.setString(1, nombreUsuario);

            // Ejecutar la consulta SQL y obtener los resultados
            ResultSet rs = pstmt.executeQuery();

            // Si se encontró un usuario, devolver false y mostrar un mensaje
            if (rs.next()) {
                textoComprobacion.setText("El nombre de usuario ya existe");
                return false;
            }
        } catch (SQLException e) {
            // Imprimir la traza de la excepción si ocurre un error
            e.printStackTrace();
        }

        // Si el nombre de usuario tiene menos de 3 caracteres, devolver false y mostrar un mensaje
        if (nombreUsuario.length()<3){
            textoComprobacion.setText("El nombre de usuario debe tener al menos 3 carácteres");
            return false;
        }

        // Si no se encontró un usuario y el nombre de usuario tiene al menos 3 caracteres, devolver true
        return true;
    }

    /**
     * Comprueba si la contraseña tiene al menos 4 carácteres y no tiene espacios en blanco ni ciertos carácteres extraños
     * @param passwordUsuario (char[]) obtenida desde la interfaz de usuario
     * @return true si fue creado correctamente, mensaje en la interfaz si no
     */
    /*public boolean comprobarPasswordUsuario(char[] passwordUsuario, JLabel textoComprobacion){
        String caracteresNoPermitidos = "^`:@´;·ªº|\"{}";
        if (passwordUsuario.length<4){
            textoComprobacion.setText("La contraseña debe tener al menos 4 carácteres");
            return false;
        }
        for (char caracter : passwordUsuario) {
            if (caracter == ' ') {
                textoComprobacion.setText("La contraseña no puede tener espacios en blanco");
                return false;
            } else if (caracteresNoPermitidos.indexOf(caracter) != -1) {
                textoComprobacion.setText("La contraseña no puede tener carácteres extraños");
                return false;
            }
        }
        return true;
    }*/



    /**
     * Cuenta el número de usuarios registrados
     * @return número total de usuarios registrados
     */
    public int contarUsuarios(){
        return this.listaUsuarios.size();
    }

    /**
     * Método para insertar un nuevo usuario en la base de datos
     * @param nombreUsuario obtenido desde la interfaz de usuario
     * @param passwordUsuario obtenido desde la interfaz de usuario
     * @return true si el usuario fue insertado correctamente, false si ocurrió un error
     */
    public boolean insertarUsuario(String nombreUsuario, char[] passwordUsuario) {
        // Consulta SQL para insertar un nuevo usuario
        String sql = "INSERT INTO usuarios (nombre, password) VALUES (?, ?)";

        try (
            // Establece la conexión a la base de datos
            Connection conexion = ConectarBD.conectar();
            // Prepara la consulta SQL
            PreparedStatement pstmt = conexion.prepareStatement(sql)
        ) {
            // Establece los valores de los parámetros de la consulta SQL
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, new String(passwordUsuario));

            // Ejecuta la consulta SQL
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Imprimela excepción si ocurre un error
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para obtener el nombre de un usuario de la base de datos
     * @param nombreUsuario nombre de usuario a buscar en la base de datos
     * @return el nombre si fue encontrado correctamente, null si no se encontró
     */
    public String obtenerNombreUsuario(String nombreUsuario) {
        // Consulta SQL para obtener el nombre de un usuario
        String sql = "SELECT nombre FROM usuarios WHERE nombre = ?";

        try (
            // Establece la conexión a la base de datos
            Connection conexion = ConectarBD.conectar();
            // Prepara la consulta SQL
            PreparedStatement pstmt = conexion.prepareStatement(sql)
        ) {
            // Establece el valor del parámetro de la consulta SQL
            pstmt.setString(1, nombreUsuario);

            // Ejecuta la consulta SQL y obtener los resultados
            ResultSet rs = pstmt.executeQuery();

            // Si se encontró un usuario, devolver su nombre
            if (rs.next()) {
                return rs.getString("nombre");
            } else {
                // Si no se encontró un usuario, devolver null
                return null;
            }
        } catch (SQLException e) {
            // Imprimir la traza de la excepción si ocurre un error
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método para obtener el nombre de un usuario de la base de datos
     * @param passwordUsuario contraseña a buscar en la base de datos
     * @return la contraseña si fue encontrada correctamente, null si no se encontró
     */
    public String obtenerPasswordUsuario(char[] passwordUsuario) {
        // Consulta SQL para obtenerla contraseña de un usuario
        String sql = "SELECT password FROM usuarios WHERE password = ?";

        try (
                // Establece la conexión a la base de datos
                Connection conexion = ConectarBD.conectar();
                // Prepara la consulta SQL
                PreparedStatement pstmt = conexion.prepareStatement(sql)
        ) {
            // Establece el valor del parámetro de la consulta SQL
            pstmt.setString(1, passwordUsuario.toString());

            // Ejecuta la consulta SQL y obtener los resultados
            ResultSet rs = pstmt.executeQuery();

            // Si se encontró la contraseña, devolver su nombre
            if (rs.next()) {
                return rs.getString("password");
            } else {
                // Si no se encontró la contraseña, devolver null
                return null;
            }
        } catch (SQLException e) {
            // Imprimir la traza de la excepción si ocurre un error
            e.printStackTrace();
            return null;
        }
    }
}