/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.agencia.objectdb.vistas;

import com.agencia.objectdb.modelos.Estudiante;
import com.agencia.objectdb.servicios.EstudianteService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EstudianteFrame extends JFrame{
   // Componentes del formulario

   private JTextField txtNombre;
   private JTextField txtApellido;
   private JTextField txtEmail;
   private JTextField txtEdad;

   //Botones
   private JButton btnGuardar;
   private JButton btnEliminar;
   private JButton btnActualizar;
   private JButton btnLimpiar;
   private JButton btnRefrescar;

   // Tabla
   private JTable tableEstudiantes;
   private EstudianteTableModel tableModel;

   // Servicios
   private EstudianteService estudianteService;
   private Estudiante estudianteSeleccionado;

   public EstudianteFrame() {
      estudianteService = new EstudianteService();
      initComponents();
      configureFrame();
      loadData();
   }

   private void initComponents() {
      setTitle("Sistema de Gestión de Esclavos (estudiantes) - ObjectDB");
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      setLayout(new BorderLayout());

      // Panel principal
      JPanel mainPanel = new JPanel(new BorderLayout(10 ,10));
      mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

      // Panel del formulario
      JPanel formPanel = createFormPanel();
      mainPanel.add(formPanel, BorderLayout.WEST);

      // Panel de la tabla
      JPanel tablePanel = createTablePanel();
      mainPanel.add(tablePanel, BorderLayout.CENTER);

      add(mainPanel);

      // Configurar evento

      configureEvents();
   }

    private JPanel createFormPanel() {
      JPanel panel = new JPanel(new GridBagLayout());
      panel.setBorder(new TitledBorder("Datos del Estudiante"));
      panel.setPreferredSize(new Dimension(300, 0));

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5,5,5,5);
      gbc.anchor = GridBagConstraints.WEST;

      //Nombre
      gbc.gridx = 0;
      gbc.gridy = 0;
      panel.add(new JLabel("Nombre:"), gbc);

      gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
      txtNombre = new JTextField(15);
      panel.add(txtNombre, gbc);

      //Apellido
      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.fill = GridBagConstraints.NONE;
      panel.add(new JLabel("Apellido:"), gbc);

      gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
      txtApellido = new JTextField(15);
      panel.add(txtApellido, gbc);

      // Email
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.fill = GridBagConstraints.NONE;
      panel.add(new JLabel("Email:"), gbc);

      gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
      txtEmail = new JTextField(15);
      panel.add(txtEmail, gbc);

      //Edad
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.fill = GridBagConstraints.NONE;
      panel.add(new JLabel("Edad:"), gbc);

      gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
      txtEdad = new JTextField(15);
      panel.add(txtEdad, gbc);


      // Panel de botones
      JPanel buttonPanel = new JPanel( new GridLayout(5,1,5,5));

      btnGuardar = new JButton("Guardar");
      btnGuardar.setBackground(new Color(76, 175,80));
      btnGuardar.setForeground(Color.BLACK);

      btnActualizar = new JButton("Actualizar");
      btnActualizar.setBackground(new Color(33, 150,243));
      btnActualizar.setForeground(Color.BLACK);
      btnActualizar.setEnabled(false);

      btnEliminar = new JButton("Eliminar");
      btnEliminar.setBackground(new Color(244, 67,54));
      btnEliminar.setForeground(Color.BLACK);
      btnEliminar.setEnabled(false);

      btnLimpiar = new JButton("Limpiar");
      btnRefrescar = new JButton("Refrescar");


      buttonPanel.add(btnGuardar);
      buttonPanel.add(btnActualizar);
      buttonPanel.add(btnEliminar);
      buttonPanel.add(btnLimpiar);
      buttonPanel.add(btnRefrescar);

      gbc.gridx = 0;
      gbc.gridy =4;
      gbc.gridwidth = 2;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      panel.add(buttonPanel, gbc);


      return panel;

   }

    private JPanel createTablePanel() {
      JPanel panel = new JPanel(new BorderLayout());
      panel.setBorder(new TitledBorder("Lista de Estudiantes"));

      tableModel = new EstudianteTableModel();
      tableEstudiantes = new JTable(tableModel);
      tableEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      tableEstudiantes.setRowHeight(25);

      /* Configurar ancho de columnas */

      //ID:
      tableEstudiantes.getColumnModel()
      .getColumn(0)
      .setPreferredWidth(50);

      //Nombre
      tableEstudiantes.getColumnModel()
      .getColumn(1)
      .setPreferredWidth(100);
      
      // Apellidos
      tableEstudiantes.getColumnModel()
      .getColumn(2)
      .setPreferredWidth(100);

      // Email
      tableEstudiantes.getColumnModel()
      .getColumn(3)
      .setPreferredWidth(200);

      // Edad
      tableEstudiantes.getColumnModel()
      .getColumn(4)
      .setPreferredWidth(60);

      JScrollPane scrollPane = new JScrollPane(tableEstudiantes);
      panel.add(scrollPane, BorderLayout.CENTER);

      return panel;
   }

	private void configureEvents() {

		// Eventos de cada selección de nuestra tabal
		tableEstudiantes.getSelectionModel().addListSelectionListener(
			e -> {
				if (!e.getValueIsAdjusting()) {
					int selectedRow = tableEstudiantes.getSelectedRow();
					if (selectedRow != -1) {
						estudianteSeleccionado = tableModel.getEstudianteAt(selectedRow);
						loadStudentData(estudianteSeleccionado);
						btnActualizar.setEnabled(true);
						btnEliminar.setEnabled(true);
					} else {
						clearSelection();
					}
				}
			}
		);

		btnGuardar.addActionListener(e -> guardarEstudiante());
        btnActualizar.addActionListener(e -> actualizarEstudiante());
		btnEliminar.addActionListener(e -> eliminarEstudiante());
		btnLimpiar.addActionListener(e -> limpiarFormulario());
		btnRefrescar.addActionListener(e -> loadData());

		// Evento de cierre de ventana
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing (WindowEvent e) {
				cerrarAplicacion();
			}

		});

	}

	private void configureFrame() {
		setSize (900, 600);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(800,500));
	}


	private void guardarEstudiante() {
		try {
			if(validarCampos()) {
                                Estudiante estudiante = new Estudiante(
                                    txtNombre.getText().trim(),
                                    txtApellido.getText().trim(),
                                    txtEmail.getText().trim(),
                                    Integer.parseInt(txtEdad.getText().trim())	
                                );

				estudianteService.guardaEstudiante(estudiante);
				loadData();
				limpiarFormulario();
				mostrarMensaje("Estudiante guardado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			mostrarMensaje("Error al guardar;" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


    private void eliminarEstudiante() {
        if(estudianteSeleccionado != null) {
            int opcion = JOptionPane.showConfirmDialog(
                this,
                "Estas seguro de eliminar a " + estudianteSeleccionado.getNombre() + " " + estudianteSeleccionado.getApellido() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == JOptionPane.YES_OPTION) {
                try {
                    estudianteService.eliminarEstudiante(estudianteSeleccionado.getId());
                    loadData();
                    limpiarFormulario();
                    mostrarMensaje(
                        "Estudiante eliminado correctamente", 
                        "Exito", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception e) {
                    mostrarMensaje(
                        "Error al eliminar: " + e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }


    private void actualizarEstudiante() {
        try {
            if (estudianteSeleccionado != null && validarCampos()) {
                estudianteSeleccionado.setNombre(txtNombre.getText().trim());
                estudianteSeleccionado.setApellido(txtApellido.getText().trim());
                estudianteSeleccionado.setEmail(txtEmail.getText().trim());
                estudianteSeleccionado.setEdad(Integer.parseInt(txtEdad.getText().trim()));

                estudianteService.actualizarEstudiante(estudianteSeleccionado);
                loadData();
                limpiarFormulario();
                mostrarMensaje(
                    "Estudiante actualizado correctamente", 
                    "Exito", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (Exception e) {
            mostrarMensaje(
                "Error al actualizar" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtEdad.setText("");
        clearSelection();
    }
    
    private void clearSelection() {
        tableEstudiantes.clearSelection();
        estudianteSeleccionado = null;
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
    
    private void loadData() {
        try {
            tableModel.setEstudiantes(estudianteService.obtenerTodoLosEstudiantes());
        } catch (Exception e) {
            mostrarMensaje(
                "Error al cargar los datos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadStudentData(Estudiante estudiante) {
        if(estudiante != null) {
            txtNombre.setText(estudiante.getNombre());
            txtApellido.setText(estudiante.getApellido());
            txtEmail.setText(estudiante.getEmail());
            txtEdad.setText(String.valueOf(estudiante.getEdad()));
        }
    }
    
        private boolean validarCampos() {
            if(txtNombre.getText().trim().isEmpty() || 
                txtApellido.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() ||
                txtEdad.getText().trim().isEmpty()) {
                mostrarMensaje("Todos los campos son obligatorios", "Error de validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            try {
                int edad = Integer.parseInt(txtEdad.getText().trim());
                if(edad <= 0 || edad > 7000) {
                    mostrarMensaje("La edad debe ser un número entre 1 y 7000", "Error de validación", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("La edad debe ser un número válido", "Error de validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            return true; // ← AGREGAR ESTO
        }

    
    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);

    }
    
	private void cerrarAplicacion() {
        int opcion = JOptionPane.showConfirmDialog(
            this, 
            "¿Estas seguro de salir de la aplicación?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION
        );

        if(opcion == JOptionPane.YES_OPTION) {
            estudianteService.cerrar();
            System.exit(opcion);
        }
	}

}