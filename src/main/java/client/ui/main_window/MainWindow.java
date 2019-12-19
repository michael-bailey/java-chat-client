//Created by Mitchell Hardie
package client.ui.main_window;

import client.ui.interfaces.Window;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow implements Window {
	private Stage stage;

	public MainWindow(){
		this.stage = new Stage();
	}
	
	public Scene createWindow(){
		this.stage.setTitle("Application");
		// init main grid
		GridPane mainGrid = new GridPane();
		// create vertical boxes for pannels
		VBox friendFrame = new VBox();
		friendFrame.prefHeightProperty().bind(stage.heightProperty().multiply(1));
		friendFrame.prefWidthProperty().bind(stage.widthProperty().multiply(0.10));
		
		VBox chatFrame = new VBox();
		chatFrame.prefHeightProperty().bind(stage.heightProperty().multiply(1));
		chatFrame.prefWidthProperty().bind(stage.widthProperty().multiply(0.90));
		
		VBox msgFrame = new VBox();
		//msgFrame.prefHeightProperty().bind(stage.widthProperty().multiply(0.80));
		msgFrame.prefWidthProperty().bind(stage.widthProperty().multiply(0.90));
		
		VBox scrollBarFrame = new VBox();
		scrollBarFrame.prefHeightProperty().bind(stage.heightProperty().multiply(1));
		//scrollBarFrame.prefWidthProperty().bind(stage.widthProperty().multiply(0.05));
		// create frames
		friendFrame.getChildren().add(FriendGrid());
		chatFrame.getChildren().add(ChatGrid());
		msgFrame.getChildren().add(MsgGrid());
		scrollBarFrame.getChildren().add(ScrollBarGrid());
		// add frames to main gird
		mainGrid.add(friendFrame,0,0,1,2);
		mainGrid.add(chatFrame,1,0);
		mainGrid.add(msgFrame,1,1,2,1);
		mainGrid.add(scrollBarFrame,2,0);
		// config grid
		//mainGrid.setPrefSize(1920,1080);
		// create scene
		Scene scene = new Scene(mainGrid,1920,1080);
		return scene;
	}
	public GridPane FriendGrid(){
		GridPane root = new GridPane();
		VBox friendVB = new VBox();
		root.add(friendVB,0,0);
		return root;
	}
	public GridPane ChatGrid(){
		GridPane root = new GridPane();
		return root;
	}
	public GridPane MsgGrid(){
		GridPane root = new GridPane();
		TextField msgEntry = new TextField();
		Button sendBtn = new Button();
		msgEntry.prefWidthProperty().bind(root.widthProperty().multiply(0.90));
		sendBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.10));
		root.add(msgEntry,0,0);
		root.add(sendBtn,1,0);
		return root;
	}
	public GridPane ScrollBarGrid(){
		GridPane root = new GridPane();
		ScrollBar sc = new ScrollBar();
		sc.setMin(0);
		sc.setMax(100);
		sc.setValue(100);
		sc.setOrientation(Orientation.VERTICAL);
		sc.setPrefHeight(1080);
		//sc.prefHeightProperty().bind(root.heightProperty().multiply(1));
		root.add(sc,0,0);
		return root;
	}

	@Override
	public void show() {
		this.stage.setScene(this.createWindow());
		this.stage.show();
	}

	@Override
	public void hide() {
		this.stage.hide();
	}
}
