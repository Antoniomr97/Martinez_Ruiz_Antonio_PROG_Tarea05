package biblioteca.modelo.dominio;

import java.util.ArrayList;

// Inicializamos la clase
public class Libro {

    // Definimos las variables constantes como la expresion regular a usar para obtener el ISBN y el Max de autores que puede tener un libro.

    /*
    ^ indica que empieza por el principio de la cadena
    \\d indica que es un numero real
    {13} indica que tiene que tener 13
    */
    public static final String ISBN_PATTERN = "^\\d{13}$";
    public static final int MAX_AUTORES = 3;

    // Iniciamos los atributos
    private String isbn;
    private String titulo;
    private int anio;
    private Categoria categoria;
    private int unidadesDisponibles;
    private ArrayList<Autor> autores = new ArrayList<>();

    // Creamos un constructor con sus valores
    public Libro(String isbn, String titulo, int anio, Categoria categoria, int unidadesDisponibles) {
        if (!isbn.matches(ISBN_PATTERN)) throw new IllegalArgumentException("ISBN inválido");
        if (anio <= 0) throw new IllegalArgumentException("Año inválido");
        if (unidadesDisponibles < 0) throw new IllegalArgumentException("Unidades no pueden ser negativas");
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.categoria = categoria;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    // Creamos un constructor desde un objeto y añadimos los autores
    public Libro(Libro libro) {
        this(libro.isbn, libro.titulo, libro.anio, libro.categoria, libro.unidadesDisponibles);
        this.autores = new ArrayList<>(libro.autores);
    }

    // Creamos un metodo, que comprueba si esta lleno el array de autores y entonces añade el autor que recibe
    public void addAutor(Autor autor) {
        if (autores.size() < MAX_AUTORES) autores.add(autor);
    }

    // Obtener el Autor por su posicion en el array
    public Autor[] getAutores() { return autores.toArray(new Autor[0]); }

    // Con el metodo for juntar en sb como String todos los autores separados por una , y un espacio
    public String autoresComoCadena() {
        StringBuilder sb = new StringBuilder();
        for (Autor a : autores) sb.append(a.toString()).append(", ");
        if (sb.length() > 0) sb.setLength(sb.length()-2);
        return sb.toString();
    }

    // Comprueba si las unidades disponibles de ese libro son mayores a 0 y si es asi les resta 1. Si no hay unidades se le muestra el correspondiente mensaje
    public void tomarPrestado() {
        if (unidadesDisponibles > 0) {
            unidadesDisponibles--;
        } else {
            System.out.println("No quedan unidades disponibles");
        }
    }

    // Devuelve la unidad lo que aumenta la unidad del libro seleccionado
    public void devolverUnidad() { unidadesDisponibles++; }

    // Obtienes cuantas unidades ahi mediante el get
    public int getUnidadesDisponibles() { return unidadesDisponibles; }

    // Getters
    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public Categoria getCategoria() { return categoria; }
    public int getAnio() { return anio; }

    // Comprobamos que sea una instancia de libro
    // Y ya se comparan las dos isbn
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Libro)) return false;
        return isbn.equals(((Libro)o).isbn);
    }

    // Sobreescribimos hasCode de manera simple ya que solo hay un elemento que hashear
    @Override
    public int hashCode() { return isbn.hashCode(); }

    @Override
    public String toString() {
        return titulo + " (" + anio + ") - ISBN: " + isbn + " - Unidades: " + unidadesDisponibles + " - Autores: " + autoresComoCadena();
    }
}
