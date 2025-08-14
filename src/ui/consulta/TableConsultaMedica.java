package ui.consulta;

import dao.ConsultaMedicaDAO;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.ConsultaMedica;

public class TableConsultaMedica extends JPanel {
    private final JTable tabla;
    private final DefaultTableModel modelo;
    private final DateTimeFormatter fechaFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private FormConsultaMedica form;

    public TableConsultaMedica() {
        String[] columnas = {
            "ID", "Matrícula", "Nombre", "Motivo", "Signos Vitales",
            "Medicamentos", "Observaciones", "Fecha", "Acciones"
        };

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8; // Solo la columna de acciones
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabla.setSelectionBackground(new Color(220, 240, 255));

        tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("Acciones").setCellEditor(new ButtonEditor(new JCheckBox(), this));

        setLayout(new BorderLayout());
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarDatos();
    }

    public void setFormulario(FormConsultaMedica form) {
        this.form = form;
    }

    public FormConsultaMedica getFormulario() {
        return form;
    }

    public void cargarDatos() {
        modelo.setRowCount(0);
        try {
            List<ConsultaMedica> lista = new ConsultaMedicaDAO().listar();

            for (ConsultaMedica consulta : lista) {
                modelo.addRow(new Object[]{
                    consulta.getId(),
                    consulta.getMatricula(),
                    consulta.getNombreEstudiante(),
                    consulta.getMotivo(),
                    consulta.getSignosVitales(),
                    consulta.getMedicamentos(),
                    consulta.getObservaciones(),
                    consulta.getFecha().format(fechaFmt),
                    "Editar / Eliminar"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al cargar datos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ConsultaMedica obtenerConsultaEnFila(int fila) {
        try {
            int id = (int) modelo.getValueAt(fila, 0);
            String matricula = (String) modelo.getValueAt(fila, 1);
            String nombre = (String) modelo.getValueAt(fila, 2);
            String motivo = (String) modelo.getValueAt(fila, 3);
            String signos = (String) modelo.getValueAt(fila, 4);
            String medicamentos = (String) modelo.getValueAt(fila, 5);
            String observaciones = (String) modelo.getValueAt(fila, 6);
            String fechaStr = (String) modelo.getValueAt(fila, 7);

            ConsultaMedica c = new ConsultaMedica();
            c.setId(id);
            c.setMatricula(matricula);
            c.setNombreEstudiante(nombre);
            c.setMotivo(motivo);
            c.setSignosVitales(signos);
            c.setMedicamentos(medicamentos);
            c.setObservaciones(observaciones);
            c.setFecha(LocalDate.parse(fechaStr, fechaFmt));
            return c;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al obtener consulta: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void eliminarConsulta(int fila) {
        int id = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar la consulta con ID " + id + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                new ConsultaMedicaDAO().eliminar(id);
                cargarDatos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}