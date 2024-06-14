package cl.ingenieriasoftware.demo_t2.controllers;

import cl.ingenieriasoftware.demo_t2.DemoApplication;
import cl.ingenieriasoftware.demo_t2.Util.AlertMessage;
import cl.ingenieriasoftware.demo_t2.services.ApiService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginController {
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasenia;

    /*
    Ítalo: este el método que se gatilla desde el botón "Acceder" del formulario "iniciar-sesion-view"
    Dentro de la propiedad del botón acceder encontrarás el "onAction" que llama a este método.
   */
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsuario.getText();
        String password = txtContrasenia.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertMessage.show(Alert.AlertType.ERROR, "Error", "Por favor, ingresa el nombre de usuario y la contraseña");
            return;
        }

        try {
            ApiService.login(username, password);
            AlertMessage.show(Alert.AlertType.INFORMATION, "Éxito", "Inicio de sesión exitoso");

            //Ítalo: cargamos el formulario de verificar tarjeta
            FXMLLoader loader = new FXMLLoader(DemoApplication.class.getResource("verificar-tc-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Verificar tarjeta"); //Ítalo: título de la ventana
            stage.show();

            //Ítalo: cerramos la ventana actual (la del inicio de sesión)
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            String mensaje = e.getMessage();
            if(mensaje == null){
                mensaje = "Por el momento no es posible iniciar sesión"; //Ítalo: este mensaje se gatilla en el caso que la API presente algún error
            }
            AlertMessage.show(Alert.AlertType.ERROR, "Error",  mensaje);
        } catch (InterruptedException e) {
            AlertMessage.show(Alert.AlertType.ERROR, "Error",   e.getMessage());
        }
    }


}