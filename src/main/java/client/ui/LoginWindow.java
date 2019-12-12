//created by mitchel hardie

package client.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class LoginWindow {
    public void createWindow(Stage stage){
        stage.setTitle("Login Window");
        // init grid layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        // add contents to grid
        Text title = new Text("Login");
        title.setFont(Font.font("Consolas",FontWeight.NORMAL,20));
        grid.add(title,0,0,2,1);
	    Label userName = new Label("Username:");
	    grid.add(userName,0,1);
	    TextField userTextField = new TextField();
	    grid.add(userTextField,1,1);
        Label pw = new Label("Password:");
        grid.add(pw,0,2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox,1,2);
        
        // add login button
        Button loginBtn = new Button("Sign in");
        HBox loginHB = new HBox(10);
        loginHB.setAlignment(Pos.CENTER);
        HBox.setHgrow(loginBtn,Priority.ALWAYS);
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginHB.getChildren().add(loginBtn);
        grid.add(loginHB,0,4,2,1);
        
        // add create account button
        Button createAccountBtn = new Button("Create Account");
        HBox createAccountHB = new HBox(10);
        createAccountHB.setAlignment(Pos.CENTER);
        HBox.setHgrow(createAccountBtn,Priority.ALWAYS);
        createAccountBtn.setMaxWidth(Double.MAX_VALUE);
        createAccountHB.getChildren().add(createAccountBtn);
        grid.add(createAccountHB,0,6,2,1);
        
        // construct scene & display
        Scene scene = new Scene(grid,300,275);
        stage.setScene(scene);
        stage.show();
    }
}