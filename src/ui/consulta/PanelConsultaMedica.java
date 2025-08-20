package ui.consulta;

import java.awt.*;
import javax.swing.*;

public class PanelConsultaMedica extends JPanel {
    private FormConsultaMedica form;
    private TableConsultaMedica table;

    public PanelConsultaMedica() {
        // 🎨 Colores personalizados
        Color fondoLila = new Color(245, 240, 255);
        Color panelRosita = new Color(250, 245, 255);

        setLayout(new BorderLayout());
        setBackground(fondoLila);

        // 🔝 Panel superior con botón volver
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondoLila);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 40));

        JButton btnVolver = new JButton("← Regresar");
        btnVolver.setFocusPainted(false);
        btnVolver.setBackground(new Color(230, 225, 250));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnVolver.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) {
                frame.dispose();
            }
        });
        panelSuperior.add(btnVolver, BorderLayout.WEST);

        // 🧩 Crear formulario y tabla con conexión mutua
        JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(this);
        table = new TableConsultaMedica(ventanaActual);
        form = new FormConsultaMedica();
        form.setTabla(table);
        table.setFormulario(form);

        // 🎯 Fondo central con título y panel flotante
        JPanel fondoCentral = new JPanel(new GridBagLayout());
        fondoCentral.setBackground(fondoLila);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 20, 10, 20);

        // 🏷️ Título centrado
        JLabel titulo = new JLabel("Consultas Médicas");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(fondoLila);
        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.setPreferredSize(new Dimension(800, 60));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        fondoCentral.add(panelTitulo, gbc);

        // 🧩 Panel flotante rosita con formulario dentro
        JPanel panelFlotante = new JPanel(new BorderLayout());
        panelFlotante.setPreferredSize(new Dimension(720, 520));
        panelFlotante.setBackground(panelRosita);
        panelFlotante.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        panelFlotante.add(form, BorderLayout.CENTER);

        gbc.gridy = 1;
        fondoCentral.add(panelFlotante, gbc);

        // 📋 Botón para ver registros
        JButton btnVerRegistros = new JButton("📋 Ver registros");
        btnVerRegistros.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnVerRegistros.setFocusPainted(false);
        btnVerRegistros.setBackground(new Color(230, 225, 250));
        btnVerRegistros.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        btnVerRegistros.addActionListener(e -> {
            JFrame ventanaRegistros = new JFrame("Registros de Consultas");
            ventanaRegistros.setSize(900, 600);
            ventanaRegistros.setLocationRelativeTo(null);

            TableConsultaMedica tablaConDiseño = new TableConsultaMedica(ventanaRegistros);
            tablaConDiseño.setFormulario(form);

            ventanaRegistros.setContentPane(tablaConDiseño);
            ventanaRegistros.setVisible(true);
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(fondoLila);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelBoton.add(btnVerRegistros);

        // 🧩 Ensamblar todo
        add(panelSuperior, BorderLayout.NORTH);
        add(fondoCentral, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        table.cargarDatos(); // Carga inicial
    }
}