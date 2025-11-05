package testParteKevin;

import utn.tfi.grupo7.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase de prueba para verificar la conexión con la base de datos "empresa"
 * y listar los empleados junto con sus legajos asociados.
 */
public class TestConexion {

    public static void main(String[] args) {

        Connection conn = null;

        try {
            // 1️⃣ Obtenemos la conexión desde DatabaseConnection
            conn = DatabaseConnection.getConnection();

            // 2️⃣ Creamos un Statement para ejecutar consultas SQL
            Statement stmt = conn.createStatement();

            // 3️⃣ Consulta para listar empleados y su legajo
            String sql = """
                SELECT e.id AS emp_id, e.nombre, e.apellido, e.dni, e.area,
                       l.id AS legajo_id, l.nro_legajo, l.categoria, l.estado
                FROM empleado e
                LEFT JOIN legajo l ON e.legajo_id = l.id
                WHERE e.eliminado = 0
            """;

            ResultSet rs = stmt.executeQuery(sql);

            // 4️⃣ Recorremos los resultados y los mostramos en consola
            System.out.println("Lista de empleados con su legajo:");
            while (rs.next()) {
                System.out.println(
                    "Empleado ID: " + rs.getLong("emp_id") +
                    ", Nombre: " + rs.getString("nombre") +
                    ", Apellido: " + rs.getString("apellido") +
                    ", DNI: " + rs.getString("dni") +
                    ", Área: " + rs.getString("area") +
                    " | Legajo ID: " + rs.getLong("legajo_id") +
                    ", NroLegajo: " + rs.getString("nro_legajo") +
                    ", Categoria: " + rs.getString("categoria") +
                    ", Estado: " + rs.getString("estado")
                );
            }

            // 5️⃣ Cerramos el ResultSet y Statement
            rs.close();
            stmt.close();

        } catch (Exception e) {
            // Si hay un error de conexión o SQL, lo mostramos
            e.printStackTrace();
        } finally {
            // 6️⃣ Cerramos la conexión al final
            DatabaseConnection.closeConnection();
        }
    }
}