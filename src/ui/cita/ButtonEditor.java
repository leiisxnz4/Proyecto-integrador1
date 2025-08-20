package ui.cita;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import models.CitaMedica;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final JButton editar;
    private final JButton eliminar;
    private final JTable tabla;
    private final CitaMedicaTable tablePanel;
    private int filaActual;

    public ButtonEditor(JTable tabla, CitaMedicaTable tablePanel) {
        this.tabla = tabla;
        this.tablePanel = tablePanel;

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        editar = new JButton("✏️");
        eliminar = new JButton("🗑️");

        editar.setFocusable(false);
        eliminar.setFocusable(false);

        editar.addActionListener(e -> editarCita());
        eliminar.addActionListener(e -> eliminarCita());

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

    private void editarCita() {
        fireEditingStopped();

        CitaMedica cita = tablePanel.obtenerCitaEnFila(filaActual);
        if (cita != null) {
            JFrame ventanaEdicion = new JFrame("Editar Cita");
            ventanaEdicion.setSize(600, 400);
            ventanaEdicion.setLocationRelativeTo(tabla);

            // 🎨 Fondo visual coherente
            Color fondoLila = new Color(245, 240, 255);
            Color panelSuave = new Color(250, 245, 255);

            CitaMedicaForm formulario = new CitaMedicaForm(ventanaEdicion, tablePanel);
            formulario.cargarCitaParaEdicion(cita);

            JPanel panelFlotante = new JPanel(new BorderLayout());
            panelFlotante.setBackground(panelSuave);
            panelFlotante.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
            panelFlotante.add(formulario, BorderLayout.CENTER);

            JPanel fondo = new JPanel(new BorderLayout());
            fondo.setBackground(fondoLila);
            fondo.add(panelFlotante, BorderLayout.CENTER);

            ventanaEdicion.setContentPane(fondo);
            ventanaEdicion.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(tabla, "❌ No se pudo cargar la cita para edición.");
        }
    }

    private void eliminarCita() {
        fireEditingStopped();

        int confirm = JOptionPane.showConfirmDialog(tabla, "¿Eliminar esta cita?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tablePanel.eliminarCita(filaActual);
        }
    }
}