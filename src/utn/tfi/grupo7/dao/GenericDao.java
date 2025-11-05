package utn.tfi.grupo7.dao;

import java.sql.Connection;
import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD de cualquier entidad.
 * @param <T> Tipo de entidad (Empleado, Legajo, etc.)
 */
public interface GenericDao<T> {

    // Crear un registro en la base de datos usando una conexión externa
    void insert(T t, Connection conn) throws Exception;

    // Leer un registro por ID usando conexión externa
    T getById(Long id, Connection conn) throws Exception;

    // Leer todos los registros usando conexión externa
    List<T> getAll(Connection conn) throws Exception;

    // Actualizar un registro usando conexión externa
    void update(T t, Connection conn) throws Exception;

    // Eliminar un registro por ID (baja lógica) usando conexión externa
    void delete(Long id, Connection conn) throws Exception;
}