package utn.tfi.grupo7.dao;

import utn.tfi.grupo7.entities.Legajo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO concreto para la entidad Legajo
 * Implementa operaciones CRUD usando PreparedStatement
 */
public class LegajoDao implements GenericDao<Legajo> {

    @Override
    public void insert(Legajo legajo, Connection conn) throws SQLException {
        String sql = "INSERT INTO legajo (nro_legajo, categoria, estado, fecha_alta, observaciones, eliminado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, legajo.getNroLegajo());
            ps.setString(2, legajo.getCategoria());
            ps.setString(3, legajo.getEstado());
            ps.setDate(4, legajo.getFechaAlta() != null ? Date.valueOf(legajo.getFechaAlta()) : null);
            ps.setString(5, legajo.getObservaciones());
            ps.setBoolean(6, legajo.getEliminado());
            ps.executeUpdate();

            // Obtener el ID generado y setearlo en el objeto
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    legajo.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Legajo getById(Long id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM legajo WHERE id = ? AND eliminado = 0";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Legajo> getAll(Connection conn) throws SQLException {
        String sql = "SELECT * FROM legajo WHERE eliminado = 0";
        List<Legajo> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    @Override
    public void update(Legajo legajo, Connection conn) throws SQLException {
        String sql = "UPDATE legajo SET nro_legajo=?, categoria=?, estado=?, fecha_alta=?, observaciones=?, eliminado=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, legajo.getNroLegajo());
            ps.setString(2, legajo.getCategoria());
            ps.setString(3, legajo.getEstado());
            ps.setDate(4, legajo.getFechaAlta() != null ? Date.valueOf(legajo.getFechaAlta()) : null);
            ps.setString(5, legajo.getObservaciones());
            ps.setBoolean(6, legajo.getEliminado());
            ps.setLong(7, legajo.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id, Connection conn) throws SQLException {
        String sql = "UPDATE legajo SET eliminado = 1 WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet a un objeto Legajo
    private Legajo mapRow(ResultSet rs) throws SQLException {
        return new Legajo(
                rs.getLong("id"),
                rs.getString("nro_legajo"),
                rs.getString("categoria"),
                rs.getString("estado"),
                rs.getDate("fecha_alta") != null ? rs.getDate("fecha_alta").toLocalDate() : null,
                rs.getString("observaciones"),
                rs.getBoolean("eliminado")
        );
    }
}