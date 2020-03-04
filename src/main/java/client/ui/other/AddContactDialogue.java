package client.ui.other;


import java.io.IOException;
import java.net.URL;

import client.interfaces.IWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * AddTempContactDialogue
 */
public class AddContactDialogue implements IWindow {

    private Stage window;

    private URL windowUrl = this.getClass().getClassLoader().getResource("layouts/other/AddContactDialogue.fxml");

    @FXML TextField nameField;
    @FXML TextField userIDField;

    public AddContactDialogue() {
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

    @Override
    public void show() {
        this.nameField.setText("");
        this.userIDField.setText("");
        this.window.show();
    }

    @Override
    public void hide() {
        this.window.hide();
    }
}