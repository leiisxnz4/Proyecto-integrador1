package ui.login;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private boolean clicked;
    private int row;
    private final Consumer<Integer> accion;

    public ButtonEditor(JCheckBox checkBox, String label, Consumer<Integer> accion) {
        super(checkBox);
        this.accion = accion;
        button = new JButton(label);
        button.setBackground(new Color(230, 220, 250));
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
        this.row = row;
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked && accion != null) {
            accion.accept(row);
        }
        clicked = false;
        return "";
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
}