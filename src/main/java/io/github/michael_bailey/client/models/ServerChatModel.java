package io.github.michael_bailey.client.models;

import io.github.michael_bailey.client.Delegates.Interfaces.IServerModuleDelegate;
import io.github.michael_bailey.client.managers.NetworkModules.ServerModule;
import javafx.beans.property.SimpleBooleanProperty;
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

    transient ObservableList<Server> serverObservableList;
    transient ObservableList<Contact> contactObservableList;
    transient ObservableBooleanValue connectionObservable;

    public ServerChatModel(ChatWindowModel model) {
        this.serverList = new ArrayList<>(4);
        this.serverObservableList = FXCollections.observableList(new ArrayList<>());
        this.contactObservableList = FXCollections.observableList(new ArrayList<>());
        this.connectionObservable = new SimpleBooleanProperty();

        Account account = new Account();
        account.uuid = UUID.randomUUID();
        account.displayName = "michael-bailey";
        account.privateKey = null;
        account.publicKey = null;

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
        System.out.println("Connected");
    }

    @Override
    public void serverDidDisconnect() {
        System.out.println("Disconnected");
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
