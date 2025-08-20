package ui.medicamento;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import models.Medicamento;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton editarBtn;
    private JButton eliminarBtn;
    private MedicamentoTable table;
    private int fila;

    public ButtonEditor(JCheckBox checkBox, MedicamentoTable table) {
        this.table = table;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setBackground(new Color(250, 245, 255)); // Fondo rosita suave

        editarBtn = crearBoton("✏️");
        eliminarBtn = crearBoton("🗑️");

        editarBtn.addActionListener(e -> {
            Medicamento m = table.obtenerMedicamentoEnFila(fila);

            JDialog ventanaEdicion = new JDialog((Frame) SwingUtilities.getWindowAncestor(table), "✏️ Editar Medicamento", true);
            ventanaEdicion.setSize(500, 420);
            ventanaEdicion.setLocationRelativeTo(null);
            ventanaEdicion.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            MedicamentoForm formEdicion = new MedicamentoForm();
            formEdicion.setVentana(ventanaEdicion); // ✅ Conecta la ventana para que se pueda cerrar
            formEdicion.llenarFormularioParaEditar(m);
            formEdicion.setTabla(table);

            // Título centrado
            JLabel titulo = new JLabel("Editar Medicamento", SwingConstants.CENTER);
            titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
            titulo.setForeground(new Color(100, 80, 120));

            JPanel tituloPanel = new JPanel(new BorderLayout());
            tituloPanel.setBackground(new Color(250, 245, 255));
            tituloPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            tituloPanel.add(titulo, BorderLayout.CENTER);

            // Panel rosita con formulario
            JPanel panelFormulario = new JPanel(new BorderLayout());
            panelFormulario.setBackground(new Color(250, 245, 255));
            panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 200, 240), 1),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
            ));
            panelFormulario.add(tituloPanel, BorderLayout.NORTH);
            panelFormulario.add(formEdicion, BorderLayout.CENTER);

            // Centrar el panel rosita
            JPanel centro = new JPanel(new GridBagLayout());
            centro.setBackground(new Color(245, 240, 255));
            centro.add(panelFormulario);

            ventanaEdicion.setLayout(new BorderLayout());
            ventanaEdicion.add(centro, BorderLayout.CENTER);
            ventanaEdicion.setVisible(true);

            fireEditingStopped(); // ✅ Detiene la edición de celda
        });

        eliminarBtn.addActionListener(e -> {
            table.eliminarMedicamento(fila);
            fireEditingStopped();
        });

        panel.add(editarBtn);
        panel.add(eliminarBtn);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(230, 220, 250));
        btn.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        btn.setPreferredSize(new Dimension(40, 30));
        return btn;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
        this.fila = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}