package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Libro;
import biblioteca.modelo.dominio.Prestamo;
import biblioteca.modelo.dominio.Usuario;

import java.time.LocalDate;

public class Prestamos {
    private Prestamo[] prestamos;

    public Prestamos(int capacidad){
        prestamos = new Prestamo[capacidad];
    }

    // Registrar un nuevo préstamo
    public void prestar(Libro libro, Usuario usuario, LocalDate fecha){
        for (int i = 0; i < prestamos.length; i++) {
            if (prestamos[i] == null) {
                prestamos[i] = new Prestamo(libro, usuario, fecha);
                libro.tomarPrestado(); // restamos unidades disponibles
                return;
            }
        }
        System.out.println("No se pueden registrar más préstamos.");
    }

    // Marcamos un préstamo como devuelto
    public boolean devolver(Libro libro, Usuario usuario, LocalDate fecha){
        for (Prestamo p : prestamos) {
            if (p != null && !p.isDevuelto()
                    && p.getLibro().equals(libro)
                    && p.getUsuario().equals(usuario)) {
                p.marcarDevuelto(fecha); // Ya aumenta la unidad
                return true;
            }
        }
        return false;
    }

    // Devuelve todos los préstamos que tiene el usuario actualmente
    public Prestamo[] prestamosUsuario(Usuario usuario){
        int count = 0;
        for (Prestamo p : prestamos) {
            if (p != null && !p.isDevuelto() && p.getUsuario().equals(usuario)) count++;
        }

        Prestamo[] resultado = new Prestamo[count];
        int index = 0;
        for (Prestamo p : prestamos) {
            if (p != null && !p.isDevuelto() && p.getUsuario().equals(usuario)) {
                resultado[index] = p;
                index++;
            }
        }
        return resultado;
    }

    // Devuelve todos los libros que están actualmente prestados
    public Libro[] librosPrestados(){
        int count = 0;
        for (Prestamo p : prestamos) {
            if (p != null && !p.isDevuelto()) count++;
        }

        Libro[] resultado = new Libro[count];
        int index = 0;
        for (Prestamo p : prestamos) {
            if (p != null && !p.isDevuelto()) {
                resultado[index] = p.getLibro();
                index++;
            }
        }
        return resultado;
    }

    // Devuelve el historico de los prestamos

    public Prestamo[] historico(){
        int count = 0;
        for (Prestamo p : prestamos) {
            if (p != null) count++;
        }

        Prestamo[] resultado = new Prestamo[count];
        int index = 0;
        for (Prestamo p : prestamos) {
            if (p != null) {
                resultado[index] = p;
                index++;
            }
        }
        return resultado;
    }
}
