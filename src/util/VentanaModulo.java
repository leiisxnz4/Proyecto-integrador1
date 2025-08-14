package util;

import java.awt.*;
import javax.swing.*;

public class VentanaModulo extends JFrame {
    public VentanaModulo(String titulo, JPanel contenido) {
        setTitle(titulo);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(contenido);
        getContentPane().setBackground(new Color(250, 245, 255));
        setVisible(true);
    }
}