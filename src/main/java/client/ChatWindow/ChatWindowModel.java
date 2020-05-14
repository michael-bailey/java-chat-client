package client.ChatWindow;

import client.classes.Server;
import client.ChatWindow.ListCells.ServerListCellModel;
import client.managers.DataManager;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.MultipleSelectionModel;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class ChatWindowModel {

    // urls
    URL serverAddDialogueFXMLURL = getClass().getClassLoader().getResource("layouts/MainWindow/ServerAddDialogue.fxml");

    // server properties.
    private final SimpleMapProperty<UUID, Server> ServerStore;
    private final SimpleListProperty<ServerListCellModel> serverViewList = new SimpleListProperty<>();
    private final SimpleObjectProperty<ServerListCellModel> serverSelection = new SimpleObjectProperty<>();

    // server list view change listeners.
    private final ChangeListener<? super MultipleSelectionModel<ServerListCellModel>> selectionChangeListener = (observable, oldValue, newValue) -> {
        System.out.println("setting server");
        this.serverSelection.set(newValue.getSelectedItem());
    };

    public ChatWindowModel(ChatWindowModelBuilder builder) {
        this.ServerStore = builder.ServerStore;
    }

    public SimpleListProperty<ServerListCellModel> serverViewListProperty() {
        return serverViewList;
    }

    public static class ChatWindowModelBuilder {

        private SimpleMapProperty<UUID, Server> ServerStore = new SimpleMapProperty<>();
        private SimpleObjectProperty<EventHandler<WindowEvent>> logoutCallback = new SimpleObjectProperty();

        public ChatWindowModelBuilder serverStore(Map<UUID, Server> serverMap) {
            this.ServerStore.set(FXCollections.observableMap(serverMap));
            return this;
        }

        public ChatWindowModelBuilder logoutCallback(EventHandler<WindowEvent> eventHandler) {
            this.logoutCallback.set(eventHandler);
            return this;
        }

        public ChatWindowModel build() {
            return new ChatWindowModel(this);
        }


    }
}
