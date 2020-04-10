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
import javafx.collections.ObservableList;
import javafx.scene.input.Clipboard;

import java.io.File;
import java.security.Key;
import java.util.*;

public class ApplicationModel {

    private static ApplicationModel instance;

    // login value
    private final SimpleBooleanProperty loginStatus;

    // server list value
    private final SimpleListProperty<Server> serverList;

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

    public ApplicationModel() {
        loginStatus = new SimpleBooleanProperty(false);

        // create the server list property
        serverList = new SimpleListProperty<Server>(FXCollections.observableList(new ArrayList<Server>()));
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
        if (this.dataManager.unlock(username, password)) {
            // unpack the data from the data manager

            // get server list
            ArrayList<Server> userServerList = (ArrayList<Server>) dataManager.getObject("Servers");
            if (userServerList != null) {
                serverList.set(FXCollections.observableList(userServerList));
            } else {
                userServerList = new ArrayList<>();
                dataManager.addObject("Servers", userServerList);
                serverList.set(FXCollections.observableList(userServerList));
            }

            this.loginStatus.set(true);
        } else {
            // check if the file exists
            if (new File(username + ".dat").exists()) {
                System.out.println("login failed!");
                this.loginStatus.set(false);

            // create a new file and user
            } else {
                // create a new file
                this.dataManager.createNew(username, password);


                // save and login
                this.dataManager.Save();
                this.loginStatus.set(true);
            }
        }
    }

    public void logout() {
        // pack the data and lock the data manager
        this.dataManager.lock();


        this.loginStatus.set(false);
    }

    public SimpleBooleanProperty loginStatusProperty() {
        return loginStatus;
    }

    public SimpleListProperty<Server> serverListProperty() {
        return serverList;
    }
}
