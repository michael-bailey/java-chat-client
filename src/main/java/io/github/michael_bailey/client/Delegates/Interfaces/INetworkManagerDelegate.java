package io.github.michael_bailey.client.Delegates.Interfaces;

public interface INetworkManagerDelegate {

    default void networkingWillStart() { }
    default void networkingDidStart() { }

    default void networkingWillStop() { }
    default void networkingDidStop() { }
}
