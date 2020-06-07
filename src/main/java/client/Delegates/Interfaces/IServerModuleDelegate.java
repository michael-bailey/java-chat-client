package client.Delegates.Interfaces;

import client.StorageDataTypes.Contact;

public interface IServerModuleDelegate {
    default void serverReceivedMessage() {}
    default void serverReceivedClients() {}

    default void serverWillConnect() {}
    default void serverDidConnect() {}
    default void serverWillDisconnect() {}

    default void serverWillUpdateClients() {}

    default void serverDidUpdateClients(Contact[] clients) {}

    default void serverWillSendMessage() {}

    default void serverDidSendMessage() {}

    default void serverDidError() {}
}
