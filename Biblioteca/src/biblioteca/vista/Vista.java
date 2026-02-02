package biblioteca.vista;

import biblioteca.controlador.Controlador;
import biblioteca.modelo.dominio.*;
import biblioteca.utilidades.Entrada;

import java.time.LocalDate;

public class Vista {

    private Controlador controlador;

    public Vista() {
        // Inicialización
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    // --------------------- MÉTODOS PRINCIPALES ---------------------

    public void comenzar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = Entrada.entero();
            switch (opcion) {
                case 1: insertarUsuario(); break;
                case 2: borrarUsuario(); break;
                case 3: mostrarUsuarios(); break;
                case 4: insertarLibro(); break;
                case 5: borrarLibro(); break;
                case 6: mostrarLibros(); break;
                case 7: nuevoPrestamo(); break;
                case 8: devolverPrestamo(); break;
                case 9: mostrarPrestamosUsuarios(); break;
                case 10: mostrarPrestamos(); break;
                case 0: salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
        terminar();
    }

    public void terminar() {
        controlador.terminar();
    }

    private void mostrarMenu() {
        System.out.println("\n=== MENÚ BIBLIOTECA ===");
        System.out.println("1. Insertar usuario");
        System.out.println("2. Borrar usuario");
        System.out.println("3. Mostrar usuarios");
        System.out.println("4. Insertar libro");
        System.out.println("5. Borrar libro");
        System.out.println("6. Mostrar libros");
        System.out.println("7. Nuevo préstamo");
        System.out.println("8. Devolver préstamo");
        System.out.println("9. Mostrar préstamos por usuario");
        System.out.println("10. Mostrar todos los préstamos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // --------------------- USUARIOS ---------------------

    private void insertarUsuario() {
        System.out.println("\n--- Insertar Usuario ---");

        String id = "";
        while (id == null || !id.matches(Usuario.ID_PATTERN)) {
            System.out.print("ID (ej: AB123): ");
            id = Entrada.cadena();
            if (id == null || id.trim().isEmpty() || !id.matches(Usuario.ID_PATTERN))
                System.out.println("Error: ID inválido.\n");
        }

        String nombre = "";
        while (nombre == null || nombre.trim().isEmpty()) {
            System.out.print("Nombre (ej: Antonio): ");
            nombre = Entrada.cadena();
            if (nombre == null || nombre.trim().isEmpty())
                System.out.println("Error: el nombre no puede estar vacío.\n");
        }

        String email = "";
        while (email == null || !email.matches(Usuario.EMAIL_BASIC)) {
            System.out.print("Email (ej: antonio@mail.com): ");
            email = Entrada.cadena();
            if (email == null || email.trim().isEmpty() || !email.matches(Usuario.EMAIL_BASIC))
                System.out.println("Error: email inválido.\n");
        }

        // Dirección
        String via = "";
        while (via == null || via.trim().isEmpty()) {
            System.out.print("Vía (ej: Calle Mayor): ");
            via = Entrada.cadena();
            if (via == null || via.trim().isEmpty())
                System.out.println("Error: la vía no puede estar vacía.\n");
        }

        String numero = "";
        while (numero == null || numero.trim().isEmpty()) {
            System.out.print("Número (ej: 15): ");
            numero = Entrada.cadena();
            if (numero == null || numero.trim().isEmpty())
                System.out.println("Error: el número no puede estar vacío.\n");
        }

        String cp = "";
        while (cp == null || !cp.matches(Direccion.CP_PATTERN)) {
            System.out.print("Código postal (ej: 28001): ");
            cp = Entrada.cadena();
            if (cp == null || cp.trim().isEmpty() || !cp.matches(Direccion.CP_PATTERN))
                System.out.println("Error: código postal inválido.\n");
        }

        String localidad = "";
        while (localidad == null || localidad.trim().isEmpty()) {
            System.out.print("Localidad (ej: Madrid): ");
            localidad = Entrada.cadena();
            if (localidad == null || localidad.trim().isEmpty())
                System.out.println("Error: la localidad no puede estar vacía.\n");
        }

        controlador.alta(new Usuario(id, nombre, email, new Direccion(via, numero, cp, localidad)));
        mostrarMensaje("Usuario dado de alta correctamente.");
    }

    private void borrarUsuario() {
        System.out.println("\n--- Borrar Usuario ---");
        String id = "";
        while (id == null || id.trim().isEmpty()) {
            System.out.print("ID usuario (ej: AB123): ");
            id = Entrada.cadena();
            if (id == null || id.trim().isEmpty()) System.out.println("Error: ID no puede estar vacío.\n");
        }
        controlador.baja(new Usuario(id, null, null, null));
    }

    private void mostrarUsuarios() {
        System.out.println("\n--- Listado de Usuarios ---");
        Usuario[] usuarios = controlador.listadoUsuarios();
        if (usuarios.length == 0) System.out.println("No hay usuarios registrados.");
        else for (Usuario u : usuarios) System.out.println(u);
    }

    // --------------------- LIBROS ---------------------

    private void insertarLibro() {
        System.out.println("\n--- Insertar Libro ---");

        String isbn = "";
        while (isbn == null || !isbn.matches(Libro.ISBN_PATTERN)) {
            System.out.print("ISBN (13 dígitos, ej: 9781234567890): ");
            isbn = Entrada.cadena();
            if (isbn == null || !isbn.matches(Libro.ISBN_PATTERN))
                System.out.println("Error: ISBN inválido.\n");
        }

        String titulo = "";
        while (titulo == null || titulo.trim().isEmpty()) {
            System.out.print("Título (ej: Java Avanzado): ");
            titulo = Entrada.cadena();
            if (titulo == null || titulo.trim().isEmpty())
                System.out.println("Error: el título no puede estar vacío.\n");
        }

        int anio = 0;
        while (anio <= 0) {
            System.out.print("Año publicación (ej: 2023): ");
            try { anio = Entrada.entero(); } catch (Exception e) { anio = 0; }
            if (anio <= 0) System.out.println("Error: año inválido.\n");
        }

        // Categoría
        Categoria[] categorias = Categoria.values();
        int catIndex = -1;
        while (catIndex < 0 || catIndex >= categorias.length) {
            System.out.println("Categoría:");
            for (int i = 0; i < categorias.length; i++) System.out.println((i+1) + ". " + categorias[i]);
            System.out.print("Seleccione opción (ej: 3): ");
            try { catIndex = Entrada.entero() - 1; } catch (Exception e) { catIndex = -1; }
            if (catIndex < 0 || catIndex >= categorias.length) System.out.println("Error: opción inválida.\n");
        }

        int unidades = -1;
        while (unidades < 1) {
            System.out.print("Unidades disponibles (ej: 5): ");
            try { unidades = Entrada.entero(); } catch (Exception e) { unidades = -1; }
            if (unidades < 1) System.out.println("Error: debe haber al menos 1 unidad.\n");
        }

        Libro libro = new Libro(isbn, titulo, anio, categorias[catIndex], unidades);

        int numAutores = 0;
        while (numAutores < 1 || numAutores > Libro.MAX_AUTORES) {
            System.out.print("Número de autores (ej: 1): ");
            try { numAutores = Entrada.entero(); } catch (Exception e) { numAutores = 0; }
            if (numAutores < 1 || numAutores > Libro.MAX_AUTORES) System.out.println("Error: número inválido.\n");
        }

        for (int i = 0; i < numAutores; i++) {
            System.out.println("Autor " + (i+1));
            String nombre = "";
            while (nombre == null || nombre.trim().isEmpty()) {
                System.out.print("Nombre (ej: Juan): ");
                nombre = Entrada.cadena();
                if (nombre == null || nombre.trim().isEmpty()) System.out.println("Error: nombre vacío.\n");
            }

            String apellidos = "";
            while (apellidos == null || apellidos.trim().isEmpty()) {
                System.out.print("Apellidos (ej: Pérez): ");
                apellidos = Entrada.cadena();
                if (apellidos == null || apellidos.trim().isEmpty()) System.out.println("Error: apellidos vacíos.\n");
            }

            String nacionalidad = "";
            while (nacionalidad == null || nacionalidad.trim().isEmpty()) {
                System.out.print("Nacionalidad (ej: Española): ");
                nacionalidad = Entrada.cadena();
                if (nacionalidad == null || nacionalidad.trim().isEmpty()) System.out.println("Error: nacionalidad vacía.\n");
            }

            libro.addAutor(new Autor(nombre, apellidos, nacionalidad));
        }

        controlador.alta(libro);
        mostrarMensaje("Libro dado de alta correctamente.");
    }

    private void borrarLibro() {
        System.out.println("\n--- Borrar Libro ---");
        String isbn = "";
        while (isbn == null || isbn.trim().isEmpty()) {
            System.out.print("ISBN del libro (ej: 9781234567890): ");
            isbn = Entrada.cadena();
            if (isbn == null || isbn.trim().isEmpty()) System.out.println("Error: ISBN no puede estar vacío.\n");
        }
        controlador.baja(new Libro(isbn, null, 0, null, 0));
    }

    private void mostrarLibros() {
        System.out.println("\n--- Listado de Libros ---");
        Libro[] libros = controlador.listadoLibros();
        if (libros.length == 0) System.out.println("No hay libros registrados.");
        else for (Libro l : libros) System.out.println(l);
    }

    // --------------------- PRÉSTAMOS ---------------------

    private void nuevoPrestamo() {
        System.out.println("\n--- Nuevo Préstamo ---");
        String isbn = "";
        while (isbn == null || isbn.trim().isEmpty()) {
            System.out.print("ISBN libro (ej: 9781234567890): ");
            isbn = Entrada.cadena();
        }

        String id = "";
        while (id == null || id.trim().isEmpty()) {
            System.out.print("ID usuario (ej: AB123): ");
            id = Entrada.cadena();
        }

        Libro libro = controlador.buscar(new Libro(isbn, null, 0, null, 0));
        Usuario usuario = controlador.buscar(new Usuario(id, null, null, null));
        if (libro != null && usuario != null) controlador.prestar(libro, usuario, LocalDate.now());
        else System.out.println("Libro o usuario no encontrado.");
    }

    private void devolverPrestamo() {
        System.out.println("\n--- Devolver Préstamo ---");
        String isbn = "";
        while (isbn == null || isbn.trim().isEmpty()) {
            System.out.print("ISBN libro (ej: 9781234567890): ");
            isbn = Entrada.cadena();
        }

        String id = "";
        while (id == null || id.trim().isEmpty()) {
            System.out.print("ID usuario (ej: AB123): ");
            id = Entrada.cadena();
        }

        Libro libro = controlador.buscar(new Libro(isbn, null, 0, null, 0));
        Usuario usuario = controlador.buscar(new Usuario(id, null, null, null));
        if (libro != null && usuario != null) controlador.devolver(libro, usuario, LocalDate.now());
        else System.out.println("Libro o usuario no encontrado.");
    }

    private void mostrarPrestamosUsuarios() {
        System.out.println("\n--- Préstamos de Usuario ---");
        String id = "";
        while (id == null || id.trim().isEmpty()) {
            System.out.print("ID usuario (ej: AB123): ");
            id = Entrada.cadena();
        }
        Usuario usuario = controlador.buscar(new Usuario(id, null, null, null));
        if (usuario != null) {
            Prestamo[] prestamos = controlador.listadoPrestamos(usuario);
            if (prestamos.length == 0) System.out.println("No hay préstamos activos.");
            else for (Prestamo p : prestamos) System.out.println(p);
        } else System.out.println("Usuario no encontrado.");
    }

    private void mostrarPrestamos() {
        System.out.println("\n--- Todos los Préstamos ---");
        Prestamo[] prestamos = controlador.listadoPrestamos();
        if (prestamos.length == 0) System.out.println("No hay préstamos registrados.");
        else for (Prestamo p : prestamos) System.out.println(p);
    }

    // --------------------- MENSAJES ---------------------

    // He creado este metodo para mostrar los mensajes del controlador
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
