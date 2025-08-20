package ui.consulta;

import dao.ConsultaMedicaDAO;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import models.ConsultaMedica;

public class TableConsultaMedica extends JPanel {
    private final JTable tabla;
    private final DefaultTableModel modelo;
    private final DateTimeFormatter fechaFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private FormConsultaMedica form;

    public TableConsultaMedica(JFrame ventanaActual) {
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
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> {
            if (ventanaActual != null) {
                ventanaActual.dispose();
            }
        });
        panelSuperior.add(btnRegresar, BorderLayout.WEST);

        JLabel titulo = new JLabel("Consultas Médicas");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(new Color(80, 60, 120));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelSuperior.add(titulo, BorderLayout.CENTER);

        // 🧩 Tabla y modelo
        String[] columnas = {
            "ID", "Matrícula", "Nombre", "Motivo", "Signos Vitales",
            "Medicamentos", "Observaciones", "Fecha", "Acciones"
        };

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
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

        tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("Acciones").setCellEditor(new ButtonEditor(tabla, this));
        tabla.getColumn("Acciones").setPreferredWidth(100);

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
        panelFlotante.setPreferredSize(new Dimension(800, 420));
        panelFlotante.setBackground(panelSuave);
        panelFlotante.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panelFlotante.add(scroll, BorderLayout.CENTER);

        fondoCentral.add(panelFlotante, gbc);

        // 🧩 Ensamblar todo
        add(panelSuperior, BorderLayout.NORTH);
        add(fondoCentral, BorderLayout.CENTER);

        cargarDatos();
    }

    public void setFormulario(FormConsultaMedica form) {
        this.form = form;
    }

    public FormConsultaMedica getFormulario() {
        return form;
    }

    public void cargarDatos() {
        modelo.setRowCount(0);
        try {
            List<ConsultaMedica> lista = new ConsultaMedicaDAO().listar();
            for (ConsultaMedica consulta : lista) {
                modelo.addRow(new Object[]{
                    consulta.getId(),
                    consulta.getMatricula(),
                    consulta.getNombreEstudiante(),
                    consulta.getMotivo(),
                    consulta.getSignosVitales(),
                    consulta.getMedicamentos(),
                    consulta.getObservaciones(),
                    consulta.getFecha().format(fechaFmt),
                    "" // ✅ Sin texto, solo íconos
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al cargar datos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ConsultaMedica obtenerConsultaEnFila(int fila) {
        try {
            int id = (int) modelo.getValueAt(fila, 0);
            String matricula = (String) modelo.getValueAt(fila, 1);
            String nombre = (String) modelo.getValueAt(fila, 2);
            String motivo = (String) modelo.getValueAt(fila, 3);
            String signos = (String) modelo.getValueAt(fila, 4);
            String medicamentos = (String) modelo.getValueAt(fila, 5);
            String observaciones = (String) modelo.getValueAt(fila, 6);
            String fechaStr = (String) modelo.getValueAt(fila, 7);

            ConsultaMedica c = new ConsultaMedica();
            c.setId(id);
            c.setMatricula(matricula);
            c.setNombreEstudiante(nombre);
            c.setMotivo(motivo);
            c.setSignosVitales(signos);
            c.setMedicamentos(medicamentos);
            c.setObservaciones(observaciones);
            c.setFecha(LocalDate.parse(fechaStr, fechaFmt));
            return c;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al obtener consulta: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void eliminarConsulta(int fila) {
        int id = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showOptionDialog(
            this,
            "¿Estás segura de que deseas eliminar la consulta #" + id + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new Object[]{"Eliminar", "Cancelar"},
            "Cancelar"
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                new ConsultaMedicaDAO().eliminar(id);
                cargarDatos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error al eliminar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}