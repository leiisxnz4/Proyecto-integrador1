package ui.cita;

import dao.CitaMedicaDAO;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.CitaMedica;

public class CitaMedicaTable extends JPanel {
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private CitaMedicaForm formularioRef;
    private final DateTimeFormatter fechaFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private final DateTimeFormatter horaFmt = DateTimeFormatter.ofPattern("hh:mm a");

    public CitaMedicaTable() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Citas Médicas Agendadas"));

        String[] columnas = {"ID", "Matrícula", "Nombre", "Fecha", "Hora", "Motivo", "Acciones"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabla.setSelectionBackground(new Color(220, 240, 255));

        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(130);

        tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("Acciones").setCellEditor(new ButtonEditor(tabla, this));

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarDatos();
    }

    public void setFormulario(CitaMedicaForm form) {
        this.formularioRef = form;
    }

    public CitaMedicaForm getFormulario() {
        return formularioRef;
    }

    public void cargarDatos() {
        modelo.setRowCount(0);
        try {
            List<CitaMedica> lista = new CitaMedicaDAO().listar();
            for (CitaMedica c : lista) {
                modelo.addRow(new Object[]{
                    c.getId(),
                    c.getMatricula(),
                    c.getNombreEstudiante(),
                    c.getFecha().format(fechaFmt),
                    c.getHora().format(horaFmt),
                    c.getMotivo(),
                    "Editar / Eliminar"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al cargar citas: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public CitaMedica obtenerCitaEnFila(int fila) {
        try {
            int id = (int) modelo.getValueAt(fila, 0);
            String matricula = (String) modelo.getValueAt(fila, 1);
            String nombre = (String) modelo.getValueAt(fila, 2);
            String fechaStr = (String) modelo.getValueAt(fila, 3);
            String horaStr = (String) modelo.getValueAt(fila, 4);
            String motivo = (String) modelo.getValueAt(fila, 5);

            CitaMedica cita = new CitaMedica();
            cita.setId(id);
            cita.setMatricula(matricula);
            cita.setNombreEstudiante(nombre);
            cita.setFecha(LocalDate.parse(fechaStr, fechaFmt));
            cita.setHora(LocalTime.parse(horaStr, horaFmt));
            cita.setMotivo(motivo);
            return cita;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al obtener cita: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void eliminarCita(int fila) {
        int id = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showOptionDialog(
            this,
            "¿Eliminar la cita con ID " + id + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new Object[]{"Sí", "No"},
            "No"
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                new CitaMedicaDAO().eliminar(id);
                cargarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error al eliminar cita: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}