//created by Mitchell Hardie

//package client.ui;
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
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

public class LoginWindow {
	public void createWindow(Stage stage){
		stage.setTitle("Login Window");
		// init main gird
		GridPane mainGrid = new GridPane();
		mainGrid.setAlignment(Pos.CENTER);
		mainGrid.setVgap(10);

		// init login grid
		GridPane loginGrid = new GridPane();
		loginGrid.setAlignment(Pos.CENTER);
		loginGrid.setHgap(10);
		loginGrid.setVgap(10);
		loginGrid.setPadding(new Insets(25,25,25,25));

		// init create account grid
		GridPane createAccGrid = new GridPane();
		createAccGrid.setAlignment(Pos.CENTER);
		createAccGrid.setPadding(new Insets(10,10,10,10));

		// create roots - vertical box
		VBox loginRoot = new VBox();
		loginRoot.setAlignment(Pos.CENTER);
		loginRoot.setId("loginBox");
		loginRoot.getStylesheets().add("cssLogin.css");
		VBox createAccRoot = new VBox();
		createAccRoot.setAlignment(Pos.CENTER);
		createAccRoot.setId("loginBox");
		createAccRoot.getStylesheets().add("cssLogin.css");

		// add contents to grid
		Text title = new Text("Login");
		title.setFont(Font.font("Consolas",FontWeight.NORMAL,20));
		loginGrid.add(title,0,0,2,1);
		Label username = new Label("Username:");
		loginGrid.add(username,0,1);
		TextField userTextField = new TextField();
		loginGrid.add(userTextField,0,2,2,1);
		Label pw = new Label("Password:");
		loginGrid.add(pw,0,3);
		PasswordField pwBox = new PasswordField();
		loginGrid.add(pwBox,0,4,2,1);

		// add login button
		Button loginBtn = new Button("Sign in");
		HBox loginHB = new HBox(10);
		loginHB.setAlignment(Pos.CENTER);
		HBox.setHgrow(loginBtn,Priority.ALWAYS);
		loginBtn.setMaxWidth(Double.MAX_VALUE);
		loginHB.getChildren().add(loginBtn);
		loginGrid.add(loginHB,0,5,2,1);	

		// add create account button
		Button createAccBtn = new Button("Create Account");
		createAccBtn.setId("createAccount");
		createAccBtn.getStylesheets().add("buttonStyle.css");
		HBox createAccHB = new HBox(10);
		createAccHB.setAlignment(Pos.CENTER);
		HBox.setHgrow(createAccBtn,Priority.ALWAYS);
		createAccBtn.setMaxWidth(Double.MAX_VALUE);
		createAccHB.getChildren().add(createAccBtn);
		createAccGrid.add(createAccHB,1,0);
		// add create account text label
		Text accMsg = new Text("New to <name>?");
		accMsg.setFont(Font.font("Consolas",FontWeight.NORMAL,11));
		createAccGrid.add(accMsg,0,0);

		// add to grids to roots
		loginRoot.getChildren().add(loginGrid);
		createAccRoot.getChildren().add(createAccGrid);
		// add roots to main grid
		mainGrid.add(loginRoot,0,0);
		mainGrid.add(createAccRoot,0,1);	

		// construct scene & display	
		Scene scene = new Scene(mainGrid,300,275);
		stage.setScene(scene);
		stage.show();
	}
}

