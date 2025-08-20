package dao;

import java.sql.*;
import java.util.ArrayList;
import models.Estudiante;
import util.ConexionDB;

public class EstudianteDAO {

    public boolean guardar(Estudiante e) throws SQLException {
        String validarSQL = "SELECT COUNT(*) FROM estudiantes WHERE nombre = ? AND grupo = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement psVal = con.prepareStatement(validarSQL)) {
            psVal.setString(1, e.getNombre());
            psVal.setString(2, e.getGrupo());
            ResultSet rsVal = psVal.executeQuery();
            if (rsVal.next() && rsVal.getInt(1) > 0) {
                return false;
            }
        }

        String sql = "INSERT INTO estudiantes (nombre, edad, grupo, tutor, telefono, matricula, condiciones_medicas) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setInt(2, e.getEdad());
            ps.setString(3, e.getGrupo());
            ps.setString(4, e.getTutor());
            ps.setString(5, e.getTelefono());
            ps.setString(6, e.getMatricula());
            ps.setString(7, e.getCondicionesMedicas());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idGenerado = rs.getInt("id");
                e.setId(idGenerado);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Estudiante> listar() throws SQLException {
        ArrayList<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";
        try (Connection con = ConexionDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Estudiante e = new Estudiante(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("edad"),
                    rs.getString("grupo"),
                    rs.getString("tutor"),
                    rs.getString("telefono"),
                    rs.getString("matricula"),
                    rs.getString("condiciones_medicas")
                );
                lista.add(e);
            }
        }
        return lista;
    }

    public boolean actualizar(Estudiante e) {
        String sql = "UPDATE estudiantes SET nombre = ?, edad = ?, grupo = ?, tutor = ?, telefono = ?, matricula = ?, condiciones_medicas = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setInt(2, e.getEdad());
            ps.setString(3, e.getGrupo());
            ps.setString(4, e.getTutor());
            ps.setString(5, e.getTelefono());
            ps.setString(6, e.getMatricula());
            ps.setString(7, e.getCondicionesMedicas());
            ps.setInt(8, e.getId());

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException ex) {
            System.err.println("❌ Error al actualizar estudiante: " + ex.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;
        }
    }
}