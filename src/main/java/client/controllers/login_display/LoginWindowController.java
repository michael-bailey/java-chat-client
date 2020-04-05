package client.controllers.login_display;

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

    private ChangeListener<Boolean> loggedInListener = (observableValue, oldValue, newValue) -> {
        if ((Boolean) newValue) {
            this.hideView();
        } else {
            this.showView();
        }
    };

    private ChangeListener<String> usernameListener = (observableValue, oldValue, newValue) -> {
        ObservableList<String> styleClass = this.usernameField.getStyleClass();
        styleClass.clear();
        styleClass.add("TextBox");

        if (!(newValue.length() > 0)) {
            styleClass.add("TextBoxEmpty");
            this.model.usernameCorrectProperty().setValue(false);
            return;
        }

        if (newValue.matches("[a-zA-Z0-9_-]*")) {
            styleClass.add("TextBoxCorrect");
            this.model.usernameCorrectProperty().setValue(true);
        } else {
            styleClass.add("TextBoxWrong");
            this.model.usernameCorrectProperty().set(false);
        }
    };

    private ChangeListener<String> passwordListener = (observableValue, oldValue, newValue) -> {
        ObservableList<String> styleClass = this.passwordField.getStyleClass();
        styleClass.clear();
        styleClass.add("TextBox");

        if (!(newValue.length() > 0)) {
            styleClass.add("TextBoxEmpty");
            this.model.passwordCorrectProperty().setValue(false);
            return;
        }

        if (newValue.matches("[a-zA-Z]*")) {
            styleClass.add("TextBoxWrong");
            this.model.passwordCorrectProperty().setValue(false);
        } else {
            styleClass.add("TextBoxCorrect");
            this.model.passwordCorrectProperty().setValue(true);
        }
    };

    private LoginWindowModel model = new LoginWindowModel();
    private TranslateTransition usernameAnimation;
    private TranslateTransition passwordAnimation;

    public LoginWindowController() {
        System.out.println(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(this.usernameField.getStyleClass().toString());

        // state listeners
        this.passwordField.textProperty().addListener(passwordListener);
        this.usernameField.textProperty().addListener(usernameListener);
        this.model.loginStatusProperty().addListener(loggedInListener);

        // property bindings
        this.model.usernameStringProperty().bindBidirectional(usernameField.textProperty());
        this.model.passwordStringProperty().bindBidirectional(passwordField.textProperty());

        // animations
        usernameAnimation = this.model.getErrorAnimation();
        usernameAnimation.setNode(this.usernameField);

        passwordAnimation = this.model.getErrorAnimation();
        passwordAnimation.setNode(this.passwordField);


        this.usernameField.requestFocus();
    }

    @FXML void Login(ActionEvent event) {
        this.model.login();

        if (!this.model.usernameCorrectProperty().get()) {
            this.usernameAnimation.play();
        }

        if (!this.model.passwordCorrectProperty().get()) {
            this.passwordAnimation.play();
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
