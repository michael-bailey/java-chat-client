package client.Delegates;

import client.Delegates.Interfaces.IServerModuleDelegate;

public class ServerModuleDelegate implements IServerModuleDelegate {
    @Override
    public void serverReceivedMessage() {
        System.out.println("received message");
    }

    @Override
    public void serverReceivedClients() {
        System.out.println("received client list");
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
    public void serverWillUpdateClients() {
        System.out.println("server will update clients");
    }

    @Override
    public void serverDidUpdateClients() {
        System.out.println("server did update clients");
    }

    @Override
    public void serverWillSendMessage() {
        System.out.println("server will send message");
    }

    @Override
    public void serverDidSendMessage() {
        System.out.println("server did send message");
    }

    @Override
    public void serverDidError() {

    }
}
