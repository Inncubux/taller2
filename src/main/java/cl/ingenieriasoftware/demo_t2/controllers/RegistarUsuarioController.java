package cl.ingenieriasoftware.demo_t2.controllers;

import cl.ingenieriasoftware.demo_t2.DemoApplication;
import cl.ingenieriasoftware.demo_t2.Util.AlertMessage;
import cl.ingenieriasoftware.demo_t2.entities.CreditCard;
import cl.ingenieriasoftware.demo_t2.entities.Usuario;
import cl.ingenieriasoftware.demo_t2.services.ApiService;
import cl.ingenieriasoftware.demo_t2.services.CreditCardService;
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
    private List<Usuario> ListUsuario;
    @FXML
    private void handle(ActionEvent event) {
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
        try {
            for (int i = 0; i < ListUsuario.size(); i++) {
                if (email.equals(ListUsuario.get(i).getEmail())) {
                    AlertMessage.show(Alert.AlertType.ERROR, "ERROR", "este correo ya esta asociado a una cuenta existente");
                    return;
                }
            }
            ListUsuario.add(new Usuario(nombre,apellido,email,contrasena));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
