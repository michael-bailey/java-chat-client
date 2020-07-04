package io.github.michael_bailey.client.managers.NetworkModules;

import io.github.michael_bailey.client.Delegates.Interfaces.IServerModuleDelegate;
import io.github.michael_bailey.client.Delegates.ServerModuleDelegate;
import io.github.michael_bailey.client.models.Account;
import io.github.michael_bailey.client.models.Contact;
import io.github.michael_bailey.client.models.Server;
import io.github.michael_bailey.java_server.Protocol.Command;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static io.github.michael_bailey.java_server.Protocol.Command.*;
import static java.lang.Thread.sleep;

public class ServerModule {

    Account account;

    private static final int serverConnectionPort = 6000;
    private final IServerModuleDelegate delegate;

    // server fields
    private Server currentServer;
    private Thread serverConnectionThread;
    private Queue<Command> sendQueue;
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
    public static Server getServerDetails(String ipAddress) {
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

            var a = new Server();
            a.address = command.getParam("host");
            a.displayName = command.getParam("name");
            a.ownerEmail = command.getParam("owner");

            return a;
        } catch (IOException e) {
            return null;
        }
    }

// MARK: server thread function.
    private void serverThreadFn() {
        try {
            Command command = null;

            System.out.println("entering loop");
            while (serverThreadRunning) {

                // check for messages from the server;
                while (in.available() > 0) {
                    command = Command.valueOf(in.readUTF());
                    Contact contact;
                    switch (command.command) {

                        case MESSAGE:
                            delegate.serverWillSendMessage();
                            delegate.serverDidSendMessage();
                            break;

                        case CLIENT:
                            if (command.getParam("uuid").equals(this.account.uuid.toString())) {
                                System.out.println("ignored incoming client");
                                break;
                            }
                            delegate.clientWillAddClient();
                            System.out.println("client added: " + command);

                            contact = Contact.valueOf(command.toString());

                            out.writeUTF(SUCCESS);
                            delegate.clientDidAddClient(contact);
                            break;

                        case CLIENT_REMOVE:
                            delegate.clientWillRemoveClient();
                            out.writeUTF(SUCCESS);

                            contact = Contact.valueOf(command.toString());

                            delegate.clientDidRemoveClient(contact);

                        default:
                            System.out.println("idk here's the command it sent");
                            System.out.println("command = " + command);
                            break;
                    }
                }

                // check for messages to send to the server
                synchronized (sendQueue) {
                    while (!sendQueue.isEmpty()) {
                        command = sendQueue.remove();
                        switch (command.command) {

                            case UPDATE_CLIENTS:
                                delegate.clientWillUpdateClients();
                                out.writeUTF(command.toString());
                                if (!valueOf(in.readUTF()).command.equals(SUCCESS)) {
                                    sendQueue.clear();
                                    out.writeUTF(new Command(ERROR).toString());
                                }
                                delegate.clientDidUpdateClients();
                                break;

                            default:
                                System.out.println("idk here's the command you sent");
                                System.out.println("command = " + command);
                                break;
                        }
                    }
                }
            }
            sleep(2000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("an error occured");
        } catch (IOException e) {
            System.out.println("socket closed");
        } catch (InterruptedException e) {
            System.out.println("interupted");
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
            serverSocket = new Socket(serverDetails.address, serverConnectionPort);
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

            // get the external ip
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader ipIn = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

            String ip = ipIn.readLine(); //you get the IP as a String

            System.out.println("sending a connect request");
            var parameters = new HashMap<String, String>();
            parameters.put("name", account.displayName);
            parameters.put("uuid", account.uuid.toString());
            parameters.put("host", "\"" + ip +"\"");
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
            sendQueue = new LinkedList<>();

            System.out.println("starting thread");
            this.serverThreadRunning = true;
            serverConnectionThread.start();

            System.out.println("adding updateClient to the send queue");
            sendQueue.add(valueOf(UPDATE_CLIENTS));

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
        } catch (SocketException e) {
            System.out.println("socket closed");
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
