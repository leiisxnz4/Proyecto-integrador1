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

        Color fondoLila = new Color(245, 240, 255);
        setBackground(fondoLila);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        txtMatricula = crearCampoTexto();
        txtMotivo = crearCampoTexto();
        txtSignosVitales = crearCampoTexto();
        txtMedicamentos = crearCampoTexto();
        txtObservaciones = crearCampoTexto();
        txtFecha = crearCampoTexto("2025-08-06");

        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Matrícula del estudiante", txtMatricula));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Motivo", txtMotivo));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Signos Vitales", txtSignosVitales));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Medicamentos", txtMedicamentos));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Observaciones", txtObservaciones));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Fecha (AAAA-MM-DD)", txtFecha));
        add(Box.createVerticalStrut(20));

        btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(230, 225, 250));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGuardar.setMaximumSize(new Dimension(160, 40));
        btnGuardar.addActionListener(e -> guardarOActualizar());

        add(btnGuardar);
        add(Box.createVerticalGlue());
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

    public void setTabla(TableConsultaMedica tabla) {
        this.tablaRef = tabla;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void guardarOActualizar() {
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
        txtFecha.setText("2025-08-06");
        idActual = null;
        btnGuardar.setText("Guardar");
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
        btnGuardar.setText("Actualizar");
    }
}