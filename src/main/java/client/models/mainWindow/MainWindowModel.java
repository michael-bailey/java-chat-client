package client.models.mainWindow;

import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.models.ApplicationModel;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class MainWindowModel {

    // changeListeners


    ChangeListener<? super String> searchChangeListener = (observable, oldValue, newValue) -> {
        this.contactViewList.clear();
        Pattern regex = Pattern.compile(newValue + "[a-zA-z0-9]*");
        for (Contact i : this.appContactList) {
            if (regex.matcher(i.getUsername()).matches()) {
                this.contactViewList.add(i);
            }
        }
    };


    @SuppressWarnings("unchecked cast")
    ListChangeListener<? super Server> appServerListChange = c -> {
        this.serverViewList.set((ObservableList<Server>) c.getList());
    };

    @SuppressWarnings("unchecked cast")
    ListChangeListener<? super Contact> appContactListChange = c -> {
        this.contactViewList.set((ObservableList<Contact>) c.getList());
    };

    @SuppressWarnings("unchecked cast")
    ListChangeListener<? super Message> appMessageListChange = c -> {
        this.messageViewList.set((ObservableList<Message>) c.getList());
    };

    private SimpleStringProperty searchString = new SimpleStringProperty();
    private SimpleStringProperty messageString = new SimpleStringProperty();

    // bound to the views
    private SimpleListProperty<Server> serverViewList = new SimpleListProperty<>();
    private SimpleListProperty<Contact> contactViewList = new SimpleListProperty<>();
    private SimpleListProperty<Message> messageViewList = new SimpleListProperty<>();

    // bound to the app
    private SimpleListProperty<Server> appServerList = new SimpleListProperty<>();
    private SimpleListProperty<Contact> appContactList = new SimpleListProperty<>();
    private SimpleListProperty<Message> appMessageList = new SimpleListProperty<>();

    ApplicationModel appModel = ApplicationModel.getInstance();

    public MainWindowModel() {
        appModel.serverListProperty().bindBidirectional(this.appServerList);
        appModel.onlineContactsListProperty().bindBidirectional(this.appContactList);
        appModel.messageListProperty().bindBidirectional(this.appMessageList);

        this.appMessageList.set(FXCollections.observableList(new ArrayList<>()));
        this.appContactList.set(FXCollections.observableList(new ArrayList<>()));
        this.appServerList.set(FXCollections.observableList(new ArrayList<>()));

        serverViewList.bindBidirectional(appServerList);

        this.searchString.addListener(this.searchChangeListener);
        this.appServerList.addListener(this.appServerListChange);
        this.appContactList.addListener(this.appContactListChange);
        this.appMessageList.addListener(this.appMessageListChange);
    }

    public void logout() {
        ApplicationModel.getInstance().logout();
    }

    public SimpleStringProperty searchStringProperty() {
        return searchString;
    }

    public SimpleStringProperty messageStringProperty() {
        return messageString;
    }

    public SimpleListProperty<Server> serverViewListProperty() {
        return serverViewList;
    }

    public SimpleListProperty<Contact> contactViewListProperty() {
        return contactViewList;
    }

    public SimpleListProperty<Message> messageViewListProperty() {
        return messageViewList;
    }
}
