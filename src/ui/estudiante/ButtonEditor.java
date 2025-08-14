package ui.estudiante;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import models.Estudiante;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton editarBtn;
    private JButton eliminarBtn;
    private EstudianteTable table;
    private int fila;

    public ButtonEditor(JCheckBox checkBox, EstudianteTable table) {
        this.table = table;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

        editarBtn = new JButton("✏️");
        eliminarBtn = new JButton("🗑️");

        editarBtn.addActionListener(e -> {
            Estudiante est = table.obtenerEstudianteEnFila(fila);
            table.getForm().llenarFormularioParaEditar(est);
            fireEditingStopped(); // Detiene la edición de celda
        });

        eliminarBtn.addActionListener(e -> {
            try {
                table.eliminarEstudiante(fila);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel,
                    "❌ Error al eliminar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            fireEditingStopped(); // Detiene la edición de celda
        });

        panel.add(editarBtn);
        panel.add(eliminarBtn);
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