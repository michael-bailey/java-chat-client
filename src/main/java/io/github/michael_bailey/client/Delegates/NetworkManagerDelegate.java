package io.github.michael_bailey.client.Delegates;

import io.github.michael_bailey.client.Delegates.Interfaces.INetworkManagerDelegate;
import io.github.michael_bailey.client.StorageDataTypes.Contact;

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
