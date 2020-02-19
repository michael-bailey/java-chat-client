package client;

import client.classes.Account;
import client.interfaces.IWindow;
import client.ui.main_window.chat_pane.MessageTextBox;
import javafx.stage.Stage;
import javafx.application.Application;

import client.ui.preference_window.PreferenceWindow;
import client.ui.login_display.LoginWindow;
import client.ui.main_window.MainWindow;
import baselib.managers.DataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    private ArrayList<String> testMessages;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        System.out.println(this);

        this.loginWindow = new LoginWindow();
        this.mainWindow = new MainWindow(1);

        this.loginWindow.setOnRequestLogin(event -> {
            this.LoginRequest(this.loginWindow.getUsername(), this.loginWindow.getPassword());
        });

        this.loginWindow.setOnRequestCreate(event -> {
            this.LoginCreateUser(this.loginWindow.getUsername(), this.loginWindow.getPassword());
        });

        this.mainWindow.setOnRequestClose(event -> {
            this.RequestLogout();
        });

        this.mainWindow.setOnRequestSendMessage(event -> {
            String a = this.mainWindow.getMessageBoxText();
            this.testMessages.add(a);
            this.mainWindow.addMessage(a);
        });

        this.dataManager = new DataManager();

        // show the login window
        this.loginWindow.show();
    }

    private void RequestLogout() {
        this.dataManager.lock();
        this.mainWindow.hide();
        this.loginWindow.show();
    }

    public void LoginRequest(String username, String Password) {
        if (this.dataManager.unlock(username, Password)) {
            this.loginWindow.hide();

            this.testMessages = (ArrayList<String>) this.dataManager.getObject("TestMessages");

            Iterator a = this.testMessages.iterator();
            while (a.hasNext()) {
                String tmp = (String) a.next();
                this.mainWindow.addMessage(tmp);
            }

            this.account = (Account) this.dataManager.getObject("account");
            this.mainWindow.show();
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
            this.testMessages = new ArrayList<String>();
            this.dataManager.addObject( "TestMessages", this.testMessages);
            this.mainWindow.show();
        }
    }

    private void shutdown() {
        this.dataManager.lock();
        try {
            this.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
