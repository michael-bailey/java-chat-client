package client.models.login_window;

import client.classes.Contact;
import client.models.ApplicationModel;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public class LoginWindowModel {

    private TranslateTransition usernameAnimation = new TranslateTransition();
    private TranslateTransition passwordAnimation = new TranslateTransition();
    private ApplicationModel appModel = ApplicationModel.getInstance();

    private SimpleBooleanProperty loginStatus = new SimpleBooleanProperty();
    private SimpleStringProperty usernameString = new SimpleStringProperty();
    private SimpleStringProperty passwordString = new SimpleStringProperty();
    private SimpleListProperty<String> usernameBoxCSS = new SimpleListProperty();
    private SimpleListProperty<String> passwordBoxCSS = new SimpleListProperty();
    private SimpleBooleanProperty usernameCorrect = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty passwordCorrect = new SimpleBooleanProperty(false);

    private ChangeListener<String> usernameListener = (observableValue, oldValue, newValue) -> {
        ObservableList<String> styleClass = this.usernameBoxCSS;
        styleClass.clear();
        styleClass.add("TextBox");

        if (!(newValue.length() > 0)) {
            styleClass.add("TextBoxEmpty");
            this.usernameCorrectProperty().setValue(false);
            return;
        }

        if (newValue.matches("[a-zA-Z0-9_-]*")) {
            styleClass.add("TextBoxCorrect");
            this.usernameCorrectProperty().setValue(true);
        } else {
            styleClass.add("TextBoxWrong");
            this.usernameCorrectProperty().set(false);
        }
    };
    private ChangeListener<String> passwordListener = (observableValue, oldValue, newValue) -> {
        ObservableList<String> styleClass = this.passwordBoxCSS;
        styleClass.clear();
        styleClass.add("TextBox");

        if (!(newValue.length() > 0)) {
            styleClass.add("TextBoxEmpty");
            this.passwordCorrectProperty().setValue(false);
            return;
        }

        if (newValue.matches("[a-zA-Z]*")) {
            styleClass.add("TextBoxWrong");
            this.passwordCorrectProperty().setValue(false);
        } else {
            styleClass.add("TextBoxCorrect");
            this.passwordCorrectProperty().setValue(true);
        }
    };



    public LoginWindowModel() {

        // setting up animations
        usernameAnimation.byYProperty();
        usernameAnimation.setFromX(8.0);
        usernameAnimation.setToX(0);
        usernameAnimation.setAutoReverse(true);
        usernameAnimation.setDuration(Duration.millis(75));
        usernameAnimation.setCycleCount(5);

        passwordAnimation.byYProperty();
        passwordAnimation.setFromX(8.0);
        passwordAnimation.setToX(0);
        passwordAnimation.setAutoReverse(true);
        passwordAnimation.setDuration(Duration.millis(75));
        passwordAnimation.setCycleCount(5);

        this.passwordString.addListener(passwordListener);
        this.usernameString.addListener(usernameListener);

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

    public TranslateTransition getUsernameAnimation() {
        return usernameAnimation;
    }

    public TranslateTransition getPasswordAnimation() {
        return passwordAnimation;
    }

    public ObservableList<String> getUsernameBoxCSS() {
        return usernameBoxCSS.get();
    }

    public SimpleListProperty<String> usernameBoxCSSProperty() {
        return usernameBoxCSS;
    }

    public ObservableList<String> getPasswordBoxCSS() {
        return passwordBoxCSS.get();
    }

    public SimpleListProperty<String> passwordBoxCSSProperty() {
        return passwordBoxCSS;
    }
}
