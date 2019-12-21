//Created by Mitchell Hardie
package client.ui.main_window;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class Message{
	private Text msg;
	private VBox msgVBox = new VBox();
	private final double width,height;
	public Message(String msg){
		this.msg = new Text(msg);
		this.msg.setFont(Font.font("Consolas",FontWeight.NORMAL,18));
		this.msg.setFill(Color.WHITE);
		//-------------------------
		this.width = this.msg.getLayoutBounds().getWidth();
		msgVBox.setMaxWidth(width+10);
		msgVBox.setAlignment(Pos.CENTER);
		this.height = this.msg.getLayoutBounds().getHeight();
		msgVBox.setMaxHeight(height+10);
		//-------------------------
		msgVBox.getChildren().add(this.msg);
		msgVBox.setId("sentMsg");
		msgVBox.getStylesheets().add("cssMsg.css");
	}
	public VBox getMsg(){return msgVBox;}
	public double getMsgHeight(){return msgVBox.getMaxHeight();}
}
