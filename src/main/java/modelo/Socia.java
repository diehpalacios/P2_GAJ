package modelo;

public class Socia {
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private int edad;
    private String email;

    public Socia(String dni, String nombre, String apellido, String telefono, int edad, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellido;
        this.telefono = telefono;
        this.edad = edad;
        this.email = email;
    }

    // Getters y Setters
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getTelefono() { return telefono; }
    public int getEdad() { return edad; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("DNI: %s | Nombre: %s %s | Tel: %s | Edad: %d | Email: %s", dni, nombre, apellidos, telefono, edad, email);
    }

}
