package ui.reporte;

import java.awt.*;
import javax.swing.*;

public class HistorialForm extends JPanel {
    private final JTextField matriculaField;
    private final JButton buscarBtn;

    public HistorialForm() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        matriculaField = new JTextField(15);
        buscarBtn = new JButton("Buscar Historial");

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Matrícula:"), gbc);

        gbc.gridx = 1;
        add(matriculaField, gbc);

        gbc.gridx = 2;
        add(buscarBtn, gbc);
    }

    public JTextField getMatriculaField() {
        return matriculaField;
    }

    public JButton getBuscarBtn() {
        return buscarBtn;
    }
}