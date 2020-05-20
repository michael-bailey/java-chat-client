package client.managers.Delegates;

import client.classes.Contact;

public interface INetworkManagerDelegate {
    void ptpReceivedMessage();
    void stdReceivedMessage();
    Contact[] updateClientList();
}
