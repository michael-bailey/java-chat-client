package client.ChatWindow.CellFactories;

import client.ChatWindow.ListCells.ServerListCell;
import client.ChatWindow.ListCells.ServerListCellModel;
import client.classes.Server;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ServerCellFactory implements Callback<ListView<ServerListCellModel>, ListCell<ServerListCellModel>> {

    @Override
    public ListCell<ServerListCellModel> call(ListView<ServerListCellModel> serverListCellModelListView) {
        return null;
    }
}