package client.managers.Delegates;

import client.classes.Contact;

public class NetworkManagerDelegate implements INetworkManagerDelegate {

    @Override
    public void ptpReceivedMessage() {
        System.out.println("received message ptp");
    }

    @Override
    public void stdReceivedMessage() {
        System.out.println("received message server");
    }

    @Override
    public Contact[] updateClientList() {
        return new Contact[0];
    }
}
