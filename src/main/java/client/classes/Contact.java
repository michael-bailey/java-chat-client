package client.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {
    private static final long serialVersionUID = 365392247250419493L;
    
    private String contactName;
    private String contactUserID;

    private ArrayList<Message> messages;

    public Contact(String contactName, String contactUserID) {
        this.contactName = contactName;
        this.contactUserID = contactUserID;
        this.messages = new ArrayList<>();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactUserID() {
        return contactUserID;
    }
}
