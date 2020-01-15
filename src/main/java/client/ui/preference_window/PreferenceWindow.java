package client.ui.preference_window;

import client.interfaces.IWindow;
import client.interfaces.controllers.IPreferenceWindowController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class PreferenceWindow implements IWindow {

    // defining the window
    Stage stage;

    // urls of fxml resources
    URL mainSceneURL = getClass().getClassLoader().getResource("layouts/PreferenceWindow/PreferenceWindow.fxml");
    URL AccountPaneURL = getClass().getClassLoader().getResource("layouts/PreferenceWindow/panes/AccountPane.fxml");

    // urls for stylesheets

    // other attributes
    HashMap<String, Object> preferences;
    Pane currentPane;

    // fxml components.
    private Scene mainScene;

    @FXML
    private Pane SettingsMenuPane;

    public PreferenceWindow(HashMap<String, Object> preferences) throws IOException {
        // create stage
        this.stage = new Stage();
        this.preferences = preferences;
        FXMLLoader tmpLoader = new FXMLLoader(mainSceneURL);
        tmpLoader.setController(this);
        this.mainScene = tmpLoader.load();
        this.mainScene.getStylesheets().add("css/PreferenceWindow/PreferenceWindow.css");
        this.stage.setScene(this.mainScene);
    }

    @FXML
    private void switchAccountPane(ActionEvent e) throws IOException {
        this.SettingsMenuPane.getChildren().clear();
        FXMLLoader tmpLoader = new FXMLLoader(AccountPaneURL);
        tmpLoader.setController(this);
        this.currentPane = tmpLoader.load();
        this.SettingsMenuPane.getChildren().add(this.currentPane);
        System.out.println("switch to account pane");
    }

    @FXML
    private void accountSettingsChanged(ActionEvent e) {

    }

    @FXML
    private void switchWindowPane(ActionEvent e) {

    }

    @Override
    public void show() {
        this.stage.show();
    }

    @Override
    public void hide() {
        this.stage.hide();
    }
}
