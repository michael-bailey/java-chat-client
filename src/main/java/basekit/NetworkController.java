package basekit;

import basekit.interfaces.*;
import java.io.IOException;
import javax.jmdns.JmDNS;
import j

class NetworkController extends Object implements DataBusClient {


    // saving the instance of the data bus for ease of use
    private DataBus dataBusInstance;

    // mdns object.
    private JmDNS mDns;

    public NetworkController() {

        try {
        // create a mdns object for connecting to other mdns users
        this.mDns = JmDNS.create();
        this.mDns.getClass();
        
        

        // register this object on the data bus.
        this.dataBusInstance = DataBus.getInstance();
        this.dataBusInstance.register(this, "netSendData");

        } catch (IOException e) {
            System.out.println(" IOException occurred.");
            System.out.println("=== Stack Trace ===");
            e.printStackTrace();
            System.exit(1234589);
        }
    }

    @Override
    public void call(DataBusClient caller, String message, Object params) {
        switch(message) {
            case "programWillExit":
                break;
            case "programDidStart":
                break;
            default:
                break;
        }

    }

    @Override
    public void respond(DataBusClient responder, String result) {

    }

}