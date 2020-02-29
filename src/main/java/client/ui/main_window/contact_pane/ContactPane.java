package client.ui.main_window.contact_pane;

import client.classes.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.util.Iterator;
import java.util.regex.*;

import java.util.ArrayList;
import java.util.List;

public class ContactPane extends AnchorPane {

    private ListView<ContactBox> listView = new ListView<ContactBox>();
    private Button addContactButton = new Button("+");
    private TextField searchBox = new TextField();

    private EventHandler onKeyTypedHandler = event -> {
        List tmpList = listView.getItems();
        ArrayList newContactList = new ArrayList<ContactBox>();

        Iterator tmpIterator = tmpList.iterator();
        while (tmpIterator.hasNext()) {
            ContactBox tmpContactBox = (ContactBox) tmpIterator.next();

            tmpContactBox.getName();

            if (Pattern.compile(this.searchBox.getText() + "[a-zA-Z]*").matcher(tmpContactBox.getName()).matches()) {
                newContactList.add(tmpContactBox);
            }
        }

        this.listView.setItems(FXCollections.observableList(newContactList));
    };


    public ContactPane() {
        System.out.println(this);

        this.listView.getStyleClass().add("ContactPane");

        this.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        this.setPrefSize(200,Region.USE_COMPUTED_SIZE);
        this.setMaxSize(Region.USE_PREF_SIZE,Double.MAX_VALUE);

        this.listView.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.listView.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        this.listView.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        this.addContactButton.getStyleClass().add("sendButton");
        this.searchBox.getStyleClass().add("messageBox");

        AnchorPane.setTopAnchor(this.listView, 0.0);
        AnchorPane.setLeftAnchor(this.listView, 0.0);
        AnchorPane.setRightAnchor(this.listView, 0.0);
        AnchorPane.setBottomAnchor(this.listView, 33.0);

        AnchorPane.setBottomAnchor(this.addContactButton, 4.0);
        AnchorPane.setLeftAnchor(this.addContactButton, 4.0);

        AnchorPane.setBottomAnchor(this.searchBox, 4.0);
        AnchorPane.setLeftAnchor(this.searchBox,33.0);
        AnchorPane.setRightAnchor(this.searchBox, 4.0);

        this.getChildren().add(this.listView);
        this.getChildren().add(this.addContactButton);
        this.getChildren().add(this.searchBox);


    }

    void setContacts(ArrayList contacts) {
        this.listView.setItems(FXCollections.observableList(contacts));
    }

    public ArrayList getContacts() {
        return (ArrayList) this.listView.getItems();
    }
}
