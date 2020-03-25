//Created by Mitchell Hardie
package client.views.main_window.chat_pane;

import client.classes.Message;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MessageCell extends ListCell<Message> {

	private Label msg;

	public MessageCell() {
		this.msg = new Label();
		this.msg.setFont(Font.font("Consolas",FontWeight.NORMAL,16));
		this.msg.getStyleClass().add("message");

		this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		this.setPrefSize(Region.USE_COMPUTED_SIZE, 25.0);
		this.setMaxSize(Double.MAX_VALUE, Region.USE_PREF_SIZE);

		this.getChildren().add(this.msg);
	}

	@Override
	protected void updateItem(Message message, boolean b) {
		super.updateItem(message, b);
		if (!b) {
			msg.setText(message.getMessage());
		}
	}
}
