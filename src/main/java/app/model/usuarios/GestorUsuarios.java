package app.model.usuarios;

import app.model.CodigoError;
import app.model.basedatos.ConectarBD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;
import services.Recursos;

/**
 * Clase que gestiona los usuarios. Hace las operaciones relacionadas con la base de datos. (Registro, conexión, comprobación de datos, etc)
 */
public class GestorUsuarios {
    private final Recursos sRecursos = Recursos.getService();
    private final ConectarBD conectarBD = new ConectarBD();

    /**
     * Método para registrar un nuevo usuario en la base de datos
     * @param nombreUsuario obtenido desde la interfaz de usuario
     * @param passwordUsuario obtenido desde la interfaz de usuario
     * @param textoComprobacion para mostrar mensajes de error
     * @param textoLogin para mostrar mensajes
     * @return true si el usuario fue registrado correctamente
     */
    public int registrarUsuario(String nombreUsuario, String passwordUsuario) {
        try (Connection conexion = conectarBD.conectar()) {
            String passwordHashed = BCrypt.hashpw(passwordUsuario, BCrypt.gensalt());     // Encripta la contraseña del usuario para más seguridad. Siempre es un String de 60 carácteres.

            String sql = "INSERT INTO usuario (nombreU, passwordU) VALUES (?, ?)";        // Consulta SQL para insertar un nuevo usuario
            PreparedStatement pstmt = conexion.prepareStatement(sql);                     // Prepara la consulta
            pstmt.setString(1, nombreUsuario);                               // Establece el primer valor de la consulta SQL
            pstmt.setString(2, passwordHashed);                              // Establece el segundo valor de la consulta SQL

            pstmt.executeUpdate();                                                        // Ejecuta la consulta SQL
            return CodigoError.SIN_ERROR;
        } catch (SQLException e) {
            return CodigoError.ERROR_SIN_CONEXION;
        }
    }

    /**
     * Comprueba las credenciales y conecta al usuario si son correctas.
     * @param nombreUsuario obtenido desde la interfaz de usuario
     * @param passwordUsuario obtenido desde la interfaz de usuario
     * @param textoComprobacion para mostrar mensajes de error
     * @return true si el usuario fue conectado correctamente
     */
    public boolean conectarUsuario (String nombreUsuario, String passwordUsuario, JLabel textoComprobacion){
        String passwordUsuarioHashed;

        try (Connection conexion = conectarBD.conectar()) {
            /* Consulta SQL para obtener la contraseña del usuario */
            String sql = "SELECT passwordU FROM usuario WHERE nombreU = ?";
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, nombreUsuario);

            ResultSet resultado = prepare.executeQuery();
            if (resultado.next()){
                passwordUsuarioHashed = resultado.getString("passwordu");
                /* Comprueba si la contraseña del usuario es correcta */
                if (!BCrypt.checkpw(passwordUsuario, passwordUsuarioHashed)){
                    textoComprobacion.setText("Contraseña incorrecta");
                    sRecursos.crearTimer(textoComprobacion);
                    return false;
                }
            } else {
                textoComprobacion.setText("Usuario no registrado");
                sRecursos.crearTimer(textoComprobacion);
                return false;
            }
        } catch (SQLException e) {
            textoComprobacion.setText("No hay conexión");
            sRecursos.crearTimer(textoComprobacion);
            return false;
        }

        Usuario.setUsuarioConectado(nombreUsuario, passwordUsuarioHashed);
        return true;
    }


    /**
     * Comprueba si el nombre de usuario tiene al menos 3 carácteres y no existe en la base de datos
     * @param nombreUsuario obtenido desde la interfaz de usuario
     * @return true si es correcto
     */
    public int comprobarNombreUsuario(String nombreUsuario){

        try (Connection conexion = conectarBD.conectar()) {
            /* Consulta SQL para comprobar si el nombre de usuario existe */
            String sql = "SELECT nombreU FROM usuario WHERE nombreU = ?";
            PreparedStatement prepare = conexion.prepareStatement(sql);
            prepare.setString(1, nombreUsuario);

            /* Comprueba si el nombre de usuario ya existe en la base de datos */
            ResultSet resultadoQuery = prepare.executeQuery();
            if (resultadoQuery.next()) {
                return CodigoError.ERROR_USUARIO_YA_EXISTE;
            }
        } catch (SQLException e) {
            return CodigoError.ERROR_SIN_CONEXION;
        }

        /* Comprueba si el nombre de usuario tiene al menos 3 carácteres */
        if (nombreUsuario.length()<3){
            return CodigoError.ERROR_NOMBRE_CORTO;
        }

        if (nombreUsuario.length()>40) {
            return CodigoError.ERROR_NOMBRE_LARGO;
        }

        /* Si no se encontró un usuario y el nombre de usuario tiene al menos 3 caracteres, devuelve true */
        return CodigoError.SIN_ERROR;
    }

    /**
     * Comprueba si la contraseña del usuario tiene al menos 4 carácteres y no tiene espacios en blanco
     * @param passwordUsuario obtenido desde la interfaz de usuario
     * @return true si es correcta
     */
    public int comprobarPasswordUsuario(String passwordUsuario){
        String caracteresNoPermitidos = "^`:@´;·ªº|\"{}";

        /* Comprueba si la contraseña tiene al menos 4 carácteres */
        if (passwordUsuario.length()<4){
            return CodigoError.ERROR_PASSWORD_CORTA;
        }

        if (passwordUsuario.length()>50){
            return CodigoError.ERROR_PASSWORD_LARGA;
        }

        for (int i=0; i<passwordUsuario.length(); i++) {
            /* Comprueba si la contraseña tiene espacios en blanco o carácteres no permitidos */
            if (passwordUsuario.charAt(i) == ' ') {
                return CodigoError.ERROR_PASSWORD_CON_ESPACIOS;
            } else if (caracteresNoPermitidos.indexOf(passwordUsuario.charAt(i)) != -1) {
                return CodigoError.ERROR_PASSWORD_CARACTERES_RAROS;
            }
        }
        return CodigoError.SIN_ERROR;
    }

    /**
     * Cuenta el número de usuarios registrados
     * @return número total de usuarios registrados
     */
    public int contarUsuarios() {
        /* Consulta SQL para contar el número de usuarios */
        String sql = "SELECT COUNT(*) FROM usuario";
        try (Connection conexion = conectarBD.conectar()) {
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