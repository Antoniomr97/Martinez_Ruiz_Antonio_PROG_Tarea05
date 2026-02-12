package biblioteca.controlador;

// Importamos lo que vamos a usar: el modelo (datos), la vista (interfaz)
// y las clases que representan los objetos de la biblioteca (Usuario, Libro, Prestamo)
import biblioteca.modelo.Modelo;
import biblioteca.vista.Vista;
import biblioteca.modelo.dominio.*;
import java.time.LocalDate;  // Para manejar fechas de préstamos y devoluciones

public class Controlador {
    // Aquí guardamos las referencias al modelo y la vista para poder usarlos
    private Modelo modelo;
    private Vista vista;

    // Constructor del controlador: necesita un modelo y una vista
    public Controlador(Modelo modelo, Vista vista) {
        // Si alguno viene nulo, lanzamos un error porque no podemos funcionar sin ellos
        if (modelo == null || vista == null) {
            throw new IllegalArgumentException("El modelo y la vista no pueden ser nulos.");
        }
        this.modelo = modelo;
        this.vista = vista;
    }

    // Este metodo arranca todo: primero el modelo (crea los arrays, carga datos)
    // y luego la vista (la interfaz para que el usuario pueda interactuar)
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    // Este metodo cierra la aplicación de forma ordenada
    public void terminar() {
        System.out.println("Termina Controlador"); // Avisamos en la consola
        modelo.terminar(); // Cerramos el modelo primero, siguiendo el orden jerárquico
    }

    // --- MÉTODOS QUE LA VISTA NECESITA ---
    // Estos métodos son los que la interfaz va a llamar para trabajar con los datos

    // --- USUARIOS ---
    public void alta(Usuario u) { modelo.alta(u); }          // Añadir un usuario
    public Usuario buscar(Usuario u) { return modelo.buscar(u); }  // Buscar un usuario
    public boolean baja(Usuario u) { return modelo.baja(u); }      // Eliminar un usuario
    public Usuario[] listadoUsuarios() { return modelo.listadoUsuarios(); } // Lista de todos los usuarios

    // --- LIBROS ---
    public void alta(Libro l) { modelo.alta(l); }            // Añadir un libro
    public Libro buscar(Libro l) { return modelo.buscar(l); }      // Buscar un libro
    public boolean baja(Libro l) { return modelo.baja(l); }        // Eliminar un libro
    public Libro[] listadoLibros() { return modelo.listadoLibros(); } // Lista de todos los libros

    // --- PRÉSTAMOS ---
    public void prestar(Libro l, Usuario u, LocalDate f) {
        // Le decimos al modelo que registre un préstamo de un libro a un usuario en una fecha
        modelo.prestar(l, u, f);
    }

    public boolean devolver(Libro l, Usuario u, LocalDate f) {
        // Registramos la devolución de un libro; devuelve true si todo bien
        return modelo.devolver(l, u, f);
    }

    public Prestamo[] listadoPrestamos() {
        // Devuelve todos los préstamos que existen
        return modelo.listadoPrestamos();
    }

    public Prestamo[] listadoPrestamos(Usuario u) {
        // Devuelve los préstamos que corresponden a un usuario concreto
        return modelo.listadoPrestamos(u);
    }
}

/**
 * En esté script no hace falta cambiar nada ya que trabaja todo con metodos
 * y con lo que le pasa el Modelo, no trata directamente los Arrays o ArrayList
 * */