//Created by Mitchell Hardie
package client.ui.main_window.widgets;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class MessageTextBox extends Label {

	private Text msg;
	private VBox msgVBox = new VBox();

	public MessageTextBox(String msg) {
		super(msg);
		this.getStyleClass().add(".message");
		this.setFont(Font.font("Consolas",FontWeight.NORMAL,16));
		this.getStyleClass().add("message");
		this.setRotate(180);

		this.minHeight(Region.USE_PREF_SIZE);
		this.prefHeight(25.0);
		this.maxHeight(Region.USE_PREF_SIZE);

	}

	public VBox getMsg() {
		return msgVBox;
	}

	public double getMsgHeight() {
		return msgVBox.getMaxHeight();
	}
}
