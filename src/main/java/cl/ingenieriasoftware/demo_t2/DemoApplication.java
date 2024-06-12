package cl.ingenieriasoftware.demo_t2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DemoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DemoApplication.class.getResource("registrar-usuario-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bienvenido(a)");
        stage.setScene(scene);
        stage.show();
    }
}