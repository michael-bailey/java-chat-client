package baselib.interfaces;

import baselib.classes.NetworkPacket;

public interface NetworkManagerController {

    void dataReceived(String serviceName, NetworkPacket data);

}
