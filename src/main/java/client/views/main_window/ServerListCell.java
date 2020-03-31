package client.views.main_window;

import client.classes.Server;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;


public class ServerListCell extends ListCell<Server> {

    URL fxmlURL = getClass().getClassLoader().getResource("layouts/MainWindow/serverCell/ServerCell.fxml");

    // ui elements
    @FXML AnchorPane root;
    @FXML Label label;
    @FXML ImageView imgView;

    @Override
    protected void updateItem(Server item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        server.set(item);

        if (!empty) {
            try {
                FXMLLoader loader = new FXMLLoader(fxmlURL);
                loader.setController(this);
                loader.load();

                this.label.setText(server.get().getServerName());

                setGraphic(root);
            } catch (IOException e) {
                e.printStackTrace();
                setText("");
            }
        } else {
            setText("?");
        }
    }

    private SimpleObjectProperty<Server> server = new SimpleObjectProperty<>();

}
