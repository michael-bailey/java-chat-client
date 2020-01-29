package client;

import client.classes.Account;
import client.interfaces.controllers.ILoginWindowController;
import client.interfaces.controllers.IMainWindowController;
import client.interfaces.controllers.IPreferenceWindowController;
import client.ui.main_window.widgets.ChatPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

import client.ui.preference_window.PreferenceWindow;
import client.ui.login_display.LoginWindow;
import client.ui.main_window.MainWindow;
import baselib.managers.DataManager;

import java.io.IOException;
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
    
    private String loginMsg = "Incorrect Login Details!", createAccountMsg = "Invalid Details! Please correct!";

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        this.loginWindow = new LoginWindow(this);
        this.dataManager = new DataManager();

        // TODO REMOVE THIS WHEN NOT NEEDED.
        LoginRequest("michael", "password");

        // show the login window
        //this.loginWindow.show();
    }

    @Override
    public void LoginRequest(String username, String Password) {
        if (this.dataManager.unlock(username, Password)) {
            try {
                this.loginWindow.hide();

                // setting up windows that require a login to be complete.
                this.mainWindow = new MainWindow(this, 1);
                mainWindow.show();
                this.preferenceWindow = new PreferenceWindow(this.dataManager);

                this.account = (Account) this.dataManager.getObject("account");

                this.mainWindow.show();
            } catch (IOException e) {

            }
        }else{loginWindow.incorrectDetails(this.loginMsg);}
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
        }else{loginWindow.incorrectDetails(this.createAccountMsg);}
    }
}
