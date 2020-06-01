package client.managers.NetworkModules;

import client.managers.NetworkManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class PTPModule {

    private final Pattern parser = Pattern.compile("([?!])([a-zA-z0-9]*):|([a-zA-z]*):([a-zA-Z0-9\\-+\\[\\]{}_=/]+|(\"(.*?)\")+)");

    private final NetworkManager  manager;
    private final int listenPort;

    // ptp fields
    boolean peerToPeerRunning = true;
    private Thread ptpServerThread;
    private ServerSocket ptpServerSocket;
    private ExecutorService ptpThreadPool;

    public PTPModule(NetworkManager manager, int port) {
        this.manager = manager;
        this.listenPort = port;
    }

    /**
     * ptpThreadFn
     *
     * defines the function that will be called by the ptp thread
     *
     * this listens for a new connection to the client.
     *
     * @return a new socket returned from the accept call
     */
    private void ptpThreadFn() {
        while (this.peerToPeerRunning) {
            try {
                var ptpConnection = this.ptpServerSocket.accept();
                System.out.println("ptpConnection = " + ptpConnection);
                System.out.println("ptpThreadPool = " + ptpThreadPool);
                Runnable fn = () -> ptpThreadWorkerFn(ptpConnection);
                System.out.println("fn = " + fn);
                this.ptpThreadPool.execute(fn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ptpThreadWorkerFn
     *
     * this is called when a client connects to this process for peer to peer communication
     * it first asks for a request (simalar to a server).
     * after wich the client should send the data over.
     * to which it will respond with success for fail.
     *
     * @param socket the new connection
     */
    public void ptpThreadWorkerFn(Socket socket) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF("?request:");
            out.flush();

            String response = in.readUTF();

            Matcher matcher	= parser.matcher(response);

            String command = "";
            if (matcher.find()) {
                command = matcher.group();
                System.out.println("command = " + command);
            } else {
                return;
            }

            HashMap<String,String> data = new HashMap<>();
            switch(command) {

                case "!message:":
                    System.out.println("message");
                    // extract the key value pares to construct a hashmap
                    while (matcher.find()) {

                        // get the next arg and split by the colon
                        String argument = matcher.group();
                        String[] kvp = argument.split(":");

                        // check for quotes
                        if (kvp[1].charAt(0) == '\"') {
                            kvp[1] = kvp[1].substring(0, kvp[1].length()-1);
                        }

                        //add the data to the hashmap
                        data.put(kvp[0], kvp[1]);

                    }
                    out.writeUTF("!success:");
                    // close the socket and notify the delegate.
                    socket.close();
                    System.out.println("data = " + data);
                    manager.ptpReceivedMessage(data);
                    return;

                case "!test:":
                    // send the test was a success back to the client
                    System.out.println("testString");;
                    out.writeUTF("!success:");
                    socket.close();
                    return;

                default:
                    // close the socket as the data was wrong
                    System.out.println("error with received request.");
                    System.out.println("response = " + response);
                    socket.close();
                    return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalThreadStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * ptpStart
     *
     * this will create a new server socket and ptp thread
     * it the cretes a new thread pool for incoming connection's
     * then proceds to start the ptp thread.
     *
     */
    public boolean start() {
        try {
            // create the server socket
            ptpServerSocket = new ServerSocket(listenPort);

            // create the server thread
            ptpServerThread = new Thread(() -> this.ptpThreadFn());

            // creating a thread pool
            this.ptpThreadPool = newCachedThreadPool();

            // tell server thread wo run.
            this.peerToPeerRunning = true;
            this.ptpServerThread.start();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            this.peerToPeerRunning = false;

            return false;
        }
    }

    public void stop() {
        this.peerToPeerRunning = false;
        this.ptpServerThread.interrupt();

        this.ptpThreadPool = null;
        this.ptpServerSocket = null;
    }
}
