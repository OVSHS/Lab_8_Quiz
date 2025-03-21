/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab_8_quiz;

/**
 *
 * @author Mario
 */
import java.util.NoSuchElementException;

public class GestionListaReproduccion {
    private static class Nodo {
        Cancion data;
        Nodo siguiente;
        public Nodo(Cancion data) {
            this.data = data;
            this.siguiente = null;
        }
    }
    public static class Cancion {
        private String nombre;
        private String artista;
        private String duracion;
        private String genero;
        private String rutaArchivo;
        private String rutaImagen;
        public Cancion(String nombre, String artista, String duracion, String genero, String rutaArchivo, String rutaImagen) {
            this.nombre = nombre;
            this.artista = artista;
            this.duracion = duracion;
            this.genero = genero;
            this.rutaArchivo = rutaArchivo;
            this.rutaImagen = rutaImagen;
        }
        public String getNombre() {
            return nombre;
        }
        public String getArtista() {
            return artista;
        }
        public String getDuracion() {
            return duracion;
        }
        public String getGenero() {
            return genero;
        }
        public String getRutaArchivo() {
            return rutaArchivo;
        }
        public String getRutaImagen() {
            return rutaImagen;
        }
        @Override
        public String toString() {
            return nombre + " - " + artista;
        }
    }
    private Nodo cabeza;
    public GestionListaReproduccion() {
        cabeza = null;
    }
    public void addSong(Cancion c) {
        Nodo nuevo = new Nodo(c);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo temp = cabeza;
            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = nuevo;
        }
    }
    public boolean removeSong(int index) {
        if (cabeza == null || index < 0) {
            return false;
        }
        if (index == 0) {
            cabeza = cabeza.siguiente;
            return true;
        }
        Nodo temp = cabeza;
        int i = 0;
        while (temp != null && i < index - 1) {
            temp = temp.siguiente;
            i++;
        }
        if (temp == null || temp.siguiente == null) {
            return false;
        }
        temp.siguiente = temp.siguiente.siguiente;
        return true;
    }
    public Cancion getSong(int index) {
        if (cabeza == null || index < 0) {
            throw new NoSuchElementException("Indice invalido o lista vacia");
        }
        Nodo temp = cabeza;
        int i = 0;
        while (temp != null) {
            if (i == index) {
                return temp.data;
            }
            temp = temp.siguiente;
            i++;
        }
        throw new NoSuchElementException("Indice fuera de alcance");
    }
    public int size() {
        Nodo temp = cabeza;
        int c = 0;
        while (temp != null) {
            c++;
            temp = temp.siguiente;
        }
        return c;
    }
}

