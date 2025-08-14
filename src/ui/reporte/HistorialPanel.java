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
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Historial Clínico", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnVolver = new JButton("← Volver");
        btnVolver.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) {
                frame.dispose(); // Cierra la ventana actual
            }
        });

        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Formulario y tabla
        form = new HistorialForm(); // Usa matrícula
        table = new HistorialTable(); // Muestra íconos y datos

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.add(form, BorderLayout.NORTH);
        contenido.add(table, BorderLayout.CENTER);

        add(contenido, BorderLayout.CENTER);

        // Acción del botón buscar
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