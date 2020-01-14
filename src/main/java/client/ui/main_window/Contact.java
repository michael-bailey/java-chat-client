package client.ui.main_window;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.Serializable;

class Contact implements Serializable {
	private String name;
	private int id;
	private VBox contactVB = new VBox();
	public Contact(String name,int id){
		this.name=name;
		this.id=id;
		Image img = new Image(getClass().getResourceAsStream(""));
		Button contactBtn = new Button();
		contactBtn.setGraphic(new ImageView(img));
		contactBtn.setId("BtnDesign");
		contactBtn.getStylesheets().add("contactCss.css");
		contactVB.getChildren().add(contactBtn);
	}
	public VBox getContactVB(){return contactVB;}
	public String getName(){return name;}
}
