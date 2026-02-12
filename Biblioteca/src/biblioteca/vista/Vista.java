package biblioteca.vista;

import biblioteca.controlador.Controlador;
import biblioteca.modelo.dominio.*;
import biblioteca.utilidades.Entrada;
import java.util.ArrayList;
import java.util.Comparator;

// La Vista es la capa que interactúa con el usuario
// Llama a Consola para leer datos y al Controlador para manipular los datos
public class Vista {

    private Controlador controlador; // Guardamos la referencia al controlador

    // Setter para asignar el controlador
    public void setControlador(Controlador controlador) {
        if (controlador != null) this.controlador = controlador;
    }

    // --- METODO PRINCIPAL ---
    public void comenzar() {
        int opcionElegida;
        do {
            Consola.mostrarMenu();   // Mostramos el menú por consola
            opcionElegida = Entrada.entero();  // Leemos la opción que el usuario ha elegido

            // Comprobamos que la opción elegida sea válida
            if (opcionElegida >= 0 && opcionElegida < Opcion.values().length) {
                // Convertimos el número elegido a la constante del enum Opcion
                ejecutarOpcion(Opcion.values()[opcionElegida]);
            } else {
                System.out.println("Opción no válida.");
            }
        } while (opcionElegida != Opcion.SALIR.ordinal());
        // Repetimos hasta que el usuario elija salir (SALIR es una constante del enum)
    }

    // Ejecuta la opción seleccionada en el menú
    private void ejecutarOpcion(Opcion opcion) {
        try {
            switch (opcion) {
                case INSERTAR_USUARIO: insertarUsuario(); break;
                case BORRAR_USUARIO: borrarUsuario(); break;
                case MOSTRAR_USUARIO: mostrarUsuarios(); break;
                case INSERTAR_LIBRO: insertarLibro(); break;
                case BORRAR_LIBRO: borrarLibro(); break;
                case MOSTRAR_LIBRO: mostrarLibros(); break;
                case NUEVO_PRESTAMO: nuevoPrestamo(); break;
                case DEVOLVER_PRESTAMOS: devolverPrestamo(); break;
                case MOSTRAR_PRESTAMOS: mostrarPrestamos(); break;
                case MOSTRAR_PRESTAMOS_USUARIOS: mostrarPrestamosUsuario(); break;
                case SALIR: terminar(); break;
            }
        } catch (Exception e) {
            // Si algo falla, mostramos el mensaje de error
            System.out.println("Error en la operación: " + e.getMessage());
        }
    }

    // Cierre de la Vista
    public void terminar() {
        System.out.println("Termina Vista");
        controlador.terminar(); // Llama al cierre en cascada del Controlador y Modelo
    }

    // ------------------ MÉTODOS DE USUARIOS ------------------

    // Insertar un usuario
    private void insertarUsuario() {
        Usuario nuevoUsuario = Consola.nuevoUsuario(false); // Pedimos los datos completos al usuario

        // Comprobamos si ya existe un usuario con la misma ID
        if (controlador.buscar(nuevoUsuario) != null) {
            System.out.println("Error: Ya existe un usuario con la ID " + nuevoUsuario.getId());
        } else {
            controlador.alta(nuevoUsuario); // Damos de alta al nuevo usuario
            System.out.println("Usuario registrado con éxito.");
        }
    }

    // Borrar un usuario
    private void borrarUsuario() {
        Usuario usuarioBusqueda = Consola.nuevoUsuario(true); // Creamos un objeto mínimo solo con ID
        if (controlador.baja(usuarioBusqueda))
            System.out.println("Usuario borrado.");
        else
            System.out.println("No se encontró el usuario.");
    }

    // Mostrar usuarios ordenados alfabéticamente por nombre
    private void mostrarUsuarios() {
        // Convertimos el array que devuelve el controlador en un ArrayList
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        for (Usuario u : controlador.listadoUsuarios()) {
            listaUsuarios.add(u);
        }

        if (listaUsuarios.isEmpty()) {
            System.out.println("No hay usuarios.");
            return;
        }

        // Ordenamos alfabéticamente por nombre, ignorando mayúsculas/minúsculas
        listaUsuarios.sort(Comparator.comparing(Usuario::getNombre, String.CASE_INSENSITIVE_ORDER));

        // Mostramos cada usuario
        for (Usuario usuario : listaUsuarios) {
            System.out.println(usuario);
        }
    }

    // ------------------ MÉTODOS DE LIBROS ------------------

    // Insertar un libro
    private void insertarLibro() {
        Libro nuevoLibro = Consola.nuevoLibro(false); // Pedimos todos los datos del libro

        // Comprobamos si ya existe el ISBN
        if (controlador.buscar(nuevoLibro) != null) {
            System.out.println("Error: No se puede insertar. El ISBN " + nuevoLibro.getIsbn() + " ya existe.");
        } else {
            controlador.alta(nuevoLibro); // Damos de alta el libro
            System.out.println("Libro registrado con éxito.");
        }
    }

