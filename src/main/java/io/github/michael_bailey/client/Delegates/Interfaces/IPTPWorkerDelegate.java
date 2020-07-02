package io.github.michael_bailey.client.Delegates.Interfaces;

public interface IPTPWorkerDelegate {

    default void willReceiveMessage() { }
    default void didReceiveMessage() { }

    default void willReceiveTest() { }
    default void didReceiveTest() { }
}
