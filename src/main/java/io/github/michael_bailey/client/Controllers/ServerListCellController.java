package io.github.michael_bailey.client.Controllers;

import io.github.michael_bailey.client.models.Server;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ServerListCellController extends ListCell<Server> {

    @FXML public AnchorPane root;
    @FXML public Label label;
    @FXML public ImageView imgView;

    public ServerListCellController() {
        super();
    }

    @Override
    protected void updateItem(Server item, boolean empty) {
        super.updateItem(item, empty);

        Platform.runLater(() -> setText(""));
        Platform.runLater(() -> setGraphic(null));

        if (item == null) {
            return;
        }

        if (item != null) {

            this.label.textProperty().setValue(item.displayName);

            Platform.runLater(() -> setText(""));
            Platform.runLater(() -> setGraphic(root));
        }
    }
}
