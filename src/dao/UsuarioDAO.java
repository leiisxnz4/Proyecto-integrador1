package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;
import util.SeguridadUtil;

public class UsuarioDAO {

    // Valida si el usuario y la contraseña coinciden
    public boolean validar(String usuario, String clavePlano) {
        if (usuario == null || clavePlano == null || usuario.isEmpty() || clavePlano.isEmpty()) {
            return false;
        }

        try (Connection conn = ConexionDB.getConnection()) {
            String sql = "SELECT clave FROM usuarios WHERE usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String claveGuardada = rs.getString("clave");
                if (claveGuardada == null || claveGuardada.isEmpty()) return false;

                String claveCifrada = SeguridadUtil.cifrarSHA256(clavePlano);
                return claveGuardada.equals(claveCifrada);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al validar usuario: " + e.getMessage());
        }
        return false;
    }

    // Verifica si el usuario ya existe
    public boolean existeUsuario(String usuario) {
        try (Connection conn = ConexionDB.getConnection()) {
            String sql = "SELECT 1 FROM usuarios WHERE usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar existencia de usuario: " + e.getMessage());
            return false;
        }
    }

    // Inserta un nuevo usuario con clave cifrada
    public boolean insertarUsuario(String usuario, String clavePlano, String rol) {
        if (usuario == null || clavePlano == null || rol == null ||
            usuario.isEmpty() || clavePlano.isEmpty() || rol.isEmpty()) {
            return false;
        }

        String claveCifrada = SeguridadUtil.cifrarSHA256(clavePlano);
        try (Connection conn = ConexionDB.getConnection()) {
            String sql = "INSERT INTO usuarios (usuario, clave, rol, activo) VALUES (?, ?, ?, TRUE)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, claveCifrada);
            stmt.setString(3, rol);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    // Elimina un usuario por nombre
    public boolean eliminarUsuario(String usuario) {
        try (Connection conn = ConexionDB.getConnection()) {
            String sql = "DELETE FROM usuarios WHERE usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    // Obtiene el nombre del usuario
    public String obtenerNombre(String usuario) {
        try (Connection conn = ConexionDB.getConnection()) {
            String sql = "SELECT usuario FROM usuarios WHERE usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("usuario");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener nombre: " + e.getMessage());
        }
        return "Desconocido";
    }

    // Obtiene el rol del usuario
    public String obtenerRol(String usuario) {
        try (Connection conn = ConexionDB.getConnection()) {
            String sql = "SELECT rol FROM usuarios WHERE usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("rol");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener rol: " + e.getMessage());
        }
        return "desconocido";
    }

    // Lista todos los usuarios registrados
    public List<String[]> listarUsuarios() {
        List<String[]> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection()) {
            String sql = "SELECT usuario, rol, activo FROM usuarios ORDER BY usuario";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String usuario = rs.getString("usuario");
                String rol = rs.getString("rol");
                String activo = rs.getBoolean("activo") ? "Sí" : "No";
                lista.add(new String[] { usuario, rol, activo });
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }
}