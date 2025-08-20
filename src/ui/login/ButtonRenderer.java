package ui.login;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer(String texto) {
        setText(texto);
        setOpaque(true);
        setBackground(new Color(230, 220, 250));
        setFont(new Font("SansSerif", Font.PLAIN, 12));
        setFocusPainted(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}