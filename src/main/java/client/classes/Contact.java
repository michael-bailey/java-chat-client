package client.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

//TODO implement the UUID as the Contact user id
public class Contact implements Serializable {
    private static final long serialVersionUID = 365392247250419493L;
    
    private String username;
    private UUID UUID;

    private ArrayList<Message> messages;

    public Contact(String username) {
        this.username = username;
        this.UUID = UUID.randomUUID();
        this.messages = new ArrayList<Message>();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public String getUsername() {
        return username;
    }

    public UUID getUUID() {
        return UUID;
    }
}
