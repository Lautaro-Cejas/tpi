package utn.tfi.grupo7.service;

import java.util.List;

/**
 * Interfaz genérica para la capa de Servicio.
 * Define el contrato CRUD básico que la capa de Menú (AppMenu) espera.
 */
public interface GenericService<T> {

    /**
     * Lógica de negocio para crear una entidad.
     * Puede incluir transacciones si la creación es compuesta.
     */
    void crear(T t) throws Exception;

    /**
     * Lógica de negocio para obtener una entidad por su ID.
     */
    T getById(Long id) throws Exception;

    /**
     * Lógica de negocio para obtener todas las entidades.
     */
    List<T> getAll() throws Exception;

    /**
     * Lógica de negocio para actualizar una entidad.
     */
    void actualizar(T t) throws Exception;

    /**
     * Lógica de negocio para eliminar (baja lógica) una entidad.
     */
    void eliminar(Long id) throws Exception;
}