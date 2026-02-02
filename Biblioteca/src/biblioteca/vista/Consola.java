package biblioteca.vista;

import biblioteca.modelo.dominio.*;
import biblioteca.modelo.negocio.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Consola {
    private Scanner input = new Scanner(System.in);

    private Libros libros;
    private Usuarios usuarios;
    private Prestamos prestamos;

    public Consola(Libros libros, Usuarios usuarios, Prestamos prestamos) {
        this.libros = libros;
        this.usuarios = usuarios;
        this.prestamos = prestamos;
    }

    // Metodo principal que inicia el menú
    public void iniciar() {
        while (true) {
            System.out.println("=== MENÚ PRINCIPAL ===");
            System.out.println("1. Alta libro");
            System.out.println("2. Baja libro");
            System.out.println("3. Listar libros");
            System.out.println("4. Alta usuario");
            System.out.println("5. Listar usuarios");
            System.out.println("6. Prestar libro");
            System.out.println("7. Devolver libro");
            System.out.println("8. Ver préstamos de un usuario");
            System.out.println("9. Ver historial de préstamos");
            System.out.println("10. Salir");
            System.out.print("Opción: ");
            String opcion = input.nextLine();

            switch (opcion) {
                case "1": altaLibro(); break;
                case "2": bajaLibro(); break;
                case "3": listarLibros(); break;
                case "4": altaUsuario(); break;
                case "5": listarUsuarios(); break;
                case "6": prestar(); break;
                case "7": devolver(); break;
                case "8": prestamosUsuario(); break;
                case "9": historial(); break;
                case "10": System.out.println("Saliendo..."); return;
                default: System.out.println("Opción inválida.\n");
            }
        }
    }

    /*
    Alta de libro
    Pedimos todos los datos, validando que sean correctos
    ISBN, año, categoría, unidades y autores tienen validación específica
   */
    private void altaLibro() {
        String titulo = "";
        while (titulo == null || titulo.trim().isEmpty()) {
            System.out.print("Título del libro: ");
            titulo = input.nextLine();
            if (titulo == null || titulo.trim().isEmpty()) {
                System.out.println("Error: el título no puede estar vacío.\n");
            }
        }

        String isbn = "";
        while (true) {
            System.out.print("ISBN (13 dígitos): ");
            isbn = input.nextLine();
            if (isbn != null && isbn.matches(Libro.ISBN_PATTERN)) break;
            System.out.println("ISBN inválido. Debe tener 13 dígitos.\n");
        }

        int anio = 0;
        while (anio <= 0) {
            System.out.print("Año de publicación: ");
            String entrada = input.nextLine();
            if (entrada == null || entrada.trim().isEmpty()) {
                System.out.println("Error: el año no puede estar vacío");
                continue;
            }
            try {
                anio = Integer.parseInt(entrada.trim());
                if (anio <= 0) System.out.println("Error: el año debe ser mayor que 0");
            } catch (NumberFormatException e) {
                System.out.println("Error: introduce un número válido");
            }
        }

        // Validamos la categoría
        Categoria categoria = null;
        while (categoria == null) {
            System.out.println("Selecciona la categoría:");
            Categoria[] categorias = Categoria.values();
            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + ". " + categorias[i]);
            }
            System.out.print("Opción (1-" + categorias.length + "): ");
            String entrada = input.nextLine();
            try {
                int opcion = Integer.parseInt(entrada.trim());
                if (opcion >= 1 && opcion <= categorias.length) {
                    categoria = categorias[opcion - 1];
                } else {
                    System.out.println("Error: opción inválida.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: introduce un número válido.\n");
            }
        }

        // Validamos las unidades
        int unidades = -1;
        while (unidades < 1) {
            System.out.print("Unidades disponibles: ");
            String entrada = input.nextLine();
            if (entrada == null || entrada.trim().isEmpty()) {
                System.out.println("Error: unidades no puede estar vacío");
                continue;
            }
            try {
                unidades = Integer.parseInt(entrada.trim());
                if (unidades < 0) System.out.println("Error: unidades debe ser 0 o más");
            } catch (NumberFormatException e) {
                System.out.println("Error: introduce un número válido");
            }
        }

        // Creamos el libro
        Libro libro = new Libro(isbn, titulo, anio, categoria, unidades);

        // Pedimos los autores
        int numAutores = 0;
        while (numAutores <= 0 || numAutores > Libro.MAX_AUTORES) {
            System.out.print("¿Cuántos autores tiene el libro? (1-" + Libro.MAX_AUTORES + "): ");
            String entrada = input.nextLine();
            try {
                numAutores = Integer.parseInt(entrada.trim());
                if (numAutores <= 0 || numAutores > Libro.MAX_AUTORES)
                    System.out.println("Error: número de autores inválido.\n");
            } catch (NumberFormatException e) {
                System.out.println("Error: introduce un número válido.\n");
            }
        }

        // Solicitamos los datos de cada autor
        for (int i = 1; i <= numAutores; i++) {
            System.out.println("Datos del autor " + i + ":");
            String nombre = "";
            while (nombre == null || nombre.trim().isEmpty()) {
                System.out.print("Nombre: ");
                nombre = input.nextLine();
                if (nombre == null || nombre.trim().isEmpty()) System.out.println("Error: el nombre no puede estar vacío");
            }

            String apellidos = "";
            while (apellidos == null || apellidos.trim().isEmpty()) {
                System.out.print("Apellidos: ");
                apellidos = input.nextLine();
                if (apellidos == null || apellidos.trim().isEmpty()) System.out.println("Error: los apellidos no pueden estar vacíos");
            }

            String nacionalidad = "";
            while (nacionalidad == null || nacionalidad.trim().isEmpty()) {
                System.out.print("Nacionalidad: ");
                nacionalidad = input.nextLine();
                if (nacionalidad == null || nacionalidad.trim().isEmpty()) System.out.println("Error: la nacionalidad no puede estar vacía");
            }

            // Creamos el autor y lo añadimos al libro
            libro.addAutor(new Autor(nombre.trim(), apellidos.trim(), nacionalidad.trim()));
        }

        // Damos de alta el libro en el sistema
        libros.alta(libro);
        System.out.println("Libro añadido.\n");
    }



    /*
      Baja de libro
      Buscamos por ISBN y lo eliminamos
     */
    private void bajaLibro() {
        String isbn = "";
        while (isbn == null || isbn.trim().isEmpty()) {
            System.out.print("ISBN del libro a dar de baja: ");
            isbn = input.nextLine();
            if (isbn == null || isbn.trim().isEmpty()) System.out.println("Error: ISBN no puede estar vacío");
        }

        Libro libro = null;
        for (Libro l : libros.todos()) if (l.getIsbn().equals(isbn)) libro = l;

        if (libro != null && libros.bajaLibro(libro)) {
            System.out.println("Libro eliminado.\n");
        } else {
            System.out.println("Libro no encontrado.\n");
        }
    }

    /*
      Listado de libros
      Mostramos todos los libros existentes
     */
    private void listarLibros() {
        Libro[] todos = libros.todos();
        if (todos.length == 0) {
            System.out.println("No hay libros registrados.\n");
            return;
        }
        System.out.println("=== LISTADO DE LIBROS ===");
        for (Libro l : todos) {
            System.out.println(l);
        }
        System.out.println();
    }

    /*
      Alta de usuario
      Validamos ID, nombre, email y dirección
     */
    private void altaUsuario() {
        String id = "";
        while (true) {
            System.out.print("ID Usuario (ej: AB123): ");
            id = input.nextLine();
            if (id != null && id.matches(Usuario.ID_PATTERN)) break;
            System.out.println("ID inválido.\n");
        }

        String nombre = "";
        while (nombre == null || nombre.trim().isEmpty()) {
            System.out.print("Nombre: ");
            nombre = input.nextLine();
            if (nombre == null || nombre.trim().isEmpty()) {
                System.out.println("Error: el nombre no puede estar vacío");
            }
        }

        String email = "";
        while (true) {
            System.out.print("Email: ");
            email = input.nextLine();
            if (email != null && email.matches(Usuario.EMAIL_BASIC)) break;
            System.out.println("Email inválido.\n");
        }

        String via = "";
        while (via == null || via.trim().isEmpty()) {
            System.out.print("Vía: ");
            via = input.nextLine();
            if (via == null || via.trim().isEmpty()) System.out.println("Error: la vía no puede estar vacía");
        }

        String numero = "";
        while (numero == null || numero.trim().isEmpty()) {
            System.out.print("Número: ");
            numero = input.nextLine();
            if (numero == null || numero.trim().isEmpty()) System.out.println("Error: el número no puede estar vacío");
        }

        String cp = "";
        while (true) {
            System.out.print("Código Postal: ");
            cp = input.nextLine();
            if (cp != null && cp.matches(Direccion.CP_PATTERN)) break;
            System.out.println("CP inválido.\n");
        }

        String localidad = "";
        while (localidad == null || localidad.trim().isEmpty()) {
            System.out.print("Localidad: ");
            localidad = input.nextLine();
            if (localidad == null || localidad.trim().isEmpty()) System.out.println("Error: la localidad no puede estar vacía");
        }

        // Creamos el objeto Dirección y Usuario
        Direccion direccion = new Direccion(via, numero, cp, localidad);
        Usuario usuario = new Usuario(id, nombre, email, direccion);
        usuarios.alta(usuario);
        System.out.println("Usuario añadido.\n");
    }

    /*
      Listado de usuarios
      Mostramos todos los usuarios existentes
     */
    private void listarUsuarios() {
        Usuario[] todos = usuarios.todos();
        if (todos.length == 0) {
            System.out.println("No hay usuarios registrados.\n");
            return;
        }
        System.out.println("=== LISTADO DE USUARIOS ===");
        for (Usuario u : todos) {
            System.out.println(u);
        }
        System.out.println();
    }

    /*
      Registrar préstamo
      Validamos existencia de libro y usuario y que haya unidades disponibles
     */
    private void prestar() {
        String isbn = "";
        while (isbn == null || isbn.trim().isEmpty()) {
            System.out.print("ISBN del libro: ");
            isbn = input.nextLine();
            if (isbn == null || isbn.trim().isEmpty()) System.out.println("Error: ISBN no puede estar vacío");
        }

        String id = "";
        while (id == null || id.trim().isEmpty()) {
            System.out.print("ID del usuario: ");
            id = input.nextLine();
            if (id == null || id.trim().isEmpty()) System.out.println("Error: ID de usuario no puede estar vacío");
        }

        Libro libro = null;
        for (Libro l : libros.todos()) if (l.getIsbn().equals(isbn)) libro = l;

        Usuario usuario = null;
        for (Usuario u : usuarios.todos()) if (u.getId().equals(id)) usuario = u;

        if (libro != null && usuario != null) {
            if (libro.getUnidadesDisponibles() > 0) {
                prestamos.prestar(libro, usuario, LocalDate.now());
                System.out.println("Préstamo registrado.\n");
            } else System.out.println("No hay unidades disponibles.\n");
        } else System.out.println("Libro o usuario no encontrado.\n");
    }

    /*
      Devolver libro
      Validamos existencia de libro y usuario
     */
    private void devolver() {
        String isbn = "";
        while (isbn == null || isbn.trim().isEmpty()) {
            System.out.print("ISBN del libro: ");
            isbn = input.nextLine();
            if (isbn == null || isbn.trim().isEmpty()) System.out.println("Error: ISBN no puede estar vacío");
        }

        String id = "";
        while (id == null || id.trim().isEmpty()) {
            System.out.print("ID del usuario: ");
            id = input.nextLine();
            if (id == null || id.trim().isEmpty()) System.out.println("Error: ID de usuario no puede estar vacío");
        }

        Libro libro = null;
        for (Libro l : libros.todos()) if (l.getIsbn().equals(isbn)) libro = l;

        Usuario usuario = null;
        for (Usuario u : usuarios.todos()) if (u.getId().equals(id)) usuario = u;

        if (libro != null && usuario != null) {
            boolean resultado = prestamos.devolver(libro, usuario, LocalDate.now());
            System.out.println(resultado ? "Libro devuelto correctamente.\n" : "No se pudo devolver.\n");
        } else {
            System.out.println("Libro o usuario no encontrado.\n");
        }
    }

    /*
      Ver préstamos activos de un usuario
      Validamos existencia del usuario
     */
    private void prestamosUsuario() {
        String id = "";
        while (id == null || id.trim().isEmpty()) {
            System.out.print("ID del usuario: ");
            id = input.nextLine();
            if (id == null || id.trim().isEmpty()) System.out.println("Error: ID de usuario no puede estar vacío");
        }

        Usuario usuario = null;
        for (Usuario u : usuarios.todos()) if (u.getId().equals(id)) usuario = u;

        if (usuario != null) {
            Prestamo[] pUsuario = prestamos.prestamosUsuario(usuario);
            if (pUsuario.length == 0) {
                System.out.println("No tiene préstamos activos.\n");
            } else {
                for (Prestamo p : pUsuario) System.out.println(p);
            }
        } else {
            System.out.println("Usuario no encontrado.\n");
        }
        System.out.println();
    }

    /*
      Historial completo de préstamos
     */
    private void historial() {
        Prestamo[] historial = prestamos.historico();
        if (historial.length == 0) {
            System.out.println("No hay préstamos registrados.\n");
            return;
        }
        System.out.println("--- HISTORIAL DE PRÉSTAMOS ---");
        for (Prestamo p : historial) System.out.println(p);
        System.out.println();
    }
}
