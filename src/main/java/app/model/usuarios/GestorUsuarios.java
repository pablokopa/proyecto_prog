package app.model.usuarios;
import app.model.basedatos.ConectarBD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase que gestiona los usuarios
 */
public class GestorUsuarios {

    /**
     * Método para registrar un nuevo usuario en la base de datos
     * @param nombreUsuario obtenido desde la interfaz de usuario
     * @param passwordUsuario obtenido desde la interfaz de usuario
     * @param textoComprobacion para mostrar mensajes de error
     * @param textoLogin para mostrar mensajes
     * @return true si el usuario fue registrado correctamente
     */
    public boolean registrarUsuario(String nombreUsuario, String passwordUsuario, JLabel textoComprobacion, JLabel textoLogin) {
        if(!comprobarNombreUsuario(nombreUsuario, textoComprobacion)
                || !comprobarPasswordUsuario(passwordUsuario, textoComprobacion)){
            textoLogin.setText("Registro fallido..");
            return false;
        }

        try (Connection conexion = ConectarBD.conectar()) {
            String passwordHashed = BCrypt.hashpw(passwordUsuario, BCrypt.gensalt());     // Encripta la contraseña del usuario para más seguridad. Siempre es un String de 60 carácteres.

            String sql = "INSERT INTO usuario (nombreU, passwordU) VALUES (?, ?)";        // Consulta SQL para insertar un nuevo usuario
            PreparedStatement pstmt = conexion.prepareStatement(sql);                     // Prepara la consulta
            pstmt.setString(1, nombreUsuario);                               // Establece el primer valor de la consulta SQL
            pstmt.setString(2, passwordHashed);                              // Establece el segundo valor de la consulta SQL

            pstmt.executeUpdate();                                                        // Ejecuta la consulta SQL
            return true;
        } catch (SQLException e) {
            textoComprobacion.setText("No hay conexión");
            return false;
        }
    }

    /**
     * Método para conectar un usuario a la base de datos
     * @param nombreUsuario obtenido desde la interfaz de usuario
     * @param passwordUsuario obtenido desde la interfaz de usuario
     * @param textoComprobacion para mostrar mensajes de error
     * @return true si el usuario fue conectado correctamente
     */
    public boolean conectarUsuario (String nombreUsuario, String passwordUsuario, JLabel textoComprobacion){

        try (Connection conexion = ConectarBD.conectar()) {
            String sql = "SELECT passwordU FROM usuario WHERE nombreU = ?";
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, nombreUsuario);

            ResultSet resultado = prepare.executeQuery();
            if (resultado.next()){
                String passwordU = resultado.getString("passwordu");
                if (!BCrypt.checkpw(passwordUsuario, passwordU)){
                    textoComprobacion.setText("Contraseña incorrecta");
                    return false;
                }
            } else {
                textoComprobacion.setText("Usuario no registrado");
                return false;
            }
        } catch (SQLException e) {
            textoComprobacion.setText("No hay conexión");
            return false;
        }
        return true;
    }


    /**
     * Comprueba si el nombre de usuario tiene al menos 3 carácteres y no existe en la base de datos
     * @param nombreUsuario obtenido desde la interfaz de usuario
     * @return true si es correcto
     */
    public boolean comprobarNombreUsuario(String nombreUsuario, JLabel textoComprobacion){

        try (Connection conexion = ConectarBD.conectar()) {
            String sql = "SELECT nombreU FROM usuario WHERE nombreU = ?";     // Consulta SQL para comprobar si el nombre de usuario existe
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, nombreUsuario);

            ResultSet resultadoQuery = prepare.executeQuery();
            if (resultadoQuery.next()) {
                textoComprobacion.setText("El nombre de usuario ya existe");
                return false;
            }
        } catch (SQLException e) {
            textoComprobacion.setText("No hay conexión");
            return false;
        }

        /* Comprueba si el nombre de usuario tiene al menos 3 carácteres */
        if (nombreUsuario.length()<3){
            textoComprobacion.setText("El nombre de usuario debe tener al menos 3 carácteres");
            return false;
        }

        /* Si no se encontró un usuario y el nombre de usuario tiene al menos 3 caracteres, devuelve true */
        return true;
    }

    /**
     * Comprueba si la contraseña del usuario tiene al menos 4 carácteres y no tiene espacios en blanco
     * @param passwordUsuario obtenido desde la interfaz de usuario
     * @param textoComprobacion para mostrar mensajes de error
     * @return true si es correcta
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
    public int contarUsuarios() {
        String sql = "SELECT COUNT(*) FROM usuario";
        try (Connection conexion = ConectarBD.conectar()) {
            PreparedStatement prepare = conexion.prepareStatement(sql);
            ResultSet resultadoQuery = prepare.executeQuery();
            if(resultadoQuery.next()){
                return resultadoQuery.getInt(1);
            }
        } catch (SQLException e) {
            return 0;
        }
        return 0;
    }
}