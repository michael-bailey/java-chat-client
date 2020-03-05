package client.ui.main_window.contact_pane;

import client.classes.Contact;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;

public class ContactBox extends AnchorPane {

    Label contactName;
    ImageView userImage;
    Label contactUserID;


    URL fxmlUrl = getClass().getClassLoader().getResource("layouts/MainWindow/ContactPane/ContactBox.fxml");

    EventHandler onClick;

    public ContactBox(Contact contact) {
        super();
        System.out.println(this);

        // setting up the box's properties
        this.setMinSize(100,50);
        this.setPrefSize(Region.USE_COMPUTED_SIZE,50);
        this.setMaxSize(Double.MAX_VALUE, 50);

        this.setOnMouseClicked(event -> {
            if(this.onClick != null) {
                this.onClick.handle(event);
            }
        });

        this.contactName = new Label(contact.contactName);
        this.contactUserID = new Label(contact.contactUserID);

        this.userImage = new ImageView();
        this.userImage.setImage(new Image("img/developer_board-24px.svg"));

        // setting anchors
        AnchorPane.setLeftAnchor(this.userImage, 0.0);
        AnchorPane.setTopAnchor(this.userImage, 0.0);
        AnchorPane.setBottomAnchor(this.userImage, 0.0);

        AnchorPane.setLeftAnchor(this.contactName, 54.0);
        AnchorPane.setTopAnchor(this.contactName, 4.0);
        AnchorPane.setBottomAnchor(this.contactName, 26.0);
        AnchorPane.setRightAnchor(this.contactName,4.0);

        AnchorPane.setLeftAnchor(this.contactUserID, 54.0);
        AnchorPane.setTopAnchor(this.contactUserID, 26.0);
        AnchorPane.setBottomAnchor(this.contactUserID, 4.0);
        AnchorPane.setRightAnchor(this.contactUserID,4.0);

        this.getChildren().add(this.userImage);
        this.getChildren().add(this.contactName);
        this.getChildren().add(this.contactUserID);


    }

    public String getName() {
        return this.contactName.getText();
    }

    public String getUserID() {
        return this.contactUserID.getText();
    }

    public EventHandler getOnClick() {
        return onClick;
    }

    public void setOnClick(EventHandler onClick) {
        this.onClick = onClick;
    }
}
