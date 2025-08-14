package ui;

import java.awt.*;
import javax.swing.*;
import ui.cita.CitaMedicaPanel;
import ui.consulta.PanelConsultaMedica;
import ui.medicamento.MedicamentoPanel;
import ui.recomendacion.PanelRecomendacionMedica;
import ui.reporte.HistorialPanel;

public class MainMenu extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainMenu() {
        setTitle("Sistema de enfermería UTT");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Agregar las "pantallas"
        contentPanel.add(crearMenuPrincipal(), "menu");
        contentPanel.add(crearPanelConVolver(new CitaMedicaPanel()), "citas");
        contentPanel.add(crearPanelConVolver(new PanelConsultaMedica()), "consultas");
        contentPanel.add(crearPanelConVolver(new MedicamentoPanel()), "medicamentos");
        contentPanel.add(crearPanelConVolver(new HistorialPanel()), "reportes");
        contentPanel.add(crearPanelConVolver(new PanelRecomendacionMedica()), "recomendaciones");
        contentPanel.add(crearPanelConVolver(new HistorialPanel()), "historial");

        setContentPane(contentPanel);
        cardLayout.show(contentPanel, "menu");
    }

    private JPanel crearMenuPrincipal() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 240, 250));

        JLabel titulo = new JLabel("Sistema de enfermería UTT", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(64, 0, 128));
        mainPanel.add(titulo, BorderLayout.NORTH);

        JPanel iconPanel = new JPanel(new GridLayout(2, 3, 30, 30));
        iconPanel.setBackground(new Color(245, 240, 250));
        iconPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JButton btnCitas = crearBoton("Citas", "Icons/citas.png");
        JButton btnConsulta = crearBoton("Consulta", "Icons/consulta.png");
        JButton btnMedicamentos = crearBoton("Medicamentos", "Icons/medicamentos.png");
        JButton btnReportes = crearBoton("Reportes", "Icons/reportes.png");
        JButton btnRecomendaciones = crearBoton("Recomendaciones", "Icons/recomendaciones.png");
        JButton btnHistorial = crearBoton("Historial", "Icons/historial.png");

        btnCitas.addActionListener(e -> cardLayout.show(contentPanel, "citas"));
        btnConsulta.addActionListener(e -> cardLayout.show(contentPanel, "consultas"));
        btnMedicamentos.addActionListener(e -> cardLayout.show(contentPanel, "medicamentos"));
        btnReportes.addActionListener(e -> cardLayout.show(contentPanel, "reportes"));
        btnRecomendaciones.addActionListener(e -> cardLayout.show(contentPanel, "recomendaciones"));
        btnHistorial.addActionListener(e -> cardLayout.show(contentPanel, "historial"));

        iconPanel.add(btnCitas);
        iconPanel.add(btnConsulta);
        iconPanel.add(btnMedicamentos);
        iconPanel.add(btnReportes);
        iconPanel.add(btnRecomendaciones);
        iconPanel.add(btnHistorial);

        mainPanel.add(iconPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JButton crearBoton(String texto, String rutaIcono) {
        ImageIcon icon = new ImageIcon(rutaIcono);
        Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JButton btn = new JButton(texto, new ImageIcon(img));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        return btn;
    }

    private JPanel crearPanelConVolver(JPanel panelContenido) {
        JPanel panel = new JPanel(new BorderLayout());

        JButton btnVolver = new JButton("← Volver al menú");
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "menu"));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(btnVolver, BorderLayout.WEST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(panelContenido, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}
