package io.github.michael_bailey.client;

import io.github.michael_bailey.client.LoginWindow.LoginWindowController;
import io.github.michael_bailey.client.LoginWindow.LoginWindowModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

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


    /**
     * this is called by main
     * @param args arguments passed from the command line
     * @throws Exception any exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("os.name"));
        launch(args);
    }

    /**
     * this is called after the application class has finished.
     * @param stage the stage given to the function by application.
     * @throws Exception any thing that could go wrong.
     */
    public void start(Stage stage) throws Exception {
        System.out.println(this);

        Platform.setImplicitExit(false);

        FXMLLoader tmpFxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/LoginWindow/LoginWindow.fxml"));
        tmpFxmlLoader.load();
        this.loginWindow = tmpFxmlLoader.getController();
        this.loginWindow.setModel(new LoginWindowModel());

        // show the login window
        this.loginWindow.showView();
    }
}
