package io.github.michael_bailey.client.ui.LoginWindow;

import javafx.application.Platform;
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

    private LoginWindowModel model;

    public LoginWindowController() {
        System.out.println(this);
    }

    public void setModel(LoginWindowModel model) {
        this.model = model;

        this.model.usernameStringProperty().bindBidirectional(this.usernameField.textProperty());
        this.model.passwordStringProperty().bindBidirectional(this.passwordField.textProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.usernameField.requestFocus();
    }

    @FXML void Login(ActionEvent event) {
        var chatWindowModel = this.model.login();

        if (chatWindowModel != null) {
            this.hideView();
        } else {
            this.showView();
        }
    }

    @FXML void cancel(ActionEvent event) {
        Platform.exit();
    }

    @FXML void windowClose(WindowEvent event) {
        Platform.exit();
    }

    public void showView() {
        this.stage.show();
    }

    public void hideView() {
        this.stage.hide();
    }
}
