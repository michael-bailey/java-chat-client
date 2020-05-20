package client.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

//TODO implement the UUID as the Contact user id
public class Contact implements Serializable {
    private static final long serialVersionUID = 365392247250419493L;
    
    public final String username;
    public final UUID UUID;
    public final ArrayList<Message> messages;

    private Contact(Builder builder) {
        this.username = builder.username;
        this.UUID = java.util.UUID.randomUUID();
        this.messages = new ArrayList<Message>();
    }

    public static class Builder {

        private UUID uuid;
        private String username;


        public Contact build() {
            return new Contact(this);
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }
    }
}
