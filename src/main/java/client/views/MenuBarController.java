package client.views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {

    @FXML
    private MenuBar MainMenu;

    @FXML
    private MenuItem quitItem;

    public MenuBarController() {

    }

    @FXML
    void quitApplication(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.MainMenu.setUseSystemMenuBar(true);
    }
}
