package ui.consulta;

import java.awt.*;
import javax.swing.*;

public class PanelConsultaMedica extends JPanel {
    private FormConsultaMedica form;
    private TableConsultaMedica table;

    public PanelConsultaMedica() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        form = new FormConsultaMedica();
        table = new TableConsultaMedica();

        // Conexión entre componentes
        form.setTabla(table);
        table.setFormulario(form);

        // Panel superior con título y botón de regreso
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Consultas Médicas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titulo, BorderLayout.CENTER);

        JButton btnVolver = new JButton("← Volver");
        btnVolver.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) {
                frame.dispose(); // Cierra la ventana actual
            }
        });
        topPanel.add(btnVolver, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, form, table);
        split.setResizeWeight(0.4);
        split.setDividerSize(8);
        add(split, BorderLayout.CENTER);

        table.cargarDatos(); // Carga inicial
    }
}