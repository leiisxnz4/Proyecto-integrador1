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
        // 🎨 Colores suaves
        Color fondoRosita = new Color(250, 245, 255);
        Color bordeLila = new Color(200, 180, 230);
        Color encabezadoLila = new Color(230, 220, 250);

        setLayout(new BorderLayout());
        setBackground(fondoRosita);

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
        tabla.setBackground(Color.WHITE);
        tabla.setGridColor(bordeLila);
        tabla.setSelectionBackground(encabezadoLila);
        tabla.setSelectionForeground(Color.BLACK);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(encabezadoLila);
        tabla.getTableHeader().setForeground(new Color(80, 60, 120));
        tabla.getTableHeader().setBorder(BorderFactory.createLineBorder(bordeLila));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(bordeLila));
        scroll.getViewport().setBackground(fondoRosita);

        add(scroll, BorderLayout.CENTER);
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