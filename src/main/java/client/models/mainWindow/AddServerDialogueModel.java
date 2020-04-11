package client.models.mainWindow;

import client.classes.Server;
import client.models.ApplicationModel;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

public class AddServerDialogueModel {

    SimpleListProperty<Server> serverList = new SimpleListProperty<>();

    private ApplicationModel applicationModel = ApplicationModel.getInstance();

    public AddServerDialogueModel() {
        this.serverList.bindBidirectional(applicationModel.serverListProperty());
    }

    public void add(String ipAddress, String name) {
        this.serverList.add(new Server(ipAddress, name));
    }

}
