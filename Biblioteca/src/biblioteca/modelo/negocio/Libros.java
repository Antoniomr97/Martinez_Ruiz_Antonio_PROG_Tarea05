package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Libro;
import java.util.ArrayList;

public class Libros {
    // Lista dinámica de libros
    private ArrayList<Libro> libros;

    // Constructor: inicializamos la lista
    public Libros() {
        this.libros = new ArrayList<>();
    }

    // Metodo para añadir un libro
    public void alta(Libro libro) {
        // Comprobamos que no introduzcan un null
        if (libro == null) {
            System.out.println("No puede registrar libros vacíos.");
            return;
        }

        // Comprobamos si el libro ya existe
        if (buscar(libro) != null) {
            System.out.println("El libro ya está registrado.");
            return;
        }

        // Ya no hay límite de capacidad, añadimos directamente
        libros.add(libro);
    }

    // Metodo para eliminar un libro
    public boolean bajaLibro(Libro libro) {
        return libros.remove(libro);
    }

    // Metodo para buscar un libro
    public Libro buscar(Libro libro) {
        if (libro == null) {
            return null;
        }

        for (Libro l : libros) {
            if (l.equals(libro)) return l;
        }
        return null;
    }

    // Metodo para obtener todos los libros en forma de array
    public Libro[] todos() {
        return libros.toArray(new Libro[0]);
    }
}
