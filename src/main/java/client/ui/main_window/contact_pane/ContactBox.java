package client.ui.main_window.contact_pane;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class ContactBox extends Node {

    @FXML Label ContactName;
    @FXML ImageView UserImage;
    @FXML Label ContactUserID;
    AnchorPane root;

    URL fxmlUrl = getClass().getClassLoader().getResource("layouts/MainWindow/ContactPane/ContactBox.fxml");

    EventHandler onClick;

    public ContactBox() throws IOException {
        FXMLLoader tmpLoader = new FXMLLoader(fxmlUrl);
        tmpLoader.setController(this);
        this.root = tmpLoader.load();

        this.setOnMouseClicked(event -> {
            this.onClick.handle(event);
        });
    }

    public EventHandler getOnClick() {
        return onClick;
    }

    public void setOnClick(EventHandler onClick) {
        this.onClick = onClick;
    }
}
