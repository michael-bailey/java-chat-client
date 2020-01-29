package client.ui.main_window;


import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MainWindowMenuBar extends MenuBar {

    public MainWindowMenuBar() {
        super();
        this.getMenus().add(FileMenu());
    }

    private Menu FileMenu() {
        Menu tmp = new Menu("File");
        tmp.getItems().add(new MenuItem("hellow world"));
        return tmp;
    }
}
