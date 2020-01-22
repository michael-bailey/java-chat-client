package client.ui.preference_window;

import client.interfaces.IWindow;
import client.interfaces.controllers.IPreferenceWindowController;
import client.ui.preference_window.panes.AccountPaneController;
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

/**
 * this class 
 */
public class PreferenceWindow implements IWindow {

    // defining the window
    Stage stage;

    // urls of fxml resources
    URL mainSceneURL = getClass().getClassLoader().getResource("layouts/PreferenceWindow/PreferenceWindow.fxml");
    URL AccountPaneURL = getClass().getClassLoader().getResource("layouts/PreferenceWindow/panes/AccountPane.fxml");
    URL WindowPaneURL = getClass().getClassLoader().getResource("layouts/PreferenceWindow/panes/WindowPane.fxml");

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
        this.stage.setMinHeight(350);
        this.stage.setMinWidth(600);

        this.preferences = preferences;

        FXMLLoader tmpLoader = new FXMLLoader(mainSceneURL);
        tmpLoader.setController(this);
        this.mainScene = tmpLoader.load();
        this.mainScene.getStylesheets().add("css/PreferenceWindow/PreferenceWindow.css");
        this.stage.setScene(this.mainScene);
        this.switchAccountPane(null);
    }

    @FXML
    private void switchAccountPane(ActionEvent e) throws IOException {

        this.SettingsMenuPane.getChildren().clear();

        FXMLLoader tmpLoader = new FXMLLoader(AccountPaneURL);
        tmpLoader.setController(this);
        this.currentPane = tmpLoader.load();
        this.SettingsMenuPane.getChildren().add(this.currentPane);

        System.out.println("switched to account pane");
    }

    @FXML
    private void accountSettingsChanged(ActionEvent e) {

    }

    @FXML
    private void switchWindowPane(ActionEvent e) throws IOException {

        this.SettingsMenuPane.getChildren().clear();

        FXMLLoader tmpLoader = new FXMLLoader(this.WindowPaneURL);
        tmpLoader.setController(this);
        this.currentPane = tmpLoader.load();
        this.SettingsMenuPane.getChildren().add(this.currentPane);

        System.out.println("switched to account pane");

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
