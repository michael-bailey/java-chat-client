package client.models.mainWindow;

import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.models.ApplicationModel;
import client.views.main_window.ContactListCell;
import client.views.main_window.MessageListCell;
import client.views.main_window.ServerListCell;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

    private final SimpleStringProperty searchString = new SimpleStringProperty();
    private final SimpleStringProperty messageString = new SimpleStringProperty();

    private final SimpleObjectProperty<Contact> currentContact = new SimpleObjectProperty<Contact>();
    private final SimpleObjectProperty<Server> currentServer = new SimpleObjectProperty<Server>();

    // bound to the views
    private final SimpleListProperty<Server> serverViewList = new SimpleListProperty<>();
    private final SimpleListProperty<Contact> contactViewList = new SimpleListProperty<>();
    private final SimpleListProperty<Message> messageViewList = new SimpleListProperty<>();

    // bound to the app
    private final SimpleListProperty<Server> appServerList = new SimpleListProperty<>();
    private final SimpleListProperty<Contact> appContactList = new SimpleListProperty<>();
    private final SimpleListProperty<Message> appMessageList = new SimpleListProperty<>();

    @SuppressWarnings("unchecked cast")
    private final ListChangeListener<? super Server> appServerListChange = c -> {
        this.serverViewList.set((ObservableList<Server>) c.getList());
    };
    @SuppressWarnings("unchecked cast")
    private final ListChangeListener<? super Contact> appContactListChange = c -> {
        this.contactViewList.set((ObservableList<Contact>) c.getList());
    };
    @SuppressWarnings("unchecked cast")
    private final ListChangeListener<? super Message> appMessageListChange = c -> {
        this.messageViewList.set((ObservableList<Message>) c.getList());
    };

    private final ChangeListener<String> searchChangeListener = (observable, oldValue, newValue) -> {
        this.contactViewList.clear();
        Pattern regex = Pattern.compile(newValue + "[a-zA-z0-9]*");
        for (Contact i : this.appContactList) {
            if (regex.matcher(i.getUsername()).matches()) {
                this.contactViewList.add(i);
            }
        }
    };
    private final ChangeListener<MultipleSelectionModel<Server>> serverSelectionChangeListener = (observable, oldValue, newValue) -> {
        this.currentServer.set(newValue.getSelectedItem());
    };
    private final ChangeListener<MultipleSelectionModel<Contact>> contactChangeListener = (observable, oldValue, newValue) -> {
        this.currentContact.set(newValue.getSelectedItem());
    };

    // list view factories
    private final Callback messageCellFactory = new Callback<ListView<Message>, ListCell<Message>>() {
        @Override
        public ListCell<Message> call(ListView<Message> studentListView) {
            return new MessageListCell();
        }
    };
    private final Callback contactCellFactory = new Callback<ListView<Contact>, ContactListCell>() {
        @Override
        public ContactListCell call(ListView<Contact> studentListView) {
            return new ContactListCell();
        }
    };
    private final Callback serverCellFactory = new Callback<ListView<Server>, ServerListCell>() {
        @Override
        public ServerListCell call(ListView<Server> studentListView) {
            return new ServerListCell();
        }
    };


    public MainWindowModel() {
        appModel.serverListProperty().bindBidirectional(this.appServerList);
        appModel.onlineContactsListProperty().bindBidirectional(this.appContactList);
        appModel.messageListProperty().bindBidirectional(this.appMessageList);

        appModel.currentContactProperty().bindBidirectional(this.currentContact);
        appModel.currentServerProperty().bindBidirectional(this.currentServer);

        this.appMessageList.set(FXCollections.observableList(new ArrayList<>()));
        this.appContactList.set(FXCollections.observableList(new ArrayList<>()));
        this.appServerList.set(FXCollections.observableList(new ArrayList<>()));

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


    public Callback getMessageCellFactory() {
        return messageCellFactory;
    }

    public Callback getContactCellFactory() {
        return contactCellFactory;
    }

    public Callback getServerCellFactory() {
        return serverCellFactory;
    }

    public ChangeListener<MultipleSelectionModel<Server>> getServerSelectionChangeListener() {
        return serverSelectionChangeListener;
    }

    public ChangeListener<MultipleSelectionModel<Contact>> getContactSelectionChangeListener() {
        return contactChangeListener;
    }
}
