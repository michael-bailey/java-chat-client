package client.controllers.login_display;

import client.classes.Message;
import client.models.login_window.LoginWindowModel;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.*;

public class LoginWindowController implements Initializable {

    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private AnchorPane root;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button cancelButton;
    @FXML private Button loginButton;

    private LoginWindowModel model = new LoginWindowModel();

    private ChangeListener<Boolean> loggedInListener = (observableValue, oldValue, newValue) -> {
        if (newValue) {
            this.hideView();
        } else {
            this.showView();
        }
    };

    public LoginWindowController() {
        System.out.println(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(this.usernameField.getStyleClass().toString());

        // state listeners
        this.model.loginStatusProperty().addListener(loggedInListener);

        // set observables
        this.model.usernameBoxCSSProperty().set(this.usernameField.getStyleClass());
        this.model.passwordBoxCSSProperty().set(this.passwordField.getStyleClass());

        // property bindings
        this.model.usernameStringProperty().bindBidirectional(usernameField.textProperty());
        this.model.passwordStringProperty().bindBidirectional(passwordField.textProperty());

        this.model.getUsernameAnimation().setNode(this.usernameField);
        this.model.getPasswordAnimation().setNode(this.passwordField);

        this.usernameField.requestFocus();
    }

    @FXML void Login(ActionEvent event) {
        this.model.login();

        if (!this.model.usernameCorrectProperty().get()) {
            this.model.getUsernameAnimation().play();
        }

        if (!this.model.passwordCorrectProperty().get()) {
            this.model.getPasswordAnimation().play();
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
