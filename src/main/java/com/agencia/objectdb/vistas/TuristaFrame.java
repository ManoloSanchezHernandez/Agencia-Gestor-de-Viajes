package com.agencia.objectdb.vistas;

import com.agencia.objectdb.modelos.Turista;
import com.agencia.objectdb.servicios.TuristaService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TuristaFrame extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtTelefono;
    private JTextField txtCodigoTurista;

    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnLimpiar;
    private JButton btnRefrescar;

    private JTable tableTurista;
    private TuristaTableModel tableModel;

    private TuristaService turistaService;
    private Turista turistaSeleccionado;

    public TuristaFrame() {
        turistaService = new TuristaService();
        initComponents();
        configureFrame();
        loadData();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión de Turistas - ObjectDB");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.WEST);

        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);

        configureEvents();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Datos del Turista"));
        panel.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Código Turista
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtCodigoTurista = new JTextField(15);
        panel.add(txtCodigoTurista, gbc);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNombre = new JTextField(15);
        panel.add(txtNombre, gbc);

        // Apellidos
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Apellidos:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtApellidos = new JTextField(15);
        panel.add(txtApellidos, gbc);

        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtTelefono = new JTextField(15);
        panel.add(txtTelefono, gbc);

        // Botones
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnRefrescar = new JButton("Refrescar");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(btnRefrescar);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Lista de Turistas"));

        tableModel = new TuristaTableModel();
        tableTurista = new JTable(tableModel);
        tableTurista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTurista.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tableTurista);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void configureEvents() {
        tableTurista.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableTurista.getSelectedRow();
                if (selectedRow != -1) {
                    turistaSeleccionado = tableModel.getTuristaAt(selectedRow);
                    loadTuristaData(turistaSeleccionado);
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                } else {
                    clearSelection();
                }
            }
        });

        btnGuardar.addActionListener(e -> guardarTurista());
        btnActualizar.addActionListener(e -> actualizarTurista());
        btnEliminar.addActionListener(e -> eliminarTurista());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnRefrescar.addActionListener(e -> loadData());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarAplicacion();
            }
        });
    }

    private void configureFrame() {
        setSize(900, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 500));
    }

    private void guardarTurista() {
        try {
            if (validarCampos()) {
                Turista turista = new Turista();
                turista.setCodigoTurista(txtCodigoTurista.getText().trim());
                turista.setNombre(txtNombre.getText().trim());
                turista.setApellidos(txtApellidos.getText().trim());
                turista.setTelefono(txtTelefono.getText().trim());

                turistaService.guardarTurista(turista);
                loadData();
                limpiarFormulario();
                mostrarMensaje("Turista guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            mostrarMensaje("Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTurista() {
        try {
            if (turistaSeleccionado != null && validarCampos()) {
                turistaSeleccionado.setCodigoTurista(txtCodigoTurista.getText().trim());
                turistaSeleccionado.setNombre(txtNombre.getText().trim());
                turistaSeleccionado.setApellidos(txtApellidos.getText().trim());
                turistaSeleccionado.setTelefono(txtTelefono.getText().trim());

                turistaService.actualizarTurista(turistaSeleccionado);
                loadData();
                limpiarFormulario();
                mostrarMensaje("Turista actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            mostrarMensaje("Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarTurista() {
        if (turistaSeleccionado != null) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar a " + turistaSeleccionado.getNombre() + " " + turistaSeleccionado.getApellidos() + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                try {
                    turistaService.eliminarTurista(turistaSeleccionado.getId());
                    loadData();
                    limpiarFormulario();
                    mostrarMensaje("Turista eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    mostrarMensaje("Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void limpiarFormulario() {
        txtCodigoTurista.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        clearSelection();
    }

    private void clearSelection() {
        tableTurista.clearSelection();
        turistaSeleccionado = null;
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void loadTuristaData(Turista turista) {
        if (turista != null) {
            txtCodigoTurista.setText(turista.getCodigoTurista());
            txtNombre.setText(turista.getNombre());
            txtApellidos.setText(turista.getApellidos());
            txtTelefono.setText(turista.getTelefono());
        }
    }

    private void loadData() {
        try {
            tableModel.setTuristas(turistaService.obtenerTodosLosTuristas());
        } catch (Exception e) {
            mostrarMensaje("Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (txtCodigoTurista.getText().trim().isEmpty()
                || txtNombre.getText().trim().isEmpty()
                || txtApellidos.getText().trim().isEmpty()
                || txtTelefono.getText().trim().isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    private void cerrarAplicacion() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            turistaService.cerrar();
            System.exit(0);
        }
    }
}
