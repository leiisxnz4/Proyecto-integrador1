package ui.recomendacion;

import dao.RecomendacionMedicaDAO;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import models.RecomendacionMedica;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final JButton editar;
    private final JButton eliminar;
    private final JTable tabla;
    private int filaActual;

    public ButtonEditor(JTable tabla) {
        this.tabla = tabla;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setBackground(new Color(250, 245, 255));

        editar = crearBoton("✏️");
        eliminar = crearBoton("🗑️");

        editar.addActionListener(e -> editarFila());
        eliminar.addActionListener(e -> eliminarFila());

        panel.add(editar);
        panel.add(eliminar);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btn.setBackground(new Color(230, 220, 250));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return btn;
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

            LocalDate fecha = LocalDate.parse(fechaStr);
            RecomendacionMedica r = new RecomendacionMedica(id, matricula, fecha, texto);

            RecomendacionMedicaForm formEdicion = new RecomendacionMedicaForm();
            formEdicion.cargarRecomendacion(r);

            // ✅ Ajustar ancho de campos
            formEdicion.getMatriculaField().setPreferredSize(new Dimension(450, 30));
            formEdicion.getGuardarButton().setPreferredSize(new Dimension(160, 35));

            JFrame ventana = new JFrame("✏️ Editar Recomendación");
            ventana.setSize(650, 420);
            ventana.setLocationRelativeTo(null);
            ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventana.setResizable(false); // ✅ Bloquea expansión

            JPanel panelFlotante = new JPanel(new BorderLayout());
            panelFlotante.setBackground(new Color(250, 245, 255));
            panelFlotante.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
            panelFlotante.add(formEdicion, BorderLayout.CENTER);

            ventana.setContentPane(panelFlotante);
            ventana.setVisible(true);

            formEdicion.getGuardarButton().addActionListener(ev -> {
                boolean actualizado = formEdicion.guardarRecomendacion();
                if (actualizado) {
                    ventana.dispose();

                    Component contenedor = SwingUtilities.getAncestorOfClass(RecomendacionMedicaTable.class, tabla);
                    if (contenedor instanceof RecomendacionMedicaTable tablePanel) {
                        tablePanel.cargarDatos();
                    }
                }
            });

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