package client.views.main_window.chat_pane;

import client.classes.Message;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

public class ChatPane extends AnchorPane {

    // size constants
    private int buttonWidth = 50;
    private int buttonHeight = 25;

    private Button sendButton;
    private Button AdditionalItemsButton;
    private TextField messageBox;
    private ListView<Message> messageView;

    private int i = 0;

    // event handlers
    private EventHandler onSendMessage;

    // private EventHandler scrollEvent = event -> {System.out.println("scrolling!!!" + i++);};

    /**
     * Constructor
     * this creates the chat pane fills it with the controls applies styling
     * and does some questionable stuff to get messages into the right position
     * @since 1.0
     */
    public ChatPane() {
        System.out.println(this);

        this.getStylesheets().add("css/MainWindow/ChatPane/ChatPane.css");
        this.getStyleClass().add("pane");

        this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.setPrefSize(20, 20);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // creating the message view.
        messageView = new ListView<>();
        messageView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> messageListView) {
                return new MessageCell();
            }
        });


        messageView.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        messageView.setPrefSize(20, 20);
        messageView.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        messageView.setRotate(0);
        messageView.getStyleClass().add("messageView");
        // messageView.setOnScroll(this.scrollEvent);

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
        AdditionalItemsButton.getStyleClass().add("drawerButton");

        // creating the message entry box
        messageBox = new TextField();
        messageBox.setMaxWidth(Double.MAX_VALUE);
        messageBox.setMinWidth(25);
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

        this.messageBox.setOnAction(event -> {
            if (this.onSendMessage != null) {
                this.onSendMessage.handle(event);
                this.messageBox.clear();
            }
        });
    }

    public Button getSendButton() {
        return sendButton;
    }

    public Button getAdditionalItemsButton() {
        return AdditionalItemsButton;
    }

    public TextField getMessageBox() {
        return messageBox;
    }

    public void setItems(ObservableList<Message> list) {
        this.messageView.setItems(list);
    }
}
