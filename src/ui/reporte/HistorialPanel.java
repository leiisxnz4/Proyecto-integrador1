package ui.reporte;

import dao.HistorialClinicoDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.HistorialClinico;

public class HistorialPanel extends JPanel {
    private final HistorialForm form;
    private final HistorialTable table;

    public HistorialPanel() {
        // 🎨 Colores personalizados
        Color fondoLila = new Color(245, 240, 255);       // Fondo general
        Color panelSuave = new Color(250, 245, 255);      // Panel flotante
        Color botonColor = new Color(230, 225, 250);
        Color bordeSuave = new Color(200, 180, 230);

        setLayout(new BorderLayout());
        setBackground(fondoLila);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // 🔝 Panel superior con botón volver y título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(fondoLila);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnVolver = new JButton("← Volver");
        btnVolver.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnVolver.setBackground(botonColor);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createLineBorder(bordeSuave));
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) {
                frame.dispose(); // ✅ Cierra la ventana flotante
            }
        });

        JLabel titulo = new JLabel("Historial Clínico", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(new Color(80, 60, 120));

        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // 🧩 Panel de contenido con formulario y tabla
        form = new HistorialForm();
        table = new HistorialTable();

        JPanel contenido = new JPanel(new BorderLayout(20, 20));
        contenido.setBackground(panelSuave);
        contenido.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 200, 240), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        contenido.add(form, BorderLayout.NORTH);
        contenido.add(table, BorderLayout.CENTER);

        add(contenido, BorderLayout.CENTER);

        // 🎯 Acción del botón buscar
        form.getBuscarBtn().addActionListener(e -> buscarHistorial());
    }

    private void buscarHistorial() {
        table.limpiarTabla();
        String matricula = form.getMatriculaField().getText().trim();

        if (matricula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingresa una matrícula válida.");
            return;
        }

        try {
            HistorialClinicoDAO dao = new HistorialClinicoDAO();
            List<HistorialClinico> lista = dao.obtenerPorMatricula(matricula);

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "📭 No se encontró historial para la matrícula: " + matricula);
            } else {
                lista.sort((a, b) -> b.getFechaLocal().compareTo(a.getFechaLocal())); // Orden descendente
                table.actualizarTabla(lista);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al cargar historial: " + ex.getMessage());
        }
    }
}