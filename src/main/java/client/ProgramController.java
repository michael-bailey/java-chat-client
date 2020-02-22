package client;

import baselib.managers.DataManager;
import client.classes.Account;
import client.classes.Message;
import client.enums.MessageAlignment;
import client.ui.login_display.LoginWindow;
import client.ui.main_window.MainWindow;
import client.ui.main_window.chat_pane.ChatPane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

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

    // this section defines the windows that are in use
    MainWindow mainWindow;
    LoginWindow loginWindow;

    //this defines all data objects that are in use
    DataManager dataManager;
    HashMap<String, Object> preferences;
    Account account;
    
    private String loginMsg = "Incorrect Login Details!", createAccountMsg = "Invalid Details! Please correct!";
    private ArrayList<Message> testMessages;

    // defining the functions of the
    private EventHandler onRequestLogin = event -> {
        this.LoginRequest(this.loginWindow.getUsername(), this.loginWindow.getPassword());
    };

    private EventHandler onRequestCreate = event -> {
        this.LoginCreateUser(this.loginWindow.getUsername(), this.loginWindow.getPassword());
    };

    private EventHandler onRequestClose = event -> {
        this.RequestLogout();
    };

    private EventHandler onRequestSendMessage = event -> {
        Message a = new Message(this.mainWindow.getMessageBoxText(), false);
        this.testMessages.add(a);
        this.mainWindow.addMessage(a);
    };

    private EventHandler onSpam = event -> {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        for(int i = 0; i < 100; i++) {
            Stage a = new Stage();
            a.setScene(new Scene(new GridPane(), 100, 100));
            a.setX(new Random().nextInt((int) screenBounds.getMaxX()));
            a.setY(new Random().nextInt((int) screenBounds.getMaxY()));
            a.show();
        }
    };



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
        System.out.println(this);

        this.loginWindow = new LoginWindow();
        this.mainWindow = new MainWindow();
        this.dataManager = new DataManager();

        this.loginWindow.setOnRequestLogin(onRequestLogin);
        this.loginWindow.setOnRequestCreate(onRequestCreate);
        this.mainWindow.setOnRequestClose(onRequestClose);
        this.mainWindow.setOnRequestSendMessage(onRequestSendMessage);
        this.mainWindow.setOnSpam(this.onSpam);

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

            this.testMessages = (ArrayList<Message>) this.dataManager.getObject("TestMessages");

            Iterator<Message> a = this.testMessages.iterator();
            while (a.hasNext()) {
                Message tmp = a.next();
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
            this.testMessages = new ArrayList<Message>();
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
