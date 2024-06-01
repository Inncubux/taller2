package cl.ingenieriasoftware.demo_t2.controllers;

import cl.ingenieriasoftware.demo_t2.DemoApplication;
import cl.ingenieriasoftware.demo_t2.Util.AlertMessage;
import cl.ingenieriasoftware.demo_t2.entities.CreditCard;
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

public class CreditCardController {

    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtFecha;
    @FXML
    private TextField txtCodigoSeguridad;
    @FXML
    private ListView<CreditCard> ListViewCreditCard;

    private ObservableList<CreditCard> creditCardList;


    @FXML
    private void handleVerificar(ActionEvent event){

        String numero = txtNumero.getText();
        String nombre = txtNombre.getText();
        String fecha = txtFecha.getText();
        String codigoSeguridad = txtCodigoSeguridad.getText();

        if (numero.isEmpty() || nombre.isEmpty() || fecha.isEmpty() || codigoSeguridad.isEmpty()) {
            AlertMessage.show(Alert.AlertType.ERROR, "Error", "Por favor, complete todos los campos");
            return;
        }

        if (!fecha.matches("\\d{2}/\\d{4}")) {
            AlertMessage.show(Alert.AlertType.ERROR, "Error", "Por favor, ingrese una fecha en el formato Mes/Año Ej: 12/2024");
            return;
        }
        String[] partesFecha = fecha.split("/");
        String mes = partesFecha[0];
        String anio = partesFecha[1];

        try {
            boolean respuesta = ApiService.verificarTarjeta(numero,mes,anio,codigoSeguridad);
            AlertMessage.show(Alert.AlertType.INFORMATION, "Éxito", "Tarjeta Válida");
            CreditCardService.getInstance().addCreditCard(numero,nombre,mes,anio,codigoSeguridad);

        } catch (IOException e) {
            String mensaje = e.getMessage();
            if(mensaje == null){
                mensaje = "Por el momento no es posible iniciar sesión";
            }
            AlertMessage.show(Alert.AlertType.ERROR, "Error",  mensaje);
        } catch (InterruptedException e) {
            AlertMessage.show(Alert.AlertType.ERROR, "Error",   e.getMessage());
        }
    }

    @FXML
    private void handleVerTarjetas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(DemoApplication.class.getResource("ver-tarjetas-view.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL); // Establecer la modalidad
        stage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Establecer la ventana propietaria
        stage.setScene(new Scene(root));
        stage.setTitle("Ver Tarjetas");


        //Ítalo: obtener la instancia del controlador desde el cargador FXML
        CreditCardController controller = loader.getController();


        creditCardList = FXCollections.observableArrayList(CreditCardService.getInstance().getTarjetas());

        //Ítalo: personalizar la apariencia de cada elemento del ListView
        controller.ListViewCreditCard.setCellFactory(param -> new ListCell<CreditCard>() {
            @Override
            protected void updateItem(CreditCard creditCard, boolean empty) {
                super.updateItem(creditCard, empty);

                if (empty || creditCard == null) {
                    setText(null);
                } else {
                    setText(creditCard.toString());
                }
            }
        });

        //Ítalo: asignar la lista de tarjetas de crédito al ListView
        controller.ListViewCreditCard.setItems(creditCardList);
        stage.showAndWait(); //Ítalo: mostrar la ventana modal y esperar hasta que se cierre (para que no se presione la de "atrás")
    }

}

