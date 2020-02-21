package client.ui.main_window;


import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;


public class MainWindowMenuBar extends MenuBar {

    private EventHandler onSpam;

    public MainWindowMenuBar() {
        super();
        this.getMenus().add(FileMenu());
    }

    private Menu FileMenu() {
        Menu tmp = new Menu("File");
        MenuItem a = new MenuItem("exit...");
        a.setOnAction(event -> {if (this.onSpam != null) {this.onSpam.handle(event);}});
        tmp.getItems().add(a);
        return tmp;
    }

    public void setOnSpam(EventHandler onSpam) {
        this.onSpam = onSpam;
    }
}
