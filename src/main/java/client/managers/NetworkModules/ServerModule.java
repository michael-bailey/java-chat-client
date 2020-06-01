package client.managers.NetworkModules;

import client.Delegates.Interfaces.IServerModuleDelegate;
import client.Delegates.ServerModuleDelegate;
import client.classes.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.regex.Matcher;

import static client.enums.PROTOCOL_MESSAGES.*;

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

    private void serverThreadFn() {
        try {
            delegate.serverWillConnect();
            Socket serverConnection = new Socket(this.currentServer.getIpAddress(), this.serverConnectionPort);
            var in = new DataInputStream(serverConnection.getInputStream());
            var out = new DataOutputStream(serverConnection.getOutputStream());

            Matcher response = parser.matcher(in.readUTF());

            if (!response.find() && !response.group().equals(REQUEST)) {
                this.disconnect();
                return;
            }

            out.writeUTF(CONNECT);

            response = parser.matcher(in.readUTF());

            if (!response.find() || !response.group().equals(SUCCESS)) {
                disconnect();
                return;
            }

            delegate.serverDidConnect();

            // event loop.
            while (serverThreadRunning) {

                // check for updated list of clients || check queue for messages to send;
                if (in.available() > 0) {
                    Matcher dataMatcher = parser.matcher(in.readUTF());

                    dataMatcher.find();
                    switch (dataMatcher.group()) {
                        case UPDATE_CLIENTS:
                            delegate.serverWillUpdateClients();
                            // get how many clients
                            for (int i = 0; i < in.readInt(); i++) {

                                // read a new client
                                var clientMatcher = parser.matcher(in.readUTF());
                                clientMatcher.find();
                            }

                            delegate.serverDidUpdateClients();
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

                while (!sendQueue.isEmpty()) {
                    String messageString = sendQueue.remove();

                    var message = parser.matcher(messageString);
                    switch (message.group()) {
                        case DISCONNECT:
                            this.disconnect();
                            break;

                        case MESSAGE:

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

    public void connect(Server serverDetails) {
        disconnect();

        while (serverConnectionThread.isAlive()) {
        }

        serverConnectionThread = new Thread(() -> serverThreadFn());
        sendQueue = (Queue<String>) new ArrayList<String>();
        serverConnectionThread.start();
    }

    public void disconnect() {
        this.serverThreadRunning = false;
    }
}
