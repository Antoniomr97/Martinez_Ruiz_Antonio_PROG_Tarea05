package biblioteca.controlador;

import biblioteca.modelo.Modelo;
import biblioteca.modelo.dominio.Libro;
import biblioteca.modelo.dominio.Prestamo;
import biblioteca.modelo.dominio.Usuario;
import biblioteca.vista.Vista;

import java.time.LocalDate;

public class Controlador {

    private Modelo modelo;
    private Vista vista;

    // Constructor recibe Modelo y Vista y los almacena
    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    // Metodo para iniciar la aplicación
    public void comenzar() {
        vista.mostrarMensaje("=== Bienvenido a la biblioteca ===");
    }

    // Metodo para finalizar la aplicación
    public void terminar() {
        vista.mostrarMensaje("Gracias por usar la biblioteca. Hasta luego.");
    }

    /* --------------------- LIBROS --------------------- */

    public void alta(Libro libro) {
        if (libro != null) {
            modelo.alta(libro);
            vista.mostrarMensaje("Libro dado de alta: " + libro.getTitulo());
        } else {
            vista.mostrarMensaje("Error: Libro nulo, no se puede dar de alta.");
        }
    }

    public Boolean baja(Libro libro) {
        if (libro == null) {
            vista.mostrarMensaje("Error: Libro nulo.");
            return false;
        }
        boolean resultado = modelo.baja(libro);
        if (resultado)
            vista.mostrarMensaje("Libro dado de baja: " + libro.getTitulo());
        else
            vista.mostrarMensaje("No se encontró el libro: " + libro.getTitulo());
        return resultado;
    }

    public Libro buscar(Libro libro) {
        if (libro == null) {
            vista.mostrarMensaje("Error: Libro nulo.");
            return null;
        }
        Libro encontrado = modelo.buscar(libro);
        if (encontrado == null)
            vista.mostrarMensaje("No se encontró el libro: " + libro.getTitulo());
        return encontrado;
    }

    public Libro[] listadoLibros() {
        Libro[] lista = modelo.listadoLibros();
        if (lista.length == 0) vista.mostrarMensaje("No hay libros registrados.");
        return lista;
    }

    /* --------------------- USUARIOS --------------------- */

    public void alta(Usuario usuario) {
        if (usuario != null) {
            modelo.alta(usuario);
            vista.mostrarMensaje("Usuario dado de alta: " + usuario.getNombre());
        } else {
            vista.mostrarMensaje("Error: Usuario nulo, no se puede dar de alta.");
        }
    }

    public boolean baja(Usuario usuario) {
        if (usuario == null) {
            vista.mostrarMensaje("Error: Usuario nulo.");
            return false;
        }
        boolean resultado = modelo.baja(usuario);
        if (resultado)
            vista.mostrarMensaje("Usuario dado de baja: " + usuario.getNombre());
        else
            vista.mostrarMensaje("No se encontró el usuario: " + usuario.getNombre());
        return resultado;
    }

    public Usuario buscar(Usuario usuario) {
        if (usuario == null) {
            vista.mostrarMensaje("Error: Usuario nulo.");
            return null;
        }
        Usuario encontrado = modelo.buscar(usuario);
        if (encontrado == null)
            vista.mostrarMensaje("No se encontró el usuario: " + usuario.getNombre());
        return encontrado;
    }

    public Usuario[] listadoUsuarios() {
        Usuario[] lista = modelo.listadoUsuarios();
        if (lista.length == 0) vista.mostrarMensaje("No hay usuarios registrados.");
        return lista;
    }

    /* --------------------- PRÉSTAMOS --------------------- */

    public void prestar(Libro libro, Usuario usuario, LocalDate fecha) {
        if (libro == null || usuario == null) {
            vista.mostrarMensaje("Error: Libro o usuario nulo, no se puede realizar el préstamo.");
            return;
        }
        if (libro.getUnidadesDisponibles() <= 0) {
            vista.mostrarMensaje("No hay unidades disponibles del libro: " + libro.getTitulo());
            return;
        }
        modelo.prestar(libro, usuario, fecha);
        vista.mostrarMensaje("Préstamo registrado: " + libro.getTitulo() + " a " + usuario.getNombre());
    }

    public boolean devolver(Libro libro, Usuario usuario, LocalDate fecha) {
        if (libro == null || usuario == null) {
            vista.mostrarMensaje("Error: Libro o usuario nulo, no se puede realizar la devolución.");
            return false;
        }
        boolean resultado = modelo.devolver(libro, usuario, fecha);
        if (resultado)
            vista.mostrarMensaje("Devolución realizada: " + libro.getTitulo() + " por " + usuario.getNombre());
        else
            vista.mostrarMensaje("No se encontró el préstamo para devolución.");
        return resultado;
    }

    public Prestamo[] listadoPrestamos(Usuario usuario) {
        if (usuario == null) {
            vista.mostrarMensaje("Error: Usuario nulo, no se pueden listar préstamos.");
            return new Prestamo[0];
        }
        Prestamo[] prestamos = modelo.listadoPrestamos(usuario);
        if (prestamos.length == 0) vista.mostrarMensaje("El usuario " + usuario.getNombre() + " no tiene préstamos activos.");
        return prestamos;
    }

    public Prestamo[] listadoPrestamos() {
        Prestamo[] prestamos = modelo.listadoPrestamos();
        if (prestamos.length == 0) vista.mostrarMensaje("No hay préstamos registrados.");
        return prestamos;
    }
}
