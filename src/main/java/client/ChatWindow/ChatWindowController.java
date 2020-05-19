package client.ChatWindow;

import client.classes.Contact;
import client.classes.Message;
import client.ChatWindow.ListCells.ServerListCellModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

    private final ChatWindowModel model;
    private EventHandler<ActionEvent> onCloseHandler;

    public ChatWindowController(ChatWindowModel model) {
        System.out.println(this);
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setting up the server view
        this.serverListView.itemsProperty().bind(this.model.serverViewListProperty());
    }

    @FXML void addServer(ActionEvent actionEvent) {
        System.out.println("to be implemented");
    }

    // TODO implement the messageing system to reflect current state.
    @FXML
    void sendMessage(ActionEvent event) {
        System.out.println("to be implemented");
    }

    //TODO remove this
    @Deprecated
    @FXML void addContact(ActionEvent actionEvent) {
        System.out.println("to be removed");
    }

    @FXML void windowClose(WindowEvent event) {
        stage.hide();
    }

    @FXML void quitApplication(ActionEvent event) {

    }
    
}
