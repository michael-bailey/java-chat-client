package baselib.managers;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import javax.jmdns.*;

/**
 * Network Manager
 * this class provides a way of managing multiple ways 
 * data can be sent to other computers.
 * 
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */
public class NetworkManager extends Object {
    // defining network interfaces
    ServerSocket incomingSocket;
    JmDNS MDNS;

    //defining threads
    ArrayList<Thread> threads = new ArrayList();

    public NetworkManager() {
        try {
            incomingSocket = new ServerSocket(1000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}