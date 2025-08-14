package ui.estudiante;

import java.awt.*;
import javax.swing.*;

public class PanelEstudiante extends JPanel {
    private EstudianteForm form;
    private EstudianteTable table;

    public PanelEstudiante() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Título y botón de regreso
        JLabel titulo = new JLabel("Gestión de Estudiantes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnVolver = new JButton("← Volver");
        btnVolver.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) {
                frame.dispose(); // Cierra la ventana actual
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        form = new EstudianteForm(); 
        table = new EstudianteTable();

        table.setFormulario(form);
        form.setTabla(table);

        JPanel centro = new JPanel();
        centro.setLayout(new BorderLayout(10, 10));
        centro.add(form, BorderLayout.NORTH); 
        centro.add(new JScrollPane(table), BorderLayout.CENTER); 

        add(centro, BorderLayout.CENTER);

        // Refrescar tabla después de guardar o actualizar
        form.getGuardarBtn().addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                table.cargarDatos(); // Refresca automáticamente
            });
        });
    }
}