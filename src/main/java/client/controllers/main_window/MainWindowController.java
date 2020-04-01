package client.controllers.main_window;

import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.models.ApplicationModel;
import client.models.mainWindow.MainWindowModel;
import client.views.main_window.ContactListCell;
import client.views.main_window.MessageListCell;
import client.views.main_window.ServerListCell;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
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
import java.util.regex.Pattern;

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

    // change listeners
    ChangeListener<? super Boolean> loginStatusChange = (observable, oldValue, newValue) -> {
        if (newValue.equals(true)) {
            this.stage.show();
        } else {
            this.stage.hide();
        }
    };

    ChangeListener<Contact> currentContactChange = (observable, oldValue, newValue) -> {
        this.model.messagesViewListProperty().set(FXCollections.observableList(newValue.getMessages()));
    };

    // implementing the search function
    ChangeListener<? super String> searchChangeListener = (observable, oldValue, newValue) -> {

        this.model.contactViewListProperty().clear();

        Pattern regex = Pattern.compile(newValue + "[a-zA-z0-9]*");
        for (Contact i : this.applicationModel.getContactList()) {
            if (regex.matcher(i.getUsername()).matches()) {
                this.model.contactViewListProperty().add(i);
            }
        }
    };

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

    ApplicationModel applicationModel = ApplicationModel.getInstance();
    MainWindowModel model = new MainWindowModel();


    public MainWindowController() {
        System.out.println(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // menu bar controls
        /*
        if (System.getProperty("os.name").equals("Mac OS X")) {
            this.menuBar.useSystemMenuBarProperty().set(true);
        }
        */

        // create windows
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(addServerDialogueURL);
            fxmlLoader.load();
            this.serverAddDialogue = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // cell factories
        this.messageListView.setCellFactory(this.messageCellFactory);
        this.contactListView.setCellFactory(this.contactCellFactory);
        this.serverListView.setCellFactory(this.serverCellFactory);

        this.contactSearchBox.textProperty().addListener(this.searchChangeListener);


        // property bindings
        
        this.model.contactViewListProperty().bindBidirectional(this.contactListView.itemsProperty());
        this.model.messagesViewListProperty().bindBidirectional(this.messageListView.itemsProperty());
        this.model.serverViewListProperty().bindBidirectional(this.serverListView.itemsProperty());
        this.serverAddDialogue.serverListPropertyProperty().bindBidirectional(this.model.serverViewListProperty());

        // main window property listeners
        this.model.searchStringProperty().addListener(this.searchChangeListener);

        // application property listeners
        this.applicationModel.loginStatusProperty().addListener(this.loginStatusChange);
        // this.applicationModel.currentContactProperty().addListener(this.);


        // event bindings
    }

    public MainWindowModel getModel() {
        return model;
    }

    // TODO implement the messageing system to reflect current state.
    @FXML
    void sendMessage(ActionEvent event) {
        if (!messageBox.getText().isEmpty()) {
            this.model.messagesViewListProperty().add(new Message(messageBox.getText(), false));
        }
        this.messageBox.clear();
    }

    // TODO depreceate this as it will mo longer be needed.
    @FXML void addContact(ActionEvent actionEvent) {
        this.contactSearchBox.clear();

        Contact tmp = new Contact("user1");

        this.model.contactViewListProperty().add(tmp);
        this.applicationModel.contactListProperty().add(tmp);
    }


    @FXML void addServer(ActionEvent actionEvent) {
        this.serverAddDialogue.showView();
    }

    @FXML
    void windowClose(ActionEvent event) {
        this.serverAddDialogue.hideView();
        this.applicationModel.logout();
    }

    @FXML
    void windowClose(WindowEvent event) {
        this.serverAddDialogue.hideView();
        this.applicationModel.logout();
    }


}
