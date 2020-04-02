package client.models.Console;

import client.classes.Contact;
import client.classes.Message;
import client.classes.Server;
import client.models.ApplicationModel;
import javafx.beans.property.SimpleStringProperty;

public class ConsoleModel {
    SimpleStringProperty commandString = new SimpleStringProperty();
    SimpleStringProperty outputString = new SimpleStringProperty();

    public void processCommand() {

        System.out.println(commandString.get().substring(0,6));
        switch (commandString.get().substring(0,6)) {
            case "addsrv":
                outputString.set(outputString.get().concat("added\n"));
                ApplicationModel.getInstance().serverListProperty().add(new Server("", "BOB"));

                break;

            case "addcnt":
                outputString.set(outputString.get().concat("added\n"));
                ApplicationModel.getInstance().onlineContactListProperty().get().add(new Contact("USER"));

                break;

            case "addmsg":
                outputString.set(outputString.get().concat("added\n"));
                ApplicationModel.getInstance().messageListProperty().add(new Message("dfgdfgdfgd", true));


                break;

            case "rmvsrv":
                outputString.get().concat("cannot rmv at this time\n");

                break;

            case "rmvcnt":
                outputString.get().concat("cannot rmv at this time\n");

                break;

            case "rmvmsg":
                outputString.get().concat("cannot rmv at this time\n");

                break;

            default:
                outputString.get().concat(String.format("do not know how to %s\n", commandString.get().substring(0,2)));
                break;
        }
        commandString.set("");
    }

    public SimpleStringProperty commandStringProperty() {
        return commandString;
    }

    public SimpleStringProperty outputStringProperty() {
        return outputString;
    }
}
