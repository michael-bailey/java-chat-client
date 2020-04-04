package client.models.login_window;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LoginWindowModel {
    SimpleBooleanProperty loginAvaliable = new SimpleBooleanProperty(false);
    SimpleIntegerProperty usernameLength = new SimpleIntegerProperty();
    SimpleIntegerProperty passwordLength = new SimpleIntegerProperty();

    public int getUsernameLength() {
        return usernameLength.get();
    }

    public SimpleIntegerProperty usernameLengthProperty() {
        return usernameLength;
    }

    public int getPasswordLength() {
        return passwordLength.get();
    }

    public SimpleIntegerProperty passwordLengthProperty() {
        return passwordLength;
    }

    public boolean isLoginAvaliable() {
        return loginAvaliable.get();
    }

    public SimpleBooleanProperty loginAvaliableProperty() {
        return loginAvaliable;
    }


}
