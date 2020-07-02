package io.github.michael_bailey.client.Delegates;

import io.github.michael_bailey.client.Delegates.Interfaces.IServerModuleDelegate;
import io.github.michael_bailey.client.models.Contact;

public class ServerModuleDelegate implements IServerModuleDelegate {
    @Override
    public void serverReceivedMessage() {
        System.out.println("received message");
    }

    @Override
    public void serverReceivedClients() {
        System.out.println("received io.github.michael_bailey.client list");
    }

    @Override
    public void serverWillConnect() {
        System.out.println("server will connect");
    }

    @Override
    public void serverDidConnect() {
        System.out.println("server connected");
    }

    @Override
    public void serverWillDisconnect() {
        System.out.println("server will disconnect");
    }

    @Override
    public void serverDidDisconnect() { System.out.println("server has disconnected"); }

    @Override
    public void serverWillSendMessage() {
        System.out.println("server will send message");
    }

    @Override
    public void serverDidSendMessage() {
        System.out.println("server did send message");
    }

    @Override
    public void clientWillAddClient() {
        System.out.println("expecting a new client");
    }

    @Override
    public void clientDidAddClient(Contact contact) {
        System.out.println("new client received");
    }

    @Override
    public void clientWillRemoveClient() {
        System.out.println("removing a client");
    }

    @Override
    public void clientDidRemoveClient(Contact contact) {
        System.out.println("client removed");
    }

    @Override
    public void clientWillUpdateClients() {
        System.out.println("requesting a update of clients");
    }

    @Override
    public void clientDidUpdateClients() {
        System.out.println("request sent");
    }
}
