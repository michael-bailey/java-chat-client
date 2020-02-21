//Created by Mitchell Hardie
package client.ui.main_window.chat_pane;

import client.classes.Message;
import client.enums.MessageAlignment;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MessageTextBox extends HBox {

	private Label msg;

	public MessageTextBox(Message message) {
		super();

		if (message.isReceived()) {
			this.setAlignment(Pos.CENTER_RIGHT);
		} else {
			this.setAlignment(Pos.CENTER_LEFT);
		}

		this.msg = new Label(message.getMessage());
		this.msg.setFont(Font.font("Consolas",FontWeight.NORMAL,16));

		this.msg.getStyleClass().add("message");
		this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		this.setPrefSize(Region.USE_COMPUTED_SIZE, 25.0);
		this.setMaxSize(Double.MAX_VALUE, Region.USE_PREF_SIZE);

		this.msg.setContextMenu(this.generateContextMenu());

		this.getChildren().add(this.msg);
	}

	private ContextMenu generateContextMenu() {
		ContextMenu tmpContextMenu = new ContextMenu();
		tmpContextMenu.getStyleClass().add("contextMenu");

		// menu items
		MenuItem copy = new MenuItem("copy text");
		MenuItem delete = new MenuItem("delete message");

		return tmpContextMenu;
	}
}
