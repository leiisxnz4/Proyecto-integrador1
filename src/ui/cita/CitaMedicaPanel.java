package ui.cita;

import java.awt.*;
import javax.swing.*;

public class CitaMedicaPanel extends JPanel {

    public CitaMedicaPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 🔝 Barra superior con botón y título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        JButton btnRegresar = new JButton("← Regresar");
        JLabel titulo = new JLabel("Gestión de Citas Médicas", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        btnRegresar.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) {
                frame.dispose(); // Cierra la ventana actual
            }
        });

        panelSuperior.add(btnRegresar, BorderLayout.WEST);
        panelSuperior.add(titulo, BorderLayout.CENTER);
        
        CitaMedicaTable tabla = new CitaMedicaTable();
        JSplitPane split = new JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            new CitaMedicaForm(null, tabla),
            tabla
        );
        split.setResizeWeight(0.4);
        split.setDividerSize(8);

        add(panelSuperior, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
    }
}