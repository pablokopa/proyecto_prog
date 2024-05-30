package app.model.basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBD {
    private static final String URL = "jdbc:postgresql://143.47.38.153:5432/baseproyecto";
    private static final String USER = "app_rol";
    private static final String PASSWORD = "password";

    /**
     * Método para establecer la conexión con la base de datos.
     * @return Connection conexión con la base de datos
     * @throws SQLException si no se puede establecer la conexión
     */
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);       // Establece la conexión y la devuelve
    }
}