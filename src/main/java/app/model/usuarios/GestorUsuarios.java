package app.model.usuarios;
import app.model.basedatos.ConectarBD;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

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

    /**
     * Método para insertar un nuevo usuario en la base de datos
     * @param nombreUsuario obtenido desde la interfaz de usuario
     * @param passwordUsuario obtenido desde la interfaz de usuario
     * @return true si el usuario fue insertado correctamente, false si ocurrió un error
     */
    public boolean registrarUsuario(String nombreUsuario, String passwordUsuario, JLabel textoComprobacion, JLabel textoLogin) {
        if(!comprobarNombreUsuario(nombreUsuario, textoComprobacion)
                || !comprobarPasswordUsuario(passwordUsuario, textoComprobacion)){
            textoLogin.setText("Registro fallido..");
            return false;
        }

        // Hashea la contraseña del usuario para más seguridad. Siempre es un String de 60 carácteres.
        String passwordHashed = BCrypt.hashpw(passwordUsuario, BCrypt.gensalt());

        // Consulta SQL para insertar un nuevo usuario
        String sql = "INSERT INTO usuarios (nombre, passwordHashed) VALUES (?, ?)";

        try (
                Connection conexion = ConectarBD.conectar();                // Establece la conexión a la base de datos
                PreparedStatement pstmt = conexion.prepareStatement(sql)    // Prepara la consulta SQL
        ) {
            pstmt.setString(1, nombreUsuario);                  // Establece el primer valor de la consulta SQL
            pstmt.setString(2, passwordHashed);                 // Establece el segundo valor de la consulta SQL

            pstmt.executeUpdate();                                          // Ejecuta la consulta SQL
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    /**
     * Elimina un usuario si se encuentra en la lista de usuarios
     * @param usuario (Usuario)
     * @return true si fue eliminado correctamente
     */
    public boolean eliminarUsuario (Usuario usuario){
        return this.listaUsuarios.remove(usuario);
    }

    /**
     * Comprueba si el nombre de usuario tiene al menos 3 carácteres y no existe en la base de datos
     * @param nombreUsuario obtenido desde la interfaz de usuario
     * @return false si encuentra un usuario con el mismo nombre o si el nombre tiene menos de 3 carácteres, true si no
     */
    public boolean comprobarNombreUsuario(String nombreUsuario, JLabel textoComprobacion){

        /* Comprueba si el nombre de usuario tiene al menos 3 carácteres */
        if (nombreUsuario.length()<3){
            textoComprobacion.setText("El nombre de usuario debe tener al menos 3 carácteres");
            return false;
        }

        /* Consulta SQL para comprobar si el nombre de usuario existe */
        String sql = "SELECT nombre FROM usuario WHERE nombre = ?";     // Consulta SQL para comprobar si el nombre de usuario existe
        try (
            Connection conexion = ConectarBD.conectar();                // Establecer la conexión a la base de datos
            PreparedStatement pstmt = conexion.prepareStatement(sql)    // Preparar la consulta SQL
        ) {
            pstmt.setString(1, nombreUsuario);              // Establece nombreUsuario al primer parámetro de la consulta SQL
            ResultSet resultadoQuery = pstmt.executeQuery();             // Ejecutar la consulta SQL y obtener los resultados

            // Si se encontró un usuario con el mismo nombre, devuelve false y muestra un mensaje
            if (resultadoQuery.next()) {
                textoComprobacion.setText("El nombre de usuario ya existe");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* Si no se encontró un usuario y el nombre de usuario tiene al menos 3 caracteres, devuelve true */
        return true;
    }

    /**
     * Comprueba si la contraseña tiene al menos 4 carácteres y no tiene espacios en blanco ni ciertos carácteres extraños
     * @param passwordUsuario (char[]) obtenida desde la interfaz de usuario
     * @return true si fue creado correctamente, mensaje en la interfaz si no
     */
    public boolean comprobarPasswordUsuario(String passwordUsuario, JLabel textoComprobacion){
        String caracteresNoPermitidos = "^`:@´;·ªº|\"{}";
        if (passwordUsuario.length()<4){
            textoComprobacion.setText("La contraseña debe tener al menos 4 carácteres");
            return false;
        }
        for (int i=0; i<passwordUsuario.length(); i++) {
            if (passwordUsuario.charAt(i) == ' ') {
                textoComprobacion.setText("La contraseña no puede tener espacios en blanco");
                return false;
            } else if (caracteresNoPermitidos.indexOf(passwordUsuario.charAt(i)) != -1) {
                textoComprobacion.setText("La contraseña no puede tener carácteres extraños");
                return false;
            }
        }
        return true;
    }

    /**
     * Cuenta el número de usuarios registrados
     * @return número total de usuarios registrados
     */
    public int contarUsuarios(){
        String sql = "SELECT COUNT(*) FROM usuarios";

        try (
                Connection conexion = ConectarBD.conectar();
                PreparedStatement pstmt = conexion.prepareStatement(sql)
        ) {
            ResultSet resultadoQuery = pstmt.executeQuery();
            if(resultadoQuery.next()){
                return resultadoQuery.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}