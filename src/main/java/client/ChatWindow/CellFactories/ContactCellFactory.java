package client.ChatWindow.CellFactories;

import client.ChatWindow.ListCells.ContactListCell;
import client.classes.Contact;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ContactCellFactory implements Callback<ListView<Contact>, ContactListCell> {
    @Override
    public ContactListCell call(ListView<Contact> contactListView) {
        return null;
    }
}
