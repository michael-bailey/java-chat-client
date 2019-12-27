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
import javafx.stage.Screen;
import javafx.stage.Stage;
public class MainWindow implements Window{
	private double width = Screen.getPrimary().getBounds().getWidth();
	private double height = Screen.getPrimary().getBounds().getHeight();
	private double currMsgHeight = 0;
	private boolean msgSent = true;
	private Stage stage;
	private TextField msgEntry = new TextField();
	private ScrollPane sp = new ScrollPane();
	private VBox chatFrame = new VBox();
	private VBox msgFrame = new VBox();
	
	public MainWindow(){
		this.stage = new Stage();
	}
	private class MsgHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event){
			Message newMsg = new Message(msgEntry.getText());
			currMsgHeight+=newMsg.getMsgHeight();
			displayMsg(newMsg.getMsg());
		}
	}
	public Scene createWindow(){
		this.stage.setTitle("Application");
		// init main grid
		GridPane mainGrid = new GridPane();
		// create vertical boxes for pannels
		VBox friendFrame = new VBox();
		friendFrame.setPrefWidth(this.width*0.10);
		friendFrame.setPrefHeight(this.height*1.0);		
		
		sp.setVmax(100);
		sp.setPrefWidth(this.width*0.90);
		sp.setPrefHeight(this.height*0.90);
		sp.setVvalue(100);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.vvalueProperty().addListener(new ChangeListener<Number>() {
		       	public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) {
				chatFrame.setLayoutY((new_val.intValue() - 1)/100);
			}
		});

		msgFrame.setPrefWidth(this.width*0.90);
		msgFrame.setPrefHeight(this.height*0.02);

		// create frames
		friendFrame.getChildren().add(FriendGrid());
		sp.setContent(chatFrame());
		msgFrame.getChildren().add(MsgGrid());
		// add frames to main gird
		mainGrid.add(friendFrame,0,0,1,2);
		mainGrid.add(sp,1,0);
		mainGrid.add(msgFrame,1,1);

		/*mainGrid.setHgrow(friendFrame,Priority.ALWAYS);
		mainGrid.setHgrow(chatFrame,Priority.ALWAYS);
		mainGrid.setHgrow(msgFrame,Priority.ALWAYS);
		mainGrid.setHgrow(scrollBarFrame,Priority.ALWAYS);
		mainGrid.setVgrow(friendFrame,Priority.ALWAYS);
		mainGrid.setVgrow(chatFrame,Priority.ALWAYS);
		mainGrid.setVgrow(msgFrame,Priority.ALWAYS);
		mainGrid.setVgrow(scrollBarFrame,Priority.ALWAYS);*/
		//mainGrid.setMaxHeight(720);
		// config grid
		//mainGrid.setPrefSize(1920,1080);
		// create scene
		Scene scene = new Scene(mainGrid,this.width,this.height);
		return scene;
	}
	public GridPane FriendGrid(){
		GridPane root = new GridPane();
		VBox friendVB = new VBox();
		root.add(friendVB,0,0);
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
		Button sendBtn = new Button();
		// create msg handler
		MsgHandler msgHandler = new MsgHandler();
		sendBtn.setOnAction(msgHandler);
		// ---------
		msgEntry.prefWidthProperty().bind(msgFrame.widthProperty().multiply(0.90));
		msgEntry.prefHeightProperty().bind(msgFrame.heightProperty().multiply(1.0));
		sendBtn.prefWidthProperty().bind(msgFrame.widthProperty().multiply(0.10));
		sendBtn.prefHeightProperty().bind(msgFrame.heightProperty().multiply(1.0));
		root.add(msgEntry,0,0);
		root.add(sendBtn,1,0);
		return root;
	}
	// get current message in text box
	public void displayMsg(VBox msg){
		if(msgSent){chatFrame.setAlignment(Pos.BOTTOM_RIGHT);}
		else{chatFrame.setAlignment(Pos.BOTTOM_LEFT);}
		chatFrame.getChildren().add(msg);
	}

	@Override
	public void show() {
		this.stage.setScene(this.createWindow());
		this.stage.setMaxHeight(1080);
		this.stage.setMinHeight(250);
		this.stage.setMaxWidth(1920);
		this.stage.setMinWidth(500);
		this.stage.show();
	}

	@Override
	public void hide() {
		this.stage.hide();
	}
}
