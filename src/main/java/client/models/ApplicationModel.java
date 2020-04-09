package client.models;

import client.classes.Account;
import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.managers.DataManager;
import client.managers.NetworkManager;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.input.Clipboard;

import java.io.File;
import java.security.Key;
import java.util.*;

public class ApplicationModel {

    private static ApplicationModel instance;

    // persistent variables
    private SimpleBooleanProperty loginStatus;

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleObjectProperty<UUID> uuid = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Key> publicKey = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Key> privateKey = new SimpleObjectProperty<>();

    private SimpleListProperty<Server> serverList = new SimpleListProperty<>();

    private SimpleMapProperty<UUID, Contact> contactHashMap = new SimpleMapProperty<>();

    private SimpleListProperty<Contact> onlineContactsList = new SimpleListProperty<Contact>();
    private SimpleListProperty<Message> MessageList = new SimpleListProperty<Message>();

    // volatile variables
    private SimpleObjectProperty<Contact> currentContact = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Server> currentServer = new SimpleObjectProperty<>();

    private Clipboard systemClipBoard = Clipboard.getSystemClipboard();
    private DataManager dataManager = new DataManager();
    private NetworkManager networkManager = new NetworkManager();

    ChangeListener<Server> serverChangeListener = (observable, oldValue, newValue) -> {
        // tell the network manager to change the server if not null
        if (newValue != null) {

        }
    };
    ChangeListener<Contact> contactChangeListener = (observable, oldValue, newValue) -> {
        if (newValue != null) {
            Contact contactData;
            if ((contactData = this.contactHashMap.get(newValue.getUUID())) != null) {
                this.messageListProperty().get().clear();
                this.messageListProperty().get().setAll(contactData.getMessages());
            } else {
                this.contactHashMap.put(newValue.getUUID(), newValue);
            }
        } else {
            this.MessageList.set(null);
        }
    };

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
                Map<UUID, Contact> contactMap = new HashMap<UUID, Contact>();
                List<Server> serverList = new ArrayList<Server>();
                Account account = new Account(username);

                // implement it in the model
                this.serverList = new SimpleListProperty<Server>(FXCollections.observableList(serverList));
                this.contactHashMap = new SimpleMapProperty<UUID, Contact>(FXCollections.observableMap(contactMap));


                this.name = new SimpleStringProperty();
                this.uuid = new SimpleObjectProperty<>();
                this.publicKey = new SimpleObjectProperty<>();
                this.privateKey = new SimpleObjectProperty<>();

                this.name.set(account.getUsername());
                this.uuid.set(account.getuuid());
                this.publicKey.set(account.getPublicKey());
                this.privateKey.set(account.getPrivateKey());

                // add to the dataManager
                this.dataManager.addObject("Contacts", contactMap);
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

    public SimpleListProperty<Message> messageListProperty() {
        return MessageList;
    }

    public SimpleBooleanProperty loginStatusProperty() {
        return loginStatus;
    }

    public SimpleListProperty<Contact> onlineContactsListProperty() {
        return onlineContactsList;
    }

    public SimpleObjectProperty<Server> currentServerProperty() {
        return currentServer;
    }

    public Clipboard getSystemClipBoard() {
        return systemClipBoard;
    }

    public Contact getCurrentContact() {
        return currentContact.get();
    }

    public SimpleObjectProperty<Contact> currentContactProperty() {
        return currentContact;
    }
}
