package client.ui.main_window.contact_pane;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ContactPane extends AnchorPane {

    private ListView<ContactBox> listView = new ListView<>();

    public ContactPane() {

    }

    void setContacts(ArrayList contacts) {
        this.listView.setItems(FXCollections.observableList(contacts));
    }
}
