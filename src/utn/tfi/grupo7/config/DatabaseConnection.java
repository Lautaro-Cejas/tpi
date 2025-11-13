package utn.tfi.grupo7.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Clase encargada de crear conexiones con la base de datos (Patrón Fábrica).
 * Lee las credenciales desde el archivo config.properties ubicado en /resources.
 */
public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "config.properties";

    /**
     * Devuelve una NUEVA conexión a la base de datos en cada llamada.
     * Lanza las excepciones (throws) para que la capa de Servicio las maneje.
     *
     * @return una nueva instancia de Connection.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     * @throws IOException si no se puede leer el archivo de propiedades.
     */
    public static Connection getConnection() throws SQLException, IOException {

        Properties props = new Properties();
        try (InputStream input = DatabaseConnection.class.getResourceAsStream(PROPERTIES_FILE)) {

            if (input == null) {
                throw new IOException("No se encontró el archivo " + PROPERTIES_FILE);
            }
            
            props.load(input);

        } catch (IOException e) {
            // Un error aquí es crítico, la app no puede conectarse.
            System.err.println("Error fatal al leer config.properties: " + e.getMessage());
            throw e;
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        // Crea y retorna una conexión nueva en cada llamada.
        return DriverManager.getConnection(url, user, password);
    }
}