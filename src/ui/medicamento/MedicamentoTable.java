package ui.medicamento;

import dao.MedicamentoDAO;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Medicamento;

public class MedicamentoTable extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;
    private MedicamentoForm form;

    public MedicamentoTable() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 240, 255)); // Fondo lila claro

        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBackground(new Color(250, 245, 255)); // Panel rosita
        contenedorTabla.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("📋 Medicamentos Registrados");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(new Color(100, 80, 120));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        contenedorTabla.add(titulo, BorderLayout.NORTH);

        String[] columnas = {"ID", "Nombre", "Presentación", "Dosis", "Cantidad", "Acciones"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(32);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(230, 220, 250));
        tabla.setSelectionBackground(new Color(220, 240, 255));
        tabla.setGridColor(new Color(230, 230, 250));

        tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("Acciones").setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 200, 240), 1));
        contenedorTabla.add(scroll, BorderLayout.CENTER);

        add(contenedorTabla, BorderLayout.CENTER);

        cargarDatos();
    }

    public void setFormulario(MedicamentoForm form) {
        this.form = form;
    }

    public MedicamentoForm getFormulario() {
        return form;
    }

    public void cargarDatos() {
        try {
            modelo.setRowCount(0);
            ArrayList<Medicamento> lista = new MedicamentoDAO().listar();
            for (Medicamento m : lista) {
                modelo.addRow(new Object[]{
                    m.getId(),
                    m.getNombre(),
                    m.getPresentacion(),
                    m.getDosis(),
                    m.getCantidad(),
                    "Editar / Eliminar"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar medicamentos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Medicamento obtenerMedicamentoEnFila(int fila) {
        int id = (int) modelo.getValueAt(fila, 0);
        String nombre = (String) modelo.getValueAt(fila, 1);
        String presentacion = (String) modelo.getValueAt(fila, 2);
        String dosis = (String) modelo.getValueAt(fila, 3);
        int cantidad = (int) modelo.getValueAt(fila, 4);

        Medicamento m = new Medicamento();
        m.setId(id);
        m.setNombre(nombre);
        m.setPresentacion(presentacion);
        m.setDosis(dosis);
        m.setCantidad(cantidad);
        return m;
    }

    public void eliminarMedicamento(int fila) {
        int id = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar el medicamento con ID " + id + "?", "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                new MedicamentoDAO().eliminar(id);
                cargarDatos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}