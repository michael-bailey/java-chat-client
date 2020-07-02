package io.github.michael_bailey.client.Delegates.Interfaces;

public interface IPTPModuleDelegate {
    default void willReceiveMessage() { }
    default void didReceiveMessage() { }

    default void willSendMessage() { }
    default void didSendMessage() { }

    default void willReceiveTest() { }
    default void didReceiveTest() { }

    default void willSendTest() { }
    default void didSendTest() { }
}
