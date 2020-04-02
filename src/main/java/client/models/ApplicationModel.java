package client.models;

import client.classes.Account;
import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.managers.DataManager;
import client.managers.NetworkManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApplicationModel {

    private static ApplicationModel instance;

    // persistent variables
    SimpleBooleanProperty loginStatus;

    SimpleStringProperty name = new SimpleStringProperty();
    SimpleObjectProperty<UUID> uuid = new SimpleObjectProperty<>();

    SimpleObjectProperty<Key> publicKey = new SimpleObjectProperty<>();
    SimpleObjectProperty<Key> privateKey = new SimpleObjectProperty<>();

    SimpleListProperty<Server> serverList = new SimpleListProperty<>();
    SimpleListProperty<Contact> contactList = new SimpleListProperty<>();

    SimpleListProperty<Contact> onlineContactList = new SimpleListProperty<>();
    SimpleListProperty<Message> currentMessageList = new SimpleListProperty<>();

    // volatile variables
    SimpleObjectProperty<Contact> currentContact = new SimpleObjectProperty<>();
    SimpleObjectProperty<Server> currentServer = new SimpleObjectProperty<>();

    DataManager dataManager = new DataManager();
    NetworkManager networkManager = new NetworkManager();

    public ApplicationModel() {
        loginStatus = new SimpleBooleanProperty(false);
    }

    public static ApplicationModel getInstance() {
        if (ApplicationModel.instance == null) {
            ApplicationModel.instance = new ApplicationModel();
        }
        return ApplicationModel.instance;
    }

    public void save() {
        this.dataManager.Save();
    }

    public void login(String username, String password) {
        if (username.length() < 4 || password.length() < 8) {
            return;
        }
        if (this.dataManager.unlock(username, password)) {
            // unpack the data
            this.contactList = new SimpleListProperty<Contact>(FXCollections.observableList((List<Contact>) this.dataManager.getObject("Contacts")));
            this.serverList = new SimpleListProperty<Server>(FXCollections.observableList((List<Server>) this.dataManager.getObject("Servers")));

            this.name = new SimpleStringProperty();
            this.uuid = new SimpleObjectProperty<>();
            this.publicKey = new SimpleObjectProperty<>();
            this.privateKey = new SimpleObjectProperty<>();

            Account account = (Account) this.dataManager.getObject("Account");
            this.name.set(account.getUsername());
            this.uuid.set(account.getuuid());
            this.publicKey.set(account.getPublicKey());
            this.privateKey.set(account.getPrivateKey());

            // set the status property
            this.loginStatus.set(true);
        } else {
            if (new File(username + ".dat").exists()) {
                System.out.println("login failed!");
                this.loginStatus.set(false);
            } else {
                // create a new file
                this.dataManager.createNew(username, password);

                // create the new data
                List<Contact> contactList = new ArrayList<Contact>();
                List<Server> serverList = new ArrayList<Server>();
                Account account = new Account(username);

                // implement it in the model
                this.contactList = new SimpleListProperty<Contact>(FXCollections.observableList(contactList));
                this.serverList = new SimpleListProperty<Server>(FXCollections.observableList(serverList));

                this.name = new SimpleStringProperty();
                this.uuid = new SimpleObjectProperty<>();
                this.publicKey = new SimpleObjectProperty<>();
                this.privateKey = new SimpleObjectProperty<>();

                this.name.set(account.getUsername());
                this.uuid.set(account.getuuid());
                this.publicKey.set(account.getPublicKey());
                this.privateKey.set(account.getPrivateKey());

                // add to the dataManager
                this.dataManager.addObject("Contacts", contactList);
                this.dataManager.addObject("Servers", serverList);
                this.dataManager.addObject("Account", account);

                this.dataManager.Save();

                // set property
                this.loginStatus.set(true);
            }
        }
    }

    public void logout() {
        this.dataManager.lock();

        this.contactList.set(null);
        this.serverList.set(null);
        
        this.name.set(null);
        this.uuid.set(null);
        this.publicKey.set(null);
        this.privateKey.set(null);

        this.loginStatus.set(false);
    }

    public SimpleListProperty<Server> serverListProperty() {
        return serverList;
    }

    public SimpleListProperty<Contact> onlineContactListProperty() {
        return onlineContactList;
    }

    public SimpleListProperty<Message> messageListProperty() { return currentMessageList; }

    public SimpleBooleanProperty loginStatusProperty() {
        return loginStatus;
    }

}
