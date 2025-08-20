package ui.estudiante;

import java.awt.*;
import javax.swing.*;

public class PanelEstudiante extends JPanel {
    private EstudianteForm form;

    public PanelEstudiante() {
        Color fondoLila = new Color(245, 240, 255);
        Color panelRosita = new Color(250, 245, 255);

        setLayout(new BorderLayout());
        setBackground(fondoLila);

        JLabel titulo = new JLabel("Gestión de Estudiantes", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton btnVolver = new JButton("← Regresar");
        btnVolver.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnVolver.setFocusPainted(false);
        btnVolver.setBackground(new Color(230, 225, 250));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) frame.dispose();
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(fondoLila);
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 40));
        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);

        form = new EstudianteForm();

        JButton btnVerTabla = new JButton("📋 Ver registros");
        btnVerTabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnVerTabla.setFocusPainted(false);
        btnVerTabla.setBackground(new Color(230, 225, 250));
        btnVerTabla.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnVerTabla.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVerTabla.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVerTabla.addActionListener(e -> {
            EstudianteTable tablaEmergente = new EstudianteTable();
            tablaEmergente.setFormulario(form);
            form.setTabla(tablaEmergente);

            // ✅ Refrescar tabla después de guardar
            form.getBtnGuardar().addActionListener(ev -> {
                SwingUtilities.invokeLater(() -> {
                    if (form.getTabla() != null) {
                        form.getTabla().cargarDatos();
                    }
                });
            });

            JFrame ventana = new JFrame("Registros de Estudiantes");
            ventana.setSize(900, 600);
            ventana.setLocationRelativeTo(null);
            ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            Color fondoEmergente = new Color(245, 240, 255);
            Color panelEmergente = new Color(250, 245, 255);

            JButton btnRegresar = new JButton("← Regresar");
            btnRegresar.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btnRegresar.setFocusPainted(false);
            btnRegresar.setBackground(new Color(230, 225, 250));
            btnRegresar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnRegresar.addActionListener(ev -> ventana.dispose());

            JPanel encabezado = new JPanel(new BorderLayout());
            encabezado.setBackground(fondoEmergente);
            encabezado.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            encabezado.add(btnRegresar, BorderLayout.WEST);

            JPanel fondo = new JPanel(new GridBagLayout());
            fondo.setBackground(fondoEmergente);

            JPanel panelFlotante = new JPanel(new BorderLayout());
            panelFlotante.setPreferredSize(new Dimension(850, 500));
            panelFlotante.setBackground(panelEmergente);
            panelFlotante.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

            panelFlotante.add(encabezado, BorderLayout.NORTH);
            panelFlotante.add(tablaEmergente, BorderLayout.CENTER);

            fondo.add(panelFlotante);
            ventana.setContentPane(fondo);
            ventana.setVisible(true);
        });

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(fondoLila);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 10, 20);

        JPanel panelFlotante = new JPanel();
        panelFlotante.setLayout(new BoxLayout(panelFlotante, BoxLayout.Y_AXIS));
        panelFlotante.setPreferredSize(new Dimension(900, 700));
        panelFlotante.setBackground(panelRosita);
        panelFlotante.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        panelFlotante.add(form);
        panelFlotante.add(Box.createVerticalStrut(20));
        panelFlotante.add(btnVerTabla);

        centro.add(panelFlotante, gbc);

        add(topPanel, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }
}