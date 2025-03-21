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
            super("Reproductor MP3");
        setSize(650, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        lista = new GestionListaReproduccion();
        reproductor = new ReproduccionMusica();
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
        btnPause.addActionListener(e -> {
            if (reproductor == null) {
                reproductor = new ReproduccionMusica();
            }
            reproductor.pause();
        });
        btnStop.addActionListener(e -> {
            if (reproductor == null) {
                reproductor = new ReproduccionMusica();
            }
            reproductor.stop();
        });
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
                if (reproductor == null) {
                    reproductor = new ReproduccionMusica();
                }
                reproductor.stop();
                reproductor.setRutaCancion(c.getRutaArchivo());
                mostrarInfoCancion(c);
            } catch (NoSuchElementException ex) {
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una cancion primero");
        }
    }
    
    private void agregarCancion() {
        JFileChooser fileChooser = new JFileChooser("./musica");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos MP3", "mp3"));
        int opcion = fileChooser.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivoMp3 = fileChooser.getSelectedFile();
            String nombre = JOptionPane.showInputDialog(this, "Nombre de la cancion", archivoMp3.getName());
            if (nombre == null || nombre.isEmpty()) {
                nombre = archivoMp3.getName();
            }
            String artista = JOptionPane.showInputDialog(this, "Artista", "Elegir");
            if (artista == null) artista = "Elegir";
            String duracion = JOptionPane.showInputDialog(this, "Duracion ", "00:00");
            if (duracion == null) duracion = "00:00";
            String genero = JOptionPane.showInputDialog(this, "Genero musical", "Elegir");
            if (genero == null) genero = "Elegir";
            String rutaImagen = null;
            int resp = JOptionPane.showConfirmDialog(this, "Deseas agregar una imagen para esta cancion?");
            if (resp == JOptionPane.YES_OPTION) {
                JFileChooser imageChooser = new JFileChooser("./imagen");
                imageChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagenes (JPG, PNG)", "jpg", "png", "jpeg"));
                int opcionImg = imageChooser.showOpenDialog(this);
                if (opcionImg == JFileChooser.APPROVE_OPTION) {
                    File archivoImg = imageChooser.getSelectedFile();
                    rutaImagen = archivoImg.getAbsolutePath();
                }
            }
            GestionListaReproduccion.Cancion nueva = new GestionListaReproduccion.Cancion(
                nombre, artista, duracion, genero, archivoMp3.getAbsolutePath(), rutaImagen
            );
            lista.addSong(nueva);
            modeloLista.addElement(nueva);
        }
    }
    
    private void eliminarCancion() {
        int i = jListCanciones.getSelectedIndex();
        if (i >= 0) {
            GestionListaReproduccion.Cancion c = lista.getSong(i);
            if (c.getRutaArchivo().equals(reproductor.getRutaCancion())) {
                if (reproductor == null) {
                    reproductor = new ReproduccionMusica();
                }
                reproductor.stop();
                reproductor.setRutaCancion(null);
            }
            lista.removeSong(i);
            modeloLista.remove(i);
            lblImagen.setIcon(null);
            lblImagen.setText("Imagen del Album/Cancion");
            lblInfoCancion.setText("Info de la cancion");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una cancion para eliminar");
        }
    }
    
    private void mostrarInfoCancion(GestionListaReproduccion.Cancion c) {
        if (c == null) return;
        String info = "Nombre: " + c.getNombre() +
                      " | Artista: " + c.getArtista() +
                      " | Duracion: " + c.getDuracion() +
                      " | Genero: " + c.getGenero();
        lblInfoCancion.setText(info);
        if (c.getRutaImagen() != null) {
            ImageIcon icon = new ImageIcon(c.getRutaImagen());
            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setText("");
        } else {
            lblImagen.setIcon(null);
            lblImagen.setText("Sin imagen disponible");
        }
    }
}
