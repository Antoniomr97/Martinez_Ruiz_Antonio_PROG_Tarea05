package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Usuario;

public class Usuarios {
    private Usuario[] usuarios;

    public Usuarios(int capacidad) {
        this.usuarios = new Usuario[capacidad];
    }

    public void alta(Usuario usuario) {
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i] == null) {
                usuarios[i] = usuario;
                return;
            }
        }
        System.out.println("Error: Límite de usuarios alcanzado.");
    }

    public boolean baja(Usuario usuario) {
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i] != null && usuarios[i].equals(usuario)) {
                // Desplazamos el resto de usuarios
                for (int j = i; j < usuarios.length - 1; j++) {
                    usuarios[j] = usuarios[j + 1];
                }
                usuarios[usuarios.length - 1] = null;
                return true;
            }
        }
        return false;
    }

    public Usuario buscar(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u != null && u.equals(usuario)) return u;
        }
        return null;
    }

    public Usuario[] todos() {
        int count = 0;
        for (Usuario u : usuarios) if (u != null) count++;
        Usuario[] resultado = new Usuario[count];
        int index = 0;
        for (Usuario u : usuarios) {
            if (u != null) resultado[index++] = u;
        }
        return resultado;
    }
}