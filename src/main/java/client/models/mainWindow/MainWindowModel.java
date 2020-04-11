package client.models.mainWindow;

import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.models.ApplicationModel;
import client.views.main_window.ContactListCell;
import client.views.main_window.MessageListCell;
import client.views.main_window.ServerListCell;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainWindowModel {

    ApplicationModel appModel = ApplicationModel.getInstance();

    private final SimpleListProperty<Server> appModelServerList = new SimpleListProperty<>();
    private final SimpleListProperty<Server> viewServerList = new SimpleListProperty<>();

    private final ChangeListener<? super ObservableList<Server>> appModelServerListChangeListener = (observable, oldValue, newValue) -> {
        this.viewServerList.set(newValue);
    };


    // list view factories
    private final Callback messageCellFactory = (Callback<ListView<Message>, ListCell<Message>>) studentListView -> {
        System.out.println("Creating message cell");
        return new MessageListCell();
    };
    private final Callback contactCellFactory = (Callback<ListView<Contact>, ContactListCell>) studentListView -> {
        System.out.println("Creating contact cell");
        return new ContactListCell();
    };
    private final Callback serverCellFactory = (Callback<ListView<Server>, ServerListCell>) studentListView -> {
        System.out.println("Creating ServerCell");
        return new ServerListCell();
    };


    public MainWindowModel() {

        this.appModelServerList.bind(appModel.serverListProperty());
        this.appModelServerList.addListener(this.appModelServerListChangeListener);
    }

    public void logout() {
        ApplicationModel.getInstance().logout();
    }

    public SimpleListProperty<Server> viewServerListProperty() {
        return viewServerList;
    }

    public Callback getMessageCellFactory() {
        return messageCellFactory;
    }

    public Callback getContactCellFactory() {
        return contactCellFactory;
    }

    public Callback getServerCellFactory() {
        return serverCellFactory;
    }
}
