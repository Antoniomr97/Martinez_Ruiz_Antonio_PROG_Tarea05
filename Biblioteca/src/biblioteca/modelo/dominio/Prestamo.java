package biblioteca.modelo.dominio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Esta clase representa un préstamo de un libro a un usuario
public class Prestamo {
    // Atributos del préstamo
    private LocalDate fInicio;      // Fecha en que se realiza el préstamo
    private LocalDate fLimite;      // Fecha límite para devolver el libro
    private Boolean devuelto;       // Indica si el libro ya fue devuelto
    private LocalDate fDevolucion;  // Fecha real en que se devolvió el libro
    private Libro libro;            // El libro que se presta
    private Usuario usuario;        // Usuario que recibe el libro

    // Constructor del préstamo
    public Prestamo(Libro libro, Usuario usuario, LocalDate fInicio){
        this.libro = libro;         // Guardamos el libro
        this.usuario = usuario;     // Guardamos el usuario
        this.fInicio = fInicio;     // Guardamos la fecha de inicio
        this.fLimite = fInicio.plusDays(15); // Calculamos la fecha límite (15 días después)
        this.devuelto = false;      // Inicialmente no se ha devuelto
        this.fDevolucion = null;    // No hay fecha de devolución todavía
    }

    // --- GETTERS ---
    // Nos permiten acceder a los atributos desde fuera de la clase
    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getfInicio() { return fInicio; }
    public LocalDate getfLimite() { return fLimite; }
    public Boolean isDevuelto() { return devuelto; } // isDevuelto porque es boolean
    public LocalDate getfDevolucion() { return fDevolucion; }

    // --- MÉTODOS ---

    // Calcula los días de retraso
    public int diasRetraso() {
        // Si ya se devolvió, usamos la fecha de devolución, si no usamos hoy
        LocalDate fechaComparar = devuelto ? fDevolucion : LocalDate.now();
        // Calculamos los días entre la fecha límite y la fecha a comparar
        int dias = (int) ChronoUnit.DAYS.between(fLimite, fechaComparar);
        // Si los días son positivos devolvemos el número, si no devolvemos 0
        return dias > 0 ? dias : 0;
    }

    // Comprueba si el préstamo está vencido
    public Boolean estaVencido() {
        // Está vencido si NO se ha devuelto y la fecha actual supera la fecha límite
        return !devuelto && LocalDate.now().isAfter(fLimite);
    }

    // Marca el préstamo como devuelto
    public void marcarDevuelto(LocalDate fecha) {
        this.fDevolucion = fecha; // Guardamos la fecha de devolución
        this.devuelto = true;     // Marcamos como devuelto
        if (libro != null) libro.devolverUnidad(); // Si hay libro, sumamos una unidad disponible
    }

    // Representación en texto del préstamo
    @Override
    public String toString() {
        return "Préstamo de: " + libro.getTitulo() +
                " a " + usuario.getNombre() +
                " - Inicio: " + fInicio +
                ", Límite: " + fLimite +
                ", Devuelto: " + devuelto +
                (devuelto ? ", Fecha devolución: " + fDevolucion : "");
    }
}
