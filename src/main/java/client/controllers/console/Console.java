package client.controllers.console;

import client.models.Console.ConsoleModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.BatchUpdateException;
import java.util.ResourceBundle;

public class Console implements Initializable {

    @FXML Stage stage;
    @FXML TextArea output;
    @FXML TextField commandBox;
    @FXML Button enterButton;

    ConsoleModel model = new ConsoleModel();

    public Console() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.model.outputStringProperty().bindBidirectional(this.output.textProperty());
        this.model.commandStringProperty().bindBidirectional(this.commandBox.textProperty());
    }

    public void show() {
        this.stage.show();
    }

    @FXML void onEnter (ActionEvent event) {
        this.model.processCommand();

    }

}
