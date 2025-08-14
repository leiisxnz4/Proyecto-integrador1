package ui.recomendacion;

import java.awt.*;
import javax.swing.*;

public class PanelRecomendacionMedica extends JPanel {
    private final RecomendacionMedicaForm form;
    private final RecomendacionMedicaTable table;

    public PanelRecomendacionMedica() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Recomendaciones Médicas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnVolver = new JButton("← Volver");
        btnVolver.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) {
                frame.dispose();
            }
        });

        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        form = new RecomendacionMedicaForm();
        table = new RecomendacionMedicaTable(form); 

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.add(form);
        centro.add(Box.createVerticalStrut(20)); 
        centro.add(table);

        add(centro, BorderLayout.CENTER);

        // Acción del botón guardar
        form.getGuardarButton().addActionListener(e -> {
            boolean guardado = form.guardarRecomendacion();
            if (guardado) {
                table.cargarDatos();
            }
        });
    }
}