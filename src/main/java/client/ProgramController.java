package client;

import client.ui.PreferencesWindow.PreferencesWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import client.ui.*;

public class ProgramController extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        new PreferencesWindow();

        LoginWindow login = new LoginWindow(stage);
        login.setScene(login.loginDisplay());
        login.setPreScene(login.createAccDisplay());
        stage.setScene(login.getScene());
        stage.show();
    }
}
