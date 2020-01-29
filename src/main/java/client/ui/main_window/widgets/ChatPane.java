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

public class ChatPane extends GridPane {

    // static constants
    public static String Left = ".left";
    public static String Center = ".center";
    public static String Right = ".center";

    private Button sendBtn;
    private EventHandler<ActionEvent> SendButtonEventHandler;

    private Button emojiBtn;
    private EventHandler<ActionEvent> EmojiButtonEventHandler;

    private Button photoBtn;
    private EventHandler<ActionEvent> PhotoButtonEventHandler;

    private TextField msgEntry;
    private EventHandler<KeyEvent> enterPressed;


    private ListView<MessageTextBox> messageView;

    public ChatPane() {

        GridPane root = new GridPane();
        this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.setPrefSize(20, 20);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


        // creating teh message view.
        messageView = new ListView<>();
        messageView.setMinHeight(Region.USE_COMPUTED_SIZE);
        messageView.setRotate(180);

        // creating objects for the tool row
        sendBtn = new Button("send");
        sendBtn.setMaxWidth(Double.MAX_VALUE);

        emojiBtn = new Button("emoji");
        emojiBtn.setMaxWidth(Double.MAX_VALUE);

        photoBtn = new Button("photo");
        photoBtn.setMaxWidth(Double.MAX_VALUE);

        // creating the message entry box
        msgEntry = new TextField();
        msgEntry.setMaxWidth(Double.MAX_VALUE);
        msgEntry.setId("msgEntryBox");
        msgEntry.getStylesheets().add("css/mainWindow.css");
        msgEntry.setMinWidth(200);

        // defining constraints.
        // defining column grid
        ColumnConstraints column0 = new ColumnConstraints();
        column0.hgrowProperty().set(Priority.NEVER);
        column0.setMinWidth(Region.USE_PREF_SIZE);
        column0.setMaxWidth(Region.USE_PREF_SIZE);
        column0.setPrefWidth(75);

        // defining column grid
        ColumnConstraints column1 = new ColumnConstraints();
        column1.hgrowProperty().set(Priority.ALWAYS);
        column1.setMinWidth(Region.USE_PREF_SIZE);
        column1.setMaxWidth(Double.MAX_VALUE);
        column1.setPrefWidth(200);

        // defining column grid
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(Region.USE_PREF_SIZE);
        column2.setMinWidth(Region.USE_COMPUTED_SIZE);
        column2.setMaxWidth(Region.USE_PREF_SIZE);
        column2.setPrefWidth(75);

        // defining column grid
        ColumnConstraints column3 = new ColumnConstraints();
        column3.hgrowProperty().set(Priority.NEVER);
        column3.setMinWidth(Region.USE_PREF_SIZE);
        column3.setMaxWidth(Region.USE_PREF_SIZE);
        column3.setPrefWidth(75);

        // defining row grid
        RowConstraints row0 = new RowConstraints();
        row0.vgrowProperty().set(Priority.ALWAYS);
        row0.setMinHeight(275);
        row0.setMaxHeight(Double.MAX_VALUE);

        this.getColumnConstraints().add(column0);
        this.getColumnConstraints().add(column1);
        this.getColumnConstraints().add(column2);
        this.getColumnConstraints().add(column3);
        this.getRowConstraints().add(row0);


        this.add(this.messageView,0,0, GridPane.REMAINING, 1);
        this.add(this.photoBtn,0,1);
        this.add(this.msgEntry,1,1);
        this.add(this.emojiBtn,2,1);
        this.add(this.sendBtn,3,1);

        sendBtn.setOnAction(new MsgHandler());
        msgEntry.setOnKeyPressed(new EnterPressed());
    }

    public void addMessage(String text, String alignment) {
        if (!msgEntry.getText().isEmpty()) {
            ObservableList tmplist = messageView.getItems();

            Collections.reverse(tmplist);


            MessageTextBox tmpMessage = new MessageTextBox(text);
            tmpMessage.getStyleClass().add(alignment);


            tmplist.add(new MessageTextBox(msgEntry.getText()));


            Collections.reverse(tmplist);
            msgEntry.clear();
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
