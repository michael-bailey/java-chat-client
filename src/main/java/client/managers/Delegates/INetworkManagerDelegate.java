package client.managers.Delegates;

import client.classes.Contact;

import java.util.HashMap;

public interface INetworkManagerDelegate {
    void ptpReceivedMessage(HashMap<String, String> data);
    void stdReceivedMessage();
    Contact[] updateClientList();
}
