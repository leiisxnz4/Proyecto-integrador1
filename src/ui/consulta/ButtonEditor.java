package ui.consulta;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import models.ConsultaMedica;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final JButton editarBtn;
    private final JButton eliminarBtn;
    private final TableConsultaMedica tabla;
    private int fila;

    public ButtonEditor(JTable tablaVisual, TableConsultaMedica tabla) {
        this.tabla = tabla;

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        panel.setBackground(Color.WHITE);

        editarBtn = new JButton("✏️");
        eliminarBtn = new JButton("🗑️");

        for (JButton btn : new JButton[]{editarBtn, eliminarBtn}) {
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        editarBtn.addActionListener(e -> {
            ConsultaMedica consulta = tabla.obtenerConsultaEnFila(fila);
            if (consulta != null) {
                FormConsultaMedica form = new FormConsultaMedica();
                form.setTabla(tabla);
                form.llenarFormularioParaEditar(consulta);

                JDialog ventana = new JDialog((Frame) SwingUtilities.getWindowAncestor(tablaVisual), "Editar Consulta Médica", true);
                ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                ventana.setLayout(new GridBagLayout());

                Color fondoLila = new Color(245, 240, 255);
                Color panelRosita = new Color(250, 245, 255);

                JPanel fondo = new JPanel(new GridBagLayout());
                fondo.setBackground(fondoLila);

                JPanel panelFlotante = new JPanel(new BorderLayout());
                panelFlotante.setPreferredSize(new Dimension(700, 500));
                panelFlotante.setBackground(panelRosita);
                panelFlotante.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
                panelFlotante.add(form, BorderLayout.CENTER);

                fondo.add(panelFlotante);
                ventana.setContentPane(fondo);
                ventana.pack();
                ventana.setLocationRelativeTo(tablaVisual);

                for (ActionListener al : form.getBtnGuardar().getActionListeners()) {
                    form.getBtnGuardar().removeActionListener(al);
                }

                form.getBtnGuardar().addActionListener(ev -> {
                    form.guardarOActualizar(); // Ejecuta la actualización
                    ventana.dispose();         // Cierra la ventana
                    tabla.cargarDatos();       // Refresca la tabla
                    JOptionPane.showMessageDialog(tablaVisual,
                        "✅ Consulta médica actualizada correctamente.",
                        "Actualización exitosa", JOptionPane.INFORMATION_MESSAGE);
                });

                ventana.setVisible(true);
            }
            fireEditingStopped();
        });

        eliminarBtn.addActionListener(e -> {
            try {
                tabla.eliminarConsulta(fila);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel,
                    "❌ Error al eliminar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
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