/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab_8_quiz;

/**
 *
 * @author Mario
 */
import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
public class ReproduccionMusica {
     private Player player;
    private FileInputStream fileInput;
    private BufferedInputStream buffer;
    private Thread threadReproduccion;
    private boolean enPausa = false;
    private long pausaPosicion = 0;
    private boolean reproduciendo = false;
    private String rutaCancionActual;
    public void setRutaCancion(String ruta) {
        this.rutaCancionActual = ruta;
    }
    public String getRutaCancion() {
        return rutaCancionActual;
    }
    public void play() {
        if (rutaCancionActual == null) {
            return;
        }
        if (enPausa) {
            reanudar();
            return;
        }
        if (reproduciendo) {
            return;
        }
        try {
            detenerInterno();
            fileInput = new FileInputStream(rutaCancionActual);
            buffer = new BufferedInputStream(fileInput);
            player = new Player(buffer);
            reproduciendo = true;
            threadReproduccion = new Thread(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                }
                reproduciendo = false;
                player = null;
            });
            threadReproduccion.start();
        } catch (Exception e) {
        }
    }
    public void pause() {
        if (player == null) {
            return;
        }
        if (enPausa) {
            return;
        }
        try {
            enPausa = true;
            if (fileInput != null) {
                pausaPosicion = fileInput.available();
            }
            player.close();
            player = null;
            reproduciendo = false;
        } catch (IOException e) {
        }
    }
    private void reanudar() {
        try {
            enPausa = false;
            fileInput = new FileInputStream(rutaCancionActual);
            long skip = fileInput.available() - pausaPosicion;
            if (skip > 0) {
                fileInput.skip(skip);
            }
            buffer = new BufferedInputStream(fileInput);
            player = new Player(buffer);
            reproduciendo = true;
            threadReproduccion = new Thread(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                }
                reproduciendo = false;
                player = null;
            });
            threadReproduccion.start();
        } catch (Exception e) {
        }
    }
    public void stop() {
        detenerInterno();
    }
    private void detenerInterno() {
        try {
            if (player != null) {
                player.close();
            }
            if (fileInput != null) {
                fileInput.close();
            }
        } catch (IOException e) {
        }
        enPausa = false;
        pausaPosicion = 0;
        reproduciendo = false;
        player = null;
        fileInput = null;
    }
}
