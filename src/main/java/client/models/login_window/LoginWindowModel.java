package client.models.login_window;

import client.models.ApplicationModel;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;

public class LoginWindowModel {

    private SimpleBooleanProperty loginStatus = new SimpleBooleanProperty();
    private SimpleStringProperty usernameString = new SimpleStringProperty();
    private SimpleStringProperty passwordString = new SimpleStringProperty();

    private SimpleBooleanProperty usernameCorrect = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty passwordCorrect = new SimpleBooleanProperty(false);



    private ApplicationModel appModel = ApplicationModel.getInstance();

    public LoginWindowModel() {

        // bindings
        loginStatus.bind(appModel.loginStatusProperty());

    }

    public void login() {
        System.out.println(this.usernameCorrect.getValue());
        System.out.println(this.passwordCorrect.getValue());

        if (this.usernameCorrect.getValue() && this.passwordCorrect.getValue()) {
            this.appModel.login(usernameString.get(), passwordString.get());
            this.usernameStringProperty().set("");
            this.passwordStringProperty().set("");
        }
    }

    public TranslateTransition getErrorAnimation() {
        TranslateTransition t = new TranslateTransition();
        t.byYProperty();
        t.setFromX(8.0);
        t.setToX(0);
        t.setAutoReverse(true);
        t.setDuration(Duration.millis(75));
        t.setCycleCount(5);
        return t;
    }

    public boolean isLoginStatus() {
        return loginStatus.get();
    }

    public SimpleBooleanProperty loginStatusProperty() {
        return loginStatus;
    }

    public String getUsernameString() {
        return usernameString.get();
    }

    public SimpleStringProperty usernameStringProperty() {
        return usernameString;
    }

    public String getPasswordString() {
        return passwordString.get();
    }

    public SimpleStringProperty passwordStringProperty() {
        return passwordString;
    }

    public boolean isUsernameCorrect() {
        return usernameCorrect.get();
    }

    public SimpleBooleanProperty usernameCorrectProperty() {
        return usernameCorrect;
    }

    public boolean isPasswordCorrect() {
        return passwordCorrect.get();
    }

    public SimpleBooleanProperty passwordCorrectProperty() {
        return passwordCorrect;
    }
}
