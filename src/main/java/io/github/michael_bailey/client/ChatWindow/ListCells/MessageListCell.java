package io.github.michael_bailey.client.ChatWindow.ListCells;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;

public class MessageListCell extends ListCell<MessageListCellModel> {

    URL fxmlURL = getClass().getClassLoader().getResource("layouts/ChatWindow/messageCell/MessageCell.fxml");
    @FXML HBox root;
    @FXML Label content;

    SimpleObjectProperty<MessageListCellModel> message = new SimpleObjectProperty();

    @Override
    protected void updateItem(MessageListCellModel item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        message.set(item);

        if (!empty) {
            try {
                FXMLLoader loader = new FXMLLoader(fxmlURL);
                loader.setController(this);
                loader.load();

                root.setAlignment(!message.get().received ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

                this.content.setText(item.message);

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
