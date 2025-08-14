package ui.recomendacion;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JPanel implements TableCellRenderer {
    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JButton editar = new JButton("✏️");
        JButton eliminar = new JButton("🗑️");
        editar.setFocusable(false);
        eliminar.setFocusable(false);
        editar.setEnabled(false);   // Solo visual
        eliminar.setEnabled(false); // Solo visual
        add(editar);
        add(eliminar);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        return this;
    }
}