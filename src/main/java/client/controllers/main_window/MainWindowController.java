package client.controllers.main_window;

import client.models.ApplicationModel;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private Stage stage;

    @FXML
    private ListView<?> messageListView;

    @FXML
    private TextField messageBox;

    @FXML
    private Button sendButton;

    private ApplicationModel model = ApplicationModel.getInstance();

    ChangeListener loggedInListener = (observableValue, bool, t1) -> {
        if (!(Boolean) t1) {
            this.stage.hide();
        } else {
            this.stage.show();
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.model.loginStatusProperty().addListener(loggedInListener);
    }

    @FXML
    void sendMessage(ActionEvent event) {

    }

    @FXML
    void windowClose() {
        ApplicationModel.getInstance().logout();
    }
}
