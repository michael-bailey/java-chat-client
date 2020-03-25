package client.models;

import client.classes.Account;
import client.classes.Contact;
import client.classes.Server;
import client.managers.DataManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.security.Key;
import java.util.List;
import java.util.UUID;

public class ApplicationModel {

    private static ApplicationModel instance;

    // persistent variables
    SimpleBooleanProperty loginStatus;

    SimpleStringProperty name;
    SimpleObjectProperty<UUID> uuid;

    SimpleObjectProperty<Key> publicKey;
    SimpleObjectProperty<Key> privateKey;

    SimpleListProperty<Server> serverList;
    SimpleListProperty<Contact> contactList;

    // volatile variables
    SimpleObjectProperty<Contact> currentContact;
    SimpleObjectProperty<Server> currentServer;

    DataManager dataManager = new DataManager();

    public ApplicationModel() {
        loginStatus = new SimpleBooleanProperty(false);
    }

    public static ApplicationModel getInstance() {
        if (ApplicationModel.instance == null) {
            ApplicationModel.instance = new ApplicationModel();
        }
        return ApplicationModel.instance;
    }

    public void unlockDataManager(String username, String password) {
        if (this.dataManager.unlock(username, password)) {
            // unpack the data
            this.contactList = new SimpleListProperty<Contact>(FXCollections.observableList((List<Contact>) this.dataManager.getObject("Contacts")));
            this.serverList = new SimpleListProperty<Server>(FXCollections.observableList((List<Server>) this.dataManager.getObject("Servers")));

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

                // populate the new file and the model

                // set property
                this.loginStatus.set(true);
            }
        }
    }

    public void lockDataManager() {
        this.dataManager.lock();

        this.contactList.set(null);
        this.contactList.set(null);

        this.name.set(null);
        this.uuid.set(null);
        this.publicKey.set(null);
        this.privateKey.set(null);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public UUID getUuid() {
        return uuid.get();
    }

    public SimpleObjectProperty<UUID> uuidProperty() {
        return uuid;
    }

    public Key getPublicKey() {
        return publicKey.get();
    }

    public SimpleObjectProperty<Key> publicKeyProperty() {
        return publicKey;
    }

    public Key getPrivateKey() {
        return privateKey.get();
    }

    public SimpleObjectProperty<Key> privateKeyProperty() {
        return privateKey;
    }

    public ObservableList<Server> getServerList() {
        return serverList.get();
    }

    public SimpleListProperty<Server> serverListProperty() {
        return serverList;
    }

    public ObservableList<Contact> getContactList() {
        return contactList.get();
    }

    public SimpleListProperty<Contact> contactListProperty() {
        return contactList;
    }

    public boolean getLoginStatus() {
        return loginStatus.get();
    }

    public SimpleBooleanProperty loginStatusProperty() {
        return loginStatus;
    }

    public Contact getCurrentContact() {
        return currentContact.get();
    }

    public SimpleObjectProperty<Contact> currentContactProperty() {
        return currentContact;
    }

    public Server getCurrentServer() {
        return currentServer.get();
    }

    public SimpleObjectProperty<Server> currentServerProperty() {
        return currentServer;
    }


}
