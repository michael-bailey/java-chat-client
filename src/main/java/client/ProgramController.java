package client;

import java.nio.file.FileSystems;

import javafx.stage.Stage;
import javafx.application.Application;

import client.ui.preference_window.PreferencesWindow;
import client.ui.login_display.LoginWindow;
import client.ui.main_window.MainWindow;
import client.interfaces.*;
import client.managers.DataManager;


public class ProgramController extends Application implements LoginWindowController, MainWindowController, DataManagerController {

    PreferencesWindow prefrenceWindow;
    MainWindow mainWindow;
    LoginWindow loginWindow;

    public static void main(String[] args) throws Exception {
        System.out.println(FileSystems.getDefault().getPath(".").toAbsolutePath());
        launch(args);

    }

    public void start(Stage stage) throws Exception {

        // create windows in memory
        this.prefrenceWindow = new PreferencesWindow();
        this.mainWindow = new MainWindow();
        this.loginWindow = new LoginWindow(this);

        loginWindow.show(true);

        DataManager dm = new DataManager();
        dm.createNew("test","sdgsggsagdsa");
    }

    @Override
    public void dataDidLoad() {

    }

    @Override
    public void dataFailedLoad() {

    }

    @Override
    public void LoginRequest() {
        this.loginWindow.hide();
        this.mainWindow.show();
    }

    @Override
    public void LoginCreateUser() {

    }
}
