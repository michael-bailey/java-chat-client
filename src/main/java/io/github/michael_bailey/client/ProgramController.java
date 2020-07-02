package io.github.michael_bailey.client;

import io.github.michael_bailey.client.Controllers.ChatWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.net.URL;

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

    URL fxml_url = getClass().getClassLoader().getResource("layouts/ChatWindow/ChatWindow.fxml");
    ChatWindowController controller;

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

        var loader = new FXMLLoader(fxml_url);
        loader.load();
        controller = loader.getController();
    }
}
