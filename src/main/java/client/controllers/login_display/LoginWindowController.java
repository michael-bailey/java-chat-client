package client.controllers.login_display;

import client.models.ApplicationModel;
import client.models.login_window.LoginWindowModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {

    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private AnchorPane root;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button cancelButton;
    @FXML private Button loginButton;

    ChangeListener loggedInListener = (observableValue, oldValue, newValue) -> {
        if ((Boolean) newValue) {
            this.hideView();
        } else {
            this.showView();
        }
    };

    private ApplicationModel appModel = ApplicationModel.getInstance();
    private LoginWindowModel model = new LoginWindowModel();

    public LoginWindowController() {
        System.out.println(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.appModel.loginStatusProperty().addListener(loggedInListener);

        this.model.usernameLengthProperty().bind(this.usernameField.textProperty().length());
        this.model.passwordLengthProperty().bind(this.passwordField.textProperty().length());
    }

    @FXML void Login(ActionEvent event) {
        this.appModel.login(this.usernameField.getText(), this.passwordField.getText());
    }

    @FXML void cancel(ActionEvent event) {
        Platform.exit();
    }

    @FXML void windowClose(WindowEvent event) {
        Platform.exit();
    }

    public void showView() {
        this.usernameField.textProperty().set("");
        this.passwordField.textProperty().set("");
        this.usernameField.requestFocus();
        this.stage.show();
    }

    public void hideView() {
        this.stage.hide();
        this.usernameField.textProperty().set("");
        this.passwordField.textProperty().set("");
        this.usernameField.requestFocus();
    }
}
