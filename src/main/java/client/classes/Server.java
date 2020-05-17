package client.classes;

import java.beans.Transient;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.UUID;

public class Server implements Serializable {


    private static final long serialVersionUID = 7209367093717893717L;

    private final String host;
    private final String name;
    private final String owner;


    public Server(Builder builder) {
        this.host = builder.host;
        this.name = builder.name;
        this.owner = builder.owner;
    }

    public static class Builder {
        String owner;
        String host;
        String name;

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public Server build() {
            return new Server( this);
        }


    }

    public String getIpAddress() {
        return host;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
