package io.github.michael_bailey.client.models;

import io.github.michael_bailey.client.Delegates.Interfaces.IServerModuleDelegate;
import io.github.michael_bailey.client.managers.NetworkModules.ServerModule;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ServerChatModel implements IServerModuleDelegate {
    // Timers
    Timer serverUpdateTimer;

    // managers
    ServerModule serverModule;

    // storage
    ArrayList<Server> serverList;

    transient SimpleListProperty<Server> serverObservableList;
    transient SimpleListProperty<Contact> contactObservableList;
    transient SimpleBooleanProperty connectionObservable;

    public ServerChatModel(Account account) {
        System.out.println(this);

        this.serverList = new ArrayList<>(4);
        this.serverObservableList = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.contactObservableList = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.connectionObservable = new SimpleBooleanProperty();



        serverList.add(Server.valueOf("!server: host:\"127.0.0.1\" name:\"michael-server\" owner:\"noreply@email.com\""));
        serverModule = new ServerModule(account, this);

        serverUpdateTimer = new Timer(true);
        serverUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                var items = new ArrayList<Server>();
                serverList.forEach(i -> {
                    var server = ServerModule.getServerDetails(i.address);

                    if (server == null) {
                        serverObservableList.remove(i);
                        return;
                    }

                    if (!serverObservableList.contains(i)) {
                        serverObservableList.add(i);
                    }
                });
                // serverObservableList.setAll(items);
            }
        }, 0, 5000);
    }

    public void connect(Server server) {
        System.out.println("ChatModel: server = " + server);
        if (server == null) {
            return;
        }
        this.serverModule.connect(server);
    }

    public void disconnect() {
        serverModule.disconnect();
    }

    public ObservableList<Server> serverList() {
        return serverObservableList;
    }
    public ObservableList<Contact> contactList() { return contactObservableList; }
    public ObservableBooleanValue connection() { return connectionObservable; }

    @Override
    public void serverDidConnect() {
        System.out.println("ServerModel: server connected");
        this.connectionObservable.set(true);
    }

    @Override
    public void serverDidDisconnect() {
        System.out.println("ServerModel: server disconnected");
        this.connectionObservable.set(false);
        this.contactObservableList.clear();
    }

    @Override
    public void clientDidAddClient(Contact contact) {
        this.contactObservableList.add(contact);
    }

    @Override
    public void clientDidRemoveClient(Contact contact) {
        this.contactObservableList.remove(contact);
    }


}
