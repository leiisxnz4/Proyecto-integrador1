package ui.cita;

import dao.CitaMedicaDAO;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import models.CitaMedica;

public class CitaMedicaTable extends JPanel {
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private CitaMedicaForm formularioRef;
    private final DateTimeFormatter fechaFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private final DateTimeFormatter horaFmt = DateTimeFormatter.ofPattern("hh:mm a");

    public CitaMedicaTable(JFrame ventanaActual) {
        // 🎨 Colores personalizados
        Color fondoLila = new Color(245, 240, 255);
        Color panelSuave = new Color(250, 245, 255);
        Color bordeLila = new Color(200, 180, 230);
        Color encabezadoLila = new Color(230, 220, 250);

        setLayout(new BorderLayout());
        setBackground(fondoLila);

        // 🔝 Panel superior con botón regresar y título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondoLila);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 40));

        JButton btnRegresar = new JButton("← Regresar");
        btnRegresar.setFocusPainted(false);
        btnRegresar.setBackground(encabezadoLila);
        btnRegresar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnRegresar.addActionListener(e -> {
            if (ventanaActual != null) {
                ventanaActual.dispose();
            }
        });
        panelSuperior.add(btnRegresar, BorderLayout.WEST);

        JLabel titulo = new JLabel("Registros de Citas");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(new Color(80, 60, 120));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelSuperior.add(titulo, BorderLayout.CENTER);

        // 🧩 Tabla y modelo
        String[] columnas = {"ID", "Matrícula", "Nombre", "Fecha", "Hora", "Motivo", "Acciones"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setBackground(Color.WHITE);
        tabla.setGridColor(bordeLila);
        tabla.setSelectionBackground(encabezadoLila);
        tabla.setSelectionForeground(Color.BLACK);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(encabezadoLila);
        header.setForeground(new Color(80, 60, 120));
        header.setBorder(BorderFactory.createLineBorder(bordeLila));

        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(130);

        tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("Acciones").setCellEditor(new ButtonEditor(tabla, this));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(bordeLila));
        scroll.getViewport().setBackground(fondoLila);

        // 🧼 Panel flotante
        JPanel fondoCentral = new JPanel(new GridBagLayout());
        fondoCentral.setBackground(fondoLila);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 10, 20);

        JPanel panelFlotante = new JPanel(new BorderLayout());
        panelFlotante.setPreferredSize(new Dimension(800, 400));
        panelFlotante.setBackground(panelSuave);
        panelFlotante.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panelFlotante.add(scroll, BorderLayout.CENTER);

        fondoCentral.add(panelFlotante, gbc);

        // 🧩 Ensamblar todo
        add(panelSuperior, BorderLayout.NORTH);
        add(fondoCentral, BorderLayout.CENTER);

        cargarDatos();
    }

    public void setFormulario(CitaMedicaForm form) {
        this.formularioRef = form;
    }

    public CitaMedicaForm getFormulario() {
        return formularioRef;
    }

    public void cargarDatos() {
        modelo.setRowCount(0);
        try {
            List<CitaMedica> lista = new CitaMedicaDAO().listar();
            for (CitaMedica c : lista) {
                modelo.addRow(new Object[]{
                    c.getId(),
                    c.getMatricula(),
                    c.getNombreEstudiante(),
                    c.getFecha().format(fechaFmt),
                    c.getHora().format(horaFmt),
                    c.getMotivo(),
                    "Editar / Eliminar"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al cargar citas: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public CitaMedica obtenerCitaEnFila(int fila) {
        try {
            int id = (int) modelo.getValueAt(fila, 0);
            String matricula = (String) modelo.getValueAt(fila, 1);
            String nombre = (String) modelo.getValueAt(fila, 2);
            String fechaStr = (String) modelo.getValueAt(fila, 3);
            String horaStr = (String) modelo.getValueAt(fila, 4);
            String motivo = (String) modelo.getValueAt(fila, 5);

            CitaMedica cita = new CitaMedica();
            cita.setId(id);
            cita.setMatricula(matricula);
            cita.setNombreEstudiante(nombre);
            cita.setFecha(LocalDate.parse(fechaStr, fechaFmt));
            cita.setHora(LocalTime.parse(horaStr, horaFmt));
            cita.setMotivo(motivo);
            return cita;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al obtener cita: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void eliminarCita(int fila) {
        int id = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showOptionDialog(
            this,
            "¿Eliminar la cita con ID " + id + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new Object[]{"Sí", "No"},
            "No"
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                new CitaMedicaDAO().eliminar(id);
                cargarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error al eliminar cita: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}