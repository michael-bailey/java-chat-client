package client.models.mainWindow;

import client.classes.Contact;
import client.classes.Message;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class MainWindowModel {
    SimpleStringProperty searchString = new SimpleStringProperty();
    SimpleListProperty<Contact> contactViewList = new SimpleListProperty();
    SimpleListProperty<Message> messagesViewList = new SimpleListProperty();

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
}
