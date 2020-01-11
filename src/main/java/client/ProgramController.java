package client;

import javafx.stage.Stage;
import javafx.application.Application;

import client.ui.preference_window.PreferenceWindow;
import client.ui.login_display.LoginWindow;
import client.ui.main_window.MainWindow;
import client.interfaces.*;
import baselib.managers.DataManager;

/**
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */
public class ProgramController extends Application implements LoginWindowController, MainWindowController {

    PreferenceWindow preferenceWindow;
    MainWindow mainWindow;
    LoginWindow loginWindow;
    DataManager dataManager;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        // create windows in memory
        this.preferenceWindow = new PreferenceWindow();
        this.mainWindow = new MainWindow();
        this.loginWindow = new LoginWindow(this);
        this.dataManager = new DataManager();

        // show the Login window
        loginWindow.show(true);
    }

    @Override
    public void LoginRequest(String name, String password) {
        if (this.dataManager.unlock(name, password)) {
            this.loginWindow.hide();
            this.mainWindow.show();
        }
    }

    @Override
    public void LoginCreateUser(String username, String Password) {
        if (this.dataManager.createNew(username, Password)) {
            this.loginWindow.hide();
            this.mainWindow.show();
        }
    }
}
