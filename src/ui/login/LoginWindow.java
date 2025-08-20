package ui.login;

import dao.UsuarioDAO;
import javax.swing.*;
import ui.App;

/**
 * Ventana principal de login.
 */
public class LoginWindow extends JFrame {

    public LoginWindow() {
        setTitle("Acceso al Sistema");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LoginPanel panel = new LoginPanel();
        setContentPane(panel);

        // Evento al hacer clic en "Acceder"
        panel.getAccederBtn().addActionListener(e -> {
            String usuario = panel.getUsuarioField().getText().trim();
            String clave = new String(panel.getClaveField().getPassword());

            // Validación visual antes de consultar la base
            if (usuario.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "⚠️ Debes ingresar usuario y contraseña.");
                return;
            }

            UsuarioDAO dao = new UsuarioDAO();
            if (dao.validar(usuario, clave)) {
                String nombre = dao.obtenerNombre(usuario);
                String rol = dao.obtenerRol(usuario);
                dispose(); // Cierra la ventana de login
                new App(nombre, rol).setVisible(true); // Abre el menú con nombre y rol
            } else {
                JOptionPane.showMessageDialog(panel, "❌ Usuario o contraseña incorrectos.");
            }
        });
    }

    // Punto de entrada del sistema
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        // Inserta el usuario solo si no existe (verifica por nombre, no por clave)
        if (!dao.existeUsuario("admin")) {
            dao.insertarUsuario("admin", "admin123", "admin");
        }

        SwingUtilities.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }
}