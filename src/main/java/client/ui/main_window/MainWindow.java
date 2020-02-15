// Created by Mitchell Hardie
package client.ui.main_window;

import client.interfaces.IWindow;
import client.ui.main_window.chat_pane.ChatPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Class is responsible for creating all components of the main appliactions interface.
 * The class implements the class Window to enable show and hide features of various
 * scenes.
 * Each method of the MainWindow class is designated for each frame of the main window,
 * or an individual function for the main window.
 * Private attributes are typically important components used within the main
 * interface. Such as a VBox used in server methods.
 * @author mitch161
 * @version 1.0
 * @since 1.0
 */
public class MainWindow implements IWindow {

	// ui elements
	private ChatPane chatPane = new ChatPane();
	private MainWindowMenuBar menuBar= new MainWindowMenuBar();

	// event handlers
	private EventHandler onRequestSendMessage;
	private EventHandler onRequestDeleteMessage;
	private EventHandler onRequestEditMessage;

	// not sure about these
	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	private double width = Screen.getPrimary().getBounds().getWidth();
	private double height = Screen.getPrimary().getBounds().getHeight();
	private boolean msgSent = true;
	private Stage stage;
	private TextField msgEntry = new TextField();
	private ScrollPane messageView = new ScrollPane();
	private VBox friendFrame = new VBox();
	private VBox chatFrame = new VBox();
	private HBox msgFrame = new HBox();
	int preSize = 0;

	/*
	 * Constructors
	 * Initilizes the stage.
	 * Assigns the stage attribute to a new stage.
	 */
	public MainWindow(int a) {
		//creating the stage.
		this.stage = new Stage();
		this.stage.setTitle("chat window");
		this.stage.setMaxHeight(this.primaryScreenBounds.getHeight());
		this.stage.setMaxWidth(this.primaryScreenBounds.getWidth());
		this.stage.setMinWidth(675);
		this.stage.setMinHeight(375);

		this.chatPane.setOnMessageHandler(event -> {
			this.onRequestSendMessage.handle(event);
		});

		// creating the main
		GridPane mainGrid = new GridPane();
		mainGrid.setGridLinesVisible(true);

		mainGrid.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		mainGrid.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		mainGrid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		//setting Column constraints.
		ColumnConstraints column0 = new ColumnConstraints();
		column0.hgrowProperty().set(Priority.NEVER);
		column0.setMinWidth(Region.USE_PREF_SIZE);
		column0.setMaxWidth(Double.MAX_VALUE);
		column0.setPrefWidth(50);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.hgrowProperty().set(Priority.NEVER);
		column1.setMinWidth(Region.USE_PREF_SIZE);
		column1.setMaxWidth(Region.USE_PREF_SIZE);
		column1.setPrefWidth(200);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.hgrowProperty().set(Priority.ALWAYS);
		column2.setMinWidth(350);
		column2.setMaxWidth(Double.MAX_VALUE);
		column2.setPrefWidth(Region.USE_COMPUTED_SIZE);

		mainGrid.getColumnConstraints().add(column0);
		mainGrid.getColumnConstraints().add(column1);
		mainGrid.getColumnConstraints().add(column2);

		// height constraints
		RowConstraints row0 = new RowConstraints();
		row0.vgrowProperty().set(Priority.NEVER);
		row0.setMinHeight(Region.USE_PREF_SIZE);
		row0.setPrefHeight(25);
		row0.setMaxHeight(Region.USE_PREF_SIZE);

		RowConstraints row1 = new RowConstraints();
		row1.vgrowProperty().set(Priority.ALWAYS);
		row1.setMinHeight(250);
		row1.setMaxHeight(Double.MAX_VALUE);

		mainGrid.getRowConstraints().add(row0);
		mainGrid.getRowConstraints().add(row1);

		mainGrid.add(this.menuBar,0,0,GridPane.REMAINING, 1);
		mainGrid.add(serverFrame(),0,1);
		mainGrid.add(friendFrame,1,1);
		mainGrid.add(this.chatPane,2,1);


		// setting up the scene
		Scene scene = new Scene(mainGrid);
		scene.getStylesheets().add("css/MainWindow/MainWindow.css");
		this.stage.setScene(scene);
	}

