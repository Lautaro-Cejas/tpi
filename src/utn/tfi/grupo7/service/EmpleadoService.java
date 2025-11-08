package utn.tfi.grupo7.service;

import utn.tfi.grupo7.config.DatabaseConnection;
import utn.tfi.grupo7.dao.EmpleadoDao;
import utn.tfi.grupo7.dao.LegajoDao;
import utn.tfi.grupo7.entities.Empleado;
import utn.tfi.grupo7.entities.Legajo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementación del servicio para Empleados.
 * Orquesta los DAOs y maneja la lógica de negocio y transacciones.
 */
public class EmpleadoService implements GenericService<Empleado> {

    private EmpleadoDao empleadoDao;
    private LegajoDao legajoDao;

    public EmpleadoService() {
        this.empleadoDao = new EmpleadoDao();
        this.legajoDao = new LegajoDao();
    }

    /**
     * Crea un Empleado y su Legajo en una única transacción.
     * Este método implementa la lógica transaccional requerida.
     */
    @Override
    public void crear(Empleado empleado) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // 1. INICIO DE LA TRANSACCIÓN

            // 2. VALIDACIONES DE NEGOCIO
            if (empleado.getDni() == null || empleado.getDni().trim().isEmpty()) {
                throw new Exception("El DNI no puede estar vacío.");
            }
            if (empleado.getLegajo() == null) {
                throw new Exception("El empleado debe tener un legajo asociado.");
            }
            // ... (Agregar más validaciones) ...

            // 3. ORQUESTACIÓN (EJECUCIÓN)
            // Paso A: Crear el Legajo
            Legajo legajoParaCrear = empleado.getLegajo();
            legajoDao.insert(legajoParaCrear, conn);
            // (El ID del legajo ya fue seteado por el DAO)

            // Paso B: Crear el Empleado
            empleadoDao.insert(empleado, conn);

            // 4. CIERRE (ÉXITO)
            conn.commit();

        } catch (SQLException e) {
            // 5. CIERRE (FALLO)
            if (conn != null) {
                conn.rollback(); // Deshace todo
            }
            throw new Exception("Error en la transacción, rollback ejecutado: " + e.getMessage());

        } finally {
            // 6. DEVOLVER CONEXIÓN
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public Empleado getById(Long id) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            return empleadoDao.getById(id, conn);
        } catch (Exception e) {
            throw new Exception("Error en Service al leer empleado: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public List<Empleado> getAll() throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            return empleadoDao.getAll(conn);
        } catch (Exception e) {
            throw new Exception("Error en Service al listar empleados: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void actualizar(Empleado empleado) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            // La actualización simple no requiere transacción
            // (A menos que actualices legajo y empleado al mismo tiempo)
            empleadoDao.update(empleado, conn);
        } catch (Exception e) {
            throw new Exception("Error en Service al actualizar empleado: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            // La baja lógica es una operación simple
            empleadoDao.delete(id, conn);
        } catch (Exception e) {
            throw new Exception("Error en Service al eliminar empleado: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Búsqueda específica por DNI (Requerida por el TFI ).
     * ATENCIÓN: Esto requiere que EmpleadoDao tenga un método "getByDni".
     */
    public Empleado getEmpleadoByDni(String dni) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            // Esta línea asume que Kevin agrega el método getByDni a EmpleadoDao
            return empleadoDao.getByDni(dni, conn); 
        } catch (Exception e) {
            throw new Exception("Error en Service al buscar por DNI: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}