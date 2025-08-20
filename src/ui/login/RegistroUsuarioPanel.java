package ui.login;

import dao.UsuarioDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RegistroUsuarioPanel extends JPanel {

    private JTextField usuarioField;
    private JPasswordField claveField;
    private JComboBox<String> rolBox;
    private JTable tablaUsuarios;
    private DefaultTableModel modelo;

    public RegistroUsuarioPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 240, 255));

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(new Color(245, 240, 255));
        encabezado.add(crearBotonRegresar(), BorderLayout.WEST);
        encabezado.add(crearFormulario(), BorderLayout.CENTER);

        add(encabezado, BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);
        cargarUsuarios();
    }

    private JPanel crearBotonRegresar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(245, 240, 255));

        JButton regresarBtn = new JButton("↩️ Regresar");
        regresarBtn.setBackground(new Color(230, 220, 250));
        regresarBtn.setFocusPainted(false);
        regresarBtn.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana != null) ventana.dispose();
        });

        panel.add(regresarBtn);
        return panel;
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(250, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Registro de Usuario", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(new Color(80, 60, 120));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        usuarioField = new JTextField(15);
        panel.add(usuarioField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        claveField = new JPasswordField(15);
        panel.add(claveField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        rolBox = new JComboBox<>(new String[] { "admin", "enfermera" });
        panel.add(rolBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        JButton registrarBtn = new JButton("✅ Crear cuenta");
        registrarBtn.setBackground(new Color(230, 220, 250));
        registrarBtn.setFocusPainted(false);
        panel.add(registrarBtn, gbc);

        registrarBtn.addActionListener(e -> {
            String usuario = usuarioField.getText().trim();
            String clave = new String(claveField.getPassword());
            String rol = rolBox.getSelectedItem().toString();

            if (usuario.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Usuario y contraseña son obligatorios.");
                return;
            }

            UsuarioDAO dao = new UsuarioDAO();
            if (dao.existeUsuario(usuario)) {
                JOptionPane.showMessageDialog(this, "⚠️ El usuario ya existe.");
                return;
            }

            boolean exito = dao.insertarUsuario(usuario, clave, rol);
            String msg = exito ? "✅ Usuario creado correctamente." : "❌ Error al crear usuario.";
            JOptionPane.showMessageDialog(this, msg);

            usuarioField.setText("");
            claveField.setText("");
            rolBox.setSelectedIndex(0);

            cargarUsuarios();
        });

        return panel;
    }

    private JScrollPane crearTabla() {
        modelo = new DefaultTableModel(new String[] { "Usuario", "Rol", "Activo", "Eliminar" }, 0) {
            public boolean isCellEditable(int row, int col) {
                return col == 3;
            }
        };

        tablaUsuarios = new JTable(modelo);
        tablaUsuarios.setRowHeight(28);
        tablaUsuarios.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaUsuarios.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaUsuarios.setBackground(new Color(255, 250, 255));
        tablaUsuarios.setGridColor(new Color(220, 210, 240));

        tablaUsuarios.getColumn("Eliminar").setCellRenderer(new ButtonRenderer("🗑️"));
        tablaUsuarios.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(), "🗑️", this::eliminarUsuario));

        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBorder(BorderFactory.createTitledBorder("Usuarios registrados"));
        return scroll;
    }

    private void cargarUsuarios() {
        modelo.setRowCount(0);
        UsuarioDAO dao = new UsuarioDAO();
        List<String[]> usuarios = dao.listarUsuarios();
        for (String[] fila : usuarios) {
            modelo.addRow(new Object[] { fila[0], fila[1], fila[2], "🗑️" });
        }
    }

    private void eliminarUsuario(int fila) {
        String usuario = (String) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar al usuario '" + usuario + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            UsuarioDAO dao = new UsuarioDAO();
            boolean exito = dao.eliminarUsuario(usuario);
            if (exito) {
                JOptionPane.showMessageDialog(this, "✅ Usuario eliminado.");
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo eliminar.");
            }
        }
    }
}