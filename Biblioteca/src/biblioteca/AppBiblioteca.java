package biblioteca;

import biblioteca.controlador.Controlador;
import biblioteca.modelo.Modelo;
import biblioteca.vista.Vista;

public class AppBiblioteca {
    public static void main(String[] args) {
        // Configuramos nuestro sistema siguiendo el patrón MVC
        Modelo modelo = new Modelo(100);
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);

        vista.setControlador(controlador);

        // Iniciamos la ejecución desde el controlador
        controlador.comenzar();
    }
}