package ui.medicamento;

import java.awt.*;
import java.awt.event.*;
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

        editarBtn = new JButton("✏️");
        eliminarBtn = new JButton("🗑️");

        editarBtn.addActionListener(e -> {
            Medicamento m = table.obtenerMedicamentoEnFila(fila);
            table.getFormulario().llenarFormularioParaEditar(m);
            fireEditingStopped();
        });

        eliminarBtn.addActionListener(e -> {
            table.eliminarMedicamento(fila);
            fireEditingStopped();
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