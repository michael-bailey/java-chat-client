package client;

import client.controllers.login_display.LoginWindowController;
import client.managers.DataManager;
import client.managers.NetworkManager;
import client.classes.Account;
import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.views.main_window.MainWindow;
import client.views.other.AddContactDialogue;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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
        System.out.println(getClass().getClassLoader().getResource("layouts/LoginWindow/LoginWindow.fxml"));
        FXMLLoader tmpFxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/LoginWindow/LoginWindow.fxml"));
        tmpFxmlLoader.load();
        this.loginWindow = tmpFxmlLoader.getController();


        // show the login window
        this.loginWindow.showView();
    }

}