    // Borrar un libro
    private void borrarLibro() {
        Libro libroBusqueda = Consola.nuevoLibro(true); // Creamos un objeto mínimo con ISBN
        if (controlador.baja(libroBusqueda))
            System.out.println("Libro eliminado.");
        else
            System.out.println("Libro no encontrado.");
    }

    // Mostrar libros ordenados alfabéticamente por título
    private void mostrarLibros() {
        ArrayList<Libro> listaLibros = new ArrayList<>();
        for (Libro l : controlador.listadoLibros()) {
            listaLibros.add(l);
        }

        if (listaLibros.isEmpty()) {
            System.out.println("No hay libros.");
            return;
        }

        // Ordenamos alfabéticamente por título, ignorando mayúsculas/minúsculas
        listaLibros.sort(Comparator.comparing(Libro::getTitulo, String.CASE_INSENSITIVE_ORDER));

        // Mostramos cada libro
        for (Libro libro : listaLibros) {
            System.out.println(libro);
        }
    }

    // ------------------ MÉTODOS DE PRÉSTAMOS ------------------

    // Crear un nuevo préstamo
    private void nuevoPrestamo() {
        Libro libro = controlador.buscar(Consola.nuevoLibro(true)); // Buscamos libro por ISBN
        Usuario usuario = controlador.buscar(Consola.nuevoUsuario(true)); // Buscamos usuario por ID
        if (libro != null && usuario != null) {
            controlador.prestar(libro, usuario, Consola.leerFecha()); // Prestamos libro
            System.out.println("Préstamo realizado.");
        } else {
            System.out.println("Libro o Usuario no encontrado.");
        }
    }

    // Devolver un préstamo
    private void devolverPrestamo() {
        Libro libro = controlador.buscar(Consola.nuevoLibro(true));
        Usuario usuario = controlador.buscar(Consola.nuevoUsuario(true));
        if (libro != null && usuario != null) {

            // Buscamos el préstamo activo antes de devolver
            Prestamo prestamoActivo = null;
            for (Prestamo p : controlador.listadoPrestamos(usuario)) {
                if (p.getLibro().equals(libro) && !p.isDevuelto()) {
                    prestamoActivo = p;
                    break;
                }
            }

            boolean exito = controlador.devolver(libro, usuario, Consola.leerFecha());
            if (exito) {
                System.out.println("Devolución procesada.");
                if (prestamoActivo != null && prestamoActivo.estaVencido()) {
                    System.out.println("El préstamo estaba vencido.");
                    System.out.println("Días de retraso: " + prestamoActivo.diasRetraso());
                } else {
                    System.out.println("El préstamo se devolvió a tiempo.");
                }
            } else {
                System.out.println("No se encontró el préstamo a devolver.");
            }
        } else {
            System.out.println("Datos incorrectos.");
        }
    }

    // Mostrar todos los préstamos ordenados por fecha descendente y nombre de usuario
    private void mostrarPrestamos() {
        ArrayList<Prestamo> listaPrestamos = new ArrayList<>();
        for (Prestamo p : controlador.listadoPrestamos()) {
            listaPrestamos.add(p);
        }

        if (listaPrestamos.isEmpty()) {
            System.out.println("No hay préstamos.");
            return;
        }

        // Ordenamos: primero por fecha de inicio (descendente), luego por nombre de usuario (A-Z)
        listaPrestamos.sort((prestamo1, prestamo2) -> {
            // Primero comparamos fechas (más recientes primero)
            int comparacionFechas = prestamo2.getfInicio().compareTo(prestamo1.getfInicio());
            if (comparacionFechas == 0) { // Si la fecha es la misma
                // Ordenamos alfabéticamente por el nombre del usuario
                comparacionFechas = prestamo1.getUsuario()
                        .getNombre()
                        .compareToIgnoreCase(prestamo2.getUsuario().getNombre());
            }
            return comparacionFechas;
        });

        // Mostramos cada préstamo
        for (Prestamo prestamo : listaPrestamos) System.out.println(prestamo);
    }

    // Mostrar préstamos de un usuario específico ordenados
    private void mostrarPrestamosUsuario() {
        Usuario usuario = controlador.buscar(Consola.nuevoUsuario(true));
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        ArrayList<Prestamo> prestamosUsuario = new ArrayList<>();
        for (Prestamo p : controlador.listadoPrestamos(usuario)) {
            prestamosUsuario.add(p);
        }

        if (prestamosUsuario.isEmpty()) {
            System.out.println("Este usuario no tiene préstamos.");
            return;
        }

        // Ordenamos igual que todos los préstamos
        prestamosUsuario.sort((prestamo1, prestamo2) -> {
            int comparacionFechas = prestamo2.getfInicio().compareTo(prestamo1.getfInicio());
            if (comparacionFechas == 0) {
                comparacionFechas = prestamo1.getUsuario()
                        .getNombre()
                        .compareToIgnoreCase(prestamo2.getUsuario().getNombre());
            }
            return comparacionFechas;
        });

        // Mostramos cada préstamo
        for (Prestamo prestamo : prestamosUsuario) System.out.println(prestamo);
    }
}
