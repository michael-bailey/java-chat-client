package client.managers;

import java.io.IOException;
import javax.jmdns.JmDNS;

class NetworkController extends Object {

    // mdns object.
    private JmDNS mDns;

    public NetworkController() {

        try {
        // create a mdns object for connecting to other mdns users
        this.mDns = JmDNS.create();
        this.mDns.getClass();

        } catch (IOException e) {
            System.out.println(" IOException occurred.");
            System.out.println("=== Stack Trace ===");
            e.printStackTrace();
            System.exit(1234589);
        }
    }



}