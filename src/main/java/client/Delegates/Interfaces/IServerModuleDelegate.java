package client.Delegates.Interfaces;

public interface IServerModuleDelegate {
    void serverReceivedMessage();
    void serverReceivedClients();

    void serverWillConnect();
    void serverDidConnect();
    void serverWillDisconnect();

    void serverWillUpdateClients();

    void serverDidUpdateClients();

    void serverWillSendMessage();

    void serverDidSendMessage();

    void serverDidError();
}
