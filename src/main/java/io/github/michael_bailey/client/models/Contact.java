package io.github.michael_bailey.client.models;

import io.github.michael_bailey.java_server.Protocol.Command;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.UUID;

public class Contact {
    public String displayName;
    public Inet4Address address;
    public UUID uuid;

    public static Contact valueOf(String toString) throws UnknownHostException {
        var command = Command.valueOf(toString);
        var a = new Contact();
        a.displayName = command.getParam("name");
        a.address = (Inet4Address) Inet4Address.getByName(command.getParam("host"));
        a.uuid = UUID.fromString(command.getParam("uuid"));

        return a;
    }
}
