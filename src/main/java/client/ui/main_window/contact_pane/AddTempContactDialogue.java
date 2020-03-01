package client.ui.main_window.contact_pane;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * AddTempContactDialogue
 */
public class AddTempContactDialogue {

    private Stage window;

    private URL windowUrl = this.getClass().getClassLoader()
            .getResource("layouts.MainWindow/ContactPane/AddTempContactDialogue.fxml");

    public AddTempContactDialogue() {
        try {
            FXMLLoader tmpLoader = new FXMLLoader(this.windowUrl);
            tmpLoader.setController(this);
            this.window = tmpLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}