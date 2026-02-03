package biblioteca.vista;

import biblioteca.controlador.Controlador;
import biblioteca.modelo.dominio.*;
import biblioteca.utilidades.Entrada;

// La Vista es la capa que interactúa con el usuario
// Llama al Consola para leer datos y al Controlador para manipular los datos
public class Vista {
    private Controlador controlador; // Guardamos la referencia al controlador

    // Setter para asignar el controlador
    public void setControlador(Controlador controlador) {
        if (controlador != null) this.controlador = controlador;
    }

    // --- METODO PRINCIPAL ---
    public void comenzar() {
        int ord; // opción elegida por el usuario
        do {
            Consola.mostrarMenu();   // Mostramos el menú
            ord = Entrada.entero();  // Leemos la opción
            if (ord >= 0 && ord < Opcion.values().length) {
                ejecutarOpcion(Opcion.values()[ord]); // Ejecutamos la opción elegida
            } else {
                System.out.println("Opción no válida.");
            }
        } while (ord != Opcion.SALIR.ordinal()); // Repetimos hasta que el usuario elija salir
    }

    // Llama al metodo correspondiente según la opción elegida
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

    // ------------------ MÉTODOS ------------------

    // Insertar un usuario
    private void insertarUsuario() {
        Usuario nuevo = Consola.nuevoUsuario(false); // Pedimos datos completos

        // Comprobamos si ya existe por ID
        if (controlador.buscar(nuevo) != null) {
            System.out.println("Error: Ya existe un usuario con la ID " + nuevo.getId());
        } else {
            controlador.alta(nuevo); // Lo damos de alta
            System.out.println("Usuario registrado con éxito.");
        }
    }

    // Borrar un usuario
    private void borrarUsuario() {
        Usuario busqueda = Consola.nuevoUsuario(true); // Creamos un objeto mínimo solo con ID
        if (controlador.baja(busqueda)) System.out.println("Usuario borrado.");
        else System.out.println("No se encontró el usuario.");
    }

    // Mostrar todos los usuarios
    private void mostrarUsuarios() {
        Usuario[] lista = controlador.listadoUsuarios();
        if (lista.length == 0) System.out.println("No hay usuarios.");
        else for (Usuario u : lista) System.out.println(u);
    }

    // Insertar un libro
    private void insertarLibro() {
        Libro nuevo = Consola.nuevoLibro(false); // Pedimos todos los datos

        // Comprobamos si ya existe el ISBN
        if (controlador.buscar(nuevo) != null) {
            System.out.println("Error: No se puede insertar. El ISBN " + nuevo.getIsbn() + " ya existe.");
        } else {
            controlador.alta(nuevo); // Lo damos de alta
            System.out.println("Libro registrado con éxito.");
        }
    }

    // Borrar un libro
    private void borrarLibro() {
        Libro busq = Consola.nuevoLibro(true); // Objeto mínimo con ISBN
        if (controlador.baja(busq)) System.out.println("Libro eliminado.");
        else System.out.println("Libro no encontrado.");
    }

    // Mostrar todos los libros
    private void mostrarLibros() {
        Libro[] lista = controlador.listadoLibros();
        if (lista.length == 0) System.out.println("No hay libros.");
        else for (Libro l : lista) System.out.println(l);
    }

    // Crear un nuevo préstamo
    private void nuevoPrestamo() {
        Libro l = controlador.buscar(Consola.nuevoLibro(true));   // Buscamos libro por ISBN
        Usuario u = controlador.buscar(Consola.nuevoUsuario(true)); // Buscamos usuario por ID
        if (l != null && u != null) {
            controlador.prestar(l, u, Consola.leerFecha()); // Prestamos libro
            System.out.println("Préstamo realizado.");
        } else System.out.println("Libro o Usuario no encontrado.");
    }

    // Devolver un préstamo
    private void devolverPrestamo() {
        Libro l = controlador.buscar(Consola.nuevoLibro(true));
        Usuario u = controlador.buscar(Consola.nuevoUsuario(true));
        if (l != null && u != null) {
            // Buscar préstamo activo antes de devolver
            Prestamo prestamoActivo = null;
            for (Prestamo p : controlador.listadoPrestamos(u)) {
                if (p.getLibro().equals(l) && !p.isDevuelto()) {
                    prestamoActivo = p;
                    break;
                }
            }

            boolean ok = controlador.devolver(l, u, Consola.leerFecha());
            if (ok) {
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


    // Mostrar todos los préstamos
    private void mostrarPrestamos() {
        Prestamo[] lista = controlador.listadoPrestamos();
        if (lista.length == 0) System.out.println("No hay préstamos.");
        else for (Prestamo p : lista) System.out.println(p);
    }

    // Mostrar los préstamos de un usuario específico
    private void mostrarPrestamosUsuario() {
        Usuario u = controlador.buscar(Consola.nuevoUsuario(true));
        if (u != null) {
            Prestamo[] lista = controlador.listadoPrestamos(u);
            if (lista.length == 0) System.out.println("Este usuario no tiene préstamos.");
            else for (Prestamo p : lista) System.out.println(p);
        } else System.out.println("Usuario no encontrado.");
    }
}
