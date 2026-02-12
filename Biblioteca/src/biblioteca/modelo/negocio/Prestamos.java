package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Libro;
import biblioteca.modelo.dominio.Prestamo;
import biblioteca.modelo.dominio.Usuario;
import java.time.LocalDate;

public class Prestamos {
    private Prestamo[] prestamos;

    public Prestamos(int capacidad){
        this.prestamos = new Prestamo[capacidad];
    }

    public void prestar(Libro libro, Usuario usuario, LocalDate fecha){
        for (int i = 0; i < prestamos.length; i++) {
            if (prestamos[i] == null) {
                // Creamos el préstamo y restamos stock del libro
                prestamos[i] = new Prestamo(libro, usuario, fecha);
                libro.tomarPrestado();
                return;
            }
        }
        System.out.println("Error: Capacidad de préstamos agotada.");
    }

    public boolean devolver(Libro libro, Usuario usuario, LocalDate fecha){
        for (Prestamo p : prestamos) {
            if (p != null && !p.isDevuelto()
                    && p.getLibro().equals(libro)
                    && p.getUsuario().equals(usuario)) {
                p.marcarDevuelto(fecha);
                return true;
            }
        }
        return false;
    }

    // Cambiamos el nombre o añadimos este alias para que el Controlador no de error
    public Prestamo[] prestamosUsuario(Usuario usuario){
        int count = 0;
        for (Prestamo p : prestamos) {
            if (p != null && p.getUsuario().equals(usuario)) count++;
        }

        Prestamo[] resultado = new Prestamo[count];
        int index = 0;
        for (Prestamo p : prestamos) {
            if (p != null && p.getUsuario().equals(usuario)) {
                resultado[index++] = p;
            }
        }
        return resultado;
    }

    public Prestamo[] historico(){
        int count = 0;
        for (Prestamo p : prestamos) if (p != null) count++;

        Prestamo[] resultado = new Prestamo[count];
        int index = 0;
        for (Prestamo p : prestamos) {
            if (p != null) resultado[index++] = p;
        }
        return resultado;
    }
}