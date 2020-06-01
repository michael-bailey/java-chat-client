package client.Delegates.Interfaces;

import client.classes.Contact;

import java.util.ArrayList;
import java.util.HashMap;

public interface INetworkManagerDelegate {
    void ptpReceivedMessage(HashMap<String, String> data);
    void stdReceivedMessage(HashMap<String, String> data);
    void updateClientList(ArrayList<Contact> contacts);
}
