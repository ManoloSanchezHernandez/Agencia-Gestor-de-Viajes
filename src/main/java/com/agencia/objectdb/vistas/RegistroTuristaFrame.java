package com.agencia.objectdb.vistas;

import com.agencia.objectdb.modelos.Sucursal;
import com.agencia.objectdb.modelos.Turista;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RegistroTuristaFrame extends JFrame {

    private JTextField txtCodigo, txtNombre, txtApellidos, txtTelefono;
    private JComboBox<Sucursal> cmbSucursal;
    private EntityManagerFactory emf;

    public RegistroTuristaFrame() {
        super("Registro de Turista");
        emf = Persistence.createEntityManagerFactory("GestorViajes");

        setLayout(new GridLayout(6, 2, 5, 5));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtApellidos = new JTextField();
        txtTelefono = new JTextField();
        cmbSucursal = new JComboBox<>();

        cargarSucursales();

        add(new JLabel("Código Turista:"));
        add(txtCodigo);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Apellidos:"));
        add(txtApellidos);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(new JLabel("Sucursal:"));
        add(cmbSucursal);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> registrarTurista());
        add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void cargarSucursales() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Sucursal> sucursales = em.createQuery("SELECT s FROM Sucursal s", Sucursal.class).getResultList();
            for (Sucursal s : sucursales) {
                cmbSucursal.addItem(s);
            }
        } finally {
            em.close();
        }
    }

    private void registrarTurista() {
        EntityManager em = emf.createEntityManager();

        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String telefono = txtTelefono.getText().trim();
        Sucursal sucursal = (Sucursal) cmbSucursal.getSelectedItem();

        if (codigo.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() || sucursal == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            em.getTransaction().begin();
            Turista nuevo = new Turista(codigo, nombre, apellidos, telefono, sucursal);
            em.persist(nuevo);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(this, "Turista registrado.");
            dispose();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            JOptionPane.showMessageDialog(this, "Error al registrar turista: " + ex.getMessage());
        } finally {
            em.close();
        }
    }
}
