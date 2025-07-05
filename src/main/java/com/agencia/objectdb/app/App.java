package com.agencia.objectdb.app;

import com.agencia.objectdb.vistas.MenuPrincipalFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error con Look & Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            MenuPrincipalFrame menu = new MenuPrincipalFrame();
            menu.setVisible(true);
        });
    }
}
