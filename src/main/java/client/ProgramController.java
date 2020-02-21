package client;

import client.classes.Account;
import client.interfaces.IWindow;
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
public class ProgramController extends Application {


    // this section defines the windows that are in use
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
        this.loginWindow = new LoginWindow();

        this.loginWindow.setOnRequestLogin(event -> {
            this.LoginRequest(this.loginWindow.getUsername(), this.loginWindow.getPassword());
        });

        this.loginWindow.setOnRequestCreate(event -> {
            this.LoginCreateUser(this.loginWindow.getUsername(), this.loginWindow.getPassword());
        });

        this.dataManager = new DataManager();

        // show the login window
        this.loginWindow.show();
    }

    public void LoginRequest(String username, String Password) {
        if (this.dataManager.unlock(username, Password)) {

            this.loginWindow.hide();

            // setting up windows that require a login to be complete.
            this.mainWindow = new MainWindow(1);

            // set events for the main window
            this.mainWindow.setOnRequestSendMessage(event -> {
                this.mainWindow.addMessage(this.mainWindow.getMessageBoxText());
            });


            mainWindow.show();
            this.account = (Account) this.dataManager.getObject("account");
            this.mainWindow.show();
        } else {

        }
    }

    public void LoginCreateUser(String username, String Password) {
        if (this.dataManager.createNew(username, Password)) {
            this.loginWindow.hide();
            // set up the basic account for the user.
            this.account = new Account(username);
            this.dataManager.addObject("account", this.account);
            this.preferences = new HashMap<>();
            this.dataManager.addObject("preferences", this.preferences);
            this.mainWindow.show();
        }else{}
    }


}
