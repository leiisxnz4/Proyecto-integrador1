package ui.consulta;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JPanel implements TableCellRenderer {

    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 16, 0));
        setBackground(Color.WHITE);

        JButton editar = new JButton("✏️");
        JButton eliminar = new JButton("🗑️");

        for (JButton btn : new JButton[]{editar, eliminar}) {
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            btn.setEnabled(false); // Solo decorativo
        }

        add(editar);
        add(eliminar);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}