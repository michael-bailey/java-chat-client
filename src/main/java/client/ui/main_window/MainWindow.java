//Created by Mitchell Hardie
package client.ui.main_window;

import client.interfaces.Window;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import java.util.Scanner;
import java.io.File;
import javafx.stage.Stage;

/*
 * Class is responsible for creating all components of the main appliactions interface.
 * The class implements the class Window to enable show and hide features of various
 * scenes.
 * Each method of the MainWindow class is designated for each frame of the main window,
 * or an individual function for the main window.
 * Private attributes are typically important components used within the main
 * interface. Such as a VBox used in serverl methods.
 */
public class MainWindow implements Window{
	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	private double width = Screen.getPrimary().getBounds().getWidth();
	private double height = Screen.getPrimary().getBounds().getHeight();
	private boolean msgSent = true;
	private Stage stage;
	private TextField msgEntry = new TextField();
	private ScrollPane sp = new ScrollPane();
	private VBox friendFrame = new VBox();
	private VBox chatFrame = new VBox();
	private HBox msgFrame = new HBox();
	private ContactInterface contIn = new ContactInterface();
	/*
	 * Constructor
	 * Initilizes the stage.
	 * Assigns the stage attribute to a new stage.
	 */
	public MainWindow(){
		this.stage = new Stage();
	}
	/*
	 * Event Handlers
	 *
	 */
	private class MsgHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			Message newMsg = new Message(msgEntry.getText());
			displayMsg(newMsg.getMsg());
		}
	}
	private class LanWanHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			System.out.println("Lan Frame");
		}
	}
	private class AddContact implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			//contIn.createContact();
			System.out.println("Add Contact");

		}
	}
	public Scene createWindow(){
		this.stage.setTitle("Application");
		GridPane mainGrid = new GridPane();	
		sp.setVmax(100);
		sp.setVvalue(100);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.vvalueProperty().addListener(new ChangeListener<Number>() {
		       	public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) {
				chatFrame.setLayoutY((new_val.intValue() - 1)/100);
			}
		});

		// create frames
		friendFrame.getChildren().add(FriendGrid());
		sp.setContent(chatFrame());
		msgFrame.getChildren().add(MsgGrid());
		// add frames to main gird
		mainGrid.add(friendFrame,0,0,1,2);
		mainGrid.add(sp,1,0);
		mainGrid.add(msgFrame,1,1);

		//configure mainGrid
		mainGrid.setHgrow(friendFrame,Priority.ALWAYS);
		mainGrid.setHgrow(chatFrame,Priority.ALWAYS);
		mainGrid.setHgrow(msgFrame,Priority.ALWAYS);
		mainGrid.setVgrow(friendFrame,Priority.ALWAYS);
		mainGrid.setVgrow(chatFrame,Priority.ALWAYS);
		mainGrid.setVgrow(msgFrame,Priority.ALWAYS);

		this.stage.heightProperty().addListener(new ChangeListener(){
			/* 
			 * Ensures that the listener of the height property updates all the frames
			 * of the stage so their heights resize correctly.
			 * @param arg0 observable value.
			 * @param arg1 actual value of the height of the stage.
			 * @param arg2 new value of the height of the stage.
			 */
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2){
				double height = (double) arg2;
				// Children added here:
				friendFrame.setPrefHeight(height*1.0);
				sp.setPrefHeight(height*0.90);
				msgFrame.setPrefHeight(height*0.10);
			}
		});
		this.stage.widthProperty().addListener(new ChangeListener(){
			/* 
			 * Ensures that the listener of the width property updates all the frames
			 * of the stage so their widths resize correctly.
			 * @param arg0 observable value.
			 * @param arg1 actual value of the width of the stage.
			 * @param arg2 new value of the width of the stage.
			 */
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2){
				double width = (double) arg2;
				// Children added here:
				friendFrame.setPrefWidth(width*0.10);
				sp.setPrefWidth(width*0.90);
				msgFrame.setPrefWidth(width*0.90);
			}
		});
		
		// create scene
		Scene scene = new Scene(mainGrid);
		return scene;
	}
	public GridPane FriendGrid(){
		GridPane root = new GridPane();
		VBox contactsVB = new VBox();
		HBox addContactsHB = new HBox();
		Button addBtn = new Button("Add");
		AddContact addContact = new AddContact();
		addBtn.setOnAction(addContact);
		addContactsHB.getChildren().add(addBtn);
		this.displayContacts(contactsVB);
		root.add(contactsVB,0,0);
		root.add(addContactsHB,0,1);

		friendFrame.heightProperty().addListener(new ChangeListener(){
			/* 
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
			/* 
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
	public VBox chatFrame(){
		chatFrame.setPrefWidth(this.width*0.90);
		chatFrame.setPrefHeight(this.height*0.90);
		chatFrame.setSpacing(10);
		chatFrame.setPadding(new Insets(20,20,20,20));
		chatFrame.setMaxHeight(this.height*0.90);
		return chatFrame;
	}
	public GridPane MsgGrid(){
		GridPane root = new GridPane();
		Button sendBtn = new Button("send"),emojiBtn = new Button("emoji"),photoBtn = new Button("photo");
		// create msg handler
		MsgHandler msgHandler = new MsgHandler();
		sendBtn.setOnAction(msgHandler);
		// ---------
		msgEntry.setId("msgEntryBox");
		msgEntry.getStylesheets().add("mainwindow.css");
		
		root.add(photoBtn,0,0);
		root.add(msgEntry,1,0);
		root.add(emojiBtn,2,0);
		root.add(sendBtn,3,0);

		msgFrame.heightProperty().addListener(new ChangeListener(){
			/* 
			 * Ensures that the listener of the height property updates all the children
			 * of MsgGrid so their heights resize correctly.
			 * @param arg0 observable value.
			 * @param arg1 actual value of the height of the MsgGrid(root).
			 * @param arg2 new value of the height of the MsgGrid(root).
			 */
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2){
				double height = (double) arg2;
				// Children added here:
				photoBtn.setPrefHeight(height*1.0);
				msgEntry.setPrefHeight(height*1.0);
				emojiBtn.setPrefHeight(height*1.0);
				sendBtn.setPrefHeight(height*1.0);
			}
		});
		msgFrame.widthProperty().addListener(new ChangeListener(){
			/* 
			 * Ensures that the listener of the width property updates all the children
			 * of MsgGrid so their widths resize correctly.
			 * @param arg0 observable value.
			 * @param arg1 actual value of the width of the MsgGrid(root).
			 * @param arg2 new value of the width of the MsgGrid(root).
			 */
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2){
				double width = (double) arg2;
				// Children added here:
				photoBtn.setPrefWidth(width*0.05);
				msgEntry.setPrefWidth(width*0.85);
				emojiBtn.setPrefWidth(width*0.05);
				sendBtn.setPrefWidth(width*0.05);
			}
		});

		return root;
	}
	// get current message in text box
	public void displayMsg(VBox msg){
		if(msgSent){chatFrame.setAlignment(Pos.BOTTOM_RIGHT);}
		else{chatFrame.setAlignment(Pos.BOTTOM_LEFT);}
		chatFrame.getChildren().add(msg);
	}
	public void displayContacts(VBox contactsVB){
		for(int i=0;i<contIn.getContactArrSize();i++){
			contactsVB.getChildren().add(contIn.getContact(i).getContactVB());
		}
	}
	@Override
	public void show() {
		this.stage.setScene(this.createWindow());
		this.stage.setMaxHeight(this.primaryScreenBounds.getHeight());
		this.stage.setMinHeight(250);
		this.stage.setMaxWidth(this.primaryScreenBounds.getWidth());
		this.stage.setMinWidth(500);
		this.stage.setMaximized(true);
		this.stage.show();
	}

	@Override
	public void hide() {
		this.stage.hide();
	}
}
/*class LanFrame{
	private Scene scene;
	private VBox lanVB = new VBox();
	public Scene createFrame(){

		return scene;
	}
}*/
