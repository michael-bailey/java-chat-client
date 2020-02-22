// Created by Mitchell Hardie
package client.ui.main_window;

import client.classes.Message;
import client.enums.MessageAlignment;
import client.interfaces.IWindow;
import client.ui.main_window.chat_pane.ChatPane;
import client.ui.main_window.chat_pane.MessageTextBox;
import client.ui.main_window.contact_pane.ContactPane;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

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
	private ContactPane contactPane = new ContactPane();
	private ChatPane chatPane = new ChatPane();
	private MainWindowMenuBar menuBar = new MainWindowMenuBar();

	// event handlers
	private EventHandler onRequestSendMessage;
	private EventHandler onRequestDeleteMessage;
	private EventHandler onRequestEditMessage;
	private EventHandler onSpam;

	// window events
	private EventHandler onRequestClose;

	// not sure about these
	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	private Stage stage;

	/*
	 * Constructors
	 * Initializes the stage.
	 * Assigns the stage attribute to a new stage.
	 */
	public MainWindow() {
		//creating the stage.
		this.stage = new Stage();
		this.stage.setTitle("chat window");
		this.stage.setMaxHeight(this.primaryScreenBounds.getHeight());
		this.stage.setMaxWidth(this.primaryScreenBounds.getWidth());
		this.stage.setMinWidth(675);
		this.stage.setMinHeight(375);

		this.stage.setOnCloseRequest(event -> {
			if (this.onRequestClose != null) {
				this.onRequestClose.handle(event);
			}
		});

		this.chatPane.setOnMessageHandler(event -> {
			if (this.onRequestSendMessage != null) {
				this.onRequestSendMessage.handle(event);
			}
		});

		this.menuBar.setOnSpam(event -> {
			if (this.onSpam != null) {
				this.onSpam.handle(event);
			}
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
		// TODO mainGrid.add(null, 0, 1);
		mainGrid.add(this.contactPane, 1, 1);
		mainGrid.add(this.chatPane,2,1);


		// setting up the scene
		Scene scene = new Scene(mainGrid);
		scene.getStylesheets().add("css/MainWindow/MainWindow.css");
		scene.getStylesheets().add("css/MainWindow/ContactPane/ContactPane.css");
		scene.getStylesheets().add("css/ContextMenu.css");
		this.stage.setScene(scene);
	}


	public String getMessageBoxText() {
		return this.chatPane.getMessageText();
	}

	public void addMessage(Message message) {
		this.chatPane.appendMessage(message);
	}
	public void loadMessages(ArrayList<MessageTextBox> messages) {
		this.chatPane.loadMessages(messages);
	}

	@Override
	public void show() {
		this.stage.show();
	}

	@Override
	public void hide() {
		this.stage.hide();
		this.chatPane.clearAll();
	}

	public EventHandler getOnRequestSendMessage() {
		return onRequestSendMessage;
	}

	public void setOnRequestSendMessage(EventHandler onRequestSendMessage) {
		this.onRequestSendMessage = onRequestSendMessage;
	}

	public EventHandler getOnRequestClose() {
		return onRequestClose;
	}

	public void setOnRequestClose(EventHandler onRequestClose) {
		this.onRequestClose = onRequestClose;
	}

	public EventHandler getOnSpam() {
		return onSpam;
	}

	public void setOnSpam(EventHandler onSpam) {
		this.onSpam = onSpam;
	}
}
