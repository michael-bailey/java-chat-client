package client.models.mainWindow;

import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class MainWindowModel {
    SimpleStringProperty searchString = new SimpleStringProperty();
    SimpleListProperty<Contact> contactViewList = new SimpleListProperty();
    SimpleListProperty<Message> messagesViewList = new SimpleListProperty();
    SimpleListProperty<Server> serverViewList = new SimpleListProperty();

    public String getSearchString() {
        return searchString.get();
    }

    public SimpleStringProperty searchStringProperty() {
        return searchString;
    }

    public ObservableList<Contact> getContactViewList() {
        return contactViewList.get();
    }

    public SimpleListProperty<Contact> contactViewListProperty() {
        return contactViewList;
    }

    public ObservableList<Message> getMessagesViewList() {
        return messagesViewList.get();
    }

    public SimpleListProperty<Message> messagesViewListProperty() {
        return messagesViewList;
    }

    public ObservableList<Server> getServerViewList() {
        return serverViewList.get();
    }

    public SimpleListProperty<Server> serverViewListProperty() {
        return serverViewList;
    }
}
