package app.model.basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DatabaseTest {

    private Connection conexion;

    public void setup() throws SQLException {
        conexion = DriverManager.getConnection("jdbc:h2:mem:testdb; DB_CLOSE_DELAY=-1","sa","");

        createTables();
        insertTestData();
    }

    public void teardown() throws SQLException {
        conexion.close();
    }

    private void createTables() throws SQLException{
        Statement statement = conexion.createStatement();
        statement.execute("CREATE TABLE usuario (nombreu VARCHAR(50) NOT NULL PRIMARY KEY, passwordu VARCHAR(50) NOT NULL);");
        statement.execute("CREATE TABLE etiqueta (nombree VARCHAR (50) NOT NULL PRIMARY KEY);");
        statement.execute("CREATE TABLE tarea (" +
                "idt INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombret VARCHAR(50) NOT NULL, " +
                "descripciont TEXT, " +
                "fechacreaciont TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, " +
                "fechafinalizaciont TIMESTAMP, " +
                "completadat BOOLEAN DEFAULT FALSE, " +
                "nombreu VARCHAR(50) NOT NULL, " +
                "nombree VARCHAR(50) NOT NULL, " +
                "FOREIGN KEY (nombreu) REFERENCES usuario(nombreu), " +
                "FOREIGN KEY (nombree) REFERENCES etiqueta(nombree));");
    }

    private void insertTestData() throws SQLException {
        Statement statement = conexion.createStatement();
        statement.execute("INSERT INTO etiqueta (nombree) values ('Importante / Urgente')");
        statement.execute("INSERT INTO etiqueta (nombree) values ('Importante / No urgente')");
        statement.execute("INSERT INTO etiqueta (nombree) values ('No importante / Urgente')");
        statement.execute("INSERT INTO etiqueta (nombree) values ('No importante / No urgente')");
    }

}
