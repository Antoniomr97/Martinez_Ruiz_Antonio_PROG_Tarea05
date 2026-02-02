package biblioteca.vista;

import biblioteca.modelo.dominio.*;
import biblioteca.modelo.negocio.*;
import biblioteca.utilidades.Entrada;

import java.time.LocalDate;


public class Consola {

    private Consola(){};

    public void mostrarMenu(){};

    // Este metodo si el usuario existe lo busca y si no está lo crea, por eso el boolean paraBuscar
    public Usuario nuevoUsuario(boolean paraBuscar){
        return null;
    };

    // Este metodo si el libro existe lo busca y si no está lo crea, por eso el boolean paraBuscar
    public Libro nuevoLibro(boolean paraBuscar){
        return null;
    };

    private Autor nuevoAutor(){
        return null;
    };

    public LocalDate leerFecha(){
        return null;
    };
}
