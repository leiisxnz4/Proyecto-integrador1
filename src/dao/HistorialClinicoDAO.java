package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.HistorialClinico;
import util.ConexionDB;

public class HistorialClinicoDAO {

    public List<HistorialClinico> obtenerPorMatricula(String matricula) throws SQLException {
        List<HistorialClinico> historial = new ArrayList<>();

        try (Connection con = ConexionDB.getConnection()) {

            String sqlConsulta = """
                SELECT c.fecha, c.motivo
                FROM consultas c
                WHERE c.matricula = ?
            """;
            try (PreparedStatement ps = con.prepareStatement(sqlConsulta)) {
                ps.setString(1, matricula);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        historial.add(new HistorialClinico("Consulta",
                            getFecha(rs, "fecha"),
                            getTexto(rs, "motivo", "Sin motivo")));
                    }
                }
            }

            String sqlCita = """
                SELECT ci.fecha, ci.hora, ci.motivo
                FROM citas ci
                WHERE ci.matricula = ?
            """;
            try (PreparedStatement ps = con.prepareStatement(sqlCita)) {
                ps.setString(1, matricula);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String fechaHora = getFecha(rs, "fecha") + " " + getHora(rs, "hora");
                        historial.add(new HistorialClinico("Cita", fechaHora,
                            getTexto(rs, "motivo", "Sin motivo")));
                    }
                }
            }

            String sqlRec = """
                SELECT r.fecha, r.recomendacion
                FROM recomendaciones r
                WHERE r.matricula = ?
            """;
            try (PreparedStatement ps = con.prepareStatement(sqlRec)) {
                ps.setString(1, matricula);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        historial.add(new HistorialClinico("Recomendación",
                            getFecha(rs, "fecha"),
                            getTexto(rs, "recomendacion", "Sin recomendación")));
                    }
                }
            }
        }

        return historial;
    }

    private String getFecha(ResultSet rs, String column) throws SQLException {
        Date date = rs.getDate(column);
        return (date != null) ? date.toString() : "Sin fecha";
    }

    private String getHora(ResultSet rs, String column) throws SQLException {
        Time time = rs.getTime(column);
        return (time != null) ? time.toString() : "Sin hora";
    }

    private String getTexto(ResultSet rs, String column, String defaultValue) throws SQLException {
        String value = rs.getString(column);
        return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
    }
}