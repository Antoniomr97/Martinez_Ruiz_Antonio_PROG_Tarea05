package biblioteca;

import biblioteca.controlador.Controlador;
import biblioteca.modelo.Modelo;
import biblioteca.vista.Vista;

public class AppBiblioteca {

    public static void main(String[] args) {

        // 1. Crear el modelo con una capacidad (por ejemplo 100)
        Modelo modelo = new Modelo(100);

        // 2. Crear la vista
        Vista vista = new Vista();

        // 3. Crear el controlador y conectarlo con modelo y vista
        Controlador controlador = new Controlador(modelo, vista);

        // 4. Asignar el controlador a la vista
        vista.setControlador(controlador);

        // 5. Iniciar la aplicación
        controlador.comenzar();  // muestra mensaje de bienvenida
        vista.comenzar();        // ejecuta el menú interactivo
    }
}
