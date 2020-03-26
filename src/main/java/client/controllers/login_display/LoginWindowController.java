package client.controllers.login_display;

import client.models.ApplicationModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginWindowController {

    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private AnchorPane root;
    @FXML private TextField UsernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button cancelButton;
    @FXML private Button loginButton;

    ChangeListener loggedInListener = (observableValue, bool, t1) -> {
        if ((Boolean) t1) {
            this.stage.hide();
        } else {
            this.stage.show();
        }
    };

    private ApplicationModel model = ApplicationModel.getInstance();

    public LoginWindowController() {
        this.model.loginStatusProperty().addListener(loggedInListener);
    }

    @FXML void Login(ActionEvent event) {
        this.model.login(this.UsernameField.getText(), this.passwordField.getText());
    }

    @FXML void cancel(ActionEvent event) {
        Platform.exit();
    }

    public void showView() {
        this.stage.show();
    }

    public void hideView() {
        this.stage.hide();
    }
}
