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
        setBackground(new Color(250, 245, 255)); // ✅ Fondo rosita
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        // Matrícula
        add(new JLabel("Matrícula:"), gbc);
        gbc.gridy++;
        matriculaField = new JTextField();
        matriculaField.setFont(fieldFont);
        matriculaField.setPreferredSize(new Dimension(300, 30));
        matriculaField.setBackground(Color.WHITE);
        matriculaField.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        add(matriculaField, gbc);

        // Fecha
        gbc.gridy++;
        add(new JLabel("Fecha (AAAA-MM-DD):"), gbc);
        gbc.gridy++;
        fechaField = new JTextField(LocalDate.now().toString());
        fechaField.setFont(fieldFont);
        fechaField.setPreferredSize(new Dimension(300, 30));
        fechaField.setBackground(Color.WHITE);
        fechaField.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        add(fechaField, gbc);

        // Recomendación
        gbc.gridy++;
        add(new JLabel("Recomendación:"), gbc);
        gbc.gridy++;
        contenidoArea = new JTextArea(5, 20);
        contenidoArea.setFont(fieldFont);
        contenidoArea.setLineWrap(true);
        contenidoArea.setWrapStyleWord(true);
        contenidoArea.setBackground(Color.WHITE);
        contenidoArea.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        JScrollPane scroll = new JScrollPane(contenidoArea);
        scroll.setPreferredSize(new Dimension(300, 100));
        add(scroll, gbc);

        // Botón Guardar
        gbc.gridy++;
        guardarButton = new JButton("Guardar");
        guardarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        guardarButton.setBackground(new Color(230, 220, 250));
        guardarButton.setFocusPainted(false);
        guardarButton.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        guardarButton.setPreferredSize(new Dimension(140, 35));
        add(guardarButton, gbc);

        guardarButton.addActionListener(e -> guardarRecomendacion());
    }

    public void cargarRecomendacion(RecomendacionMedica r) {
        this.recomendacionActual = r;
        matriculaField.setText(r.getMatricula());
        fechaField.setText(r.getFecha().toString());
        contenidoArea.setText(r.getTexto());
        guardarButton.setText("✏️ Actualizar");
    }

    public boolean guardarRecomendacion() {
        try {
            String matricula = matriculaField.getText().trim();
            LocalDate fecha = LocalDate.parse(fechaField.getText().trim());
            String texto = contenidoArea.getText().trim();

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