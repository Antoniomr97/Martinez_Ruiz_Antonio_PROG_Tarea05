package biblioteca.vista;

import biblioteca.modelo.dominio.*;
import biblioteca.utilidades.Entrada;
import java.time.LocalDate;

// Esta clase se encarga de toda la interacción con el usuario por consola
// Es estática, no hace falta crear objetos de Consola
public class Consola {

    private Consola() {} // Constructor privado según diagrama, no queremos instancias

    // --- MENÚ PRINCIPAL ---
    public static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("   GESTIÓN DE BIBLIOTECA");
        System.out.println("=".repeat(30));

        // Recorremos todas las opciones del enum Opcion y las mostramos
        for (Opcion op : Opcion.values()) {
            System.out.printf("%2d.- %s%n", op.ordinal(), op.toString().replace("_", " "));
        }

        System.out.print("\nSeleccione una opción: "); // Pedimos que el usuario elija
    }

    // --- CREACIÓN DE USUARIOS ---
    public static Usuario nuevoUsuario(boolean paraBuscar) {
        // Pedimos el ID y validamos con el patrón definido en Usuario
        String id = leerValidado("ID Usuario (ej. AB123): ", Usuario.ID_PATTERN);

        if (paraBuscar) {
            // Si es solo para buscar, devolvemos un objeto "mínimo" con ID
            return new Usuario(id, "Buscando", "temp@mail.com", new Direccion("C", "0", "00000", "L"));
        }

        // Pedimos los datos completos
        String nombre = leerObligatorio("Nombre");
        String email = leerValidado("Email: ", Usuario.EMAIL_BASIC);

        System.out.println("--- Dirección ---");
        String via = leerObligatorio("Calle/Vía");
        String num = leerObligatorio("Número");
        String cp = leerValidado("Código Postal (5 dígitos): ", Direccion.CP_PATTERN);
        String loc = leerObligatorio("Localidad");

        return new Usuario(id, nombre, email, new Direccion(via, num, cp, loc));
    }

    // --- CREACIÓN DE LIBROS ---
    public static Libro nuevoLibro(boolean paraBuscar) {
        // Pedimos ISBN y validamos
        String isbn = leerValidado("ISBN (13 dígitos): ", Libro.ISBN_PATTERN);

        if (paraBuscar) {
            // Objeto mínimo para buscar solo por ISBN
            return new Libro(isbn, "Buscando", 2024, Categoria.OTROS, 0);
        }

        // Datos completos del libro
        String titulo = leerObligatorio("Título");
        System.out.print("Año publicación: ");
        int anio = Entrada.entero();

        // Elegimos categoría
        Categoria cat = elegirCategoria();

        System.out.print("Unidades: ");
        int uni = Entrada.entero();

        Libro libro = new Libro(isbn, titulo, anio, cat, uni);

        // Gestión de autores (mínimo 1, máximo 3)
        int numAutores;
        do {
            System.out.print("¿Cuántos autores tiene? (1-3): ");
            numAutores = Entrada.entero();
        } while (numAutores < 1 || numAutores > Libro.MAX_AUTORES);

        // Pedimos los datos de cada autor
        for (int i = 0; i < numAutores; i++) {
            System.out.println("Autor " + (i + 1) + ":");
            libro.addAutor(nuevoAutor());
        }

        return libro;
    }

    // Método auxiliar para crear un autor
    private static Autor nuevoAutor() {
        String nom = leerObligatorio("Nombre Autor");
        String ape = leerObligatorio("Apellidos Autor");
        String nac = leerObligatorio("Nacionalidad");
        return new Autor(nom, ape, nac);
    }

    // Metodo para leer la fecha (aquí siempre devuelve la fecha actual)
    public static LocalDate leerFecha() {
        return LocalDate.now();
    }

    // --- MÉTODOS AUXILIARES DE VALIDACIÓN ---
    // Pedimos un dato obligatorio (no vacío)
    private static String leerObligatorio(String msg) {
        while (true) {
            System.out.print(msg + ": ");
            String s = Entrada.cadena();
            if (s != null && !s.trim().isEmpty()) return s;
            System.out.println("Error: El campo no puede estar vacío.");
        }
    }

    // Pedimos un dato que cumpla un patrón regex
    private static String leerValidado(String msg, String regex) {
        while (true) {
            String s = leerObligatorio(msg); // Primero pedimos que no esté vacío
            if (s.matches(regex)) return s;   // Si cumple el patrón, devolvemos
            System.out.println("Error: El formato no coincide con el requerido.");
        }
    }

    // Elegir categoría de libro de entre las opciones disponibles
    private static Categoria elegirCategoria() {
        while (true) {
            for (Categoria c : Categoria.values()) {
                System.out.println(c.ordinal() + ".- " + c);
            }
            System.out.print("Elija categoría: ");
            int op = Entrada.entero();
            if (op >= 0 && op < Categoria.values().length) return Categoria.values()[op];
        }
    }
}
