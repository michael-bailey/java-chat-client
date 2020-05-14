package client.LoginWindow;

import client.ChatWindow.ChatWindowController;
import client.ChatWindow.ChatWindowModel;
import client.classes.Server;
import client.exceptions.PasswordException;
import client.managers.DataManager;
//import client.models.ApplicationModel;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class LoginWindowModel {

    private final URL MainWindowUrl = getClass().getClassLoader().getResource("layouts/MainWindow/MainWindow.fxml");

    private DataManager dataManager = new DataManager();
    private ChatWindowController chatWindowController;

    private SimpleStringProperty usernameString = new SimpleStringProperty(new String());
    private SimpleStringProperty passwordString = new SimpleStringProperty(new String());
    private SimpleListProperty<String> usernameBoxCSS = new SimpleListProperty(FXCollections.observableList(new ArrayList<String>()));
    private SimpleListProperty<String> passwordBoxCSS = new SimpleListProperty(FXCollections.observableList(new ArrayList<String>()));
    private SimpleBooleanProperty usernameCorrect = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty passwordCorrect = new SimpleBooleanProperty(false);

    private ChangeListener<String> usernameListener = (observableValue, oldValue, newValue) -> {
        ObservableList<String> styleClass = this.usernameBoxCSS;
        styleClass.clear();
        styleClass.add("TextBox");

        if (!(newValue.length() > 0)) {
            styleClass.add("TextBoxEmpty");
            this.usernameCorrect.setValue(false);
            return;
        }

        if (!newValue.matches("[^a-zA-Z0-9]*")) {
            styleClass.add("TextBoxCorrect");
            this.usernameCorrect.setValue(true);
        } else {
            styleClass.add("TextBoxWrong");
            this.usernameCorrect.set(false);
        }
    };
    private ChangeListener<String> passwordListener = (observableValue, oldValue, newValue) -> {
        ObservableList<String> styleClass = this.passwordBoxCSS;
        styleClass.clear();
        styleClass.add("TextBox");

        if (!(newValue.length() > 0)) {
            styleClass.add("TextBoxEmpty");
            this.passwordCorrect.setValue(false);
            return;
        }

        if (newValue.matches("[a-zA-Z]*")) {
            styleClass.add("TextBoxWrong");
            this.passwordCorrect.setValue(false);
        } else {
            styleClass.add("TextBoxCorrect");
            this.passwordCorrect.setValue(true);
        }
    };

    private TranslateTransition usernameAnimation = new TranslateTransition();
    private TranslateTransition passwordAnimation = new TranslateTransition();


    public LoginWindowModel() {
        System.out.println(this);
        setupAnimations();

        this.passwordString.addListener(passwordListener);
        this.usernameString.addListener(usernameListener);
    }

    private void setupAnimations() {
        // setting up animations
        usernameAnimation.byYProperty();
        usernameAnimation.setFromX(8.0);
        usernameAnimation.setToX(0);
        usernameAnimation.setAutoReverse(true);
        usernameAnimation.setDuration(Duration.millis(75));
        usernameAnimation.setCycleCount(5);

        passwordAnimation.byYProperty();
        passwordAnimation.setFromX(8.0);
        passwordAnimation.setToX(0);
        passwordAnimation.setAutoReverse(true);
        passwordAnimation.setDuration(Duration.millis(75));
        passwordAnimation.setCycleCount(5);
    }

    public ChatWindowModel login() {
        if (dataManager.isUnlocked()) {
            return null;
        }

        if (!DataManager.checkFileNameValid(this.usernameString.get()) || !DataManager.checkPasswordValid(this.passwordString.get())) {
            return null;
        }

        try {
            dataManager.unlock(usernameString.get(), passwordString.get());

            // if successful then load the main window
            if (dataManager.isUnlocked()) {
                return null;
            }

            FXMLLoader loader = new FXMLLoader(this.MainWindowUrl);

            loader.setControllerFactory(c -> {

                var serverStore = (HashMap<UUID, Server>) dataManager.getObject("Servers");

                // add stores to the model
                var modelBuilder = new ChatWindowModel.ChatWindowModelBuilder();
                modelBuilder.serverStore(serverStore)
                            .logoutCallback(event -> {
                                System.out.println("logging out...");
                                this.dataManager.lock();
                            });

                return new ChatWindowController(modelBuilder.build());
            });

            loader.load();
            ChatWindowController newController = loader.getController();

            // if the file isn't found then
        } catch (FileNotFoundException e) {
            System.out.println("file not found");

            if (!dataManager.isUnlocked()) {
                return null;
            }

            dataManager.createNew(this.usernameString.get(), this.passwordString.get());

            if (!dataManager.isUnlocked()) {
                return null;
            }

            try {
                FXMLLoader loader = new FXMLLoader(this.MainWindowUrl);
                loader.setControllerFactory(c -> {

                    var serverStore = new HashMap<UUID, Server>();
                    dataManager.addObject("Servers", serverStore);

                    // add store models to the model.
                    var modelBuilder = new ChatWindowModel.ChatWindowModelBuilder();
                    modelBuilder.serverStore(serverStore)
                            .logoutCallback(event -> {
                                System.out.println("logging out...");
                                this.dataManager.lock();
                            });

                    return new ChatWindowController(modelBuilder.build());
                });
                loader.load();
                chatWindowController = loader.getController();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void logout() {
        return;
    }

    public SimpleStringProperty usernameStringProperty() {
        return usernameString;
    }

    public SimpleStringProperty passwordStringProperty() {
        return passwordString;
    }
}
