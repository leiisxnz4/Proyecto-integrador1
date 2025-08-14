package ui.recomendacion;

import dao.RecomendacionMedicaDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import models.RecomendacionMedica;

public class RecomendacionMedicaTable extends JPanel {
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final RecomendacionMedicaForm formulario;

    public RecomendacionMedicaTable(RecomendacionMedicaForm formulario) {
        this.formulario = formulario;
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Matrícula", "Fecha", "Recomendación", "Acciones"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(32);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));

        tabla.getColumn("Acciones").setCellRenderer(new AccionRenderer());
        tabla.getColumn("Acciones").setCellEditor(new AccionEditor());

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        cargarDatos();
    }

    public void cargarDatos() {
        modelo.setRowCount(0);
        try {
            List<RecomendacionMedica> lista = new RecomendacionMedicaDAO().listar();
            for (RecomendacionMedica r : lista) {
                modelo.addRow(new Object[]{
                    r.getId(),
                    r.getMatricula(),
                    r.getFecha(),
                    r.getTexto(),
                    "Acciones"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al cargar recomendaciones: " + e.getMessage());
        }
    }

    private static class AccionRenderer extends JPanel implements TableCellRenderer {
        public AccionRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            JButton editar = new JButton("✏️");
            JButton eliminar = new JButton("🗑️");
            editar.setFocusable(false);
            eliminar.setFocusable(false);
            add(editar);
            add(eliminar);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }

    private class AccionEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton editar;
        private final JButton eliminar;
        private int filaActual;

        public AccionEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            editar = new JButton("✏️");
            eliminar = new JButton("🗑️");

            editar.addActionListener(e -> editarFila(filaActual));
            eliminar.addActionListener(e -> eliminarFila(filaActual));

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

        private void editarFila(int fila) {
            try {
                int id = (int) modelo.getValueAt(fila, 0);
                RecomendacionMedica r = new RecomendacionMedicaDAO().buscarPorId(id);

                if (r == null) {
                    JOptionPane.showMessageDialog(tabla, "❌ No se encontró la recomendación.");
                    return;
                }

                formulario.cargarRecomendacion(r); // Carga en el formulario visible

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(tabla, "❌ Error al editar: " + ex.getMessage());
            }

            stopCellEditing();
        }

        private void eliminarFila(int fila) {
            int id = (int) modelo.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(
                tabla,
                "¿Eliminar esta recomendación?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    new RecomendacionMedicaDAO().eliminar(id);
                    cargarDatos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(tabla, "❌ Error al eliminar: " + ex.getMessage());
                }
            }

            stopCellEditing();
        }
    }
}