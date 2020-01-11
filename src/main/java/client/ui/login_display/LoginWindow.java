//created by Mitchell Hardie
package client.ui.login_display;

import java.io.IOException;

import client.interfaces.LoginWindowController;
import client.interfaces.Window;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * @author Mitch161
 * @version 1.0
 * @since 1.0
 */
public class LoginWindow implements Window {

	private Stage stage;
	private LoginWindowController controller;
	private String css = "LoginWindow.css";

	private TextField userTextField = new TextField();
	private PasswordField pwBox = new PasswordField();

	public LoginWindow(LoginWindowController controller) throws IOException {
		System.out.println(this);
		this.controller = controller;

		// creating a new stage
		this.stage = new Stage();
		
		// window properties
		this.stage.setResizable(false);

		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent event) {
				System.exit(1);
			}
		});
	}

	// Event Handlers
	private class LoginHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event){
			System.out.println("logging in...");
			controller.LoginRequest(userTextField.getText(), pwBox.getText());
		}
	}

	private class CreateAccountHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			System.out.println("creating user...");
			controller.LoginCreateUser(userTextField.getText(), pwBox.getText());
		}
	}

	private class switchToCreateButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event){
			stage.setScene(createAccDisplay());
		}
	}

	private class switchToLoginButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event){
			stage.setScene(loginDisplay());
		}
	}

	// Displays
	private Scene loginDisplay(){
		stage.setTitle("Login Window");
		stage.setHeight(350);
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
		loginRoot.getStylesheets().setAll(this.css);
		VBox createAccRoot = new VBox();
		createAccRoot.setAlignment(Pos.CENTER);
		createAccRoot.setId("loginBox");
		createAccRoot.getStylesheets().add(this.css);

		// add contents to grid
		Text title = new Text("Login");
		title.setFont(Font.font("Consolas",FontWeight.NORMAL,20));
		loginGrid.add(title,0,0,2,1);
		Label username = new Label("Username:");
		loginGrid.add(username,0,1);
		loginGrid.add(this.userTextField,0,2,2,1);
		Label pw = new Label("Password:");
		loginGrid.add(pw,0,3);
		loginGrid.add(this.pwBox,0,4,2,1);

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
		
		
		// button actions
		LoginHandler loginAttempt = new LoginHandler();
		loginBtn.setOnAction(loginAttempt);
		switchToCreateButtonHandler createAccPress = new switchToCreateButtonHandler();	
		createAccBtn.setOnAction(createAccPress);
		

		// add to grids to roots
		loginRoot.getChildren().add(loginGrid);
		createAccRoot.getChildren().add(createAccGrid);
		// add roots to main grid
		mainGrid.add(loginRoot,0,0);
		mainGrid.add(createAccRoot,0,1);	

		// return the scene
		return new Scene(mainGrid,300,275);
	}

	private Scene createAccDisplay(){
		stage.setTitle("Create Account");
		stage.setHeight(300);
		// init main grid
		GridPane mainGrid = new GridPane();
		mainGrid.setAlignment(Pos.CENTER);

		// init create account grid
		GridPane createAccGrid = new GridPane();
		createAccGrid.setAlignment(Pos.CENTER);
		createAccGrid.setHgap(10);
		createAccGrid.setVgap(10);
		createAccGrid.setPadding(new Insets(25,25,25,25));

		// create root - vertical box
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setId("loginBox");
		root.getStylesheets().add(this.css);

		// add contents to grid
		Text title = new Text("Create Account");
		title.setFont(Font.font("Consolas",FontWeight.NORMAL,20));
		createAccGrid.add(title,0,0,2,1);
		Label username = new Label("Username:");
		createAccGrid.add(username,0,1);
		createAccGrid.add(this.userTextField,0,2,2,1);
		Label pw = new Label("Password:");
		createAccGrid.add(pw,0,3);
		createAccGrid.add(this.pwBox,0,4,2,1);

		// create button box
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		// create & add buttons to box
		Button returnBtn = new Button("Return");
		Button continueBtn = new Button("Continue");
		buttonBox.getChildren().add(returnBtn);
		buttonBox.getChildren().add(continueBtn);
		// add button box to grid
		createAccGrid.add(buttonBox,0,6,2,1);
		// create button events
		switchToLoginButtonHandler returnFunc = new switchToLoginButtonHandler();
		returnBtn.setOnAction(returnFunc);
		continueBtn.setOnAction(new CreateAccountHandler());

		// add to grids to roots
		root.getChildren().add(createAccGrid);
		// add roots to main grid
		mainGrid.add(root,0,0);

		// retrun the new scene
		return new Scene(mainGrid,300,275); 
	}

	// window functions.
	public void show(boolean userDataExists) {
		if (userDataExists) {
			this.stage.setScene(this.loginDisplay());
		} else {
			this.stage.setScene(this.createAccDisplay());
		}
		this.stage.show();
	}

	public void hide() {
		this.stage.hide();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

}
