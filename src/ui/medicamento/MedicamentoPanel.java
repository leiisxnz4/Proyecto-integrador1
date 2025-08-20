package ui.medicamento;

import java.awt.*;
import javax.swing.*;

public class MedicamentoPanel extends JPanel {
    public MedicamentoPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 240, 255)); // Fondo lila claro
        setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // 🔝 Panel superior con botón de regreso y título alineado
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(new Color(245, 240, 255));
        encabezado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnRegresar = new JButton("← Regresar");
        btnRegresar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnRegresar.setBackground(new Color(230, 220, 250));
        btnRegresar.setFocusPainted(false);
        btnRegresar.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        btnRegresar.setPreferredSize(new Dimension(120, 35));
        btnRegresar.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) {
                frame.dispose();
            }
        });

        JLabel titulo = new JLabel("Registrar Medicamento", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(new Color(100, 80, 120));

        encabezado.add(btnRegresar, BorderLayout.WEST);
        encabezado.add(titulo, BorderLayout.CENTER);
        add(encabezado, BorderLayout.NORTH);

        // 🌷 Panel rosita SOLO con el formulario
        JPanel panelFormulario = new JPanel(new BorderLayout());
        panelFormulario.setBackground(new Color(250, 245, 255)); // Panel rosita
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 200, 240), 1),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        MedicamentoForm form = new MedicamentoForm();
        panelFormulario.add(form, BorderLayout.CENTER);

        // Centrar el panel rosita
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(new Color(245, 240, 255));
        centro.add(panelFormulario);
        add(centro, BorderLayout.CENTER);

        // 📋 Botón para abrir tabla flotante (fuera del panel rosita)
        JButton verTablaBtn = new JButton("📋 Ver registros");
        verTablaBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        verTablaBtn.setBackground(new Color(230, 220, 250));
        verTablaBtn.setFocusPainted(false);
        verTablaBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        verTablaBtn.setPreferredSize(new Dimension(160, 35));

        verTablaBtn.addActionListener(e -> {
            JFrame ventanaTabla = new JFrame("📋 Medicamentos Registrados");
            ventanaTabla.setSize(800, 500);
            ventanaTabla.setLocationRelativeTo(null);
            ventanaTabla.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            MedicamentoTable tabla = new MedicamentoTable();
            tabla.setFormulario(form);
            form.setTabla(tabla);

            JButton regresarBtn = new JButton("← Regresar");
            regresarBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            regresarBtn.setBackground(new Color(230, 220, 250));
            regresarBtn.setFocusPainted(false);
            regresarBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
            regresarBtn.addActionListener(ev -> ventanaTabla.dispose());

            JPanel topTabla = new JPanel(new BorderLayout());
            topTabla.setBackground(new Color(250, 245, 255));
            topTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            topTabla.add(regresarBtn, BorderLayout.WEST);

            ventanaTabla.setLayout(new BorderLayout());
            ventanaTabla.add(topTabla, BorderLayout.NORTH);
            ventanaTabla.add(tabla, BorderLayout.CENTER);
            ventanaTabla.setVisible(true);
        });

        JPanel botonPanel = new JPanel();
        botonPanel.setBackground(new Color(245, 240, 255));
        botonPanel.add(verTablaBtn);
        add(botonPanel, BorderLayout.SOUTH);
    }
}