package io.github.michael_bailey.client.ChatWindow.CellFactories;

import io.github.michael_bailey.client.ChatWindow.ListCells.ContactListCell;
import io.github.michael_bailey.client.StorageDataTypes.Contact;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ContactCellFactory implements Callback<ListView<Contact>, ContactListCell> {
    @Override
    public ContactListCell call(ListView<Contact> contactListView) {
        return null;
    }
}
