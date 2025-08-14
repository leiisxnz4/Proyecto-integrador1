package ui.estudiante;

import dao.EstudianteDAO;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Estudiante;

public class EstudianteTable extends JPanel {
    private JTable tabla;
    private DefaultTableModel model;
    private EstudianteForm form;

    public EstudianteTable() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Estudiantes Registrados"));

        String[] columnas = {
            "ID", "Nombre", "Edad", "Grupo", "Tutor",
            "Teléfono", "Matrícula", "Condiciones médicas", "Acciones"
        };

        model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }
        };

        tabla = new JTable(model);
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("Acciones").setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarDatos();
    }

    public void setFormulario(EstudianteForm form) {
        this.form = form;
    }

    public void cargarDatos() {
        model.setRowCount(0);
        try {
            ArrayList<Estudiante> lista = new EstudianteDAO().listar();
            for (Estudiante e : lista) {
                model.addRow(new Object[]{
                    e.getId(), e.getNombre(), e.getEdad(), e.getGrupo(),
                    e.getTutor(), e.getTelefono(), e.getMatricula(), e.getCondicionesMedicas(),
                    "Editar / Eliminar"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al cargar estudiantes: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Estudiante obtenerEstudianteEnFila(int fila) {
        int id = (int) model.getValueAt(fila, 0);
        String nombre = (String) model.getValueAt(fila, 1);
        int edad = (int) model.getValueAt(fila, 2);
        String grupo = (String) model.getValueAt(fila, 3);
        String tutor = (String) model.getValueAt(fila, 4);
        String telefono = (String) model.getValueAt(fila, 5);
        String matricula = (String) model.getValueAt(fila, 6);
        String condiciones = (String) model.getValueAt(fila, 7);

        Estudiante est = new Estudiante();
        est.setId(id);
        est.setNombre(nombre);
        est.setEdad(edad);
        est.setGrupo(grupo);
        est.setTutor(tutor);
        est.setTelefono(telefono);
        est.setMatricula(matricula);
        est.setCondicionesMedicas(condiciones);
        return est;
    }

    public void eliminarEstudiante(int fila) {
        int id = (int) model.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar el estudiante con ID " + id + "?", "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                EstudianteDAO dao = new EstudianteDAO();
                dao.eliminar(id);
                cargarDatos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error al eliminar estudiante: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public EstudianteForm getForm() {
        return form;
    }
}