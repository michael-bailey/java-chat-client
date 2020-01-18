package client;

import client.classes.Account;
import client.interfaces.controllers.ILoginWindowController;
import client.interfaces.controllers.IMainWindowController;
import client.interfaces.controllers.IPreferenceWindowController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.application.Application;

import client.ui.preference_window.PreferenceWindow;
import client.ui.login_display.LoginWindow;
import client.ui.main_window.MainWindow;
import baselib.managers.DataManager;

import java.util.HashMap;

/**
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */
public class ProgramController extends Application implements ILoginWindowController, IMainWindowController, IPreferenceWindowController {


    // this section defines the windows that are in use
    PreferenceWindow preferenceWindow;
    MainWindow mainWindow;
    LoginWindow loginWindow;

    //this defines all data objects that are in use
    DataManager dataManager;
    HashMap<String, Object> preferences;
    Account account;


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        // create windows in memory
        this.mainWindow = new MainWindow(this);
        this.loginWindow = new LoginWindow(this);
        this.preferenceWindow = new PreferenceWindow(new HashMap<>());

        this.dataManager = new DataManager();

        // show the Login window
        this.loginWindow.show();
        this.preferenceWindow.show();
    }

    @Override
    public void LoginRequest(String username, String Password) {
        if (this.dataManager.unlock(username, Password)) {
            this.loginWindow.hide();
            this.account = (Account) this.dataManager.getObject("account");
            this.preferences = (HashMap<String, Object>) this.dataManager.getObject("preferences");
            // this.contacts
            this.mainWindow.show();
        } else {
            // code to show error message
        }
    }

    @Override
    public void LoginCreateUser(String username, String Password) {
        if (this.dataManager.createNew(username, Password)) {
            this.loginWindow.hide();
            // set up the basic account for the user.
            this.account = new Account(username);
            this.dataManager.addObject("account", this.account);
            this.preferences = new HashMap<>();
            this.dataManager.addObject("preferences", this.preferences);
            this.mainWindow.show();
        } else {
            // code to say the account exists
        }
    }
}
