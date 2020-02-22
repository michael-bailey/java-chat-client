package client.ui.preference_window;

import baselib.managers.DataManager;
import client.interfaces.IWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * this class 
 */
public class PreferenceWindow implements IWindow {

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

    @Override
    public void show() {
        this.stage.show();
    }

    @Override
    public void hide() {
        this.stage.hide();
    }
}
