package ui.medicamento;

import dao.MedicamentoDAO;
import java.awt.*;
import javax.swing.*;
import models.Medicamento;

public class MedicamentoForm extends JPanel {
    private JTextField nombreField, presentacionField, dosisField, cantidadField;
    private JButton guardarBtn;

    private Integer idActual = null; // ID en edición
    private MedicamentoTable tablaRef; // Referencia a la tabla

    public MedicamentoForm() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Registrar Medicamento"));

        nombreField = new JTextField();
        presentacionField = new JTextField();
        dosisField = new JTextField();
        cantidadField = new JTextField();
        guardarBtn = new JButton("Guardar");

        add(new JLabel("Nombre:")); add(nombreField);
        add(new JLabel("Presentación:")); add(presentacionField);
        add(new JLabel("Dosis:")); add(dosisField);
        add(new JLabel("Cantidad de unidades disponibles:")); add(cantidadField);
        add(new JLabel("")); add(guardarBtn);

        guardarBtn.addActionListener(e -> guardarOActualizar());
    }

    public void setTabla(MedicamentoTable tabla) {
        this.tablaRef = tabla;
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

            Medicamento m = new Medicamento(
                idActual != null ? idActual : 0,
                nombre,
                presentacion,
                dosis,
                cantidad
            );

            MedicamentoDAO dao = new MedicamentoDAO();

            if (idActual == null) {
                dao.guardar(m);
                JOptionPane.showMessageDialog(this, "✅ Medicamento guardado correctamente.");
            } else {
                dao.actualizar(m);
                JOptionPane.showMessageDialog(this, "✏️ Medicamento actualizado correctamente.");
            }

            limpiar();
            if (tablaRef != null) tablaRef.cargarDatos();
            nombreField.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar medicamento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiar() {
        nombreField.setText("");
        presentacionField.setText("");
        dosisField.setText("");
        cantidadField.setText("");
        idActual = null;
        guardarBtn.setText("Guardar");
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
        guardarBtn.setText("Actualizar");
    }
}