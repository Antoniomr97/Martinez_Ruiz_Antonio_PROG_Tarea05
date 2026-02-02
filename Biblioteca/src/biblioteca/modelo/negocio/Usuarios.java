package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Usuario;

// Inicializamos la clase y creamos un array de usuario. Similar a lo realizado en libros
public class Usuarios {
    private Usuario[] usuarios;

    public Usuarios(int capacidad) {
        this.usuarios = new Usuario[capacidad];
    }

    // Dar de alta un usuario en el primer hueco libre
    public void alta(Usuario usuario) {
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i] == null) {
                usuarios[i] = usuario;
                return; // Agregado y salimos
            }
        }
        System.out.println("No hay espacio para más usuarios.");
    }

    // Dar de baja un usuario buscando por equals
    public Boolean baja(Usuario usuario) {
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i] != null && usuarios[i].equals(usuario)) {
                for (int j = i; j < usuarios.length - 1; j++) {
                    usuarios[j] = usuarios[j + 1];
                }// Este for lo que hace es reemplazar cada usuario por el siguiente a partir del que coincide
                usuarios[usuarios.length - 1] = null;
                return true;
            }
        }
        return false;
    }

    // Buscar un usuario en el array
    public Usuario buscar(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u != null && u.equals(usuario)) {
                return u; // Encontrado
            }
        }
        return null; // No encontrado
    }

    // Mostrar todos
    public Usuario[] todos() {
        int count = 0;
        for (Usuario u : usuarios) if (u != null) count++;
        Usuario[] resultado = new Usuario[count];
        int index = 0;
        for (Usuario u : usuarios) {
            if (u != null) {
                resultado[index] = u;
                index++;
            }
        }
        return resultado;
    }
}

