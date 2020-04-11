package client.models.Console;

import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.models.ApplicationModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.UUID;

public class ConsoleModel {
    SimpleStringProperty commandString = new SimpleStringProperty();
    SimpleStringProperty outputString = new SimpleStringProperty();

    ApplicationModel appModel = ApplicationModel.getInstance();

    public ConsoleModel() {

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
                    break;

                case "remove":
                    this.remove(args);
                    break;


            }
        }

        commandString.set("");
    }

    private void add(ArrayList<String> args) {
        switch (args.get(0)) {
            case "server":
                this.write("Adding server");
                appModel.serverListProperty().add(new Server(args.get(1), args.get(2), UUID.randomUUID()));
                this.write("Added to server");
                break;

            case "contact":
                this.write("Adding Contact");
                //ObservableList<Contact> clist = appModel.onlineContactsListProperty();
                //clist.add(new Contact(args.get(1)));
                this.write("Added to Contacts");
                break;

            case "message":
                this.write("Adding Message");
                //ObservableList<Message> mlist = appModel.messageListProperty();
                //mlist.add(new Message(args.subList(1, args.size() - 1).toString(), Boolean.valueOf(args.get(args.size() - 1))));
                this.write("Added to Messages");
                break;
        }
    }
    
    private void remove(ArrayList<String> args) {

    }

    private void write(String string) {
        this.outputString.set(this.outputString.get().concat(string+"\n"));
    }

    public SimpleStringProperty commandStringProperty() {
        return commandString;
    }

    public SimpleStringProperty outputStringProperty() {
        return outputString;
    }

}
