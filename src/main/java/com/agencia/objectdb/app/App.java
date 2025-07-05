package com.agencia.objectdb.app;

import com.agencia.objectdb.vistas.EstudianteFrame;
import javax.swing.*;


public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Configurar Look and Feel

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al congigurar Look and Feel: " + e.getMessage());
        }

        // Ejecutar ene el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            EstudianteFrame estudianteFrame = new EstudianteFrame();
            estudianteFrame.setVisible(true);
        });
    }
    
}