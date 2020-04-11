package client.classes;

import java.beans.Transient;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.UUID;

public class Server implements Serializable {
    private static final long serialVersionUID = 7209367093717893717L;

    UUID uuid;
    String host;
    String serverName;

    public Server(String host, String serverName, UUID uuid) {
        this.host = host;
        this.serverName = serverName;
        this.uuid = uuid;
    }

    public String getIpAddress() {
        return host;
    }

    public String getServerName() {
        return serverName;
    }
}
