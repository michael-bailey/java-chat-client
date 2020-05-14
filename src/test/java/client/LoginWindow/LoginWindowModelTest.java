package client.LoginWindow;

import org.junit.Test;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;


import static org.junit.Assert.*;

public class LoginWindowModelTest {

    public LoginWindowModelTest() {  }

    @Test
    public void objectCreated() {
        var model = new LoginWindowModel();
        assertNotNull(model);
    }

    @Test
    public void getProperties() {
        var model = new LoginWindowModel();

        assertNotNull(model.passwordStringProperty());
        assertNotNull(model.passwordStringProperty());
    }

    @Test
    public void bindingsLoginLogout() {
        var model = new LoginWindowModel();

        var username = "testfile";
        var password = "validPassword!2";

        new File(username + ".dat").delete();

        // vars for binding to
        var usernameProp = new SimpleStringProperty("");
        var passwordProp = new SimpleStringProperty("");

        // bind the properties.
        model.usernameStringProperty().bind(usernameProp);
        model.passwordStringProperty().bind(passwordProp);

        usernameProp.set(username);
        passwordProp.set(password);

        assertNotNull(model.login());
    }

}
