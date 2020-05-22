package client.managers;

import client.classes.Contact;
import client.classes.Server;
import client.managers.Delegates.INetworkManagerDelegate;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

class Worker implements Runnable {

    private Socket connection;
    private DataInputStream in;
    private DataOutputStream out;

    private boolean connected = false;

    private UUID uuid = null;
    private String username = null;
    private String ipaddress = null;

    public Worker(Socket connection) {
        this.connection = connection;

        try {
            in = new DataInputStream(connection.getInputStream());
            out = new DataOutputStream(connection.getOutputStream());

            out.writeUTF("?details:");

            this.ipaddress = connection.getLocalAddress().getHostAddress();

            Iterator<Object> tokenizer = new StringTokenizer(in.readUTF()).asIterator();
            String response = (String) tokenizer.next();
            if (response.equals("!details:")) {
                while(tokenizer.hasNext()) {
                    String[] parameter = (String[]) ((String) tokenizer.next()).split(":");
                    switch (parameter[0]) {
                        case "username":
                            this.username = parameter[1];
                            break;

                        case "uuid":
                            this.uuid = UUID.fromString(parameter[1]);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (connected) {
            try {
                String request = in.readUTF();
                switch (request) {
                    case "?disconnect:":
                        this.connected = false;
                        out.writeUTF("!ending:");
                        break;
                    default:
                        out.writeUTF("!unknown:");
                        break;
                }
                sleep(1000);
            } catch (IOException e) {
                e.printStackTrace();
                this.connected = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateClientList(ArrayList<Worker> clientList) {
        try {
            out.writeUTF("!clientUpdate:");
            for (Worker i : clientList) {
                out.writeUTF("!client: username:" + i.username + "uuid:" + i.uuid + "ipaddress:" + i.ipaddress);
            }
            out.writeUTF("!end:");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class TestServer extends Thread {
    String name = "testserver";
    String owner = "michael-bailey";

    private ArrayList<Worker> clientList = new ArrayList();

    private final ServerSocket srvSock;
    private final ExecutorService threadPool;
    private Boolean running = false;

    public TestServer() throws IOException{
        this.running = true;
        threadPool = Executors.newFixedThreadPool(128);
        srvSock = new ServerSocket(6000);
        this.start();
    }

    public void stopServer() {
        synchronized (this.running) {
            this.running = false;
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                Socket connection = srvSock.accept();

                DataInputStream in = new DataInputStream(connection.getInputStream());
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());

                // decide if the connection will be kept or discarded
                out.writeUTF("?request:");
                out.flush();

                String response = in.readUTF();

                switch (response) {
                    case "!info:":
                        out.writeUTF("!success: name:"+this.name+" owner:"+this.owner+"");
                        out.flush();
                        connection.close();
                        break;

                    case "!connect:":
                        Worker worker = new Worker(connection);
                        this.clientList.add(worker);
                        threadPool.execute(worker);

                        for (Worker i : this.clientList) {
                            i.updateClientList(this.clientList);
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Worker> getClientList() {
        return clientList;
    }
}

public class NetworkManagerTest implements INetworkManagerDelegate {

    public NetworkManagerTest() {

    }

    @Test
    public void objectCreated() {
        var netmgr = new NetworkManager();
        assertNotNull(netmgr);
    }

// MARK: tests for auxiliary functions.

    @Test
    public void getServerDetails() throws IOException {
        TestServer server = new TestServer();

        var netmgr = new NetworkManager();
        assertNotNull(netmgr);

        Server a = netmgr.getServerDetails("127.0.0.1");
        assertNotNull(a);

        server.stopServer();
    }

// MARK: tests for the ptp networking.

    @Test
    public void ptpThreadingTest() {

        // start the network manager and start the ptp functions.
        var netmgr = new NetworkManager(this);
        assertNotNull(netmgr);
        assertTrue(netmgr.ptpStart());

        // create thread pool of 128 mock clients.
        Executor clientPool = Executors.newFixedThreadPool(128);

        // create and start all 128 mock clients
        for (int i = 0; i < 128; i++) {

            // execute new mock client
            clientPool.execute(() -> {
                try {
                    // connect to the network manager and get streams
                    var mockClient = new Socket("127.0.0.1", 6001);
                    var in = new DataInputStream(mockClient.getInputStream());
                    var out = new DataOutputStream(mockClient.getOutputStream());

                    // read the reply from the network manager
                    String reply = in.readUTF();
                    assertEquals(reply.toString(), "?request:");

                    // ensure the network manager is working and write test
                    if (reply.equals("?request:")) {
                        out.writeUTF("!test:");

                        // assert it was a success. and close
                        assertEquals("!success:", in.readUTF());
                        mockClient.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        netmgr.ptpStopThreads();
        Runtime.getRuntime().gc();
    }

    @Test
    public void getUUID() {
        UUID uuid = UUID.randomUUID();
        System.out.println("uuid = " + uuid);
    }

    @Test
    public void ptpMessageSend() throws NoSuchAlgorithmException {

        // defining the message
        var username = "michael-bailey";
        var uuid = UUID.fromString("15e54bc0-b82d-4b62-9d4a-ba86a2d55bd4");
        var message = "this is a test message used, for testing";
        var checksum = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(message.getBytes()));

        // print the variables
        System.out.println("username = " + username);
        System.out.println("uuid = " + uuid);
        System.out.println("message = " + message);
        System.out.println("checksum = " + checksum);

        try {
            // start the network manager and start the ptp functions.
            var netmgr = new NetworkManager(this);
            assertNotNull(netmgr);
            assertTrue(netmgr.ptpStart());

            Socket mockClient = new Socket("127.0.0.1", 6001);
            var in = new DataInputStream(mockClient.getInputStream());
            var out = new DataOutputStream(mockClient.getOutputStream());

            // this will have built in checks in the actual client
            assertEquals("?request:", in.readUTF());

            System.out.println("!message: uuid:"+uuid+" username:\""+username+"\" message:\""+message+"\" checksum:"+checksum);
            out.writeUTF("!message: uuid:"+uuid+" username:\""+username+"\" message:\""+message+"\" checksum:"+checksum);

            assertEquals("!success:", in.readUTF());

            netmgr.ptpStopThreads();
            Runtime.getRuntime().gc();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// MARK: tests for server connections

    /*
    @Test
    public synchronized void connectToAndDisconnectFromServer() throws IOException {
        TestServer server = new TestServer();
        server.start();
    }

    @Test
    public void changeServer() {

    }

    @Test
    public void sendMessage() {

    }
    */

// MARK: network delegate methods used for testing

    @Override
    public void ptpReceivedMessage(HashMap<String, String> data) {
        System.out.println(data);
        assertEquals("michael-bailey", data.get("username"));
    }

    @Override
    public void stdReceivedMessage() {

    }

    @Override
    public Contact[] updateClientList() {
        return new Contact[0];
    }
}
