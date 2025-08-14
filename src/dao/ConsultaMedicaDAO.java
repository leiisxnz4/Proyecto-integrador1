package dao;

import java.sql.*;
import java.util.ArrayList;
import models.ConsultaMedica;
import util.ConexionDB;

public class ConsultaMedicaDAO {

    public boolean guardar(ConsultaMedica consulta) throws SQLException {
        String validarSQL = """
            SELECT COUNT(*) FROM consultas
            WHERE matricula = ? AND fecha = ? AND motivo = ?
        """;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement psVal = con.prepareStatement(validarSQL)) {

            psVal.setString(1, consulta.getMatricula());
            psVal.setDate(2, Date.valueOf(consulta.getFecha()));
            psVal.setString(3, consulta.getMotivo());

            ResultSet rsVal = psVal.executeQuery();
            if (rsVal.next() && rsVal.getInt(1) > 0) {
                return false; // Ya existe una consulta similar
            }
        }

        String sql = """
            INSERT INTO consultas (matricula, motivo, signos_vitales, medicamentos, observaciones, fecha)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id
        """;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, consulta.getMatricula());
            ps.setString(2, consulta.getMotivo());
            ps.setString(3, consulta.getSignosVitales());
            ps.setString(4, consulta.getMedicamentos());
            ps.setString(5, consulta.getObservaciones());
            ps.setDate(6, Date.valueOf(consulta.getFecha()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                consulta.setId(rs.getInt("id"));
                return true;
            }
        }

        return false;
    }

    public boolean actualizar(ConsultaMedica consulta) throws SQLException {
        String sql = """
            UPDATE consultas
            SET matricula = ?, motivo = ?, signos_vitales = ?, medicamentos = ?, observaciones = ?, fecha = ?
            WHERE id = ?
        """;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, consulta.getMatricula());
            ps.setString(2, consulta.getMotivo());
            ps.setString(3, consulta.getSignosVitales());
            ps.setString(4, consulta.getMedicamentos());
            ps.setString(5, consulta.getObservaciones());
            ps.setDate(6, Date.valueOf(consulta.getFecha()));
            ps.setInt(7, consulta.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM consultas WHERE id = ?";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public ArrayList<ConsultaMedica> listar() throws SQLException {
        ArrayList<ConsultaMedica> lista = new ArrayList<>();
        String sql = """
            SELECT c.*, e.nombre
            FROM consultas c
            JOIN estudiantes e ON c.matricula = e.matricula
            ORDER BY c.fecha DESC
        """;

        try (Connection con = ConexionDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ConsultaMedica consulta = new ConsultaMedica();
                consulta.setId(rs.getInt("id"));
                consulta.setMatricula(rs.getString("matricula"));
                consulta.setNombreEstudiante(rs.getString("nombre"));
                consulta.setMotivo(rs.getString("motivo"));
                consulta.setSignosVitales(rs.getString("signos_vitales"));
                consulta.setMedicamentos(rs.getString("medicamentos"));
                consulta.setObservaciones(rs.getString("observaciones"));
                consulta.setFecha(rs.getDate("fecha").toLocalDate());
                lista.add(consulta);
            }
        }

        return lista;
    }
}