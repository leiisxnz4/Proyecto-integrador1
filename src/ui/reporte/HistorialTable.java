package ui.reporte;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.HistorialClinico;

public class HistorialTable extends JPanel {
    private final DefaultTableModel modelo;
    private final JTable tabla;

    public HistorialTable() {
        setLayout(new BorderLayout());

        String[] columnas = {"Tipo", "Fecha", "Detalle"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    public void actualizarTabla(List<HistorialClinico> historial) {
        modelo.setRowCount(0);
        for (HistorialClinico h : historial) {
            modelo.addRow(new Object[]{
                h.getIcono() + " " + h.getTipo(),
                h.getFecha(),
                h.getDetalle()
            });
        }
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
    }
}