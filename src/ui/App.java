package ui;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import ui.cita.CitaMedicaPanel;
import ui.consulta.PanelConsultaMedica;
import ui.estudiante.PanelEstudiante;
import ui.login.LoginWindow;
import ui.login.RegistroUsuarioPanel;
import ui.medicamento.MedicamentoPanel;
import ui.recomendacion.RecomendacionMedicaPanel;
import ui.reporte.HistorialPanel;
import util.VentanaModulo;

public class App extends JFrame {

    private final String nombreUsuario;
    private final String rolUsuario;

    public App(String nombreUsuario, String rolUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.rolUsuario = rolUsuario;
        initUI();
    }

    private void initUI() {
        configurarVentana();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 240, 250));
        setContentPane(mainPanel);

        JLabel titulo = new JLabel("Sistema de enfermería UTT", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        mainPanel.add(titulo, BorderLayout.NORTH);

        JPanel iconPanel = crearPanelDeIconos();
        mainPanel.add(iconPanel, BorderLayout.CENTER);

        JPanel barraInferior = new JPanel(new BorderLayout());
        barraInferior.setBackground(new Color(220, 210, 240));
        barraInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel usuarioLabel = new JLabel("Usuario: " + nombreUsuario + " (" + rolUsuario + ")");
        JLabel fechaLabel = new JLabel(LocalDate.now().toString());

        JButton btnSalir = new JButton("Salir del sistema");
        btnSalir.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnSalir.setBackground(new Color(230, 220, 250));
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        btnSalir.addActionListener(e -> {
            dispose();
            new LoginWindow().setVisible(true);
        });

        barraInferior.add(usuarioLabel, BorderLayout.WEST);
        barraInferior.add(fechaLabel, BorderLayout.EAST);
        barraInferior.add(btnSalir, BorderLayout.CENTER);

        mainPanel.add(barraInferior, BorderLayout.SOUTH);
    }

    private void configurarVentana() {
        setTitle("Sistema de enfermería UTT");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel crearPanelDeIconos() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        panel.setBackground(new Color(245, 240, 250));

        panel.add(crearBoton("Citas", "Icons/citas.png", new CitaMedicaPanel()));
        panel.add(crearBoton("Consulta", "Icons/consulta.png", new PanelConsultaMedica()));
        panel.add(crearBoton("Medicamentos", "Icons/medicamentos.png", new MedicamentoPanel()));
        panel.add(crearBoton("Recomendaciones", "Icons/recomendaciones.png", new RecomendacionMedicaPanel()));
        panel.add(crearBoton("Historial", "Icons/historial.png", new HistorialPanel()));
        panel.add(crearBoton("Estudiantes", "Icons/estudiantes.png", new PanelEstudiante()));

        // Solo mostrar si el usuario es admin
        if (rolUsuario.equalsIgnoreCase("admin")) {
            JButton btnRegistrar = crearBoton("Registrar Usuario", "Icons/registro.png", new RegistroUsuarioPanel());
            panel.add(btnRegistrar);
        }

        return panel;
    }

    private JButton crearBoton(String texto, String rutaIcono, JPanel panelContenido) {
        ImageIcon icon = new ImageIcon(rutaIcono);
        Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JButton boton = new JButton(texto, new ImageIcon(img));
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setFocusPainted(false);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder());

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(230, 230, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.WHITE);
            }
        });

        boton.addActionListener(e -> new VentanaModulo(texto, panelContenido));

        return boton;
    }
}