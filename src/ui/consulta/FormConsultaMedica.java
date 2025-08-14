package ui.consulta;

import dao.ConsultaMedicaDAO;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import models.ConsultaMedica;

public class FormConsultaMedica extends JPanel {
    private JTextField txtMatricula, txtMotivo, txtSignosVitales, txtMedicamentos, txtObservaciones, txtFecha;
    private JButton btnGuardar;
    private final ConsultaMedicaDAO consultaDAO;
    private Integer idActual = null;
    private TableConsultaMedica tablaRef;

    public FormConsultaMedica() {
        consultaDAO = new ConsultaMedicaDAO();
        setLayout(new GridLayout(7, 2, 5, 5));

        txtMatricula = new JTextField();
        txtMotivo = new JTextField();
        txtSignosVitales = new JTextField();
        txtMedicamentos = new JTextField();
        txtObservaciones = new JTextField();
        txtFecha = new JTextField(); // formato: yyyy-MM-dd

        add(new JLabel("Matrícula del estudiante:")); add(txtMatricula);
        add(new JLabel("Motivo:")); add(txtMotivo);
        add(new JLabel("Signos Vitales:")); add(txtSignosVitales);
        add(new JLabel("Medicamentos:")); add(txtMedicamentos);
        add(new JLabel("Observaciones:")); add(txtObservaciones);
        add(new JLabel("Fecha (yyyy-MM-dd):")); add(txtFecha);

        btnGuardar = new JButton("Guardar Consulta");
        add(new JLabel()); add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarOActualizar());
    }

    public void setTabla(TableConsultaMedica tabla) {
        this.tablaRef = tabla;
    }

    private void guardarOActualizar() {
        try {
            ConsultaMedica consulta = obtenerConsulta();
            if (idActual != null) consulta.setId(idActual);

            boolean resultado;
            if (idActual == null) {
                resultado = consultaDAO.guardar(consulta);
                if (resultado) {
                    JOptionPane.showMessageDialog(this, "Consulta médica guardada. ID: " + consulta.getId());
                } else {
                    JOptionPane.showMessageDialog(this, "Ya existe una consulta con los mismos datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                resultado = consultaDAO.actualizar(consulta);
                if (resultado) {
                    JOptionPane.showMessageDialog(this, "Consulta médica actualizada.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar la consulta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            limpiar();
            if (tablaRef != null) tablaRef.cargarDatos();
            txtMatricula.requestFocus();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener el formato yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiar() {
        txtMatricula.setText("");
        txtMotivo.setText("");
        txtSignosVitales.setText("");
        txtMedicamentos.setText("");
        txtObservaciones.setText("");
        txtFecha.setText("");
        idActual = null;
        btnGuardar.setText("Guardar Consulta");
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public ConsultaMedica obtenerConsulta() throws DateTimeParseException {
        String matricula = txtMatricula.getText().trim();
        String motivo = txtMotivo.getText().trim();
        String signos = txtSignosVitales.getText().trim();
        String medicamentos = txtMedicamentos.getText().trim();
        String observaciones = txtObservaciones.getText().trim();
        LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());

        return new ConsultaMedica(matricula, motivo, signos, medicamentos, observaciones, fecha);
    }

    public void llenarFormularioParaEditar(ConsultaMedica c) {
        idActual = c.getId();
        txtMatricula.setText(c.getMatricula());
        txtMotivo.setText(c.getMotivo());
        txtSignosVitales.setText(c.getSignosVitales());
        txtMedicamentos.setText(c.getMedicamentos());
        txtObservaciones.setText(c.getObservaciones());
        txtFecha.setText(c.getFecha().toString());
        btnGuardar.setText("Actualizar Consulta");
    }
}