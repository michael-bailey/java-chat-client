package client.Delegates;

import client.Delegates.Interfaces.INetworkManagerDelegate;
import client.StorageDataTypes.Contact;

import java.util.ArrayList;
import java.util.HashMap;

public class NetworkManagerDelegate implements INetworkManagerDelegate {

    @Override
    public void ptpReceivedMessage(HashMap<String, String> data) {
        System.out.println("data = " + data);
    }

    @Override
    public void stdReceivedMessage(HashMap<String, String> data) {
        System.out.println("data = " + data);
    }

    @Override
    public void updateClientList(ArrayList<Contact> contacts) {
        System.out.println("contacts = " + contacts);
    }
}
