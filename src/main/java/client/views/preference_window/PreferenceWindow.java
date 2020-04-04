package client.views.preference_window;

import client.managers.DataManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * this class 
 */
public class PreferenceWindow {

    Stage stage;
    URL mainSceneURL = getClass().getClassLoader().getResource("layouts/PreferenceWindow/PreferenceWindow.fxml");
    Pane currentPane;
    private Scene mainScene;

    // fxml components

    public PreferenceWindow(DataManager dataManager) throws IOException {
        FXMLLoader tmpLoader = new FXMLLoader(mainSceneURL);
        tmpLoader.setController(this);
        this.stage = tmpLoader.load();
        this.stage.getScene().getStylesheets().add("css/PreferenceWindow/PreferenceWindow.css");
    }

    public void addTab(Tab pane) {

    }


    public void show() {
        this.stage.show();
    }


    public void hide() {
        this.stage.hide();
    }
}
