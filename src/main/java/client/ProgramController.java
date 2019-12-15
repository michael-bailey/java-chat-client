package client;

import client.ui.PreferencesWindow.PreferencesWindow;
import javafx.application.*;
import javafx.stage.Stage;
import client.ui.*;

public class ProgramController extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        LoginWindow login = new LoginWindow();
        login.createWindow(stage);

        new PreferencesWindow();
    }
}