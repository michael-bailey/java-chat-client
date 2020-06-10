package io.github.michael_bailey.client.ChatWindow.ListCells;

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

public class ContactListCell extends ListCell<ContactListCellModel> {

    URL fxmlURL = getClass().getClassLoader().getResource("layouts/ChatWindow/ContactCell/ContactCell.fxml");

    @FXML AnchorPane root;
    @FXML Label Username;
    @FXML Label UUID;
    @FXML ImageView profileImg;

    SimpleObjectProperty<ContactListCellModel> contact = new SimpleObjectProperty<>();

    @Override
    protected void updateItem(ContactListCellModel item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);
        setGraphic(null);

        contact.set(item);

        if (!empty) {
            try {
                FXMLLoader loader = new FXMLLoader(fxmlURL);
                loader.setController(this);
                loader.load();

                Username.setText(item.name);
                UUID.setText(item.uuid.toString());

                Tooltip t = new Tooltip();
                t.setText(item.uuid.toString());
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
