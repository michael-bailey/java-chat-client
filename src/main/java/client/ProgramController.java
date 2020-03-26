package client;

import client.classes.Account;
import client.controllers.login_display.LoginWindowController;
import client.controllers.main_window.MainWindowController;
import client.models.ApplicationModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import java.awt.Desktop;

/**
 * Program Controller.
 *
 * This class manages all data and resources for the program.
 * it has all interactions between windows and data models defined in this class.
 *
 * it also contatins all event handlers that
 *
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */
public class ProgramController extends Application {

    // this section defines the windows that are in use

    private LoginWindowController loginWindow;

    private BooleanProperty dataManagerLocked;

    private Account account;
    private MainWindowController mainWindow;

    /**
     * this is called by main
     * @param args arguments passed from the command line
     * @throws Exception any exception
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /**
     * this is called after the application class has finished.
     * @param stage the stage given to the function by application.
     * @throws Exception any thing that could go wrong.
     */
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);

        FXMLLoader tmpFxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/MainMenu.fxml"));
        MenuBar tmpmenu = tmpFxmlLoader.load();
        stage.setScene(new Scene(tmpmenu));

        tmpFxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/LoginWindow/LoginWindow.fxml"));
        tmpFxmlLoader.load();
        this.loginWindow = tmpFxmlLoader.getController();

        tmpFxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/MainWindow/MainWindow.fxml"));
        tmpFxmlLoader.load();
        this.mainWindow = tmpFxmlLoader.getController();



        // show the login window
        this.loginWindow.showView();
    }

    public void quitApplication() {
        ApplicationModel.getInstance().save();
        Platform.exit();
    }

}
