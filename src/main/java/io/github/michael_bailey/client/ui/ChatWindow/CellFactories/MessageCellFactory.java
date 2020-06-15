package io.github.michael_bailey.client.ui.ChatWindow.CellFactories;

import io.github.michael_bailey.client.ui.ChatWindow.ListCells.MessageListCell;
import io.github.michael_bailey.client.StorageDataTypes.Message;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class MessageCellFactory implements Callback<ListView<Message>, MessageListCell> {
    @Override
    public MessageListCell call(ListView<Message> messageListView) {
        System.out.println("Creating message cell");
        return new MessageListCell();
    }
}
