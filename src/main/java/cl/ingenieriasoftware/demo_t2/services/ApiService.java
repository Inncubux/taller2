package cl.ingenieriasoftware.demo_t2.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class ApiService {

    /*
     * NOTA IMPORTANTE de Ítalo:
     * En este proyecto, el token se está almacenando en una variable dentro de la clase ApiService
     * con fines demostrativos y para simplificar el funcionamiento de la API. Sin embargo, en un
     * entorno de producción, no se recomienda esta práctica.
     *
     * Lo más adecuado sería separar la lógica de administración del token en una clase dedicada,
     * como un TokenManager o AuthenticationService, para mantener una clara separación de
     * responsabilidades y facilitar el mantenimiento y la escalabilidad del código.
     *
     * Además, se deberían considerar aspectos de seguridad, como el almacenamiento seguro del token,
     * su validez y caducidad, y la implementación de mecanismos de autenticación y autorización robustos.
     */
    private static String token;


    private static final String BASE_URL = "https://idonosob.pythonanywhere.com"; //Ítalo: URL base de la API

    public static void login(String username, String password) throws IOException, InterruptedException {
        String loginUrl = BASE_URL + "/login";

        String requestBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(loginUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject responseBodyJson = gson.fromJson(response.body(), JsonObject.class);

        /* Ítalo: si no escribes correctamente la propiedad del JsonObject que vas a obtener, el programa se caerá
         es recomendable colocar todo dentro de un try-catch
         */
        if (response.statusCode() == 200) {
            token = responseBodyJson.get("access_token").getAsString();

        } else {
            // Ítalo: extrae el mensaje de error directamente del JsonObject
            String errorMessage = responseBodyJson.get("msg").getAsString();
            throw new IOException(errorMessage);
        }
    }

    public static boolean verificarTarjeta(String numeroTarjeta, String mesVencimiento, String anioVencimiento, String codigoSeguridad) throws IOException, InterruptedException {
        String validarTarjetaUrl = BASE_URL + "/validar_tarjeta";

        String requestBody = String.format("{\"numero_tarjeta\":\"%s\",\"mes_vencimiento\":\"%s\",\"anio_vencimiento\":\"%s\",\"codigo_seguridad\":\"%s\"}",
                numeroTarjeta, mesVencimiento, anioVencimiento, codigoSeguridad);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(validarTarjetaUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();


        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject responseBodyJson = gson.fromJson(response.body(), JsonObject.class);

        String mensaje = responseBodyJson.get("msg").getAsString();

        if (response.statusCode() == 200) {
            //metodo para realizar cobro
            String pagar = BASE_URL + "/realizar_cargo";
            String monto = "5";
            String descripcion = "Cobro prueba";
            String requestPagar = String.format("{\"numero_tarjeta\":\"%s\",\"monto\":\"%s\",\"descripcion\":\"%s\",\"mes_vencimiento\":\"%s\",\"anio_vencimiento\":\"%s\",\"codigo_seguridad\":\"%s\"}",
                    numeroTarjeta, monto, descripcion, mesVencimiento, anioVencimiento, codigoSeguridad);
            HttpRequest pagarTest = HttpRequest.newBuilder()
                    .uri(URI.create(pagar))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(requestPagar))
                    .build();
            HttpResponse<String> response1 = httpClient.send(pagarTest, HttpResponse.BodyHandlers.ofString());
            JsonObject responseBodyJson1 = gson.fromJson(response1.body(), JsonObject.class);
            System.out.println(responseBodyJson1);
            System.out.println( obtenerSaldo(numeroTarjeta, mesVencimiento, anioVencimiento, codigoSeguridad));
            //final metoto realizar cobro
            return mensaje.equals("Tarjeta válida");
        } else {
            throw new IOException(mensaje);
        }
    }

    public static String obtenerSaldo(String numeroTarjeta, String mesVencimiento, String anioVencimiento, String codigoSeguridad) throws IOException, InterruptedException {
        String Saldo = BASE_URL + "/obtener_saldo";
        String requestBody = String.format("{\"numero_tarjeta\":\"%s\",\"mes_vencimiento\":\"%s\",\"anio_vencimiento\":\"%s\",\"codigo_seguridad\":\"%s\"}",
                numeroTarjeta, mesVencimiento, anioVencimiento, codigoSeguridad);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Saldo))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject responseBodyJson = gson.fromJson(response.body(), JsonObject.class);
        String hola = String.valueOf(responseBodyJson);
        return hola;
    }


    // Ver mas tarde
    public static void registrarUsuario(String nombre, int edad, String correo, String contrasenia){
        String registarUsuario = BASE_URL + "/registar_usuario";

    }


}