package biblioteca.modelo.dominio;

import java.util.ArrayList;

public class Libro {

    public static final String ISBN_PATTERN = "^\\d{13}$";

    private String isbn;
    private String titulo;
    private int anio;
    private Categoria categoria;
    private int unidadesDisponibles;
    private ArrayList<Autor> autores; // ahora usamos ArrayList

    // Constructor principal
    public Libro(String isbn, String titulo, int anio, Categoria categoria, int unidadesDisponibles) {
        if (!isbn.matches(ISBN_PATTERN)) throw new IllegalArgumentException("ISBN inválido");
        if (anio <= 0) throw new IllegalArgumentException("Año inválido");
        if (unidadesDisponibles < 0) throw new IllegalArgumentException("Unidades no pueden ser negativas");

        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.categoria = categoria;
        this.unidadesDisponibles = unidadesDisponibles;

        this.autores = new ArrayList<>();
    }

    // Constructor copia
    public Libro(Libro libro) {
        this(libro.isbn, libro.titulo, libro.anio, libro.categoria, libro.unidadesDisponibles);
        if (libro.autores != null) {
            this.autores.addAll(libro.autores); // copiamos autores del libro original
        }
    }

    // Añadir un autor
    public void addAutor(Autor autor) {
        if (autor != null && !autores.contains(autor)) {
            autores.add(autor);
        }
    }

    // Obtener autores
    public ArrayList<Autor> getAutores() {
        return new ArrayList<>(autores); // devolvemos copia para no exponer la lista interna
    }

    // Convierte autores a cadena
    public String autoresComoCadena() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < autores.size(); i++) {
            Autor a = autores.get(i);
            sb.append(a.toString()).append(" [").append(a.iniciales()).append("]");
            if (i < autores.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    // Manejo de unidades
    public void tomarPrestado() {
        if (unidadesDisponibles > 0) unidadesDisponibles--;
        else System.out.println("No quedan unidades disponibles");
    }

    public void devolverUnidad() {
        unidadesDisponibles++;
    }

    public int getUnidadesDisponibles() { return unidadesDisponibles; }

    // Getters básicos
    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public Categoria getCategoria() { return categoria; }
    public int getAnio() { return anio; }

    // Equals e hashCode basados en ISBN
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Libro)) return false;
        return isbn.equals(((Libro)o).isbn);
    }

    @Override
    public int hashCode() { return isbn.hashCode(); }

    @Override
    public String toString() {
        return titulo + " (" + anio + ") - ISBN: " + isbn +
                " - Unidades: " + unidadesDisponibles +
                " - Autores: " + autoresComoCadena();
    }
}
