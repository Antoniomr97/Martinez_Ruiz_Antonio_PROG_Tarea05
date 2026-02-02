package biblioteca;

import biblioteca.modelo.negocio.*;
import biblioteca.vista.Consola;

public class AppBiblioteca {
    public static final int CAPACIDAD = 50;

    public static void main(String[] args) {
        Libros libros = new Libros(CAPACIDAD);
        Usuarios usuarios = new Usuarios(CAPACIDAD);
        Prestamos prestamos = new Prestamos(CAPACIDAD);

        Consola consola = new Consola(libros, usuarios, prestamos);
        consola.iniciar();
    }
}
