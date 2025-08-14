package ui.cita;

import dao.CitaMedicaDAO;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.*;
import models.CitaMedica;

public class CitaMedicaForm extends JPanel {
    private final CitaMedicaTable tabla;
    private final JTextField matriculaField;
    private final JTextField fechaField;
    private final JTextField horaField;
    private final JTextArea motivoArea;
    private final JButton guardarBtn;

    private int citaEditandoId = -1; // ID de cita en edición (-1 si es nueva)

    public CitaMedicaForm(JFrame ventanaActual, CitaMedicaTable tabla) {
        this.tabla = tabla;
        tabla.setFormulario(this); // Vincula el formulario con la tabla

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createTitledBorder("Agendar nueva cita"));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        matriculaField = new JTextField();
        fechaField = new JTextField("2025-08-06");
        horaField = new JTextField("10:30");
        motivoArea = new JTextArea();
        guardarBtn = new JButton("Agendar");

        formPanel.add(new JLabel("Matrícula del estudiante:")); formPanel.add(matriculaField);
        formPanel.add(new JLabel("Fecha (AAAA-MM-DD):")); formPanel.add(fechaField);
        formPanel.add(new JLabel("Hora (HH:mm):")); formPanel.add(horaField);
        formPanel.add(new JLabel("Motivo:")); formPanel.add(new JScrollPane(motivoArea));
        formPanel.add(new JLabel("")); formPanel.add(guardarBtn);

        add(formPanel, BorderLayout.CENTER);

        guardarBtn.addActionListener(e -> {
            try {
                String matricula = matriculaField.getText().trim();
                LocalDate fechaCita = LocalDate.parse(fechaField.getText().trim());
                LocalTime horaCita = LocalTime.parse(horaField.getText().trim());
                String motivoCita = motivoArea.getText().trim();

                if (matricula.isEmpty() || motivoCita.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "⚠ Matrícula y motivo son obligatorios.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                CitaMedica cita = new CitaMedica();
                cita.setMatricula(matricula);
                cita.setFecha(fechaCita);
                cita.setHora(horaCita);
                cita.setMotivo(motivoCita);

                CitaMedicaDAO dao = new CitaMedicaDAO();
                boolean exito;

                if (citaEditandoId != -1) {
                    cita.setId(citaEditandoId);
                    exito = dao.actualizar(cita); 
                } else {
                    exito = dao.insertar(cita);
                }

                if (exito) {
                    JOptionPane.showMessageDialog(this,
                        citaEditandoId != -1 ? "✅ Cita actualizada correctamente." : "✅ Cita agendada correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                    tabla.cargarDatos(); // Actualiza la tabla
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "❌ No se pudo guardar la cita.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void cargarCitaParaEdicion(CitaMedica cita) {
        citaEditandoId = cita.getId();
        matriculaField.setText(cita.getMatricula());
        fechaField.setText(cita.getFecha().toString());
        horaField.setText(cita.getHora().toString());
        motivoArea.setText(cita.getMotivo());
        guardarBtn.setText("Actualizar");
    }

    private void limpiarFormulario() {
        citaEditandoId = -1;
        matriculaField.setText("");
        fechaField.setText("2025-08-06");
        horaField.setText("10:30");
        motivoArea.setText("");
        guardarBtn.setText("Agendar");
    }
}