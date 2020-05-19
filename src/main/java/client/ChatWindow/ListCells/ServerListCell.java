package client.ChatWindow.ListCells;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;


public class ServerListCell extends ListCell<ServerListCellModel> {

    URL fxmlURL = getClass().getClassLoader().getResource("layouts/ChatWindow/serverCell/ServerCell.fxml");

    // ui elements
    @FXML AnchorPane root;
    @FXML Label label;
    @FXML ImageView imgView;

    private final SimpleObjectProperty<ServerListCellModel> server = new SimpleObjectProperty<>();

    @Override
    protected void updateItem(ServerListCellModel item, boolean empty) {
        super.updateItem(item, empty);
        System.out.println("cell created");
        setText(null);
        setGraphic(null);

        if (!empty) {
            try {
                server.set(item);

                FXMLLoader loader = new FXMLLoader(fxmlURL);
                loader.setController(this);
                loader.load();

                this.label.setText(server.get().ServerName);

                setGraphic(root);
            } catch (IOException e) {
                e.printStackTrace();
                setText("Error");
            }
        } else {
            setText(null);
            setGraphic(null);
        }
    }
}
