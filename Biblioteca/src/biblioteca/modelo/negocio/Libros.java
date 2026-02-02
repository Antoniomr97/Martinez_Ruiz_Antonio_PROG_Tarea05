package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Libro;

// Inicializamos la clase Libros y abrimos un array de Libro[] sera la estructura para Libros
public class Libros {
    private Libro[] libros;

    // Creamos un constructor al que le pasamos la capacidad y ya crea el array
    public Libros(int capacidad){
        libros = new Libro[capacidad];
    }

    // Añadimos un libro si cabe
    public void alta(Libro libro){
        for (int i = 0; i < libros.length; i++) {
            if (libros[i] == null) {
                libros[i] = libro;
                return; // Añadimos el libro y salimos
            }
        }
        System.out.println("No hay espacio para más libros.");
    }

    // Quitamos un libro del array
    public Boolean bajaLibro(Libro libro){
        for (int i = 0; i < libros.length; i++) {
            if (libros[i] != null && libros[i].equals(libro)) {
                libros[i] = null; // Eliminamos el libro
                return true;
            }
        }
        return false; // Si no se encontró
    }

    // Muestra todos los libros
    public Libro[] todos(){
        // Contamos cuántos libros hay
        int cantidad = 0;
        for (Libro l : libros) {
            if (l != null) cantidad++;
        }

        // Creamos un array solo con los libros existentes
        Libro[] resultado = new Libro[cantidad];
        int index = 0;
        for (Libro l : libros) {
            if (l != null) {
                resultado[index] = l;
                index++;
            }
        }
        return resultado;
    }
}
