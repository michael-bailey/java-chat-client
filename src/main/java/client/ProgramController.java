package client;

import basekit.DataBus;
import basekit.interfaces.DataBusClient;
import client.ui.PreferencesWindow.PreferencesWindow;
import client.ui.login_display.LoginWindow;
import client.ui.main_display.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;


public class ProgramController extends Application implements DataBusClient {

    private DataBus dataBus = DataBus.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        // create windows in memory
        new PreferencesWindow();
        new MainWindow(stage);
        new LoginWindow();

        // call up the login window.
        this.dataBus.call(this, "LoginWindowShow", null);
    }

    @Override
    public void call(DataBusClient caller, String message, Object params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void respond(DataBusClient responder, String result) {
        // TODO Auto-generated method stub

    }
}
