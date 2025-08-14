package ui.consulta;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import models.ConsultaMedica;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton editarBtn;
    private JButton eliminarBtn;
    private TableConsultaMedica tabla;
    private int fila;

    public ButtonEditor(JCheckBox checkBox, TableConsultaMedica tabla) {
        this.tabla = tabla;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

        editarBtn = new JButton("✏️");
        eliminarBtn = new JButton("🗑️");

        editarBtn.addActionListener(e -> {
            ConsultaMedica c = tabla.obtenerConsultaEnFila(fila);
            tabla.getFormulario().llenarFormularioParaEditar(c);
            fireEditingStopped();
        });

        eliminarBtn.addActionListener(e -> {
            tabla.eliminarConsulta(fila);
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