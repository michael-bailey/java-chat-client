package client.ui.main_window.widgets;

import client.classes.Message;
import client.ui.main_window.widgets.MessageTextBox;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ChatPane extends AnchorPane {

    // static constants
    public static String Left = ".left";
    public static String Center = ".center";
    public static String Right = ".center";

    // size constants
    private int buttonWidth = 50;
    private int buttonHeight = 25;

    private Button sendButton;
    private EventHandler<ActionEvent> SendButtonEventHandler;

    private Button emojiButton;
    private EventHandler<ActionEvent> EmojiButtonEventHandler;

    private Button photoButton;
    private EventHandler<ActionEvent> PhotoButtonEventHandler;

    private TextField messageBox;
    private EventHandler<KeyEvent> enterPressed;


    private ListView<MessageTextBox> messageView;

    public ChatPane() {

        this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.setPrefSize(20, 20);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // creating teh message view.
        messageView = new ListView<>();
        messageView.setMinHeight(Region.USE_COMPUTED_SIZE);
        messageView.setRotate(180);

        // creating objects for the tool row
        sendButton = new Button("send");
        sendButton.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        sendButton.setPrefSize(this.buttonWidth, this.buttonHeight);
        sendButton.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        emojiButton = new Button("emoji");
        sendButton.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        sendButton.setPrefSize(this.buttonWidth, this.buttonHeight);
        sendButton.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        photoButton = new Button("photo");
        sendButton.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        sendButton.setPrefSize(this.buttonWidth, this.buttonHeight);
        sendButton.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        // creating the message entry box
        messageBox = new TextField();
        messageBox.setMaxWidth(Double.MAX_VALUE);
        messageBox.getStyleClass().add(".messageBox");
        messageBox.setMinWidth(200);

        // adding to the anchor pane
        this.getChildren().add(messageView);
        this.getChildren().add(messageBox);
        this.getChildren().add(sendButton);
        //this.getChildren().add(emojiButton);
        //this.getChildren().add(photoButton);

        // setting constraints to items
        // message view
        setTopAnchor(messageView, 0.0);
        setLeftAnchor(messageView, 0.0);
        setRightAnchor(messageView, 0.0);
        setBottomAnchor(messageView, 33.0);

        // message box
        setBottomAnchor(messageBox, 4.0);
        setLeftAnchor(messageBox, 4.0);
        setRightAnchor(messageBox, 58.0);

        setRightAnchor(sendButton, 4.0);
        setBottomAnchor(sendButton, 4.0);
        
        // setting action handlers
        sendButton.setOnAction(new MsgHandler());
        messageBox.setOnKeyPressed(new EnterPressed());
    }

    public void addMessage(String text, String alignment) {
        if (!messageBox.getText().isEmpty()) {
            ObservableList tmplist = messageView.getItems();

            Collections.reverse(tmplist);


            MessageTextBox tmpMessage = new MessageTextBox(text);
            tmpMessage.getStyleClass().add(alignment);


            tmplist.add(new MessageTextBox(messageBox.getText()));


            Collections.reverse(tmplist);
            messageBox.clear();
        }
    }

    private class EnterPressed implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                addMessage("hello", ChatPane.Left);
            }
        }
    }

    private class MsgHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            addMessage("hello", ChatPane.Left);
        }
    }
}
