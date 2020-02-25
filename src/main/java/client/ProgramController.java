package client;

import baselib.managers.NetworkManager;
import baselib.managers.DataManager;
import client.classes.Account;
import client.enums.MessageAlignment;
import client.ui.login_display.LoginWindow;
import client.ui.main_window.MainWindow;
import client.ui.main_window.chat_pane.ChatPane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Program Controller.
 *
 * This class manages all data and resources for the program.
 * it has all interactions between windows and data models defined in this class.
 *
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */
public class ProgramController extends Application {
    // this section defines the network manager to be referenced throughout the program
    NetworkManager networkManager = new NetworkManager();
    // this section defines the windows that are in use
    MainWindow mainWindow;
    LoginWindow loginWindow;

    //this defines all data objects that are in use
    DataManager dataManager;
    HashMap<String, Object> preferences;
    Account account;
    
    private String loginMsg = "Incorrect Login Details!", createAccountMsg = "Invalid Details! Please correct!";
    private ArrayList<String> testMessages;

    // defining the functions of the
    private EventHandler onRequestLogin = event -> { this.LoginRequest(this.loginWindow.getUsername(), this.loginWindow.getPassword()); };
    private EventHandler onRequestCreate = event -> { this.LoginCreateUser(this.loginWindow.getUsername(), this.loginWindow.getPassword()); };
    private EventHandler onRequestClose = event -> { this.RequestLogout(); };
    private EventHandler onRequestSendMessage = event -> { String a = this.mainWindow.getMessageBoxText(); this.testMessages.add(a); this.mainWindow.addMessage(a, MessageAlignment.sent); this.networkManager.getCurrentClient().queueMessage(this.mainWindow.getMessageBoxText())};
    
    // TMP DELETE WHEN DONE
    private EventHandler tmpContact = event -> { this.networkManager.getCurrentServer().setRequestToChat(true) };

    /**
     * this is called by main
     * @param args arguments passed from the command line
     * @throws Exception any exception
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /**
     * this is called after the application class has finished.
     * @param stage the stage given to the function by application.
     * @throws Exception any thing that could go wrong.
     */
    public void start(Stage stage) throws Exception {
	this.networkManager.start();
        System.out.println(this);

        this.loginWindow = new LoginWindow();
        this.mainWindow = new MainWindow();
        this.dataManager = new DataManager();

        this.loginWindow.setOnRequestLogin(onRequestLogin);
        this.loginWindow.setOnRequestCreate(onRequestCreate);
        this.mainWindow.setOnRequestClose(onRequestClose);
        this.mainWindow.setOnRequestSendMessage(onRequestSendMessage);


        // show the login window
        this.loginWindow.show();
    }

    /**
     * this saves data and closes the program down
     */
    private void RequestLogout() {
        this.dataManager.lock();
        this.mainWindow.hide();
        this.loginWindow.show();
    }

    /**
     * unlocks the users data and opens up the rest of the windows
     * @param username the users username
     * @param Password the users password
     */
    public void LoginRequest(String username, String Password) {
        if (this.dataManager.unlock(username, Password)) {
            this.loginWindow.hide();

            this.testMessages = (ArrayList<String>) this.dataManager.getObject("TestMessages");

            Iterator a = this.testMessages.iterator();
            while (a.hasNext()) {
                String tmp = (String) a.next();
                this.mainWindow.addMessage(tmp, MessageAlignment.recieved);
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
