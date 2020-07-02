package io.github.michael_bailey.client.Controllers;

import io.github.michael_bailey.client.models.ChatWindowModel;
import io.github.michael_bailey.client.models.Contact;
import io.github.michael_bailey.client.models.Server;
import io.github.michael_bailey.client.models.ServerChatModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatWindowController implements Initializable {

    @FXML public ListView messageListView;
    @FXML public TextField messageBox;
    @FXML public Button sendButton;

    @FXML public ListView contactListView;
    @FXML public TextField contactSearchBox;

    @FXML public Button addServerButton;
    @FXML public ListView serverListView;

    @FXML public Stage stage;

    ChatWindowModel model;
    ServerChatModel serverModel;
    // ContactChatModel contactModel

    // view model
    SimpleListProperty<Server> serversProperty = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
    SimpleListProperty<Contact> contactsProperty = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    public ChatWindowController() {
         model = new ChatWindowModel();
         // serverModel = new ServerChatModel(model);
         /*

         serverModel.serverList().addListener((ListChangeListener<? super Server>) c -> {
             if (c.wasRemoved()) {
                 serversProperty.removeAll(c.getRemoved());
             }

             if (c.wasAdded()) {
                 serversProperty.addAll(c.getAddedSubList());
             }
         });
         */
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("this = " + this);
        System.out.println("location = " + location);
        System.out.println("resources = " + resources);

        serverListView.itemsProperty().bind(this.serversProperty);
        contactListView.itemsProperty().bind(this.contactsProperty);

        // set factories
        this.serverListView.setCellFactory(param -> {
            URL FxmlURL = getClass().getClassLoader().getResource("layouts/ChatWindow/serverCell/ServerCell.fxml");

            try {
                var loader = new FXMLLoader(FxmlURL);
                loader.load();
                return loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return param;
        });

        this.contactListView.setCellFactory(param -> {
            URL FxmlURL = getClass().getClassLoader().getResource("layouts/ChatWindow/ContactCell/ContactCell.fxml");

            try {
                var loader = new FXMLLoader(FxmlURL);
                loader.load();
                return loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return param;
        });

        /*
        model.serverList().addListener((ListChangeListener<? super Server>) c -> {
            this.serversProperty.set((ObservableList<Server>) c.getList());
        });

        model.contactList().addListener((ListChangeListener<? super Contact>) c -> {
            this.contactsProperty.set((ObservableList<Contact>) c.getList());
        });

         */


        stage.show();
    }

    @FXML
    public void windowClose(WindowEvent windowEvent) {
        // this.model.disconnect();
        Platform.exit();
    }

    @FXML
    public void addServer(ActionEvent actionEvent) {
    }

    @FXML
    public void sendMessage(ActionEvent actionEvent) {
    }

    @FXML
    public void onServerSelect(MouseEvent mouseEvent) {
        //model.connect((Server) serverListView.getSelectionModel().getSelectedItem());
    }
}
