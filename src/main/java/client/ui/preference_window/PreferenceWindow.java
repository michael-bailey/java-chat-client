package client.ui.preference_window;

import baselib.managers.DataManager;
import client.classes.Account;
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

    // reference to the data manager
    DataManager dataManager;

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


    private Scene mainScene;

    // FXML components



    public PreferenceWindow(DataManager dataManager) throws IOException {

        // save the reference
        this.dataManager = dataManager;

        FXMLLoader tmpLoader = new FXMLLoader(mainSceneURL);
        tmpLoader.setController(this);
        this.stage = tmpLoader.load();
        this.stage.getScene().getStylesheets().add("css/PreferenceWindow/PreferenceWindow.css");
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
