package cl.ingenieriasoftware.demo_t2.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class leerArchivoRegistros {
    public static void main() {
        String nombreArchivo = "usuarios.txt";
        File archivo = new File(nombreArchivo);

        try (Scanner scanner = new Scanner(archivo)) {

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] campos = linea.split(",");

                String nombre = campos[0].trim();
                String apellido = campos[1].trim();
                String email = campos[2].trim();
                String contrasena = campos[3].trim();

                UsuarioService.getInstance().addUsuario(nombre, apellido, email, contrasena);
                int code = Integer.parseInt(campos[4].trim());
                if( code != 0){
                    UsuarioService.getInstance().getUsuarioC(email).setAdmin(code);
                }
            }

        } catch (FileNotFoundException e) {

            System.out.println("Error");

        }
    }
}
