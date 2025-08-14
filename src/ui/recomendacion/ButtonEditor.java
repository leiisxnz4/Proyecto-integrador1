package ui.recomendacion;

import dao.RecomendacionMedicaDAO;
import models.RecomendacionMedica;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.time.LocalDate;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final JButton editar;
    private final JButton eliminar;
    private final JTable tabla;
    private int filaActual;

    public ButtonEditor(JTable tabla) {
        this.tabla = tabla;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        editar = new JButton("✏️");
        eliminar = new JButton("🗑️");

        editar.addActionListener(e -> editarFila());
        eliminar.addActionListener(e -> eliminarFila());

        panel.add(editar);
        panel.add(eliminar);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        filaActual = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "Acciones";
    }

    private void editarFila() {
        try {
            int id = (int) tabla.getValueAt(filaActual, 0);
            String matricula = (String) tabla.getValueAt(filaActual, 1);
            String fechaStr = tabla.getValueAt(filaActual, 2).toString();
            String texto = (String) tabla.getValueAt(filaActual, 3);

            String nuevoTexto = JOptionPane.showInputDialog(
                tabla,
                "Editar recomendación:",
                texto
            );

            if (nuevoTexto != null && !nuevoTexto.trim().isEmpty()) {
                LocalDate fecha = LocalDate.parse(fechaStr);
                RecomendacionMedica r = new RecomendacionMedica(id, matricula, fecha, nuevoTexto.trim());
                new RecomendacionMedicaDAO().actualizar(r);

                // Recargar tabla
                Component contenedor = SwingUtilities.getAncestorOfClass(RecomendacionMedicaTable.class, tabla);
                if (contenedor instanceof RecomendacionMedicaTable tablePanel) {
                    tablePanel.cargarDatos();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(tabla, "❌ Error al editar: " + ex.getMessage());
        }
        stopCellEditing();
    }

    private void eliminarFila() {
        try {
            int id = (int) tabla.getValueAt(filaActual, 0);
            int confirm = JOptionPane.showConfirmDialog(
                tabla,
                "¿Eliminar esta recomendación?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                new RecomendacionMedicaDAO().eliminar(id);

                // Recargar tabla
                Component contenedor = SwingUtilities.getAncestorOfClass(RecomendacionMedicaTable.class, tabla);
                if (contenedor instanceof RecomendacionMedicaTable tablePanel) {
                    tablePanel.cargarDatos();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(tabla, "❌ Error al eliminar: " + ex.getMessage());
        }
        stopCellEditing();
    }
}