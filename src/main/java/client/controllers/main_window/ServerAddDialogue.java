package client.controllers.main_window;

import client.classes.Contact;
import client.classes.Server;
import client.models.ApplicationModel;
import client.models.mainWindow.AddServerDialogueModel;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerAddDialogue implements Initializable {

    @FXML private Stage stage;
    @FXML private TextField IPAddressBox;
    @FXML private Button cancelButton;
    @FXML private Button addButton;

    private SimpleListProperty<Server> serverListProperty = new SimpleListProperty<>();

    public ServerAddDialogue() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public ObservableList<Server> getServerListProperty() {
        return serverListProperty.get();
    }

    public SimpleListProperty<Server> serverListPropertyProperty() {
        return serverListProperty;
    }

    @FXML void onEnter(ActionEvent actionEvent) {
        //TODO add logic to ensure the ipaddress is correct
        System.out.println("ADDSERVER");
        if (this.serverListProperty.get() != null) {
            this.serverListProperty.add(new Server(this.IPAddressBox.getText(), "BOB"));
        }
    }

    @FXML void onCancel(ActionEvent actionEvent) {

    }

    public void showView() {
        this.stage.show();
    }

    public void hideView() {
        this.stage.hide();
    }
}