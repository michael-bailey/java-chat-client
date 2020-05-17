package client.models;

import client.ChatWindow.ListCells.ServerListCellModel;
import client.classes.Contact;
import client.classes.Server;
import client.managers.DataManager;
import client.managers.NetworkManager;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.UUID;


public class ApplicationModel {

    private static ApplicationModel instance;

    // login value
    private final SimpleBooleanProperty loginStatus;

    // server list value
    private final SimpleMapProperty<UUID, Server> serverStore = new SimpleMapProperty<>();
    private final SimpleListProperty<ServerListCellModel> serverList = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
    private final SimpleObjectProperty<Server> selectedServer = new SimpleObjectProperty<>();

    private final DataManager dataManager = new DataManager();
    private final NetworkManager networkManager = new NetworkManager();

    ChangeListener<Server> serverChangeListener = (observable, oldValue, newValue) -> {
        // tell the network manager to change the server if not null
        if (newValue != null) {

        }
    };
    ChangeListener<Contact> contactChangeListener = (observable, oldValue, newValue) -> {
        if (newValue != null) {

        }
    };

    private ApplicationModel() {
        loginStatus = new SimpleBooleanProperty(false);

        // bind to the network manager
        //this.loginStatus.bindBidirectional(this.networkManager.loggedInProperty());
        //this.serverStore.bindBidirectional(this.networkManager.serverStoreProperty());
        //this.serverList.bindBidirectional(this.networkManager.serverListProperty());
        //this.selectedServer.bindBidirectional(this.networkManager.currentServerProperty());

        // create the server list property
        //serverList = new SimpleListProperty<Server>(FXCollections.observableList(new ArrayList<Server>()));
        //selectedServer = new SimpleObjectProperty<Server>();
    }

    public static ApplicationModel getInstance() {
        if (ApplicationModel.instance == null) {
            ApplicationModel.instance = new ApplicationModel();
        }
        return ApplicationModel.instance;
    }

    public void login(String username, String password) {

        // check the length of the username and password
        if (username.length() < 4 || password.length() < 8) {
            return;
        }

        // attempt dataManager unlock

    }

    public void logout() {
        // pack the data and lock the data manager
        this.dataManager.lock();


        this.loginStatus.set(false);
    }

    public boolean requestAddServer(String ipAddress) {
        //return this.networkManager.requestAddServer(ipAddress);
        return false;
    }

    public SimpleBooleanProperty loginStatusProperty() {
        return loginStatus;
    }

    public SimpleListProperty<ServerListCellModel> serverListProperty() {
        return serverList;
    }

    public SimpleObjectProperty<Server> selectedServerProperty() {
        return selectedServer;
    }

    public SimpleMapProperty<UUID, Server> serverStoreProperty() {
        return serverStore;
    }
}
