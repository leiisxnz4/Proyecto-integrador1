package ui.medicamento;

import dao.MedicamentoDAO;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import models.Medicamento;

public class MedicamentoForm extends JPanel {
    private JTextField nombreField, presentacionField, dosisField, cantidadField;
    private JButton guardarBtn;
    private MedicamentoTable tablaRef;
    private Integer idActual = null;
    private JDialog ventanaRef = null;

    public MedicamentoForm() {
        setLayout(new GridBagLayout());
        setBackground(new Color(250, 245, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        add(new JLabel("Nombre:"), gbc);
        gbc.gridy = 1;
        nombreField = new JTextField();
        nombreField.setFont(fieldFont);
        nombreField.setPreferredSize(new Dimension(300, 30));
        nombreField.setBackground(Color.WHITE);
        nombreField.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        add(nombreField, gbc);

        gbc.gridy = 2;
        add(new JLabel("Presentación:"), gbc);
        gbc.gridy = 3;
        presentacionField = new JTextField();
        presentacionField.setFont(fieldFont);
        presentacionField.setPreferredSize(new Dimension(300, 30));
        presentacionField.setBackground(Color.WHITE);
        presentacionField.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        add(presentacionField, gbc);

        gbc.gridy = 4;
        add(new JLabel("Dosis:"), gbc);
        gbc.gridy = 5;
        dosisField = new JTextField();
        dosisField.setFont(fieldFont);
        dosisField.setPreferredSize(new Dimension(300, 30));
        dosisField.setBackground(Color.WHITE);
        dosisField.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        add(dosisField, gbc);

        gbc.gridy = 6;
        add(new JLabel("Cantidad de unidades disponibles:"), gbc);
        gbc.gridy = 7;
        cantidadField = new JTextField();
        cantidadField.setFont(fieldFont);
        cantidadField.setPreferredSize(new Dimension(300, 30));
        cantidadField.setBackground(Color.WHITE);
        cantidadField.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        add(cantidadField, gbc);

        gbc.gridy = 8;
        guardarBtn = new JButton("Guardar");
        guardarBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        guardarBtn.setBackground(new Color(230, 220, 250));
        guardarBtn.setFocusPainted(false);
        guardarBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 230)));
        guardarBtn.setPreferredSize(new Dimension(140, 35));
        add(guardarBtn, gbc);

        guardarBtn.addActionListener(e -> guardarOActualizar());
    }

    public void setTabla(MedicamentoTable tabla) {
        this.tablaRef = tabla;
    }

    public void setVentana(JDialog ventana) {
        this.ventanaRef = ventana;
    }

    public JButton getGuardarBtn() {
        return guardarBtn;
    }

    public void llenarFormularioParaEditar(Medicamento m) {
        idActual = m.getId();
        nombreField.setText(m.getNombre());
        presentacionField.setText(m.getPresentacion());
        dosisField.setText(m.getDosis());
        cantidadField.setText(String.valueOf(m.getCantidad()));
        guardarBtn.setText("✏️ Actualizar");

        for (ActionListener al : guardarBtn.getActionListeners()) {
            guardarBtn.removeActionListener(al);
        }
        guardarBtn.addActionListener(ev -> actualizarMedicamento());
    }

    public void limpiar() {
        nombreField.setText("");
        presentacionField.setText("");
        dosisField.setText("");
        cantidadField.setText("");
        idActual = null;
        guardarBtn.setText("Guardar");

        for (ActionListener al : guardarBtn.getActionListeners()) {
            guardarBtn.removeActionListener(al);
        }
        guardarBtn.addActionListener(e -> guardarOActualizar());
    }

    private void guardarOActualizar() {
        try {
            String nombre = nombreField.getText().trim();
            String presentacion = presentacionField.getText().trim();
            String dosis = dosisField.getText().trim();
            int cantidad = Integer.parseInt(cantidadField.getText().trim());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del medicamento es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medicamento m = new Medicamento(0, nombre, presentacion, dosis, cantidad);
            MedicamentoDAO dao = new MedicamentoDAO();
            dao.guardar(m);

            JOptionPane.showMessageDialog(this, "✅ Medicamento guardado correctamente.");
            limpiar();
            if (tablaRef != null) tablaRef.cargarDatos();
            nombreField.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar medicamento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean actualizarMedicamento() {
        try {
            String nombre = nombreField.getText().trim();
            String presentacion = presentacionField.getText().trim();
            String dosis = dosisField.getText().trim();
            int cantidad = Integer.parseInt(cantidadField.getText().trim());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del medicamento es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            Medicamento m = new Medicamento(idActual, nombre, presentacion, dosis, cantidad);
            MedicamentoDAO dao = new MedicamentoDAO();
            dao.actualizar(m);

            if (tablaRef != null) tablaRef.cargarDatos();

            JOptionPane.showMessageDialog(this, "✏️ Medicamento actualizado correctamente.");

            if (ventanaRef != null) ventanaRef.dispose(); // ✅ Cierra la ventana sin limpiar

            return true;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar medicamento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}