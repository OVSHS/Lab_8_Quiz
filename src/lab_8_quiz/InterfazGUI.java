/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab_8_quiz;

/**
 *
 * @author Mario
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.NoSuchElementException;
public class InterfazGUI extends JFrame {
    private GestionListaReproduccion lista;
    private ReproduccionMusica reproductor;
    private DefaultListModel<GestionListaReproduccion.Cancion> modeloLista;
    private JList<GestionListaReproduccion.Cancion> jListCanciones;
    private JLabel lblImagen;
    private JLabel lblInfoCancion;
    public InterfazGUI() {
        super("Reproductor MP3 con Lista Enlazada");
        setSize(650, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        lista = new GestionListaReproduccion();
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        getContentPane().add(panelPrincipal);
        modeloLista = new DefaultListModel<>();
        jListCanciones = new JList<>(modeloLista);
        jListCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(jListCanciones);
        scroll.setPreferredSize(new Dimension(220, 0));
        panelPrincipal.add(scroll, BorderLayout.WEST);
        JPanel panelCentro = new JPanel(new BorderLayout());
        lblImagen = new JLabel("Imagen del Album/Cancion", SwingConstants.CENTER);
        lblInfoCancion = new JLabel("Info de la cancion", SwingConstants.CENTER);
        panelCentro.add(lblImagen, BorderLayout.CENTER);
        panelCentro.add(lblInfoCancion, BorderLayout.SOUTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnPlay = new JButton("Play");
        JButton btnPause = new JButton("Pause");
        JButton btnStop = new JButton("Stop");
        JButton btnAdd = new JButton("Add");
        JButton btnSelect = new JButton("Select");
        JButton btnRemove = new JButton("Remove");
        panelBotones.add(btnPlay);
        panelBotones.add(btnPause);
        panelBotones.add(btnStop);
        panelBotones.add(btnAdd);
        panelBotones.add(btnSelect);
        panelBotones.add(btnRemove);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        btnPlay.addActionListener(e -> playCancion());
        btnPause.addActionListener(e -> reproductor.pause());
        btnStop.addActionListener(e -> reproductor.stop());
        btnAdd.addActionListener(e -> agregarCancion());
        btnSelect.addActionListener(e -> seleccionarCancion());
        btnRemove.addActionListener(e -> eliminarCancion());
        jListCanciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                GestionListaReproduccion.Cancion c = jListCanciones.getSelectedValue();
                if (c != null) {
                    mostrarInfoCancion(c);
                }
            }
        });
    }
    private void playCancion() {
        if (reproductor.getRutaCancion() == null) {
            JOptionPane.showMessageDialog(this, "No hay cancion seleccionada");
            return;
        }
        reproductor.play();
    }
    private void seleccionarCancion() {
        int i = jListCanciones.getSelectedIndex();
        if (i >= 0) {
            try {
                GestionListaReproduccion.Cancion c = lista.getSong(i);
                reproductor.stop();
                reproductor.setRutaCancion(c.getRutaArchivo());
                mostrarInfoCancion(c);
            } catch (NoSuchElementException ex) {
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una cancion primero");
        }
    }
}
