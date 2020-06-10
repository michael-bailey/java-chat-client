package io.github.michael_bailey.client.Delegates.Interfaces;

import io.github.michael_bailey.client.StorageDataTypes.Contact;

public interface IServerModuleDelegate {
    default void serverReceivedMessage() {}
    default void serverReceivedClients() {}

    default void serverWillConnect() {}
    default void serverDidConnect() {}

    default void serverWillDisconnect() {}
    default void serverDidDisconnect() {}

    default void serverWillUpdateClients() {}
    default void serverDidUpdateClients(Contact[] clients) {}

    default void serverWillSendMessage() {}
    default void serverDidSendMessage() {}

    default void serverDidError() {}


}
