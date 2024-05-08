package app.model.basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBD {

    private static final String URL = "jdbc:postgresql://localhost:5432/proyecto_progDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "kopelius123";

    // Método para establecer la conexión a la base de datos
    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Cargar el controlador JDBC
            Class.forName("org.postgresql.Driver");

            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    // Método para cerrar la conexión
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