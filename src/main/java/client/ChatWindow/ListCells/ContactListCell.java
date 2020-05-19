package client.ChatWindow.ListCells;

import client.classes.Contact;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class ContactListCell extends ListCell<Contact> {

    URL fxmlURL = getClass().getClassLoader().getResource("layouts/ChatWindow/ContactCell/ContactCell.fxml");

    @FXML AnchorPane root;
    @FXML Label Username;
    @FXML Label UUID;
    @FXML ImageView profileImg;

    SimpleObjectProperty<Contact> contact = new SimpleObjectProperty<>();

    @Override
    protected void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);


        setText(null);
        setGraphic(null);

        contact.set(item);

        if (!empty) {
            try {
                FXMLLoader loader = new FXMLLoader(fxmlURL);
                loader.setController(this);
                loader.load();

                Username.setText(item.getUsername());
                UUID.setText(item.getUUID().toString());

                Tooltip t = new Tooltip();
                t.setText(item.getUUID().toString());
                UUID.setTooltip(t);

                setGraphic(root);
            } catch (IOException e) {
                e.printStackTrace();
                setText("");
            }
        } else {
            setText("");
        }
    }
}
