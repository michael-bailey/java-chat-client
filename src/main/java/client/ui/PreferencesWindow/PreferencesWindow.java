package client.ui.PreferencesWindow;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PreferencesWindow extends Stage {

    public PreferencesWindow() {
        Stage preferencesStage = new Stage();
        // defining the side tab menu used for different groups of preferences
        VBox tabs = new VBox();
        Button userTab = new Button("User preferences");
        tabs.getChildren().add(userTab);

        Scene sideBar = new Scene(tabs);
        preferencesStage.setScene(sideBar);
        preferencesStage.show();
    }



}
