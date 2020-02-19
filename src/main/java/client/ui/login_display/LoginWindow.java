//created by Mitchell Hardie and Michael bailey
package client.ui.login_display;

import client.interfaces.IWindow;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * @author Mitch161, michael-bailey
 * @version 2.0
 * @since 1.0
 */
public class LoginWindow implements IWindow {

	private Stage stage;
	private String cssPath = "css/LoginWindow/LoginWindow.css";

	private TextField usernameBox = new TextField();
	private PasswordField passwordBox = new PasswordField();

	private Label titleText = new Label();

	private Label usernameLabel = new Label("Username");
	private Label passwordLabel = new Label("Password");

	private Button switchPageButton = new Button();
	private Button enterButton = new Button();

	// event handlers
	private EventHandler onRequestLogin;
	private EventHandler onRequestCreate;
	private EventHandler onRequestClose;

	public LoginWindow() {
		System.out.println(this);

		// creating a new stage
		this.stage = new Stage();
		this.stage.setResizable(false);
		this.titleText.setFont(Font.font("Consolas",FontWeight.NORMAL,20));

		// creating the scene
		AnchorPane rootNode = new AnchorPane();

		//setting widget sizes.
		this.usernameLabel.setMaxSize(200,25);
		this.usernameBox.setMaxSize(200,25);
		this.passwordBox.setMaxSize(200,25);
		this.passwordLabel.setMaxSize(200,25);

		// setting anchor points fo the ui
		AnchorPane.setTopAnchor(titleText, 30.0);
		AnchorPane.setTopAnchor(usernameLabel, 60.0);
		AnchorPane.setTopAnchor(usernameBox, 85.0);
		AnchorPane.setTopAnchor(passwordLabel, 120.0);
		AnchorPane.setTopAnchor(passwordBox, 145.0);
		AnchorPane.setTopAnchor(switchPageButton, 190.0);
		AnchorPane.setTopAnchor(enterButton, 190.0);

		AnchorPane.setLeftAnchor(titleText, 50.0);
		AnchorPane.setLeftAnchor(usernameLabel, 50.0);
		AnchorPane.setLeftAnchor(usernameBox, 50.0);
		AnchorPane.setLeftAnchor(passwordLabel, 50.0);
		AnchorPane.setLeftAnchor(passwordBox, 50.0);
		AnchorPane.setLeftAnchor(switchPageButton, 177.0);
		AnchorPane.setLeftAnchor(enterButton, 50.0);

		AnchorPane.setRightAnchor(titleText, 50.0);
		AnchorPane.setRightAnchor(usernameLabel, 50.0);
		AnchorPane.setRightAnchor(usernameBox, 50.0);
		AnchorPane.setRightAnchor(passwordLabel, 50.0);
		AnchorPane.setRightAnchor(passwordBox, 50.0);
		AnchorPane.setRightAnchor(switchPageButton, 50.0);
		AnchorPane.setRightAnchor(enterButton, 177.0);

		// add them in order to the anchor pane
		rootNode.getChildren().add(usernameLabel);
		rootNode.getChildren().add(passwordLabel);
		rootNode.getChildren().add(usernameBox);
		rootNode.getChildren().add(passwordBox);
		rootNode.getChildren().add(titleText);
		rootNode.getChildren().add(switchPageButton);
		rootNode.getChildren().add(enterButton);

		Scene tmpScene = new Scene(rootNode, 350, 260);
		tmpScene.getStylesheets().add(cssPath);

		// setting style classes

		this.enterButton.getStyleClass().add("Button");
		this.switchPageButton.getStyleClass().add("Button");
		this.passwordBox.getStyleClass().add("TextBox");
		this.usernameBox.getStyleClass().add("TextBox");
		this.titleText.setStyle("-fx-text-fill: white;");
		rootNode.getStyleClass().add("Root");

		this.stage.setScene(tmpScene);
	}

	private void loginDisplay() {
		stage.setTitle("Login Window");

		// set text
		this.titleText.setText("Login");
		this.enterButton.setText("Sign in");
		this.switchPageButton.setText("Create Account?");

		// handling events in the window
		this.switchPageButton.setOnAction(event -> {
			this.CreateDisplay();
		});

		this.enterButton.setOnAction(event -> {
			if (this.onRequestLogin != null) {
				event.consume();
				onRequestLogin.handle(event);
			}
		});

		passwordBox.setOnAction(event -> {
			if (this.onRequestLogin != null) {
				event.consume();
				onRequestLogin.handle(event);
			}
		});
	}

	private void CreateDisplay() {
		stage.setTitle("Create Account Window");

		// set text
		this.titleText.setText("Create Account");
		this.enterButton.setText("Create");
		this.switchPageButton.setText("Login?");

		// handling events in the window
		this.switchPageButton.setOnAction(event -> {
			this.loginDisplay();
		});

		this.enterButton.setOnAction(event -> {
			if (this.onRequestLogin != null) {
				event.consume();
				onRequestCreate.handle(event);
			}
		});

		passwordBox.setOnAction(event -> {
			if (this.onRequestLogin != null) {
				onRequestCreate.handle(event);
			}
		});
	}

	@Override
	public void show() {
		this.loginDisplay();

		this.stage.show();
	}

	@Override
	public void hide() {
		this.stage.hide();
		this.usernameBox.clear();
		this.passwordBox.clear();
	}

	public void setOnRequestLogin(EventHandler onRequestLogin) {
		this.onRequestLogin = onRequestLogin;
	}

	public void setOnRequestCreate(EventHandler onRequestCreate) {
		this.onRequestCreate = onRequestCreate;
	}

	public String getUsername() {
		return this.usernameBox.getText();
	}

	public String getPassword() {
		return this.passwordBox.getText();
	}

	public EventHandler getOnRequestClose() {
		return onRequestClose;
	}

	public void setOnRequestClose(EventHandler onRequestClose) {
		this.onRequestClose = onRequestClose;
	}
}
