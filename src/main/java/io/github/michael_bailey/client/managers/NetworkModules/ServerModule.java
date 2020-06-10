package io.github.michael_bailey.client.managers.NetworkModules;

import io.github.michael_bailey.client.Delegates.Interfaces.IServerModuleDelegate;
import io.github.michael_bailey.client.Delegates.ServerModuleDelegate;
import io.github.michael_bailey.client.StorageDataTypes.Account;
import io.github.michael_bailey.client.StorageDataTypes.Contact;
import io.github.michael_bailey.client.StorageDataTypes.Server;
import io.github.michael_bailey.java_server.Protocol.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static io.github.michael_bailey.java_server.Protocol.Command.*;

public class ServerModule {

    Account account;

    private static final int serverConnectionPort = 6000;
    private final IServerModuleDelegate delegate;

    // server fields
    private Server currentServer;
    private Thread serverConnectionThread;
    private Queue<String> sendQueue;
    private boolean serverThreadRunning = false;

    public ServerModule(Account account, IServerModuleDelegate delegate) {
        this.account = account;
        this.delegate = delegate;
    }

    public ServerModule(Account account) {
        this.account = account;
        this.delegate = new ServerModuleDelegate();
    }

    /**
     * getServerDetails
     *
     * this is the primary way of adding getting new Server instances
     * gets the status of the server in question and builds a server object from that.
     *
     * @param ipAddress the address of the server
     * @return new Server instance if the connection was successful.
     * todo add key exchange and encryption.
     */
    public Server getServerDetails(String ipAddress) {
        try {

            // setup connection and get data streams
            Socket connection = new Socket(ipAddress, 6000);
            DataInputStream in = new DataInputStream(connection.getInputStream());
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

            // get data sent from the server.
            Command command = Command.valueOf(in.readUTF());

            if (!command.command.equals(REQUEST)) {
                connection.close();
                return null;
            }

            // writing the command !info:
            out.writeUTF(INFO);
            out.flush();

            // get the next part of the
            command = Command.valueOf(in.readUTF());

            if (!command.command.equals(SUCCESS) ) {
                connection.close();
                return null;
            }

            var serverBuilder = new Server.Builder();
            serverBuilder.host(ipAddress);

            command.forEach((key, value) -> {
                switch (key) {

                    case "name":
                        serverBuilder.name(value);
                        break;

                    case "owner":
                        serverBuilder.owner(value);
                        break;

                    default:
                        break;

                }
            });

            connection.close();
            return serverBuilder.build();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

// MARK: server thread finction.
    private void serverThreadFn() {
        try {
            delegate.serverWillConnect();
            Socket serverConnection = new Socket(this.currentServer.getIpAddress(), serverConnectionPort);
            var in = new DataInputStream(serverConnection.getInputStream());
            var out = new DataOutputStream(serverConnection.getOutputStream());

            System.out.println("got streams");

            Command command;
            command = Command.valueOf(in.readUTF());

            System.out.println("command = " + command);
            System.out.println("command.command.equals(REQUEST) = " + command.command.equals(REQUEST));

            if (!command.command.equals(REQUEST)) {
                System.out.println("command doesn't equal request");
                this.disconnect();
                System.out.println("disconnected");
                return;
            }

            System.out.println("server sent request");

            var parameters = new HashMap<String, String>();
            parameters.put("name", account.username);
            parameters.put("uuid", account.uuid.toString());
            parameters.put("email", account.email);

            out.writeUTF(new Command(CONNECT, parameters).toString());

            System.out.println("sent connect request");

            command = Command.valueOf(in.readUTF());

            if (!command.command.equals(SUCCESS)) {
                disconnect();
                return;
            }

            System.out.println("got success back");

            delegate.serverDidConnect();

            System.out.println("starting loop");
            manage(in, out);
            System.out.println("ending loop");

            delegate.serverWillDisconnect();

            // disconnect from the server.
            out.writeUTF(DISCONNECT);
            serverConnection.close();
            serverConnectionThread = null;
            currentServer = null;

            delegate.serverDidDisconnect();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("an error occured");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("an error occured");
        } finally {
            serverConnectionThread = null;
            currentServer = null;
            this.disconnect();
        }
    }

    private void manage(DataInputStream in, DataOutputStream out) throws IOException {
        Command command;// event loop.
        while (serverThreadRunning) {

            // check for messages from the server;
            while (in.available() > 0) {
                command = Command.valueOf(in.readUTF());

                switch (command.command) {
                    case UPDATE_CLIENTS:
                        delegate.serverWillUpdateClients();

                        // get how many clients
                        ArrayList<Contact> contactList = new ArrayList<>();
                        for (int i = 0; i < Integer.parseInt(command.getParam("number")); i++) {
                            Command clientCommand = Command.valueOf(in.readUTF());
                            if (clientCommand.command.equals(CLIENT)) {
                                contactList.add(new Contact.Builder().username(clientCommand.getParam("username"))
                                        .uuid(clientCommand.getParam("uuid"))
                                        .email(clientCommand.getParam("email"))
                                        .build());

                            }
                        }

                        // todo use this to update the io.github.michael_bailey.client list?
                        delegate.serverDidUpdateClients((Contact[]) contactList.toArray());
                        break;

                    case MESSAGE:
                        delegate.serverWillSendMessage();
                        delegate.serverDidSendMessage();
                        break;

                    default:
                        out.writeUTF(ERROR);
                        delegate.serverDidError();
                        break;
                }
            }

            // check for messages to send to the server
            synchronized (sendQueue) {
                while (!sendQueue.isEmpty()) {
                    String messageString = sendQueue.remove();

                    var message = parser.matcher(messageString);
                    switch (message.group()) {
                        case DISCONNECT:
                            this.disconnect();
                            break;

                        case MESSAGE:
                            break;

                    }
                }
            }
        }
    }

    // MARK connection Management.
    public synchronized boolean connect(Server serverDetails) {
        disconnect();

        this.currentServer = serverDetails;

        serverConnectionThread = new Thread(this::serverThreadFn);
        sendQueue = new LinkedList<String>();
        serverConnectionThread.start();
        return true;
    }

    public synchronized boolean disconnect() {
        this.serverThreadRunning = false;
        return true;
    }

    public boolean isConnected() {
        if (this.currentServer == null) {
            return false;
        } else {
            return true;
        }
    }
}
