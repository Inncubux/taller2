package cl.ingenieriasoftware.demo_t2.controllers;

import cl.ingenieriasoftware.demo_t2.DemoApplication;
import cl.ingenieriasoftware.demo_t2.Util.AlertMessage;
import cl.ingenieriasoftware.demo_t2.entities.CreditCard;
import cl.ingenieriasoftware.demo_t2.entities.Usuario;
import cl.ingenieriasoftware.demo_t2.services.ApiService;
import cl.ingenieriasoftware.demo_t2.services.CreditCardService;
import cl.ingenieriasoftware.demo_t2.services.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class RegistarUsuarioController {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtContrasena;
    @FXML
    private TextField txtContrasena2;
    private ObservableList<Usuario> UsuarioList;
    @FXML
    private void handleRegistrar(ActionEvent event) throws IOException {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();
        String contrasena = txtContrasena.getText();
        String contrasena2 = txtContrasena2.getText();
        if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || contrasena.isEmpty() || contrasena2.isEmpty()){
            AlertMessage.show(Alert.AlertType.ERROR, "ERROR", "debes rellenar todos los campos");
            return;
        }
        if(!contrasena.equals(contrasena2)){
            AlertMessage.show(Alert.AlertType.ERROR, "ERROR", "ambas contraseñas deben ser iguales");
            return;
        }
        if(!UsuarioService.getInstance().addUsuario(nombre, apellido, email, contrasena)){
            AlertMessage.show(Alert.AlertType.ERROR, "ERROR", "este correo ya esta asociado a otra cuenta");
        }
        FXMLLoader loader = new FXMLLoader(DemoApplication.class.getResource("iniciar-sesion-view.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Iniciar sesion"); //Ítalo: título de la ventana
        stage.show();

        //Ítalo: cerramos la ventana actual (la del inicio de sesión)
        ((Node)(event.getSource())).getScene().getWindow().hide();

        ((Node)(event.getSource())).getScene().getWindow().hide();

    }
}
