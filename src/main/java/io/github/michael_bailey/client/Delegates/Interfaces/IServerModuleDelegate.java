package io.github.michael_bailey.client.Delegates.Interfaces;

import io.github.michael_bailey.client.models.Contact;

public interface IServerModuleDelegate {
    default void serverReceivedMessage() { }
    default void serverReceivedClients() { }

    default void serverWillConnect() { }
    default void serverDidConnect() { }

    default void serverWillDisconnect() { }
    default void serverDidDisconnect() { }

    default void serverWillUpdateClient() { }
    default void serverDidUpdateClient() { }

    default void serverWillSendMessage() { }
    default void serverDidSendMessage() { }

    default void clientWillAddClient() { }
    default void clientDidAddClient(Contact contact) { }

    default void clientWillRemoveClient() { }
    default void clientDidRemoveClient(Contact contact) { }

    default void clientWillUpdateClients() { }
    default void clientDidUpdateClients() { }

}
