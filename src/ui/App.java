package ui;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import ui.cita.CitaMedicaPanel;
import ui.consulta.PanelConsultaMedica;
import ui.estudiante.PanelEstudiante;
import ui.medicamento.MedicamentoPanel;
import ui.recomendacion.PanelRecomendacionMedica;
import ui.reporte.HistorialPanel;
import util.VentanaModulo;

public class App extends JFrame {

    public App() {
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
        barraInferior.add(new JLabel("Usuario: Enfermera"), BorderLayout.WEST);
        barraInferior.add(new JLabel(LocalDate.now().toString()), BorderLayout.EAST);
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
        panel.add(crearBoton("Recomendaciones", "Icons/recomendaciones.png", new PanelRecomendacionMedica()));
        panel.add(crearBoton("Historial", "Icons/historial.png", new HistorialPanel()));
        panel.add(crearBoton("Estudiantes", "Icons/estudiantes.png", new PanelEstudiante()));

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

        // Hover visual
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(230, 230, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.WHITE);
            }
        });

        // Abrir ventana
        boton.addActionListener(e -> new VentanaModulo(texto, panelContenido));

        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}