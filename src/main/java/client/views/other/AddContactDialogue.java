package client.views.other;

import java.io.IOException;
import java.net.URL;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * AddTempContactDialogue
 */
public class AddContactDialogue {

    private Stage window;

    private URL windowUrl = this.getClass().getClassLoader().getResource("layouts/other/AddContactDialogue.fxml");

    @FXML TextField nameField;
    @FXML TextField userIDField;

    @FXML
    private void onPressedAdd(Event event) {
        if(this.onAddContact != null) {
            this.onAddContact.handle(event);
        }
    }

    @FXML
    private void onPressedCancel(Event event) {
        this.hide();
    }

    private EventHandler onAddContact;

    public AddContactDialogue() {
        System.out.println(this);
        try {
            FXMLLoader tmpLoader = new FXMLLoader(this.windowUrl);
            tmpLoader.setController(this);
            this.window = tmpLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNameText() {
        return this.nameField.getText();
    }

    public String getUserIDText() {
        return this.userIDField.getText();
    }

    public void show() {
        this.nameField.setText("");
        this.userIDField.setText("");
        this.window.show();
    }

    public void hide() {
        this.window.hide();
    }

    public void setOnAddContact(EventHandler onAddContact) {
        this.onAddContact = onAddContact;
    }
}