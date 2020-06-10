package io.github.michael_bailey.client.Delegates.Interfaces;

import io.github.michael_bailey.client.StorageDataTypes.Contact;

import java.util.ArrayList;
import java.util.HashMap;

public interface INetworkManagerDelegate {
    void ptpReceivedMessage(HashMap<String, String> data);
    void stdReceivedMessage(HashMap<String, String> data);
    void updateClientList(ArrayList<Contact> contacts);
}
