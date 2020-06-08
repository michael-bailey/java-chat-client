package client.managers.NetworkModules;

import client.Delegates.Interfaces.IServerModuleDelegate;
import client.Delegates.ServerModuleDelegate;
import client.Protocol.Command;
import client.StorageDataTypes.Contact;
import client.StorageDataTypes.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static client.Protocol.Command.*;

public class ServerModule {

    private static final int serverConnectionPort = 6000;
    private final IServerModuleDelegate delegate;

    // server fields
    private Server currentServer;
    private Thread serverConnectionThread;
    private Queue<String> sendQueue;
    private boolean serverThreadRunning = false;

    public ServerModule(IServerModuleDelegate delegate) {
        this.delegate = delegate;
    }

    public ServerModule() {
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

            command.forEach((key, value) -> {
                switch (key) {
                    case "host":
                        serverBuilder.host(value);
                        break;

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
            Socket serverConnection = new Socket(this.currentServer.getIpAddress(), this.serverConnectionPort);
            var in = new DataInputStream(serverConnection.getInputStream());
            var out = new DataOutputStream(serverConnection.getOutputStream());

            Command command;
            command = Command.valueOf(in.readUTF());

            if (command.command.equals(REQUEST)) {
                this.disconnect();
                return;
            }

            out.writeUTF(new Command(CONNECT).toString());

            command = Command.valueOf(in.readUTF());

            if (command.command.equals(SUCCESS)) {
                disconnect();
                return;
            }

            delegate.serverDidConnect();

            // event loop.
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

                            // todo use this to update the client list?
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

            // disconnect from the server.
            out.writeUTF(DISCONNECT);
            serverConnection.close();
            serverConnectionThread = null;
            currentServer = null;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverConnectionThread = null;
            currentServer = null;
        }
    }

// MARK connection Management.
    public boolean connect(Server serverDetails) {
        disconnect();

        this.currentServer = serverDetails;

        serverConnectionThread = new Thread(() -> serverThreadFn());
        sendQueue = new LinkedList<String>();
        serverConnectionThread.start();
        return true;
    }

    public boolean disconnect() {
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
