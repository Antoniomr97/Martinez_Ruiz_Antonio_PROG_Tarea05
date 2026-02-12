package biblioteca.modelo;

import biblioteca.modelo.negocio.*;
import biblioteca.modelo.dominio.*;
import java.time.LocalDate;

/**
 * Esta es nuestra clase Fachada.
 * Su función es centralizar todas las peticiones que vienen del Controlador
 * y repartirlas a las clases de negocio (Libros, Usuarios, Prestamos).
 *
 * Ya no tenemos límite de capacidad, porque usamos ArrayLists en lugar de arrays.
 */
public class Modelo {

    // Instancias de nuestras clases de negocio
    private Libros libros;
    private Usuarios usuarios;
    private Prestamos prestamos;

    /**
     * Constructor del modelo.
     * Ya no necesita recibir capacidad porque usamos ArrayLists.
     */
    public Modelo() {
        // Nada que inicializar aquí por ahora
    }

    /**
     * Aquí es donde inicializamos nuestras clases de negocio.
     * Esto se llama al arrancar la aplicación desde el Controlador.
     */
    public void comenzar() {
        this.libros = new Libros();       // Creamos la "gestión de libros"
        this.usuarios = new Usuarios();   // Creamos la "gestión de usuarios"
        this.prestamos = new Prestamos(); // Creamos la "gestión de préstamos"
        System.out.println("Modelo: Hemos inicializado todas las clases de negocio correctamente.");
    }

    /**
     * Mensaje de despedida cuando cerramos la aplicación.
     */
    public void terminar() {
        System.out.println("El modelo ha terminado.");
    }

    // --- OPERACIONES CON LIBROS ---
    // Estos métodos llaman a la clase Libros para realizar las operaciones

    public void alta(Libro l) { libros.alta(l); }              // Añadir un libro
    public Libro buscar(Libro l) { return libros.buscar(l); }  // Buscar un libro
    public boolean baja(Libro l) { return libros.bajaLibro(l); } // Eliminar un libro
    public Libro[] listadoLibros() { return libros.todos(); }  // Listar todos los libros

    // --- OPERACIONES CON USUARIOS ---
    // Estos métodos llaman a la clase Usuarios

    public void alta(Usuario u) { usuarios.alta(u); }          // Añadir un usuario
    public Usuario buscar(Usuario u) { return usuarios.buscar(u); } // Buscar un usuario
    public boolean baja(Usuario u) { return usuarios.baja(u); }     // Eliminar un usuario
    public Usuario[] listadoUsuarios() { return usuarios.todos(); } // Listar todos los usuarios

    // --- OPERACIONES CON PRÉSTAMOS ---
    // Aquí llamamos a la clase Prestamos

    public void prestar(Libro l, Usuario u, LocalDate f) {
        // Registramos un nuevo préstamo
        prestamos.prestar(l, u, f);
    }

    public boolean devolver(Libro l, Usuario u, LocalDate f) {
        // Registramos la devolución de un libro
        return prestamos.devolver(l, u, f);
    }

    public Prestamo[] listadoPrestamos() {
        // Devuelve todos los préstamos históricos
        return prestamos.historico();
    }

    /**
     * Metodo adicional para obtener los préstamos de un usuario concreto,
     * útil para la Vista cuando queremos filtrar por usuario.
     */
    public Prestamo[] listadoPrestamos(Usuario u) {
        return prestamos.prestamosUsuario(u);
    }
}
