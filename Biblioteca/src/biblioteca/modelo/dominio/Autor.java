package biblioteca.modelo.dominio;


// Inicializamos la clase con sus respectivos atributos
public class Autor {
    private String nombre;
    private String apellidos;
    private String nacionalidad;


    // Creamos un constructor que se crea a partir de los valores que se le pasa
    public Autor(String nombre, String apellidos, String nacionalidad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacionalidad = nacionalidad;
    }

    // Creamos un constructor a partir del objeto
    public Autor(Autor autor) {
        this.nombre = autor.nombre;
        this.apellidos = autor.apellidos;
        this.nacionalidad = autor.nacionalidad;
    }

    // Generamos los getters y setters (en los setter iba a poner validaciones,

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null ni vacío");
        }
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        if (apellidos == null || apellidos.trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos no pueden ser null ni vacíos");
        }
        this.apellidos = apellidos;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        if (nacionalidad == null || nacionalidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La nacionalidad no puede ser null ni vacía");
        }
        this.nacionalidad = nacionalidad;
    }

    // Para el metodo getNombreCompleto lo que hacemos es juntar el nombre y los apellidos del objeto
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellidos;
    }

    // Creamos el metodo iniciales
    public String iniciales() {
        String resultado = "";

        // Comprobamos que el nombre no sea null ni este vacio y si lo cumple cogemos el primer caracter y le añadimos un .
        if (nombre != null && !nombre.isEmpty()) {
            resultado += nombre.charAt(0) + ". ";
        }

        // Hacemos la misma comprobacion con los apellidos
        if (apellidos != null && !apellidos.isEmpty()) {

            // Usamos el metodo split para separar las palabras por el espacio y guardarlas en el array partes
            String[] partes = apellidos.split(" ");

            // Recorremos el array y si no esta vacio añadimos al resultado sus respectivas iniciales y un .
            for (String parte : partes) {
                if (!parte.isEmpty()) {
                    resultado += parte.charAt(0) + ". ";
                }
            }
        }

        // Eliminamos espacios al principio y al final con el metodo trim de String
        return resultado.trim();
    }


    // Sobreescribimos el metodo equals comprobando si es un objeto, nulo o si la instancia es un Autor
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Autor)) return false;

        // Añadimos un casting al objeto que nos llega para poder obtener a sus metodos y atributos
        Autor otro = (Autor) obj;

        // Devolvemos si son iguales o no
        return nombre.equals(otro.nombre) &&
                apellidos.equals(otro.apellidos) &&
                nacionalidad.equals(otro.nacionalidad);
    }

    // Sobreescribimos el metodo hashCode
    @Override
    public int hashCode() {

        // Aplicamos el metodo hashCode (el cual no es de autor sino de la clase Objeto) el cual combierte texto a numeros
        int resultado = nombre.hashCode();

        // Multiplicamos por un numero primo y vamos sumando para aumentar la seguridad
        resultado = 31 * resultado + apellidos.hashCode();
        resultado = 31 * resultado + nacionalidad.hashCode();

        // Retornamos ya el resultado hasheado
        return resultado;
    }

    // Sobreescribimos el metodo toString para que muestre un String
    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                '}';
    }
}

