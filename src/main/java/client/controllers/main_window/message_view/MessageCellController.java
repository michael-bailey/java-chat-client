package client.controllers.main_window.message_view;

import client.enums.MessageAlignment;
import client.models.main_window.message_vew.MessageModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageCellController implements Initializable {

    SimpleStringProperty contentString = new SimpleStringProperty();
    SimpleObjectProperty<MessageAlignment> alignment = new SimpleObjectProperty();

    @FXML
    private Label content;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public String getContentString() {
        return contentString.get();
    }

    public SimpleStringProperty contentStringProperty() {
        return contentString;
    }

    public MessageAlignment getAlignment() {
        return alignment.get();
    }

    public SimpleObjectProperty<MessageAlignment> alignmentProperty() {
        return alignment;
    }
}
