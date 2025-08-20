package ui.cita;

import dao.CitaMedicaDAO;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.*;
import models.CitaMedica;

public class CitaMedicaForm extends JPanel {
    private final CitaMedicaTable tabla;
    private final JFrame ventanaActual;

    private final JTextField matriculaField;
    private final JTextField fechaField;
    private final JTextField horaField;
    private final JTextArea motivoArea;
    private final JButton guardarBtn;

    private int citaEditandoId = -1;

    public CitaMedicaForm(JFrame ventanaActual, CitaMedicaTable tabla) {
        this.ventanaActual = ventanaActual;
        this.tabla = tabla;
        tabla.setFormulario(this);

        Color fondoLila = new Color(245, 240, 255);
        setBackground(fondoLila);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 🧩 Campos
        matriculaField = crearCampoTexto();
        fechaField = crearCampoTexto("2025-08-06");
        horaField = crearCampoTexto("10:30");

        motivoArea = new JTextArea(4, 20);
        motivoArea.setLineWrap(true);
        motivoArea.setWrapStyleWord(true);
        motivoArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        motivoArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane motivoScroll = new JScrollPane(motivoArea);
        motivoScroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        guardarBtn = new JButton("Agendar");
        guardarBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        guardarBtn.setBackground(new Color(230, 225, 250));
        guardarBtn.setFocusPainted(false);
        guardarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        guardarBtn.setMaximumSize(new Dimension(160, 40));

        // 🧱 Agregar campos al formulario
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Matrícula del estudiante", matriculaField));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Fecha (AAAA-MM-DD)", fechaField));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Hora (HH:mm)", horaField));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Motivo", motivoScroll));
        add(Box.createVerticalStrut(20));
        add(guardarBtn);
        add(Box.createVerticalGlue());

        // 🖱️ Acción del botón Guardar
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

                    tabla.cargarDatos();
                    limpiarFormulario();
                    ventanaActual.dispose(); // 👈 Cierra la ventana flotante
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

    private JPanel crearGrupoCampo(String etiqueta, Component campo) {
        JPanel grupo = new JPanel();
        grupo.setLayout(new BoxLayout(grupo, BoxLayout.Y_AXIS));
        grupo.setBackground(new Color(245, 240, 255));

        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        campo.setMaximumSize(new Dimension(600, 40));
        campo.setPreferredSize(new Dimension(600, 40));

        grupo.add(label);
        grupo.add(Box.createVerticalStrut(6));
        grupo.add(campo);
        grupo.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));

        return grupo;
    }

    private JTextField crearCampoTexto() {
        return crearCampoTexto("");
    }

    private JTextField crearCampoTexto(String textoInicial) {
        JTextField campo = new JTextField(textoInicial);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campo.setBackground(Color.WHITE);
        campo.setMargin(new Insets(8, 8, 8, 8));
        return campo;
    }

    public JButton getGuardarButton() {
        return guardarBtn;
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