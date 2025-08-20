package ui.reporte;

import java.awt.*;
import javax.swing.*;

public class HistorialForm extends JPanel {
    private final JTextField matriculaField;
    private final JButton buscarBtn;

    public HistorialForm() {
        // 🎨 Colores personalizados
        Color fondoSuave = new Color(250, 245, 255);      // Fondo rosita
        Color bordeSuave = new Color(200, 180, 230);      // Bordes lilas
        Color botonColor = new Color(230, 220, 250);      // Botón pastel

        setLayout(new GridBagLayout());
        setBackground(fondoSuave);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel labelMatricula = new JLabel("Matrícula:");
        labelMatricula.setFont(new Font("SansSerif", Font.PLAIN, 14));
        labelMatricula.setForeground(new Color(80, 60, 120));
        add(labelMatricula, gbc);

        gbc.gridx++;
        matriculaField = new JTextField(15);
        matriculaField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        matriculaField.setPreferredSize(new Dimension(200, 30));
        matriculaField.setBackground(Color.WHITE);
        matriculaField.setBorder(BorderFactory.createLineBorder(bordeSuave));
        add(matriculaField, gbc);

        gbc.gridx++;
        buscarBtn = new JButton("🔍 Buscar Historial");
        buscarBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        buscarBtn.setBackground(botonColor);
        buscarBtn.setFocusPainted(false);
        buscarBtn.setBorder(BorderFactory.createLineBorder(bordeSuave));
        buscarBtn.setPreferredSize(new Dimension(160, 35));
        add(buscarBtn, gbc);
    }

    public JTextField getMatriculaField() {
        return matriculaField;
    }

    public JButton getBuscarBtn() {
        return buscarBtn;
    }
}