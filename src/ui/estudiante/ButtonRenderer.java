package ui.estudiante;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ButtonRenderer extends JPanel implements TableCellRenderer {
    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JButton editar = new JButton("✏️");
        JButton eliminar = new JButton("🗑️");
        editar.setEnabled(false);
        eliminar.setEnabled(false);
        add(editar);
        add(eliminar);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}