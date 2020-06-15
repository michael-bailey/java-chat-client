package io.github.michael_bailey.client.ui.ChatWindow;

import io.github.michael_bailey.client.StorageDataTypes.Contact;
import io.github.michael_bailey.client.StorageDataTypes.Message;
import io.github.michael_bailey.client.ui.ChatWindow.ListCells.ServerListCellModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatWindowController implements Initializable {

    @FXML private Stage stage;

    // message pane
    @FXML private MenuBar menuBar;
    @FXML private ListView<Message> messageListView;
    @FXML private TextField messageBox;
    @FXML private Button sendButton;

    // contact pane
    @FXML private ListView<Contact> contactListView;
    @FXML private TextField contactSearchBox;
    @FXML private Button addContactButton;

    // server Pane
    @FXML private Button addServerButton;
    @FXML private ListView<ServerListCellModel> serverListView;

    // other menus
    private final URL addServerDialogueURL = getClass().getClassLoader().getResource("layouts/ChatWindow/ServerAddDialogue.fxml");

    private ChatWindowModel model = new ChatWindowModel();

    public ChatWindowController() {
        System.out.println(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setting up the server view
        this.serverListView.itemsProperty().bind(this.model.serverViewListProperty());
    }

    public void show() {
        this.stage.show();
    }

    public void hide() {
        this.stage.hide();
    }
}
