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

public class PreferenceWindow implements IWindow {

    // defining the window
    Stage stage;

    //urls of fxml resources
    URL mainSceneURL = getClass().getClassLoader().getResource("layouts/PreferenceWindow/PreferenceWindow.fxml");
    URL AccountPaneURL = getClass().getClassLoader().getResource("layouts/PreferenceWindow/panes/AccountPane.fxml");

    // fxml components.
    private Scene mainScene;

    @FXML
    private Pane SettingsMenuPane;

    public PreferenceWindow() throws IOException {
        // create stage
        this.stage = new Stage();

        FXMLLoader tmpLoader = new FXMLLoader(mainSceneURL);
        tmpLoader.setController(this);
        this.mainScene = tmpLoader.load();




        this.stage.setScene(this.mainScene);

    }

    @FXML
    private void switchAccountPane(ActionEvent e) throws IOException {
        this.SettingsMenuPane.getChildren().clear();
        FXMLLoader tmpLoader = new FXMLLoader(AccountPaneURL);
        tmpLoader.setController(this);
        this.SettingsMenuPane.getChildren().add(tmpLoader.load());
        System.out.println("switch to account pane");
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
