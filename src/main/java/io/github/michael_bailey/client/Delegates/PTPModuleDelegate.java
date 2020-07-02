package io.github.michael_bailey.client.Delegates;

import io.github.michael_bailey.client.Delegates.Interfaces.IPTPModuleDelegate;

public class PTPModuleDelegate implements IPTPModuleDelegate {
    @Override
    public void willReceiveMessage() {
        System.out.println("PTPModule: will receive message");
    }

    @Override
    public void didReceiveMessage() {
        System.out.println("PTPModule: did receive message");
    }

    @Override
    public void willSendMessage() {
        System.out.println("PTPModule: will send message");
    }

    @Override
    public void didSendMessage() {
        System.out.println("PTPModule: did send message");
    }

    @Override
    public void willReceiveTest() {
        System.out.println("PTPModule: will receive test");
    }

    @Override
    public void didReceiveTest() {
        System.out.println("PTPModule: did receive test");
    }

    @Override
    public void willSendTest() {
        System.out.println("PTPModule: will send test");
    }

    @Override
    public void didSendTest() {
        System.out.println("PTPModule: did send test");
    }
}
