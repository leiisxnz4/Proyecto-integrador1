package ui.recomendacion;

import java.awt.*;
import javax.swing.*;

public class RecomendacionMedicaPanel extends JPanel {

    public RecomendacionMedicaPanel() {
        // 🎨 Colores personalizados
        Color fondoLila = new Color(245, 240, 255);       // Fondo general
        Color panelSuave = new Color(250, 245, 255);      // Panel flotante

        setLayout(new BorderLayout());
        setBackground(fondoLila);

        // 🔝 Panel superior con botón regresar
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondoLila);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 40));

        JButton btnRegresar = new JButton("← Regresar");
        btnRegresar.setFocusPainted(false);
        btnRegresar.setBackground(new Color(230, 225, 250));
        btnRegresar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        // ✅ Acción corregida para cerrar la ventana flotante
        btnRegresar.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(RecomendacionMedicaPanel.this);
            if (ventana instanceof JFrame frame) {
                frame.dispose(); // ✅ Cierra la ventana flotante
            }
        });

        panelSuperior.add(btnRegresar, BorderLayout.WEST);

        // 🏷️ Título centrado
        JLabel titulo = new JLabel("Gestión de Recomendaciones Médicas", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(new Color(80, 60, 120));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(fondoLila);
        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.setPreferredSize(new Dimension(800, 60));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // 🧩 Formulario flotante
        RecomendacionMedicaForm formulario = new RecomendacionMedicaForm();
        JPanel panelFlotante = new JPanel(new BorderLayout());
        panelFlotante.setPreferredSize(new Dimension(800, 460));
        panelFlotante.setBackground(panelSuave);
        panelFlotante.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        panelFlotante.add(formulario, BorderLayout.CENTER);

        // 📋 Botón para ver registros
        JButton btnVerRegistros = new JButton("📋 Ver registros");
        btnVerRegistros.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnVerRegistros.setFocusPainted(false);
        btnVerRegistros.setBackground(new Color(230, 225, 250));
        btnVerRegistros.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        btnVerRegistros.addActionListener(e -> {
            JFrame ventanaRegistros = new JFrame("Registros de Recomendaciones");
            ventanaRegistros.setSize(900, 600);
            ventanaRegistros.setLocationRelativeTo(null);
            ventanaRegistros.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            RecomendacionMedicaTable tabla = new RecomendacionMedicaTable(formulario);
            ventanaRegistros.setContentPane(tabla);
            ventanaRegistros.setVisible(true);
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(fondoLila);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelBoton.add(btnVerRegistros);

        // 🧩 Ensamblado vertical
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(fondoLila);
        centro.add(panelTitulo);
        centro.add(panelFlotante);
        centro.add(Box.createVerticalStrut(20));
        centro.add(panelBoton);

        add(panelSuperior, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }
}