package ui.recomendacion;

import dao.RecomendacionMedicaDAO;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import models.RecomendacionMedica;

public class RecomendacionMedicaForm extends JPanel {
    private final JTextField matriculaField;
    private final JTextField fechaField;
    private final JTextArea contenidoArea;
    private final JButton guardarButton;

    private RecomendacionMedica recomendacionActual = null;

    public RecomendacionMedicaForm() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Formulario de Recomendación"));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Matrícula
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Matrícula:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        matriculaField = new JTextField();
        add(matriculaField, gbc);

        // Fecha
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Fecha (AAAA-MM-DD):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fechaField = new JTextField(LocalDate.now().toString());
        add(fechaField, gbc);

        // Recomendación
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Recomendación:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contenidoArea = new JTextArea(5, 20);
        contenidoArea.setLineWrap(true);
        contenidoArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(contenidoArea);
        add(scroll, gbc);

        // Botón Guardar
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.weightx = 1.0; 
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.anchor = GridBagConstraints.EAST;
        guardarButton = new JButton("Guardar");
        add(guardarButton, gbc);

        guardarButton.addActionListener(e -> guardarRecomendacion());
    }

    public void cargarRecomendacion(RecomendacionMedica r) {
        this.recomendacionActual = r;
        matriculaField.setText(r.getMatricula());
        fechaField.setText(r.getFecha().toString());
        contenidoArea.setText(r.getTexto());
        guardarButton.setText("Actualizar");
    }

    public boolean guardarRecomendacion() {
        try {
            String matricula = matriculaField.getText().trim();
            LocalDate fecha = LocalDate.parse(fechaField.getText().trim());
            String texto = contenidoArea.getText().trim();

            if (matricula.isEmpty()) {
                throw new IllegalArgumentException("La matrícula no puede estar vacía.");
            }
            if (texto.isEmpty()) {
                throw new IllegalArgumentException("La recomendación no puede estar vacía.");
            }

            if (recomendacionActual != null) {
                recomendacionActual.setMatricula(matricula);
                recomendacionActual.setFecha(fecha);
                recomendacionActual.setTexto(texto);
                new RecomendacionMedicaDAO().actualizar(recomendacionActual);
                JOptionPane.showMessageDialog(this, "✅ Recomendación actualizada correctamente.");
            } else {
                RecomendacionMedica r = new RecomendacionMedica(0, matricula, fecha, texto);
                new RecomendacionMedicaDAO().guardar(r);
                JOptionPane.showMessageDialog(this, "✅ Recomendación guardada correctamente.");
            }

            limpiar();
            return true;

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "❌ Fecha inválida. Usa el formato AAAA-MM-DD.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "❌ " + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al guardar en la base de datos: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error inesperado: " + ex.getMessage());
        }
        return false;
    }

    public void limpiar() {
        recomendacionActual = null;
        matriculaField.setText("");
        fechaField.setText(LocalDate.now().toString());
        contenidoArea.setText("");
        guardarButton.setText("Guardar");
    }

    public JButton getGuardarButton() {
        return guardarButton;
    }

    public JTextField getMatriculaField() {
        return matriculaField;
    }
}