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
        setBackground(new Color(250, 245, 255)); // 🌸 Fondo rosita

        // 🔝 Panel superior con título y botón regresar
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(250, 245, 255));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titulo = new JLabel("Registro de Recomendaciones");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(new Color(80, 60, 120));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnRegresar = new JButton("🔙 Regresar");
        btnRegresar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnRegresar.setBackground(new Color(230, 220, 250));
        btnRegresar.setFocusPainted(false);
        btnRegresar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        btnRegresar.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof JFrame frame) {
                frame.dispose(); // ✅ Cierra la ventana flotante
            }
        });

        panelSuperior.add(btnRegresar, BorderLayout.WEST);
        panelSuperior.add(titulo, BorderLayout.CENTER);

        String[] columnas = {"ID", "Matrícula", "Fecha", "Recomendación", "Acciones"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(36);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setGridColor(new Color(230, 220, 250));

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(230, 220, 250));
        header.setForeground(new Color(80, 60, 120));

        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(255, 250, 255) : Color.WHITE);
                }
                return c;
            }
        });

        tabla.getColumn("Acciones").setCellRenderer(new AccionRenderer());
        tabla.getColumn("Acciones").setCellEditor(new AccionEditor());

        add(panelSuperior, BorderLayout.NORTH);
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

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btn.setBackground(new Color(230, 220, 250));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return btn;
    }

    private class AccionRenderer extends JPanel implements TableCellRenderer {
        public AccionRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            setOpaque(true);
            setBackground(new Color(250, 245, 255));

            JButton editar = crearBoton("✏️ Editar");
            JButton eliminar = crearBoton("🗑️ Eliminar");
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
            panel.setBackground(new Color(250, 245, 255));

            editar = crearBoton("✏️ Editar");
            eliminar = crearBoton("🗑️ Eliminar");

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

                RecomendacionMedicaForm formEdicion = new RecomendacionMedicaForm();
                formEdicion.cargarRecomendacion(r);

                JFrame ventana = new JFrame("✏️ Editar Recomendación");
                ventana.setSize(600, 400);
                ventana.setLocationRelativeTo(null);
                ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
                        cargarDatos();
                    }
                });

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