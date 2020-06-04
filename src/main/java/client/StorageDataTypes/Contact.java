package client.StorageDataTypes;

import java.io.Serializable;
import java.util.UUID;

//TODO implement the UUID as the Contact user id
public class Contact implements Serializable {
    private static final long serialVersionUID = 365392247250419493L;

    public final UUID UUID;
    public final String username;
    public final String email;

    private Contact(Builder builder) {
        this.username = builder.username;
        this.UUID = builder.uuid;
        this.email = builder.email;
    }

    public static class Builder {

        public String email;
        private UUID uuid;
        private String username;

        public Contact build() {
            return new Contact(this);
        }

        public Builder uuid(String uuid) {
            this.uuid = java.util.UUID.fromString(uuid);
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }
    }
}
