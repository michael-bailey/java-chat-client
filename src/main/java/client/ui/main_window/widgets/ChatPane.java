package client.ui.main_window.widgets;

import client.classes.Message;
import client.ui.main_window.widgets.MessageTextBox;

import com.sun.javafx.beans.event.AbstractNotifyListener;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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

    // event handlers
    private EventHandler onSendMessage;

    /**
     * Constructor
     * this creates the chat pane fills it with the controls applies styling
     * and does some questionable stuff to get messages into the right position
     * @since 1.0
     */
    public ChatPane() {

        this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.setPrefSize(20, 20);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // creating teh message view.
        messageView = new ListView<>();
        messageView.setMinHeight(Region.USE_COMPUTED_SIZE);
        messageView.setRotate(180);
        messageView.getStyleClass().add("messageView");

        // creating objects for the tool row
        sendButton = new Button("send");
        sendButton.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        sendButton.setPrefSize(this.buttonWidth, this.buttonHeight);
        sendButton.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        sendButton.getStyleClass().add("sendButton");

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
        messageBox.setMinWidth(200);
        messageBox.getStyleClass().add("messageBox");


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

        // send button
        setRightAnchor(sendButton, 4.0);
        setBottomAnchor(sendButton, 4.0);

        // set event handler
        this.sendButton.setOnAction((event) -> {
            if (this.onSendMessage != null) {
                this.onSendMessage.handle(event);
                this.messageBox.clear();
            }
            event.consume();
        });

        this.messageBox.setOnKeyTyped(event -> {
            if (this.onSendMessage != null) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    this.onSendMessage.handle(event);
                    this.messageBox.clear();
                }
            }
        });

        this.messageView.setContextMenu(this.getContextMenu());
    }

    private ContextMenu getContextMenu() {
        ContextMenu tmpContextMenu = new ContextMenu();
        tmpContextMenu.getStyleClass().add("contextMenu");

        MenuItem preset1 = new MenuItem("load preset 1");
        preset1.getStyleClass().add("contextMenuItem");

        tmpContextMenu.getItems().add(preset1);
        return tmpContextMenu;
    }

    /**
     * this appends a message to the top (bottom) of the list by reversing it many times
     * @param text a string showing what test should be put into the list
     * @param alignment where the text should be positioned
     */
    public void appendMessage(String text, String alignment) {
        ObservableList tmplist = messageView.getItems();
        Collections.reverse(tmplist);
        MessageTextBox tmpMessage = new MessageTextBox(text);
        tmpMessage.getStyleClass().add(alignment);
        tmplist.add(tmpMessage);
        Collections.reverse(tmplist);
    }

    public void loadMessages(ArrayList<Object> messages) {
        ArrayList tmpArray = (ArrayList) messages.clone();
        Collections.reverse(tmpArray);
        this.messageView.setItems((ObservableList<MessageTextBox>) tmpArray);

    }

    public void setOnMessageHandler(EventHandler handler) {
        this.onSendMessage = handler;
    }

}
