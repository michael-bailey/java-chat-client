package io.github.michael_bailey.client.models;

import io.github.michael_bailey.client.Delegates.Interfaces.IPTPModuleDelegate;
import io.github.michael_bailey.client.Delegates.Interfaces.IServerModuleDelegate;

import java.util.UUID;

public class ChatWindowModel implements IServerModuleDelegate, IPTPModuleDelegate {

    Account account;


    // ui lists
    public ChatWindowModel() {
        System.out.println(this);

        account = new Account();
        account.displayName = "pulse_audio";
        account.uuid = UUID.randomUUID();
        account.publicKey = null;
        account.privateKey = null;

        serverModel = new ServerChatModel();
    }
}
