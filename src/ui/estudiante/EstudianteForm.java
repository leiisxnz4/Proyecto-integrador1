package ui.estudiante;

import dao.EstudianteDAO;
import java.awt.*;
import javax.swing.*;
import models.Estudiante;

public class EstudianteForm extends JPanel {
    private JTextField nombreField, edadField, grupoField, tutorField, telefonoField, matriculaField;
    private JTextArea condicionesArea;
    private JButton guardarBtn;

    private Integer idActual = null;
    private EstudianteTable tablaRef;

    public EstudianteForm() {
        setLayout(new GridLayout(9, 2, 5, 5));

        nombreField = new JTextField();
        edadField = new JTextField();
        grupoField = new JTextField();
        tutorField = new JTextField();
        telefonoField = new JTextField();
        matriculaField = new JTextField();
        condicionesArea = new JTextArea(3, 20);
        guardarBtn = new JButton("Guardar");

        add(new JLabel("Nombre:")); add(nombreField);
        add(new JLabel("Edad:")); add(edadField);
        add(new JLabel("Grupo:")); add(grupoField);
        add(new JLabel("Tutor:")); add(tutorField);
        add(new JLabel("Teléfono:")); add(telefonoField);
        add(new JLabel("Matrícula:")); add(matriculaField);
        add(new JLabel("Condiciones médicas:")); add(new JScrollPane(condicionesArea));
        add(new JLabel("")); add(guardarBtn);

        guardarBtn.addActionListener(e -> guardarOActualizar());
    }

    public void setTabla(EstudianteTable tabla) {
        this.tablaRef = tabla;
    }

    private void guardarOActualizar() {
        try {
            Estudiante est = obtenerEstudiante();
            EstudianteDAO dao = new EstudianteDAO();
            boolean exito;

            if (idActual == null) {
                exito = dao.guardar(est);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Estudiante guardado correctamente. ID: " + est.getId());
                }
            } else {
                est.setId(idActual);
                exito = dao.actualizar(est);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Estudiante actualizado correctamente.");
                }
            }

            if (exito) {
                limpiar();
                idActual = null;
                guardarBtn.setText("Guardar");
                if (tablaRef != null) {
                    tablaRef.cargarDatos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Ya existe un estudiante con ese nombre y grupo.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Edad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiar() {
        nombreField.setText("");
        edadField.setText("");
        grupoField.setText("");
        tutorField.setText("");
        telefonoField.setText("");
        matriculaField.setText("");
        condicionesArea.setText("");
        idActual = null;
        guardarBtn.setText("Guardar");
    }

    public JButton getGuardarBtn() {
        return guardarBtn;
    }

    public Estudiante obtenerEstudiante() {
        String nombre = nombreField.getText().trim();
        int edad = Integer.parseInt(edadField.getText().trim());
        String grupo = grupoField.getText().trim();
        String tutor = tutorField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String matricula = matriculaField.getText().trim();
        String condiciones = condicionesArea.getText().trim();

        if (nombre.isEmpty() || grupo.isEmpty()) {
            throw new IllegalArgumentException("Nombre y Grupo son obligatorios.");
        }

        return new Estudiante(0, nombre, edad, grupo, tutor, telefono, matricula, condiciones);
    }

    public void llenarFormularioParaEditar(Estudiante e) {
        idActual = e.getId();
        nombreField.setText(e.getNombre());
        edadField.setText(String.valueOf(e.getEdad()));
        grupoField.setText(e.getGrupo());
        tutorField.setText(e.getTutor());
        telefonoField.setText(e.getTelefono());
        matriculaField.setText(e.getMatricula());
        condicionesArea.setText(e.getCondicionesMedicas());
        guardarBtn.setText("Actualizar");
    }
}