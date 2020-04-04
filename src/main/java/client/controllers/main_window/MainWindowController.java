package client.controllers.main_window;

import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.models.ApplicationModel;
import client.models.mainWindow.MainWindowModel;
import client.views.main_window.ContactListCell;
import client.views.main_window.MessageListCell;
import client.views.main_window.ServerListCell;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

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
    @FXML private ListView<Server> serverListView;

    // other menus
    URL addServerDialogueURL = getClass().getClassLoader().getResource("layouts/MainWindow/ServerAddDialogue.fxml");
    private ServerAddDialogue serverAddDialogue;

    // change Listeners
    ChangeListener<Boolean> loginStatusListener = (observable, oldValue, newValue) -> {
        if (newValue) {
            this.stage.show();
        } else {
            this.stage.hide();
        }
    };

    // list view factories
    Callback messageCellFactory = new Callback<ListView<Message>, ListCell<Message>>() {
        @Override
        public ListCell<Message> call(ListView<Message> studentListView) {
            return new MessageListCell();
        }
    };

    Callback contactCellFactory = new Callback<ListView<Contact>, ContactListCell>() {
        @Override
        public ContactListCell call(ListView<Contact> studentListView) {
            return new ContactListCell();
        }
    };

    Callback serverCellFactory = new Callback<ListView<Server>, ServerListCell>() {
        @Override
        public ServerListCell call(ListView<Server> studentListView) {
            return new ServerListCell();
        }
    };

    MainWindowModel model = new MainWindowModel();

    public MainWindowController() {
        System.out.println(this);
        ApplicationModel.getInstance().loginStatusProperty().addListener(this.loginStatusListener);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // create windows
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(addServerDialogueURL);
            fxmlLoader.load();
            this.serverAddDialogue = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ApplicationModel.getInstance().loginStatusProperty().addListener(this.loginStatusListener);

        // cell factories
        this.messageListView.setCellFactory(this.messageCellFactory);
        this.contactListView.setCellFactory(this.contactCellFactory);
        this.serverListView.setCellFactory(this.serverCellFactory);

        // view bindings
        this.model.searchStringProperty().bindBidirectional(this.contactSearchBox.textProperty());
        this.model.serverViewListProperty().bindBidirectional(this.serverListView.itemsProperty());
        this.model.contactViewListProperty().bindBidirectional(this.contactListView.itemsProperty());
        this.model.messageViewListProperty().bindBidirectional(this.messageListView.itemsProperty());

    }

    public MainWindowModel getModel() {
        return model;
    }

    @FXML void addServer(ActionEvent actionEvent) {
        this.serverAddDialogue.showView();
    }

    // TODO implement the messageing system to reflect current state.
    @FXML
    void sendMessage(ActionEvent event) {
        this.messageBox.clear();
    }

    //TODO remove this
    @Deprecated
    @FXML void addContact(ActionEvent actionEvent) {

    }

    @FXML
    void windowClose(WindowEvent event) {
        this.serverAddDialogue.hideView();
        this.model.logout();
    }
}
