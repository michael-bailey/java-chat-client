package client.ui.main_window.chat_pane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Collections;

public class ChatPane extends AnchorPane {

    // static constants
    public static String Left = ".left";
    public static String Center = ".center";
    public static String Right = ".center";

    // size constants
    private int buttonWidth = 50;
    private int buttonHeight = 25;

    private Button sendButton;
    private Button AdditionalItemsButton;
    private Button photoButton;
    private TextField messageBox;
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

        // creating the message view.
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

        AdditionalItemsButton = new Button("+");
        AdditionalItemsButton.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        AdditionalItemsButton.setPrefSize(this.buttonHeight, this.buttonHeight);
        AdditionalItemsButton.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        AdditionalItemsButton.getStyleClass().add("emojiButton");

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
        this.getChildren().add(AdditionalItemsButton);
        //this.getChildren().add(photoButton);

        // setting constraints to items
        // message view
        setTopAnchor(messageView, 0.0);
        setLeftAnchor(messageView, 0.0);
        setRightAnchor(messageView, 0.0);
        setBottomAnchor(messageView, 33.0);

        // message box
        setBottomAnchor(messageBox, 4.0);
        setLeftAnchor(messageBox, 33.0);
        setRightAnchor(messageBox, 58.0);

        // send button
        setRightAnchor(sendButton, 4.0);
        setBottomAnchor(sendButton, 4.0);

        //emoji button
        setLeftAnchor(AdditionalItemsButton, 4.0);
        setBottomAnchor(AdditionalItemsButton, 4.0);

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

    /**
     * this creates a
     * @return returns a new context menu
     */
    private ContextMenu getContextMenu() {
        ContextMenu tmpContextMenu = new ContextMenu();
        tmpContextMenu.getStyleClass().add("contextMenu");

        MenuItem preset1 = new MenuItem("load preset 1");
        preset1.getStyleClass().add("contextMenuItem");
        preset1.setOnAction(event -> {
            ArrayList<MessageTextBox> tmpList = new ArrayList();
            tmpList.add(new MessageTextBox("hi there"));
            tmpList.add(new MessageTextBox("hi "));
            tmpList.add(new MessageTextBox("hi there"));
            tmpList.add(new MessageTextBox(" there"));
            tmpList.add(new MessageTextBox("hi there"));
            tmpList.add(new MessageTextBox("hi ghfgh"));
            tmpList.add(new MessageTextBox("hi there"));
            tmpList.add(new MessageTextBox("hi ghfghf"));
            tmpList.add(new MessageTextBox("hi there"));
            this.loadMessages(tmpList);
        });

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

    /**
     * this replaces the contents of the chat pane with a new set of messages
     * @param messages an ArrayList that contains the messagesto be displayed on the messageview
     */
    public void loadMessages(ArrayList<MessageTextBox> messages) {
        ArrayList tmpArray = (ArrayList) messages.clone();
        Collections.reverse(tmpArray);
        this.messageView.setItems(FXCollections.observableArrayList(tmpArray));
    }

    public String getMessageText() {
        return this.messageBox.getText();
    }


    /**
     * this will set the event handler for when a message is to be sent
     * @param handler An Event handler that will be called when send or enter is pressed/typed
     */
    public void setOnMessageHandler(EventHandler handler) {
        this.onSendMessage = handler;
    }

    public void clearAll() {
        this.messageView.getItems().clear();
        this.messageBox.clear();
    }
}