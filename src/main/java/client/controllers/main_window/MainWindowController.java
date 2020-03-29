package client.controllers.main_window;

import client.classes.Contact;
import client.classes.Message;
import client.models.ApplicationModel;
import client.models.mainWindow.MainWindowModel;
import client.views.main_window.ContactListCell;
import client.views.main_window.MessageListCell;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MainWindowController implements Initializable {

    @FXML private Stage stage;

    // message pane
    @FXML private ListView<Message> messageListView;
    @FXML private TextField messageBox;
    @FXML private Button sendButton;
    @FXML private ListView<Contact> contactListView;
    @FXML private TextField contactSearchBox;
    @FXML private Button addContactButton;

    // change listeners
    ChangeListener<? super Boolean> loginStatusChange = (observable, oldValue, newValue) -> {
        if (newValue.equals(true)) {
            this.stage.show();
        } else {
            this.stage.hide();
        }
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

    ApplicationModel applicationModel = ApplicationModel.getInstance();
    MainWindowModel model = new MainWindowModel();


    public MainWindowController() {
        System.out.println(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // cell factorys
        this.messageListView.setCellFactory(this.messageCellFactory);
        this.contactListView.setCellFactory(this.contactCellFactory);

        this.contactSearchBox.textProperty().addListener(this.searchChangeListener);


        // property bindings
        this.model.contactViewListProperty().bindBidirectional(this.contactListView.itemsProperty());
        //this.model.contactViewListProperty().bindBidirectional(this.applicationModel.contactListProperty());
        this.model.messagesViewListProperty().bindBidirectional(this.messageListView.itemsProperty());

        // property listeners
        this.applicationModel.loginStatusProperty().addListener(this.loginStatusChange);
        this.model.searchStringProperty().addListener(this.searchChangeListener);

        // event bindings
    }

    @FXML
    void sendMessage(ActionEvent event) {
        if (!messageBox.getText().isEmpty()) {
            this.model.messagesViewListProperty().add(new Message(messageBox.getText(), false));
        }
        this.messageBox.clear();
    }

    public void addContact(ActionEvent actionEvent) {
        this.contactSearchBox.clear();

        Contact tmp = new Contact("user1");

        this.model.contactViewListProperty().add(tmp);
        this.applicationModel.contactListProperty().add(tmp);
    }

    @FXML
    void windowClose(ActionEvent event) {
        this.applicationModel.logout();
    }

    @FXML
    void windowClose(WindowEvent event) {
        this.applicationModel.logout();
    }


}
