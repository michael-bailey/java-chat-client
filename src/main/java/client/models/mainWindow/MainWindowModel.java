package client.models.mainWindow;

import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.models.ApplicationModel;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.MultipleSelectionModel;

import java.util.regex.Pattern;

public class MainWindowModel {

    // changeListeners
    ChangeListener<? super String> searchChangeListener = (observable, oldValue, newValue) -> {
        this.contactViewListProperty().clear();
        Pattern regex = Pattern.compile(newValue + "[a-zA-z0-9]*");
        for (Contact i : this.onlineContactList) {
            if (regex.matcher(i.getUsername()).matches()) {
                this.contactViewListProperty().add(i);
            }
        }
    };

    ChangeListener<MultipleSelectionModel<Server>> serverSelectionChanged = (observable, oldValue, newValue) -> {

    };

    ChangeListener<MultipleSelectionModel<Contact>> contactSelectionChanged = (observable, oldValue, newValue) -> {

    };

    private SimpleStringProperty searchString = new SimpleStringProperty();

    private SimpleListProperty<Server> serverViewList = new SimpleListProperty<>();
    private SimpleListProperty<Contact> onlineContactList = new SimpleListProperty<>();
    private SimpleListProperty<Contact> contactViewList = new SimpleListProperty();
    private SimpleListProperty<Message> messageViewList = new SimpleListProperty();

    SimpleObjectProperty<MultipleSelectionModel<Server>> serverSelectionModel = new SimpleObjectProperty<MultipleSelectionModel<Server>>();
    SimpleObjectProperty<MultipleSelectionModel<Contact>> contactSelectionModel = new SimpleObjectProperty<>();
    SimpleObjectProperty<MultipleSelectionModel<Message>> messageSelectionModel = new SimpleObjectProperty<>();


    public MainWindowModel() {
        this.searchStringProperty().addListener(this.searchChangeListener);

        ApplicationModel appModel = ApplicationModel.getInstance();

        // binding the application model to the selected properties
        appModel.serverListProperty().bindBidirectional(this.serverViewList);
        appModel.onlineContactListProperty().bindBidirectional(this.onlineContactList);
        appModel.messageListProperty().bindBidirectional(this.messageViewList);

        // selection changes
        this.contactSelectionModel.addListener(this.contactSelectionChanged);
        this.serverSelectionModel.addListener(this.serverSelectionChanged);

    }

    public SimpleStringProperty searchStringProperty() {
        return searchString;
    }

    public SimpleListProperty<Contact> contactViewListProperty() {
        return contactViewList;
    }

    public SimpleListProperty<Message> messageViewListProperty() {
        return this.messageViewList;
    }

    public SimpleObjectProperty<MultipleSelectionModel<Server>> serverSelectionModelProperty() {
        return serverSelectionModel;
    }

    public SimpleObjectProperty<MultipleSelectionModel<Contact>> contactSelectionModelProperty() {
        return contactSelectionModel;
    }

    public SimpleObjectProperty<MultipleSelectionModel<Message>> messageSelectionModelProperty() {
        return messageSelectionModel;
    }

    public void logout() {
        ApplicationModel.getInstance().logout();
    }

    public void sendMessage(String text) {

    }
}
