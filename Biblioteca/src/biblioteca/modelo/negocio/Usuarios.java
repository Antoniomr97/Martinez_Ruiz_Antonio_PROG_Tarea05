package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Usuario;
import java.util.ArrayList;

// Clase que gestiona todos los usuarios de la biblioteca
public class Usuarios {
    // Lista dinámica de usuarios
    private ArrayList<Usuario> usuarios;

    // Constructor: inicializa la lista de usuarios
    public Usuarios() {
        this.usuarios = new ArrayList<>();
    }

    // Metodo para añadir un nuevo usuario
    public void alta(Usuario usuario) {
        // Comprobamos que no sea null
        if (usuario == null) {
            System.out.println("No puede registrar usuarios vacíos.");
            return;
        }

        // Comprobamos duplicados
        if (buscar(usuario) != null) {
            System.out.println("El usuario ya está registrado.");
            return;
        }

        // Añadimos el usuario a la lista
        usuarios.add(usuario);
    }

    // Metodo para eliminar un usuario
    public boolean baja(Usuario usuario) {
        return usuarios.remove(usuario);
    }

    // Metodo para buscar un usuario concreto
    public Usuario buscar(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u.equals(usuario)) return u;
        }
        return null;
    }

    // Devuelve todos los usuarios registrados
    public Usuario[] todos() {
        return usuarios.toArray(new Usuario[0]);
    }
}
