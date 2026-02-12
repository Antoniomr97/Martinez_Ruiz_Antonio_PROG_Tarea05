package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Libro;
import biblioteca.modelo.dominio.Prestamo;
import biblioteca.modelo.dominio.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;

// Clase que gestiona todos los préstamos de la biblioteca
public class Prestamos {
    // Lista dinámica de préstamos
    private ArrayList<Prestamo> prestamos;

    // Constructor: inicializa la lista de préstamos
    public Prestamos() {
        this.prestamos = new ArrayList<>();
    }

    // Metodo para registrar un nuevo préstamo
    public void prestar(Libro libro, Usuario usuario, LocalDate fecha){
        // Creamos el préstamo y lo añadimos a la lista
        Prestamo nuevo = new Prestamo(libro, usuario, fecha);
        prestamos.add(nuevo);

        // Restamos una unidad del libro prestado
        libro.tomarPrestado();
    }

    // Metodo para devolver un libro
    public boolean devolver(Libro libro, Usuario usuario, LocalDate fecha){
        // Buscamos un préstamo que coincida con el libro y el usuario, y que no haya sido devuelto
        for (Prestamo p : prestamos) {
            if (!p.isDevuelto() &&
                    p.getLibro().equals(libro) &&
                    p.getUsuario().equals(usuario)) {

                // Marcamos como devuelto y guardamos la fecha
                p.marcarDevuelto(fecha);
                return true;
            }
        }
        // No se encontró préstamo válido
        return false;
    }

    // Devuelve todos los préstamos de un usuario concreto
    public Prestamo[] prestamosUsuario(Usuario usuario){
        ArrayList<Prestamo> resultado = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.getUsuario().equals(usuario)) {
                resultado.add(p);
            }
        }
        // Convertimos la lista dinámica en un array para mantener compatibilidad con el resto del proyecto
        return resultado.toArray(new Prestamo[0]);
    }

    // Devuelve todos los préstamos registrados (histórico completo)
    public Prestamo[] historico(){
        // Convertimos la lista dinámica en un array
        return prestamos.toArray(new Prestamo[0]);
    }
}
