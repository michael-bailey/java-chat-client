package client.ChatWindow.CellFactories;

import client.ChatWindow.ListCells.MessageListCell;
import client.classes.Message;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class MessageCellFactory implements Callback<ListView<Message>, MessageListCell> {
    @Override
    public MessageListCell call(ListView<Message> messageListView) {
        System.out.println("Creating message cell");
        return new MessageListCell();
    }
}
