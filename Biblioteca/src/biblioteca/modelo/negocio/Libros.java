package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Libro;

public class Libros {
    private Libro[] libros;

    public Libros(int capacidad) {
        this.libros = new Libro[capacidad];
    }

    public void alta(Libro libro) {
        for (int i = 0; i < libros.length; i++) {
            if (libros[i] == null) {
                libros[i] = libro;
                return;
            }
        }
        System.out.println("No hay hueco para más libros.");
    }

    public boolean bajaLibro(Libro libro) {
        for (int i = 0; i < libros.length; i++) {
            if (libros[i] != null && libros[i].equals(libro)) {
                // Desplazamos a la izquierda para evitar huecos nulos intermedios
                for (int j = i; j < libros.length - 1; j++) {
                    libros[j] = libros[j + 1];
                }
                libros[libros.length - 1] = null;
                return true;
            }
        }
        return false;
    }

    public Libro buscar(Libro libro) {
        for (Libro l : libros) {
            if (l != null && l.equals(libro)) return l;
        }
        return null;
    }

    public Libro[] todos() {
        int count = 0;
        for (Libro l : libros) if (l != null) count++;
        Libro[] res = new Libro[count];
        int index = 0;
        for (Libro l : libros) {
            if (l != null) res[index++] = l;
        }
        return res;
    }
}