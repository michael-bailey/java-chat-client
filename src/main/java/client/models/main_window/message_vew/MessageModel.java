package client.models.main_window.message_vew;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class MessageModel {
    SimpleStringProperty content = new SimpleStringProperty();
    SimpleObjectProperty<client.enums.MessageAlignment> alignment = new SimpleObjectProperty();
}
