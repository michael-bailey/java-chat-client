//created by Mitchell Hardie
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
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

public class LoginWindow {
	private Stage stage;
	private Scene scene,preScene;
	public LoginWindow(Stage stage){
		this.stage=stage;
		this.preScene=null;
	}
	// Getters
	public Scene getScene(){return scene;}
	public Scene getPreScene(){return preScene;}
	// Setters
	public void setScene(Scene scene){this.scene=scene;}
	public void setPreScene(Scene preScene){this.preScene=preScene;}
	// Event Handlers
	private class LoginHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			System.out.println("<placeholder> logging in...");
		}
	}
	private class CreateAccHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			System.out.println("Create Account");
			stage.setScene(preScene);
		}
	}
	private class ReturnHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			stage.setScene(scene);
		}
	}
	// Displays
	public Scene loginDisplay(){
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
		loginRoot.getStylesheets().add("../css_style_sheets/cssLogin.css");
		VBox createAccRoot = new VBox();
		createAccRoot.setAlignment(Pos.CENTER);
		createAccRoot.setId("loginBox");
		createAccRoot.getStylesheets().add("../css_style_sheets/cssLogin.css");

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
		createAccBtn.getStylesheets().add("../css_style_sheets/buttonStyle.css");
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
		CreateAccHandler createAccPress = new CreateAccHandler();	
		createAccBtn.setOnAction(createAccPress);

		// add to grids to roots
		loginRoot.getChildren().add(loginGrid);
		createAccRoot.getChildren().add(createAccGrid);
		// add roots to main grid
		mainGrid.add(loginRoot,0,0);
		mainGrid.add(createAccRoot,0,1);	

		// construct scene & display{
		scene = new Scene(mainGrid,300,275);
		return scene;
	}
	public Scene createAccDisplay(){
		stage.setTitle("Create Account");
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
		root.getStylesheets().add("../css_style_sheets/cssLogin.css");

		// add contents to grid
		Text title = new Text("Create Account");
		title.setFont(Font.font("Consolas",FontWeight.NORMAL,20));
		createAccGrid.add(title,0,0,2,1);
		Label username = new Label("Username:");
		createAccGrid.add(username,0,1);
		TextField userTextField = new TextField();
		createAccGrid.add(userTextField,0,2,2,1);
		Label pw = new Label("Password:");
		createAccGrid.add(pw,0,3);
		PasswordField pwBox = new PasswordField();
		createAccGrid.add(pwBox,0,4,2,1);

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
		ReturnHandler returnFunc = new ReturnHandler();
		returnBtn.setOnAction(returnFunc);

		// add to grids to roots
		root.getChildren().add(createAccGrid);
		// add roots to main grid
		mainGrid.add(root,0,0);

		preScene = new Scene(mainGrid,300,275);
		return preScene;
	}
}
