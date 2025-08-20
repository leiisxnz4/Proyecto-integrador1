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
        Color fondoLila = new Color(245, 240, 255);
        Color bordeSuave = new Color(230, 225, 250);
        setBackground(fondoLila);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        nombreField = crearCampoTexto();
        edadField = crearCampoTexto();
        grupoField = crearCampoTexto();
        tutorField = crearCampoTexto();
        telefonoField = crearCampoTexto();
        matriculaField = crearCampoTexto();
        condicionesArea = crearAreaTexto();

        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Nombre del estudiante", nombreField));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Edad", edadField));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Grupo", grupoField));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Tutor", tutorField));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Teléfono", telefonoField));
        add(Box.createVerticalStrut(10));
        add(crearGrupoCampo("Matrícula", matriculaField));
        add(Box.createVerticalStrut(10));

        JScrollPane scrollCondiciones = new JScrollPane(condicionesArea);
        scrollCondiciones.setPreferredSize(new Dimension(600, 120));
        scrollCondiciones.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, bordeSuave));
        scrollCondiciones.setBackground(Color.WHITE);
        scrollCondiciones.getViewport().setBackground(Color.WHITE);

        add(crearGrupoCampo("Condiciones médicas", scrollCondiciones));
        add(Box.createVerticalStrut(20));

        guardarBtn = new JButton("Guardar");
        guardarBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        guardarBtn.setBackground(bordeSuave);
        guardarBtn.setFocusPainted(false);
        guardarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        guardarBtn.setMaximumSize(new Dimension(160, 40));
        guardarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        guardarBtn.addActionListener(e -> guardarOActualizar());

        add(guardarBtn);
        add(Box.createVerticalGlue());
    }

    private JPanel crearGrupoCampo(String etiqueta, Component campo) {
        JPanel grupo = new JPanel();
        grupo.setLayout(new BoxLayout(grupo, BoxLayout.Y_AXIS));
        grupo.setBackground(new Color(245, 240, 255));

        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        campo.setMaximumSize(new Dimension(600, campo.getPreferredSize().height));
        campo.setPreferredSize(new Dimension(600, campo.getPreferredSize().height));

        grupo.add(label);
        grupo.add(Box.createVerticalStrut(6));
        grupo.add(campo);
        grupo.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));

        return grupo;
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setBackground(Color.WHITE);
        campo.setMargin(new Insets(8, 8, 8, 8));
        campo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(230, 225, 250)));
        return campo;
    }

    private JTextArea crearAreaTexto() {
        JTextArea area = new JTextArea(6, 20);
        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
        area.setBackground(Color.WHITE);
        area.setMargin(new Insets(8, 8, 8, 8));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(230, 225, 250)));
        return area;
    }

    public void setTabla(EstudianteTable tabla) {
        this.tablaRef = tabla;
    }

    public EstudianteTable getTabla() {
        return tablaRef;
    }

    public JButton getBtnGuardar() {
        return guardarBtn;
    }

    public void guardarOActualizar() {
        try {
            Estudiante est = obtenerEstudiante();
            if (idActual != null) est.setId(idActual);

            EstudianteDAO dao = new EstudianteDAO();
            boolean exito = (idActual == null) ? dao.guardar(est) : dao.actualizar(est);

            if (exito) {
                limpiar();
                idActual = null;
                guardarBtn.setText("Guardar");
                if (tablaRef != null) tablaRef.cargarDatos();
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