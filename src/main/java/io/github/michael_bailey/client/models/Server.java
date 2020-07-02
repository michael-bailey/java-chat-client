package io.github.michael_bailey.client.models;

import io.github.michael_bailey.java_server.Protocol.Command;

public class Server {
    public String address;
    public String displayName;
    public String ownerEmail;

    @Override
    public String toString() {
        return "!server:" +
                " host:" + address +
                " name:\"" + displayName + '\"' +
                " owner:\"" + ownerEmail + '\"';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj.getClass() == getClass())) {
            return false;
        }

        var cmpServer = (Server) obj;

        var a = cmpServer.address.equals(this.address);
        var b = cmpServer.ownerEmail.equals(this.ownerEmail);
        var c = cmpServer.displayName.equals(this.displayName);

        if (!(a && b && c)) {
            return false;
        }

        return true;
    }

    public static Server valueOf(String string) {
        var command = Command.valueOf(string);

        if (!command.command.equals("!server:")) {
            return null;
        }

        var server = new Server();
        server.displayName = command.getParam("name");
        server.address = command.getParam("host");
        server.ownerEmail = command.getParam("owner");

        return server;
    }
}