	public String getMessageBoxText() {
		return this.chatPane.getMessageText();
	}

	public void addMessage(String messageText) {
		this.chatPane.appendMessage(messageText, ChatPane.Left);
	}

	@Override
	public void show() {
		this.stage.show();
	}

	@Override
	public void hide() {
		this.stage.hide();
	}

	public EventHandler getOnRequestSendMessage() {
		return onRequestSendMessage;
	}

	public void setOnRequestSendMessage(EventHandler onRequestSendMessage) {
		this.onRequestSendMessage = onRequestSendMessage;
	}

	@Deprecated
	public MainWindow(){

		//setting up the stage.
		this.stage = new Stage();
		this.stage.setTitle("Application");

		this.stage.setMaxHeight(this.primaryScreenBounds.getHeight());
		this.stage.setMaxWidth(this.primaryScreenBounds.getWidth());
		this.stage.setMinWidth(625);
		this.stage.setMinHeight(350);
		this.stage.setScene(this.createWindow());

	}

	/*
	 * Event Handlers
	 */
	@Deprecated
	private class MsgHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			Message newMsg = new Message(msgEntry.getText());
			displayMsg(newMsg.getMsg());
		}
	}

	@Deprecated
	private class LanWanHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			System.out.println("Lan Frame");
		}
	}

	@Deprecated
	private class AddContact implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			System.out.println("Add Contact");

		}
	}

	@Deprecated
	public Scene createWindow() {

		GridPane mainGrid = new GridPane();
		mainGrid.setGridLinesVisible(true);

		mainGrid.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		mainGrid.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		mainGrid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		//setting Column constraints.
		ColumnConstraints column0 = new ColumnConstraints();
		column0.hgrowProperty().set(Priority.NEVER);
		column0.setMinWidth(Region.USE_PREF_SIZE);
		column0.setMaxWidth(Double.MAX_VALUE);
		column0.setPrefWidth(50);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.hgrowProperty().set(Priority.NEVER);
		column1.setMinWidth(Region.USE_PREF_SIZE);
		column1.setMaxWidth(Region.USE_PREF_SIZE);
		column1.setPrefWidth(200);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.hgrowProperty().set(Priority.ALWAYS);
		column2.setMinWidth(350);
		column2.setMaxWidth(Double.MAX_VALUE);
		column2.setPrefWidth(Region.USE_COMPUTED_SIZE);

		mainGrid.getColumnConstraints().add(column0);
		mainGrid.getColumnConstraints().add(column1);
		mainGrid.getColumnConstraints().add(column2);

		// height constraints
		RowConstraints row0 = new RowConstraints();
		row0.vgrowProperty().set(Priority.ALWAYS);
		row0.setMinHeight(275);
		row0.setMaxHeight(Double.MAX_VALUE);

		mainGrid.getRowConstraints().add(row0);

		mainGrid.add(serverFrame(),0,0);
		mainGrid.add(friendFrame,1,0);
		mainGrid.add(chatFrame,2,0);
		mainGrid.add(MsgGrid(),2,1 );
		
		// create scene
		Scene scene = new Scene(mainGrid);
		scene.getStylesheets().add("css/MainWindow/MainWindow.css");
		return scene;
	}

	@Deprecated
	public VBox serverFrame() {
		VBox tmpBox = new VBox();
		tmpBox.getStyleClass().add(".VBox");
		return tmpBox;
	}

	@Deprecated
	public GridPane FriendGrid() {

		GridPane root = new GridPane();

		VBox contactsVB = new VBox();
		HBox addContactsHB = new HBox();
		Button addBtn = new Button("Add");
		AddContact addContact = new AddContact();
		addBtn.setOnAction(addContact);
		addContactsHB.getChildren().add(addBtn);
		//this.displayContacts(contactsVB);
		root.add(contactsVB,0,0);
		root.add(addContactsHB,0,1);

		friendFrame.heightProperty().addListener(new ChangeListener(){
			/**
			 * Ensures that the listener of the height property updates all the children
			 * of FriendGrid so their heights resize correctly.
			 * @param arg0 observable value.
			 * @param arg1 actual value of the height of the FriendGrid(root).
			 * @param arg2 new value of the height of the FriendGrid(root).
			 */
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2){
				double height = (double) arg2;
				// Children added here:
				contactsVB.setPrefHeight(height*0.90);
				addContactsHB.setPrefHeight(height*0.10);
			}
		});

		friendFrame.widthProperty().addListener(new ChangeListener(){
			/**
			 * Ensures that the listener of the width property updates all the children
			 * of FriendGrid so their widths resize correctly.
			 * @param arg0 observable value.
			 * @param arg1 actual value of the width of the FriendGrid(root).
			 * @param arg2 new value of the width of the FriendGrid(root).
			 */
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2){
				double width = (double) arg2;
				// Children added here:
				contactsVB.setPrefWidth(width*1);
				addContactsHB.setPrefWidth(width*1);
			}
		});
		
		return root;
	}

	@Deprecated
	public VBox chatFrame() {
		chatFrame.setPrefWidth(this.width*0.90);
		//chatFrame.setPrefHeight(this.height*0.95);
		chatFrame.setSpacing(10);
		chatFrame.setPadding(new Insets(20,20,20,20));
		//chatFrame.setMaxHeight(this.height*0.95);
		return chatFrame;
	}

	@Deprecated
	public GridPane MsgGrid() {
		GridPane root = new GridPane();

		Button sendBtn = new Button("send");
		sendBtn.setMaxWidth(Double.MAX_VALUE);

		Button emojiBtn = new Button("emoji");
		emojiBtn.setMaxWidth(Double.MAX_VALUE);

		Button photoBtn = new Button("photo");
		photoBtn.setMaxWidth(Double.MAX_VALUE);

		// defining column grid
		ColumnConstraints column0 = new ColumnConstraints();
		column0.hgrowProperty().set(Priority.NEVER);
		column0.setMinWidth(Region.USE_PREF_SIZE);
		column0.setMaxWidth(Region.USE_PREF_SIZE);
		column0.setPrefWidth(50);

		// defining column grid
		ColumnConstraints column1 = new ColumnConstraints();
		column1.hgrowProperty().set(Priority.ALWAYS);
		column1.setMinWidth(Region.USE_PREF_SIZE);
		column1.setMaxWidth(Double.MAX_VALUE);
		column1.setPrefWidth(200);

		// defining column grid
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setMinWidth(Region.USE_PREF_SIZE);
		column2.setMinWidth(Region.USE_COMPUTED_SIZE);
		column2.setMaxWidth(Region.USE_PREF_SIZE);
		column2.setPrefWidth(50);

		// defining column grid
		ColumnConstraints column3 = new ColumnConstraints();
		column3.hgrowProperty().set(Priority.NEVER);
		column3.setMinWidth(Region.USE_PREF_SIZE);
		column3.setMaxWidth(Region.USE_PREF_SIZE);
		column3.setPrefWidth(50);

		root.getColumnConstraints().add(column0);
		root.getColumnConstraints().add(column1);
		root.getColumnConstraints().add(column2);
		root.getColumnConstraints().add(column3);

		// create msg handler
		MsgHandler msgHandler = new MsgHandler();
		sendBtn.setOnAction(msgHandler);
		// ---------
		msgEntry.setId("msgEntryBox");
		msgEntry.getStylesheets().add("css/MainWindow/mainWindow.css");
		msgEntry.setMinWidth(200);

		root.add(photoBtn,0,0);
		root.add(msgEntry,1,0);
		root.add(emojiBtn,2,0);
		root.add(sendBtn,3,0);

		// set msg handler
		sendBtn.setOnAction(new MsgHandler());

		return root;
	}

	// get current message in text box
	@Deprecated
	public void displayMsg(VBox msg){
		if (msgSent) {
			chatFrame.setAlignment(Pos.BOTTOM_RIGHT);
		}
		else {
			chatFrame.setAlignment(Pos.BOTTOM_LEFT);
		}
		chatFrame.getChildren().add(msg);
	}
}
