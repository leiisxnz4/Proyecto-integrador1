package ui;

import java.awt.*;
import javax.swing.*;
import ui.cita.CitaMedicaPanel;
import ui.consulta.PanelConsultaMedica;
import ui.login.LoginWindow;
import ui.medicamento.MedicamentoPanel;
import ui.recomendacion.RecomendacionMedicaPanel;
import ui.reporte.HistorialPanel;

/**
 * Menú principal del sistema, con navegación entre módulos.
 */
public class MainMenu extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private String nombreUsuario;

    /**
     * Constructor que recibe el nombre del usuario.
     */
    public MainMenu(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;

        setTitle("Sistema de enfermería UTT");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(crearMenuPrincipal(), "menu");
        contentPanel.add(crearPanelConVolver(new CitaMedicaPanel()), "citas");
        contentPanel.add(crearPanelConVolver(new PanelConsultaMedica()), "consultas");
        contentPanel.add(crearPanelConVolver(new MedicamentoPanel()), "medicamentos");
        contentPanel.add(crearPanelConVolver(new HistorialPanel()), "reportes");
        contentPanel.add(crearPanelConVolver(new RecomendacionMedicaPanel()), "recomendaciones");
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

        JLabel bienvenida = new JLabel("¡Bienvenida, " + nombreUsuario + "!", JLabel.CENTER);
        bienvenida.setFont(new Font("SansSerif", Font.PLAIN, 16));
        bienvenida.setForeground(new Color(100, 80, 140));
        bienvenida.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel encabezado = new JPanel(new GridLayout(2, 1));
        encabezado.setBackground(new Color(245, 240, 250));
        encabezado.add(titulo);
        encabezado.add(bienvenida);

        mainPanel.add(encabezado, BorderLayout.NORTH);

        JPanel iconPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        iconPanel.setBackground(new Color(245, 240, 250));
        iconPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 20, 100));

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

        JButton btnSalir = new JButton("Salir del sistema");
        btnSalir.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnSalir.setBackground(new Color(230, 220, 250));
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        btnSalir.addActionListener(e -> {
            dispose();
            new LoginWindow().setVisible(true);
        });

        JPanel salirPanel = new JPanel();
        salirPanel.setBackground(new Color(245, 240, 250));
        salirPanel.add(btnSalir);

        mainPanel.add(salirPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JButton crearBoton(String texto, String rutaIcono) {
        ImageIcon icon = new ImageIcon(rutaIcono);
        Image img = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        JButton btn = new JButton(texto, new ImageIcon(img));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
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
}