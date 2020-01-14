package baselib.interfaces;

import baselib.classes.NetworkPacket;

/**
 * NetworkManagerController
 */
public interface NetworkServiceController {

    default void serviceRecievedData(String serviceName, NetworkPacket dataPacket) {
        System.out.println(dataPacket.getType());
        System.out.println(dataPacket.getContent());
    }
}