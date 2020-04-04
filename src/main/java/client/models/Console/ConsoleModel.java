package client.models.Console;

import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.models.ApplicationModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ConsoleModel {
    SimpleStringProperty commandString = new SimpleStringProperty();
    SimpleStringProperty outputString = new SimpleStringProperty();

    ApplicationModel appModel = ApplicationModel.getInstance();

    public ConsoleModel() {
        System.out.println(appModel.onlineContactsListProperty().get());
        System.out.println(appModel.messageListProperty().get());
    }

    public void processCommand() {
        StringTokenizer tokenizer = new StringTokenizer(this.commandString.get());
        System.out.println(tokenizer.countTokens());

        if (tokenizer.countTokens() == 0) {
            return;
        } else {
            String command = tokenizer.nextToken();
            ArrayList<String> args = new ArrayList<>();

            while (tokenizer.hasMoreTokens()) {
                args.add(tokenizer.nextToken());
            }

            switch (command) {
                case "add":
                    this.add(args);
            }
        }

        commandString.set("");
    }

    public void add(ArrayList<String> args) {
        switch (args.get(0)) {
            case "server":
                ObservableList<Server> slist = appModel.serverListProperty();
                slist.add(new Server(args.get(1), args.get(2)));
                break;

            case "contact":
                ObservableList<Contact> clist = appModel.onlineContactsListProperty();
                clist.add(new Contact(args.get(1)));
                break;

            case "message":
                ObservableList<Message> mlist = appModel.messageListProperty();
                mlist.add(new Message(args.subList(1, args.size()-1).toString(), Boolean.valueOf(args.get(args.size()-1))));
                break;
        }
    }

    public SimpleStringProperty commandStringProperty() {
        return commandString;
    }

    public SimpleStringProperty outputStringProperty() {
        return outputString;
    }

}
