package client.models;

import client.classes.Account;
import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.managers.DataManager;
import client.managers.NetworkManager;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.File;
import java.security.Key;
import java.util.*;

public class ApplicationModel {

    private static ApplicationModel instance;

    // persistent variables
    SimpleBooleanProperty loginStatus;

    SimpleStringProperty name;
    SimpleObjectProperty<UUID> uuid;

    SimpleObjectProperty<Key> publicKey;
    SimpleObjectProperty<Key> privateKey;

    SimpleListProperty<Server> serverList;
    SimpleMapProperty<UUID, Contact> contactHashMap;

    SimpleListProperty<Contact> onlineContactList;
    SimpleListProperty<Message> currentMessageList;

    // volatile variables
    SimpleObjectProperty<Contact> currentContact;
    SimpleObjectProperty<Server> currentServer;

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
            this.contactHashMap = new SimpleMapProperty<UUID, Contact>(FXCollections.observableMap((Map<UUID, Contact>) this.dataManager.getObject("Contacts")));
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
                Map<UUID, Contact> contactList = new HashMap<UUID, Contact>();
                List<Server> serverList = new ArrayList<Server>();
                Account account = new Account(username);

                // implement it in the model
                this.contactHashMap = new SimpleMapProperty<UUID, Contact>(FXCollections.observableMap(contactList));
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

        this.contactHashMap.set(null);
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

    public ObservableMap<UUID, Contact> getContactList() {
        return contactHashMap.get();
    }

    public SimpleBooleanProperty loginStatusProperty() {
        return loginStatus;
    }

}
