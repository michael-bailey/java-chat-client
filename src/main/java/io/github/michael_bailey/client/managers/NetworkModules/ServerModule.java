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

    private Socket serverSocket;
    private DataInputStream in;
    private DataOutputStream out;

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

// MARK: server thread function.
    private void serverThreadFn() {
        try {
            Command command;

            System.out.println("entering loop");
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

                            case MESSAGE:
                                System.out.println("not implemented");
                                break;

                            default:
                                System.out.println("idk heres the message");
                                System.out.println("messageString = " + messageString);
                                break;
                        }
                    }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("an error occured");
        } catch (IOException e) {
            System.out.println("socket closed");
        } finally {
            serverConnectionThread = null;
            currentServer = null;
            this.disconnect();
        }
    }

    // MARK connection Management.
    public boolean connect(Server serverDetails) {

        if (this.currentServer != null) {
            System.out.println("other connection active");
            this.disconnect();
        }

        try {
            System.out.println("signal will connect");
            delegate.serverWillConnect();

            System.out.println("creating a new socket");
            serverSocket = new Socket(serverDetails.getIpAddress(), serverConnectionPort);
            in = new DataInputStream(serverSocket.getInputStream());
            out = new DataOutputStream(serverSocket.getOutputStream());


            System.out.println("getting command");
            Command command;
            command = Command.valueOf(in.readUTF());

            System.out.println("test that it is a request");
            if (!command.command.equals(REQUEST)) {
                System.out.println("command doesn't equal request");
                this.disconnect();
                return false;
            }

            System.out.println("sending a connect request");
            var parameters = new HashMap<String, String>();
            parameters.put("name", account.username);
            parameters.put("uuid", account.uuid.toString());
            parameters.put("email", account.email);
            out.writeUTF(new Command(CONNECT, parameters).toString());


            System.out.println("reading response message");
            command = Command.valueOf(in.readUTF());
            if (!command.command.equals(SUCCESS)) {
                System.out.println("command does not request");
                this.disconnect();
                return false;
            }

            System.out.println("setting up local fields");
            this.currentServer = serverDetails;
            serverConnectionThread = new Thread(this::serverThreadFn);
            sendQueue = new LinkedList<String>();

            System.out.println("starting thread");
            this.serverThreadRunning = true;
            serverConnectionThread.start();

            System.out.println("signaling did connect");
            delegate.serverDidConnect();
            return true;
        } catch (IOException e) {
            System.out.println("io exception occurred disconnecting");
            e.printStackTrace();
            this.disconnect();
            return false;
        }
    }

    public boolean disconnect() {
        try {
            System.out.println("signaling will disconnect");
            delegate.serverWillDisconnect();

            System.out.println("sending disconnect");
            out.writeUTF(new Command(DISCONNECT).toString());
            out.flush();

            System.out.println("connection is closing");
            serverSocket.close();

            System.out.println("cleanup");
            serverConnectionThread = null;
            currentServer = null;

            System.out.println("signaling has disconnected");
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
        }
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
