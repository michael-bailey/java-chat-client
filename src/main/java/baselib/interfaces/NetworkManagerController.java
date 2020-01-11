package baselib.interfaces;

import baselib.data_types.NetworkPacket;

/**
 * NetworkManagerController
 */
public interface NetworkManagerController {

    default void serviceRecievedData(String serviceName, NetworkPacket dataPacket) {
        System.out.println(dataPacket.getType());
        System.out.println(dataPacket.getContent());
    }
}