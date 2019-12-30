package client;

import java.nio.file.FileSystems;

import javafx.stage.Stage;
import javafx.application.Application;

import client.ui.preference_window.PreferencesWindow;
import client.ui.login_display.LoginWindow;
import client.ui.main_window.MainWindow;
import client.interfaces.*;
import client.managers.DataManager;

/**
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */
public class ProgramController extends Application implements LoginWindowController, MainWindowController {

    PreferencesWindow prefrenceWindow;
    MainWindow mainWindow;
    LoginWindow loginWindow;
    DataManager dataManager;

    public static void main(String[] args) throws Exception {
        System.out.println(FileSystems.getDefault().getPath(".").toAbsolutePath());
        launch(args);

    }

    public void start(Stage stage) throws Exception {

        // create windows in memory
        this.prefrenceWindow = new PreferencesWindow();
        this.mainWindow = new MainWindow();
        this.loginWindow = new LoginWindow(this);
        this.dataManager = new DataManager();

        // show the Login window
        loginWindow.show(true);

        
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
