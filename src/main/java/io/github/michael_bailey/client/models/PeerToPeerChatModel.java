package io.github.michael_bailey.client.models;

import io.github.michael_bailey.client.Delegates.Interfaces.IPTPModuleDelegate;
import io.github.michael_bailey.client.managers.NetworkModules.PTPModule;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PeerToPeerChatModel implements IPTPModuleDelegate {
    // managers
    PTPModule ptpModule;

    // storage
    HashMap<UUID, List<Message>> messageMap;

    transient SimpleMapProperty<UUID, ObservableList<Message>> serverObservableList;

    transient SimpleBooleanProperty connectionObservable;

    PeerToPeerChatModel(Account account) {
        System.out.println(this);

        ptpModule = new PTPModule(account, this);

        serverObservableList.get(UUID.randomUUID()).
    }

    public SimpleListProperty<Message> getMessageList()
}
