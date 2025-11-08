package utn.tfi.grupo7.service;

import utn.tfi.grupo7.config.DatabaseConnection;
import utn.tfi.grupo7.dao.LegajoDao;
import utn.tfi.grupo7.entities.Legajo;

import java.sql.Connection;
import java.util.List;

/**
 * Implementación del servicio para Legajos.
 * Maneja operaciones CRUD simples para la entidad Legajo.
 */
public class LegajoService implements GenericService<Legajo> {

    private LegajoDao legajoDao;

    public LegajoService() {
        this.legajoDao = new LegajoDao();
    }

    /**
     * Crea un Legajo simple.
     * No es transaccional, es una operación única.
     */
    @Override
    public void crear(Legajo legajo) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            // Validaciones
            if (legajo.getNroLegajo() == null || legajo.getNroLegajo().trim().isEmpty()) {
                throw new Exception("El número de legajo no puede estar vacío.");
            }
            legajoDao.insert(legajo, conn);
        } catch (Exception e) {
            throw new Exception("Error en Service al crear legajo: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public Legajo getById(Long id) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            return legajoDao.getById(id, conn);
        } catch (Exception e) {
            throw new Exception("Error en Service al leer legajo: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public List<Legajo> getAll() throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            return legajoDao.getAll(conn);
        } catch (Exception e) {
            throw new Exception("Error en Service al listar legajos: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void actualizar(Legajo legajo) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            legajoDao.update(legajo, conn);
        } catch (Exception e) {
            throw new Exception("Error en Service al actualizar legajo: " + e.getMessage());
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
            legajoDao.delete(id, conn);
        } catch (Exception e) {
            throw new Exception("Error en Service al eliminar legajo: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}