package app.model.basedatos;

import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConectarBD {

    private static final String URL = "jdbc:postgresql://143.47.38.153:5432/baseproyecto";
    private static final String USER = "app_rol";
    private static final String PASSWORD = "password";
    private static Connection conexion;
//    private static PreparedStatement prepare;


    /**
     * Método para establecer la conexión con la base de datos.
     * @return La conexión establecida.
     */
    public static Connection conectar() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");     // Cargar el controlador JDBC
        conexion = DriverManager.getConnection(URL, USER, PASSWORD);        // Establecer la conexión
        return conexion;
    }

    public static Connection getConexion() {
        return conexion;
    }

//    public static PreparedStatement getPrepare() {
//        return prepare;
//    }

    /**
     * Método para cerrar la conexión con la base de datos.
     * @param conexion La conexión a cerrar.
     */
    public static void cerrar(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}