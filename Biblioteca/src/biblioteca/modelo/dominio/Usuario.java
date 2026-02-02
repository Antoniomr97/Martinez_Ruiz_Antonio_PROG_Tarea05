package biblioteca.modelo.dominio;

// Inicializamos la clase con sus expresiones regulares y atributos.
public class Usuario {

    /*
    La expresion regular de la ID
    ^ Comienza desde el principio de la cadena
    [A-Z] puedes poner caracteres de A - Z y requiere 2
    \\d numero real
    {3} 3 veces
    $ Fin de la cadena
    */
    public static final String ID_PATTERN = "^[A-Z]{2}\\d{3}$"; // Ejemplo: AB123

    /*
    La expresion regular de la ID
    ^ Comienza la cadena
    [\w.-] cualquier letra, numero o guion bajo, . y -
    + uno o mas carcateres del conjunto
    @ tiene que haber un @
    \\. cualquier caracter
    \\w+ uno o mas caracteres de tipo letra, numero o guion bajo
    */
    public static final String EMAIL_BASIC = "^[\\w.-]+@[\\w.-]+\\.\\w+$";

    // Iniciamos los atributos
    private String id;
    private String nombre;
    private String email;
    private Direccion direccion;

    // Creamos el constructor
    public Usuario(String id, String nombre, String email, Direccion direccion) {
        setId(id);
        setEmail(email);
        this.nombre = nombre;
        this.direccion = direccion;
    }

    // Creamos constructor que recibe ya un objeto
    public Usuario(Usuario usuario) {
        this(usuario.id, usuario.nombre, usuario.email, usuario.direccion);
    }

    // Getter y Setter aplicando las expresiones regulares
    public String getId() { return id; }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID no puede ser null ni vacío");
        }
        if (!id.matches(ID_PATTERN)) {
            throw new IllegalArgumentException("ID inválido");
        }
        this.id = id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null ni vacío");
        }
        this.nombre = nombre;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser null ni vacío");
        }
        if (!email.matches(EMAIL_BASIC)) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) {
        if (direccion == null) {
            throw new IllegalArgumentException("La dirección no puede ser null");
        }
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario)) return false;
        return id.equals(((Usuario)o).id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ", Email: " + email + ", Dirección: " + direccion + ")";
    }
}
