package io.github.michael_bailey.client.models;

import io.github.michael_bailey.client.Delegates.Interfaces.IPTPModuleDelegate;
import io.github.michael_bailey.client.Delegates.Interfaces.IServerModuleDelegate;
import io.github.michael_bailey.client.managers.NetworkModules.PTPModule;
import io.github.michael_bailey.client.managers.NetworkModules.ServerModule;

import java.util.UUID;

public class ChatWindowModel implements IServerModuleDelegate, IPTPModuleDelegate {

    PTPModule peerToPeerModule;
    ServerModule serverModule;

    Account account;


    // ui lists
    public ChatWindowModel() {
        account = new Account();
        account.displayName = "pulse_audio";
        account.uuid = UUID.randomUUID();
        account.publicKey = null;
        account.privateKey = null;

        serverModule = new ServerModule(account, this);
        peerToPeerModule = new PTPModule(account, this);
    }
}
