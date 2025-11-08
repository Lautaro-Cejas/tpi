package utn.tfi.grupo7.dao;

import utn.tfi.grupo7.entities.Empleado;
import utn.tfi.grupo7.entities.Legajo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO concreto para la entidad Empleado
 */
public class EmpleadoDao implements GenericDao<Empleado> {

    private final LegajoDao legajoDao = new LegajoDao(); // Para mapear el Legajo de cada empleado

    @Override
    public void insert(Empleado empleado, Connection conn) throws SQLException {
        String sql = "INSERT INTO empleado (nombre, apellido, dni, email, fecha_ingreso, area, legajo_id, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setString(3, empleado.getDni());
            ps.setString(4, empleado.getEmail());
            ps.setDate(5, empleado.getFechaIngreso() != null ? Date.valueOf(empleado.getFechaIngreso()) : null);
            ps.setString(6, empleado.getArea());
            ps.setObject(7, empleado.getLegajo() != null ? empleado.getLegajo().getId() : null, Types.BIGINT);
            ps.setBoolean(8, empleado.getEliminado());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    empleado.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Empleado getById(Long id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM empleado WHERE id = ? AND eliminado = 0";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs, conn);
                }
            }
        }
        return null;
    }

    @Override
    public List<Empleado> getAll(Connection conn) throws SQLException {
        String sql = "SELECT * FROM empleado WHERE eliminado = 0";
        List<Empleado> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs, conn));
            }
        }
        return lista;
    }

    @Override
    public void update(Empleado empleado, Connection conn) throws SQLException {
        String sql = "UPDATE empleado SET nombre=?, apellido=?, dni=?, email=?, fecha_ingreso=?, area=?, legajo_id=?, eliminado=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setString(3, empleado.getDni());
            ps.setString(4, empleado.getEmail());
            ps.setDate(5, empleado.getFechaIngreso() != null ? Date.valueOf(empleado.getFechaIngreso()) : null);
            ps.setString(6, empleado.getArea());
            ps.setObject(7, empleado.getLegajo() != null ? empleado.getLegajo().getId() : null, Types.BIGINT);
            ps.setBoolean(8, empleado.getEliminado());
            ps.setLong(9, empleado.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id, Connection conn) throws SQLException {
        String sql = "UPDATE empleado SET eliminado = 1 WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    // Mapear un ResultSet a un objeto Empleado
    private Empleado mapRow(ResultSet rs, Connection conn) throws SQLException {
        Long legajoId = rs.getLong("legajo_id");
        Legajo legajo = legajoId != null ? legajoDao.getById(legajoId, conn) : null;

        return new Empleado(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("dni"),
                rs.getString("email"),
                rs.getDate("fecha_ingreso") != null ? rs.getDate("fecha_ingreso").toLocalDate() : null,
                rs.getString("area"),
                legajo,
                rs.getBoolean("eliminado")
        );
    }

    /**
     * Busca un empleado por DNI (sin incluir eliminados).
     * Necesario para cumplir el requisito TFI de búsqueda por campo relevante.
     */
    public Empleado getByDni(String dni, Connection conn) throws Exception {
        String sql = "SELECT * FROM empleado WHERE dni = ? AND eliminado = 0";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs, conn); // Reutiliza el mapRow que ya tiene
                }
            }
        }
        return null; // No se encontró
    }
}
