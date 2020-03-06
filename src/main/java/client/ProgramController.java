package client;

import baselib.managers.DataManager;
import client.classes.Account;
import client.classes.Contact;
import client.classes.Message;
import client.ui.login_display.LoginWindow;
import client.ui.main_window.MainWindow;
import client.ui.main_window.contact_pane.ContactBox;
import client.ui.other.AddContactDialogue;
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
 * it also contatins all event handlers that
 *
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */
public class ProgramController extends Application {

    // this section defines the windows that are in use
    MainWindow mainWindow = new MainWindow();
    LoginWindow loginWindow = new LoginWindow();
    AddContactDialogue addContactDialogue = new AddContactDialogue();

    //this defines all data objects that are in use
    DataManager dataManager = new DataManager();
    ArrayList<Contact> contacts;
    Account account;
    
    private String loginMsg = "Incorrect Login Details!", createAccountMsg = "Invalid Details! Please correct!";
    private ArrayList<Message> testMessages;

    private Contact currentContact;


    private EventHandler onRequestLogin = event -> {
        this.LoginRequest(this.loginWindow.getUsername(), this.loginWindow.getPassword());
    };

    private EventHandler onRequestCreate = event -> {
        this.LoginCreateUser(this.loginWindow.getUsername(), this.loginWindow.getPassword());
    };

    private EventHandler onRequestClose = event -> {
        this.RequestLogout();
    };

    private EventHandler onRequestShowAddContactDialogue = event -> { this.addContactDialogue.show(); };

    private EventHandler onRequestAddContact = event -> {
        Contact a = new Contact(this.addContactDialogue.getNameText(),this.addContactDialogue.getUserIDText());
        this.contacts.add(a);
        this.mainWindow.addContact(a);
    };

    private EventHandler onRequestChangeContact = event -> {
        Contact sourceContact = ((ContactBox) event.getSource()).getContact();
        this.currentContact = sourceContact;
        this.mainWindow.loadMessages(sourceContact.getMessages());
        this.mainWindow.setChatPaneEnabled(true);
    };

    private EventHandler onRequestSendMessage = event -> {
        Message newMessage = new Message(this.mainWindow.getMessageBoxText(), false);
        this.currentContact.addMessage(newMessage);
        this.mainWindow.addMessage(newMessage);
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

        // setting the event handlers
        this.loginWindow.setOnRequestLogin(onRequestLogin);
        this.loginWindow.setOnRequestCreate(onRequestCreate);

        this.mainWindow.setOnRequestClose(onRequestClose);
        this.mainWindow.setOnRequestSendMessage(onRequestSendMessage);
        this.mainWindow.setOnRequestAddContact(this.onRequestShowAddContactDialogue);
        this.mainWindow.setOnRequestChangeContact(this.onRequestChangeContact);

        this.addContactDialogue.setOnAddContact(this.onRequestAddContact);

        this.mainWindow.setChatPaneEnabled(false);

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

            // load contacts
            this.contacts = (ArrayList<Contact>) this.dataManager.getObject("Contacts");
            if (this.contacts != null) {
                this.mainWindow.loadContacts(this.contacts);
            } else {
                this.contacts = new ArrayList<Contact>();
                this.dataManager.addObject("Contacts", this.contacts);
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
            this.contacts = new ArrayList<>();
            this.dataManager.addObject("Contacts", this.contacts);
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
