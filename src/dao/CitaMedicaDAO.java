package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.CitaMedica;
import util.ConexionDB;

public class CitaMedicaDAO {

    public boolean insertar(CitaMedica cita) {
        String sql = "INSERT INTO citas (matricula, fecha, hora, motivo) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cita.getMatricula());
            stmt.setDate(2, Date.valueOf(cita.getFecha()));
            stmt.setTime(3, Time.valueOf(cita.getHora()));
            stmt.setString(4, cita.getMotivo());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar cita: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(CitaMedica cita) {
        String sql = "UPDATE citas SET matricula = ?, fecha = ?, hora = ?, motivo = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cita.getMatricula());
            stmt.setDate(2, Date.valueOf(cita.getFecha()));
            stmt.setTime(3, Time.valueOf(cita.getHora()));
            stmt.setString(4, cita.getMotivo());
            stmt.setInt(5, cita.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cita: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM citas WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            return false;
        }
    }

    public List<CitaMedica> listar() {
        List<CitaMedica> citas = new ArrayList<>();
        String sql = """
            SELECT c.id, c.matricula, e.nombre, c.fecha, c.hora, c.motivo
            FROM citas c
            JOIN estudiantes e ON c.matricula = e.matricula
            ORDER BY c.fecha, c.hora
        """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                citas.add(construirCitaDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas: " + e.getMessage());
        }
        return citas;
    }

    public List<CitaMedica> buscarPorMatricula(String matricula) {
        List<CitaMedica> citas = new ArrayList<>();
        String sql = """
            SELECT c.id, c.matricula, e.nombre, c.fecha, c.hora, c.motivo
            FROM citas c
            JOIN estudiantes e ON c.matricula = e.matricula
            WHERE c.matricula = ?
        """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    citas.add(construirCitaDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar por matrícula: " + e.getMessage());
        }
        return citas;
    }

    private CitaMedica construirCitaDesdeResultSet(ResultSet rs) throws SQLException {
        CitaMedica cita = new CitaMedica();
        cita.setId(rs.getInt("id"));
        cita.setMatricula(rs.getString("matricula"));
        cita.setNombreEstudiante(rs.getString("nombre"));
        cita.setFecha(rs.getDate("fecha").toLocalDate());
        cita.setHora(rs.getTime("hora").toLocalTime());
        cita.setMotivo(rs.getString("motivo"));
        return cita;
    }
}