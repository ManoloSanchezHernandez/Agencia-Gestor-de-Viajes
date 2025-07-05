package com.agencia.objectdb.vistas;

import com.agencia.objectdb.modelos.Sucursal;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;

public class RegistroSucursalFrame extends JFrame {

    private JTextField txtCodigo, txtNombre, txtDireccion, txtCiudad, txtTelefono;
    private EntityManagerFactory emf;

    public RegistroSucursalFrame() {
        super("Registro de Sucursal");
        emf = Persistence.createEntityManagerFactory("GestorViajes");

        setLayout(new GridLayout(6, 2, 5, 5));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtDireccion = new JTextField();
        txtCiudad = new JTextField();
        txtTelefono = new JTextField();

        add(new JLabel("Código Sucursal:"));
        add(txtCodigo);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Dirección:"));
        add(txtDireccion);
        add(new JLabel("Ciudad:"));
        add(txtCiudad);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> registrarSucursal());
        add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void registrarSucursal() {
        EntityManager em = emf.createEntityManager();

        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String ciudad = txtCiudad.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || ciudad.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            em.getTransaction().begin();
            Sucursal nueva = new Sucursal(codigo, nombre, direccion, telefono, ciudad);
            em.persist(nueva);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(this, "Sucursal registrada.");
            dispose();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(this, "Error al registrar sucursal: " + ex.getMessage());
        } finally {
            em.close();
        }
    }
}
