package client.classes;

import java.io.Serializable;
import java.net.InetAddress;

public class Server implements Serializable {
    private static final long serialVersionUID = 7209367093717893717L;
    
    String ipAddress;
    String serverName;

    public Server(String IPAddress, String serverName) {
        this.ipAddress = IPAddress;
        this.serverName = serverName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getServerName() {
        return serverName;
    }
}
