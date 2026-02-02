package biblioteca.modelo;

import biblioteca.modelo.negocio.Libros;
import biblioteca.modelo.negocio.Usuarios;
import biblioteca.modelo.negocio.Prestamos;
import biblioteca.modelo.dominio.Libro;
import biblioteca.modelo.dominio.Usuario;
import biblioteca.modelo.dominio.Prestamo;

import java.time.LocalDate;

// Clase Modelo centraliza la gestión de libros, usuarios y préstamos
public class Modelo {
    private final int CAPACIDAD;
    private Libros libros;
    private Usuarios usuarios;
    private Prestamos prestamos;

    // Constructor: inicializa los objetos de negocio con la capacidad indicada
    public Modelo(int capacidad) {
        this.CAPACIDAD = capacidad;
        this.libros = new Libros(capacidad);
        this.usuarios = new Usuarios(capacidad);
        this.prestamos = new Prestamos(capacidad);
    }

    /* --------------------- LIBROS --------------------- */

    public void alta(Libro libro) {
        libros.alta(libro);
    }

    public boolean baja(Libro libro) {
        return libros.bajaLibro(libro);
    }

    public Libro buscar(Libro libro) {
        for (Libro l : libros.todos()) {
            if (l.equals(libro)) return l;
        }
        return null;
    }

    public Libro[] listadoLibros() {
        return libros.todos();
    }

    /* --------------------- USUARIOS --------------------- */

    public void alta(Usuario usuario) {
        usuarios.alta(usuario);
    }

    public boolean baja(Usuario usuario) {
        return usuarios.baja(usuario);
    }

    public Usuario buscar(Usuario usuario) {
        return usuarios.buscar(usuario);
    }

    public Usuario[] listadoUsuarios() {
        return usuarios.todos();
    }

    /* --------------------- PRÉSTAMOS --------------------- */

    public void prestar(Libro libro, Usuario usuario, LocalDate fecha) {
        prestamos.prestar(libro, usuario, fecha);
    }

    public boolean devolver(Libro libro, Usuario usuario, LocalDate fecha) {
        return prestamos.devolver(libro, usuario, fecha);
    }

    public Prestamo[] listadoPrestamos() {
        return prestamos.historico();
    }

    public Prestamo[] listadoPrestamos(Usuario usuario) {
        return prestamos.prestamosUsuario(usuario);
    }

    public Libro[] librosPrestados() {
        return prestamos.librosPrestados();
    }
}
