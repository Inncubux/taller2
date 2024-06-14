package cl.ingenieriasoftware.demo_t2.services;
import cl.ingenieriasoftware.demo_t2.services.UsuarioService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class EscribirArchivoTexto {
    public static void main(String nombre, String apellido, String email, String contrasena) {
        String nombreArchivo = "usuarios.txt";
        String code = String.valueOf(UsuarioService.getInstance().getUsuarioC(email).getAdmin());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            bw.newLine();
            bw.write(nombre + "," + apellido + "," + email + "," + contrasena + "," + code);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}