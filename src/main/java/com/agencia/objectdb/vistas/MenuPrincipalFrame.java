package com.agencia.objectdb.vistas;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalFrame extends JFrame {

    public MenuPrincipalFrame() {
        super("Menú Principal");

        setLayout(new GridLayout(3, 1, 10, 10));  // Cambiado a 3 filas

        JButton btnTurista = new JButton("Registrar Turista");
        JButton btnSucursal = new JButton("Registrar Sucursal");
        JButton btnGestionTuristas = new JButton("Gestión de Turistas"); // 🔹 Nuevo botón

        btnTurista.addActionListener(e -> {
            RegistroTuristaFrame turistaFrame = new RegistroTuristaFrame();
            turistaFrame.setVisible(true);
        });

        btnSucursal.addActionListener(e -> {
            RegistroSucursalFrame sucursalFrame = new RegistroSucursalFrame();
            sucursalFrame.setVisible(true);
        });

        btnGestionTuristas.addActionListener(e -> {
            TuristaFrame frame = new TuristaFrame();  // 🔹 Lanza la vista completa de gestión
            frame.setVisible(true);
        });

        add(btnTurista);
        add(btnSucursal);
        add(btnGestionTuristas); // 🔹 Agregado al menú

        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
