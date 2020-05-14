package client.ChatWindow.ListCells;

import client.classes.Message;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;

public class MessageListCell extends ListCell<Message> {

    URL fxmlURL = getClass().getClassLoader().getResource("layouts/MainWindow/messageCell/MessageCell.fxml");
    @FXML HBox root;
    @FXML Label content;

    SimpleObjectProperty<Message> message = new SimpleObjectProperty();

    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        message.set(item);

        if (!empty) {
            try {
                FXMLLoader loader = new FXMLLoader(fxmlURL);
                loader.setController(this);
                loader.load();

                root.setAlignment(!message.get().isReceived() ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

                this.content.setText(item.getMessage());

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
