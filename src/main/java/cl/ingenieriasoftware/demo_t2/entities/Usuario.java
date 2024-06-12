package cl.ingenieriasoftware.demo_t2.entities;

public class Usuario {
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private int Admin;
    public Usuario(String nombre, String apellido, String email, String contrasena){
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena = contrasena;
        this.Admin = 0;
    }

    public String getEmail(){return this.email;}

    public String toString() {
        return String.format("%-20s %-20s %s/%s %s",
                nombre,
                apellido,
                email);
    }
    public void setAdmin(int i){
        this.Admin = i;
        return;
    }
}
