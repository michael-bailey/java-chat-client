package io.github.michael_bailey.client.Controllers;

import io.github.michael_bailey.client.models.Contact;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ContactListCellController extends ListCell<Contact> {


    public AnchorPane root;
    public ImageView profileImg;
    public Label Username;
    public Label UUID;

    @Override
    protected void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);

        Platform.runLater(() -> setText(""));
        Platform.runLater(() -> setGraphic(null));

        if (item == null) {
            return;
        }

        if (item != null) {

            this.Username.textProperty().setValue(item.displayName);
            this.UUID.textProperty().setValue(item.uuid.toString());

            Platform.runLater(() -> setText(""));
            Platform.runLater(() -> setGraphic(root));
        }
    }
}
