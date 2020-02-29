package client.ui.main_window.contact_pane;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public class ContactPane extends AnchorPane {

    private ListView<ContactBox> listView = new ListView<>();

    public ContactPane() {

        System.out.println(this);

        this.getStyleClass().add("ContactPane");

        this.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        this.setPrefSize(200,Region.USE_COMPUTED_SIZE);

        this.setMaxSize(Region.USE_PREF_SIZE,Double.MAX_VALUE);


        this.listView.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.listView.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        this.listView.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        AnchorPane.setTopAnchor(this.listView, 0.0);
        AnchorPane.setLeftAnchor(this.listView, 0.0);
        AnchorPane.setRightAnchor(this.listView, 0.0);
        AnchorPane.setBottomAnchor(this.listView, 33.0);

        this.getChildren().add(this.listView);


    }

    void setContacts(ArrayList contacts) {
        this.listView.setItems(FXCollections.observableList(contacts));
    }

    public ArrayList getContacts() {
        return (ArrayList) this.listView.getItems();
    }
}
