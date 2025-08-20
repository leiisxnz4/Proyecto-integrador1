package ui.login;

import java.awt.*;
import javax.swing.*;

/**
 * Panel visual para el inicio de sesión.
 */
public class LoginPanel extends JPanel {

    private final JTextField usuarioField;
    private final JPasswordField claveField;
    private final JButton accederBtn;

    public LoginPanel() {
        // 🎨 Colores personalizados
        Color fondoLila = new Color(245, 240, 255);
        Color panelSuave = new Color(250, 245, 255);
        Color bordeLila = new Color(200, 180, 230);
        Color botonColor = new Color(230, 220, 250);

        setLayout(new GridBagLayout());
        setBackground(fondoLila);

        // 🧩 Panel interno con campos
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(panelSuave);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // 🏷️ Título
        JLabel titulo = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(new Color(80, 60, 120));
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // 👤 Campo usuario
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        usuarioField = new JTextField(15);
        usuarioField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usuarioField.setBackground(Color.WHITE);
        usuarioField.setBorder(BorderFactory.createLineBorder(bordeLila));
        panel.add(usuarioField, gbc);

        // 🔒 Campo contraseña
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        claveField = new JPasswordField(15);
        claveField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        claveField.setBackground(Color.WHITE);
        claveField.setBorder(BorderFactory.createLineBorder(bordeLila));
        panel.add(claveField, gbc);

        // ✅ Botón acceder
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        accederBtn = new JButton("🔐 Acceder");
        accederBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        accederBtn.setBackground(botonColor);
        accederBtn.setFocusPainted(false);
        accederBtn.setBorder(BorderFactory.createLineBorder(bordeLila));
        panel.add(accederBtn, gbc);

        add(panel);
    }

    // 🔧 Getters para que LoginWindow acceda a los componentes
    public JTextField getUsuarioField() {
        return usuarioField;
    }

    public JPasswordField getClaveField() {
        return claveField;
    }

    public JButton getAccederBtn() {
        return accederBtn;
    }
}