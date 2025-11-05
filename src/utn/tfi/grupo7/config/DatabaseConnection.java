package utn.tfi.grupo7.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Clase encargada de manejar la conexión con la base de datos MySQL.
 * Lee las credenciales desde el archivo config.properties ubicado en /resources.
 */
public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "config.properties";
    private static Connection connection = null;

    /**
     * Devuelve una conexión activa a la base de datos.
     * Si no existe o está cerrada, la crea usando los datos de config.properties.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try (InputStream input = DatabaseConnection.class.getResourceAsStream(PROPERTIES_FILE)) {
                if (input == null) {
                    throw new IOException("No se encontró el archivo " + PROPERTIES_FILE);
                }

                Properties props = new Properties();
                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Conexión establecida con la base de datos.");

            } catch (IOException e) {
                System.err.println("⚠️ Error al leer config.properties: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("⚠️ Error al conectar con la base: " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Cierra la conexión si está abierta.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}