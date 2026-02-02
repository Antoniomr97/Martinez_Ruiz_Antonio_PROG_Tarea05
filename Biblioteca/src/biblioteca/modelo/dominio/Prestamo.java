package biblioteca.modelo.dominio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Inicializamos la clase y sus atributos
public class Prestamo {
    private LocalDate fInicio;
    private LocalDate fLimite;
    private Boolean devuelto;
    private LocalDate fDevolucion;
    private Libro libro;
    private Usuario usuario;

    // Creamos constructor y añadimos devuelto y fDevolucion
    public Prestamo(Libro libro, Usuario usuario, LocalDate fInicio){
        this.libro = libro;
        this.usuario = usuario;
        this.fInicio = fInicio;
        this.fLimite = fInicio.plusDays(15);
        this.devuelto = false;
        this.fDevolucion = null;
    }

    // Getters (is Devuelto es como getDevuelto pero en el esquema ponia isDevuelto)
    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getfInicio() { return fInicio; }
    public LocalDate getfLimite() { return fLimite; }
    public Boolean isDevuelto() { return devuelto; }
    public LocalDate getfDevolucion() { return fDevolucion; }

    //  Creamos el metodo diasRetraso el cual mira si ha sido devuelto, entonces utiliza la fecha de devolucion y si no es así la fecha actual
    public int diasRetraso() {
        LocalDate fechaComparar = devuelto ? fDevolucion : LocalDate.now(); // Operador ternario
        int dias = (int) ChronoUnit.DAYS.between(fLimite, fechaComparar); // Devuelve los dias entre las 2 fechas
        return dias > 0 ? dias : 0; // Si dias es mayor a 0 devuelve los dias sino devuelve 0
    }

    // Comprobamoas si ya ha vencido la fecha de devolver, comprobando que no está devuelto y que la fecha actual ha superado la limite
    public Boolean estaVencido() {
        return !devuelto && LocalDate.now().isAfter(fLimite);
    }

    // Para marcarDevuelto guardamos la fecha de devolucion como la actual y pasamos devuelto a true. Y si libro no es null suma uno a las unidades con el metodo devolverUnidad
    public void marcarDevuelto(LocalDate fecha) {
        this.fDevolucion = fecha;
        this.devuelto = true;
        if (libro != null) libro.devolverUnidad();
    }

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
