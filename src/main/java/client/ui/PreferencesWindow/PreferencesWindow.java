package client.ui.PreferencesWindow;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import client.ui.interfaces.Window;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

enum pane {
    account,
    other,
}

public class PreferencesWindow implements Window {

    Stage stage;

    public PreferencesWindow() {
        System.out.println(this);
        this.stage = new Stage();

        this.stage.setScene(new Scene((VBox) this.sideMenu()));
    }

    public Node sideMenu() {
        VBox tabs = new VBox();
        Button userTab = new Button("User preferences");
        tabs.getChildren().add(userTab);

        return tabs;
    }

    @Override
    public void show() {
        this.stage.show();
    }

    @Override
    public void hide() {
        this.stage.hide();
    }
}
