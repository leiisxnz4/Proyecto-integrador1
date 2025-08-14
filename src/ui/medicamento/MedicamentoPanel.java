package ui.medicamento;

import java.awt.*;
import javax.swing.*;

public class MedicamentoPanel extends JPanel {
    private MedicamentoForm form;
    private MedicamentoTable table;

    public MedicamentoPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 🔝 Barra superior con título y botón de regreso
        JPanel topBar = new JPanel(new BorderLayout());
        JButton btnRegresar = new JButton("← Regresar");
        JLabel titulo = new JLabel("Catálogo de Medicamentos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        topBar.add(btnRegresar, BorderLayout.WEST);
        topBar.add(titulo, BorderLayout.CENTER);

        btnRegresar.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) {
                frame.dispose();
            }
        });

        add(topBar, BorderLayout.NORTH);

        form = new MedicamentoForm();
        table = new MedicamentoTable();

        // Conexión entre componentes
        form.setTabla(table);
        table.setFormulario(form);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, form, table);
        split.setResizeWeight(0.4); 
        split.setDividerSize(8);
        add(split, BorderLayout.CENTER);

        // Refrescar tabla después de guardar o actualizar
        form.getGuardarBtn().addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                table.cargarDatos();
            });
        });
    }
}