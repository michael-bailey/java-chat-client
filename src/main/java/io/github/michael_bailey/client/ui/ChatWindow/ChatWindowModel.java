package io.github.michael_bailey.client.ui.ChatWindow;

import io.github.michael_bailey.client.StorageDataTypes.Message;
import io.github.michael_bailey.client.ui.ChatWindow.ListCells.ServerListCellModel;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChatWindowModel implements Serializable {

    // server properties.
    private SimpleListProperty<String> ServerList = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));;
    private transient SimpleListProperty<ServerListCellModel> serverViewList = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
    private transient SimpleObjectProperty<ServerListCellModel> serverSelection = new SimpleObjectProperty<>();

    private final SimpleMapProperty<UUID, Message> messageMap = new SimpleMapProperty(FXCollections.observableMap(new HashMap()));


    public ChatWindowModel() {
    }

// MARK: server functions.
    public SimpleListProperty<String> serverListProperty() {
        return ServerList;
    }

    public SimpleListProperty<ServerListCellModel> serverViewListProperty() {
        return serverViewList;
    }

    public SimpleObjectProperty<ServerListCellModel> serverSelectionProperty() {
        return serverSelection;
    }

// MARK: serialization
    public void write(OutputStream stream) throws IOException {
        var out = new ObjectOutputStream(stream);
        out.writeObject(this);
    }

    public void read(InputStream stream) throws IOException, ClassNotFoundException {
        var in = new ObjectInputStream(stream);
        ChatWindowModel newModel = (ChatWindowModel) in.readObject();
        this.ServerList.set(newModel.serverListProperty().get());
    }
}
