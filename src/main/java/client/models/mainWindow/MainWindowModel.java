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
        for (Contact i : ApplicationModel.getInstance().getContactList()) {
            if (regex.matcher(i.getUsername()).matches()) {
                this.contactViewListProperty().add(i);
            }
        }
    };

    ChangeListener<Server> serverChangeListener = (observable, oldValue, newValue) -> {

    };

    ChangeListener<Contact> contactChangeListener = (observable, oldValue, newValue) -> {

    };

    private SimpleStringProperty searchString = new SimpleStringProperty();
    private SimpleListProperty<Contact> contactViewList = new SimpleListProperty();
    private SimpleListProperty<Message> messageViewList = new SimpleListProperty();
    SimpleObjectProperty<MultipleSelectionModel<Server>> selectionModel = new SimpleObjectProperty<MultipleSelectionModel<Server>>();



    public MainWindowModel() {
        // change listener
        this.searchStringProperty().addListener(this.searchChangeListener);

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

    public SimpleObjectProperty<MultipleSelectionModel<Server>> selectionModelProperty() {
        return selectionModel;
    }


}
