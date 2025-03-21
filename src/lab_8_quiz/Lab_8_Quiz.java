/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab_8_quiz;

import javax.swing.SwingUtilities;

/**
 *
 * @author Mario
 */
public class Lab_8_Quiz {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazGUI().setVisible(true);
        });
    }
}
