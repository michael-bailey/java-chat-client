package client.interfaces.controllers;

public interface IPreferenceWindowController {
    default void preferenceChanged() {
        System.out.println("a preference was changed");
    }
}
