//Created by Mitchell Hardie
package client.ui.main_window.chat_pane;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MessageTextBox extends Label {

	private Text msg;
	private VBox msgVBox = new VBox();

	public MessageTextBox(String message) {
		super(message);

		this.getStyleClass().add(".message");
		this.setFont(Font.font("Consolas",FontWeight.NORMAL,16));
		this.getStyleClass().add("message");
		this.setRotate(180);

		this.minHeight(Region.USE_PREF_SIZE);
		this.prefHeight(25.0);
		this.maxHeight(Region.USE_PREF_SIZE);

		this.setContextMenu(this.generateContextMenu());
	}

	private ContextMenu generateContextMenu() {
		ContextMenu tmpContextMenu = new ContextMenu();
		tmpContextMenu.getStyleClass().add("contextMenu");

		// menu items
		MenuItem copy = new MenuItem("copy text");
		MenuItem delete = new MenuItem("delete message");

		return tmpContextMenu;
	}


	public VBox getMsg() {
		return msgVBox;
	}
}
