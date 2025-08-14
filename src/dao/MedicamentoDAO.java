package dao;

import java.sql.*;
import java.util.ArrayList;
import models.Medicamento;
import util.ConexionDB;

public class MedicamentoDAO {

    public void guardar(Medicamento medicamento) throws SQLException {
        String sql = "INSERT INTO medicamentos (nombre, presentacion, dosis, cantidad) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, medicamento.getNombre());
            ps.setString(2, medicamento.getPresentacion());
            ps.setString(3, medicamento.getDosis());
            ps.setInt(4, medicamento.getCantidad());
            ps.executeUpdate();
        }
    }

    public void actualizar(Medicamento medicamento) throws SQLException {
        String sql = "UPDATE medicamentos SET nombre = ?, presentacion = ?, dosis = ?, cantidad = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, medicamento.getNombre());
            ps.setString(2, medicamento.getPresentacion());
            ps.setString(3, medicamento.getDosis());
            ps.setInt(4, medicamento.getCantidad());
            ps.setInt(5, medicamento.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM medicamentos WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public ArrayList<Medicamento> listar() throws SQLException {
        ArrayList<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos ORDER BY nombre";
        try (Connection con = ConexionDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Medicamento m = new Medicamento();
                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                m.setPresentacion(rs.getString("presentacion"));
                m.setDosis(rs.getString("dosis"));
                m.setCantidad(rs.getInt("cantidad"));
                lista.add(m);
            }
        }
        return lista;
    }
}