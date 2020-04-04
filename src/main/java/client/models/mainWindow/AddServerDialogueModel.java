package client.models.mainWindow;

import client.classes.Server;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

public class AddServerDialogueModel {

    SimpleListProperty<Server> serverList = new SimpleListProperty<>();

    public AddServerDialogueModel() {
    }

    public ObservableList<Server> getServerList() {
        return serverList.get();
    }

    public SimpleListProperty<Server> serverListProperty() {
        return serverList;
    }
}
