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

        // Usamos try-with-resources para cerrar automáticamente la conexión
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = """
                SELECT e.id AS emp_id, e.nombre, e.apellido, e.dni, e.area,
                       l.id AS legajo_id, l.nro_legajo, l.categoria, l.estado
                FROM empleado e
                LEFT JOIN legajo l ON e.legajo_id = l.id
                WHERE e.eliminado = 0
            """;

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("✅ Conexión establecida con la base de datos.");
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

            rs.close();
            System.out.println("🔒 Conexión cerrada correctamente.");

        } catch (Exception e) {
            System.err.println("⚠️ Error durante la conexión o consulta: " + e.getMessage());
            e.printStackTrace();
        }
    }
}