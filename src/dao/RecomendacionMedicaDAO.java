package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.RecomendacionMedica;
import util.ConexionDB;

public class RecomendacionMedicaDAO {

    // Crear nueva recomendación
    public void guardar(RecomendacionMedica r) throws SQLException {
        String sql = "INSERT INTO recomendaciones (matricula, fecha, recomendacion) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getMatricula());
            ps.setDate(2, Date.valueOf(r.getFecha()));
            ps.setString(3, r.getTexto());
            ps.executeUpdate();
        }
    }

    // Verificar si ya existe una recomendación igual
    public boolean existe(String matricula, LocalDate fecha, String texto) throws SQLException {
        String sql = "SELECT COUNT(*) FROM recomendaciones WHERE matricula = ? AND fecha = ? AND recomendacion = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, matricula);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setString(3, texto);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // Listar todas las recomendaciones
    public List<RecomendacionMedica> listar() throws SQLException {
        List<RecomendacionMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM recomendaciones ORDER BY fecha DESC";
        try (Connection con = ConexionDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                RecomendacionMedica r = new RecomendacionMedica(
                    rs.getInt("id"),
                    rs.getString("matricula"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getString("recomendacion")
                );
                lista.add(r);
            }
        }
        return lista;
    }

    // Actualizar recomendación
    public void actualizar(RecomendacionMedica r) throws SQLException {
        String sql = "UPDATE recomendaciones SET matricula = ?, fecha = ?, recomendacion = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getMatricula());
            ps.setDate(2, Date.valueOf(r.getFecha()));
            ps.setString(3, r.getTexto());
            ps.setInt(4, r.getId());
            ps.executeUpdate();
        }
    }

    // Eliminar recomendación
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM recomendaciones WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Buscar por matrícula
    public List<RecomendacionMedica> buscarPorMatricula(String matricula) throws SQLException {
        List<RecomendacionMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM recomendaciones WHERE matricula = ? ORDER BY fecha DESC";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecomendacionMedica r = new RecomendacionMedica(
                        rs.getInt("id"),
                        rs.getString("matricula"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("recomendacion")
                    );
                    lista.add(r);
                }
            }
        }
        return lista;
    }

    // Buscar recomendación por ID
    public RecomendacionMedica buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM recomendaciones WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new RecomendacionMedica(
                        rs.getInt("id"),
                        rs.getString("matricula"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("recomendacion")
                    );
                }
            }
        }
        return null;
    }
}