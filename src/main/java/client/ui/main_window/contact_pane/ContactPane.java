package client.ui.main_window.contact_pane;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class ContactPane extends AnchorPane {

    private Button tmp;
    private ListView<ContactBox> listView = new ListView<>();
    

    //private EventHandler tmpContact;

    public ContactPane() {

	//this.tmp.setOnAction(event -> {
	//	if(this.tmpContact != null){
	//		this.tmpContact.handle(event);
	//	}
	//});
        System.out.println(this);

        this.listView.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.listView.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        this.listView.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        AnchorPane.setTopAnchor(this.listView, 0.0);
        AnchorPane.setLeftAnchor(this.listView, 0.0);
        AnchorPane.setRightAnchor(this.listView, 0.0);
        AnchorPane.setBottomAnchor(this.listView, 0.0);

        this.getChildren().add(this.listView);

    }

    void setContacts(ArrayList contacts) {

        this.listView.setItems(FXCollections.observableList(contacts));
    }
    public ArrayList getContacts() {
        return (ArrayList) this.listView.getItems();
    }

    //public EventHandler getTmpContact(){return this.tmpContact;}
    //public void setTmpContact(EventHandler tmp){this.tmpContact = tmp;}
}
