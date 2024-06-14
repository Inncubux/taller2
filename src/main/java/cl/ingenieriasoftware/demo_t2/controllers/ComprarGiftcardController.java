package cl.ingenieriasoftware.demo_t2.controllers;

import cl.ingenieriasoftware.demo_t2.Util.AlertMessage;
import cl.ingenieriasoftware.demo_t2.services.ApiService;
import cl.ingenieriasoftware.demo_t2.services.CreditCardService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ComprarGiftcardController {
    @FXML
    private TextField textNumTarjeta;
    @FXML
    private TextField textFechaExp;
    @FXML
    private TextField textCode;
    @FXML
    public void handlePagar(ActionEvent event) {
        String numTarjeta = textNumTarjeta.getText();
        String fechaExp = textFechaExp.getText();
        String code = textCode.getText();
        if(numTarjeta.isEmpty() || fechaExp.isEmpty() || code.isEmpty()){
            AlertMessage.show(Alert.AlertType.ERROR, "ERROR", "debes rellenar todos los campos");
            return;
        }
        if (!fechaExp.matches("\\d{2}/\\d{4}")) {
            AlertMessage.show(Alert.AlertType.ERROR, "Error", "Por favor, ingrese una fecha en el formato Mes/Año Ej: 12/2024");
            return;
        }
        String[] partesFecha = fechaExp.split("/");
        String mes = partesFecha[0];
        String anio = partesFecha[1];
        try {
            ApiService.login("vordenes16", "123789");
            boolean respuesta = ApiService.verificarTarjeta(numTarjeta,mes,anio,code);
            AlertMessage.show(Alert.AlertType.INFORMATION, "Éxito", "Pago realizado");
            CreditCardService.getInstance().addCreditCard(numTarjeta,"hola",mes,anio,code);

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
}
