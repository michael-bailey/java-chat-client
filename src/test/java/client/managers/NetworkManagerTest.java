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

    private UUID uuid = null;
    private String username = null;
    private String ipaddress = null;

    public Worker(TestServer parent, Socket connection) {
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
        while (true) {

        }
    }


}

class TestServer extends Thread {

    UUID uuid = UUID.randomUUID();
    String name = "testserver";

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
                        out.writeUTF("!success: name:testing owner:UNKNOWN");
                        out.flush();
                        connection.close();
                        break;

                    case "!connect:":
                        Worker worker = new Worker(this, connection);
                        this.clientList.add(worker);
                        threadPool.execute(worker);

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

    @Test
    public void threadingTest() {

        var netmgr = new NetworkManager(this);
        assertNotNull(netmgr);

        assertTrue(netmgr.ptpStart());

        Executor clientPool = Executors.newFixedThreadPool(128);

        for (int i = 0; i < 128; i++) {
            clientPool.execute(() -> {
                try {
                    var mockClient = new Socket("127.0.0.1", 6001);
                    var in = new DataInputStream(mockClient.getInputStream());
                    var out = new DataOutputStream(mockClient.getOutputStream());
                    String reply = in.readUTF();
                    assertEquals(reply.toString(), "?request:");
                    if (reply.equals("?request:")) {
                        out.writeUTF("!test:");
                        assertEquals("!success:", in.readUTF());
                        mockClient.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void connectToAndDisconnectFromServer() {

    }

    @Test
    public void changeServer() {

    }

    @Test
    public void sendMessage() {

    }

// MARK: network delegate methods used for testing

    @Override
    public void ptpReceivedMessage(HashMap<String, String> data) {

    }

    @Override
    public void stdReceivedMessage() {

    }

    @Override
    public Contact[] updateClientList() {
        return new Contact[0];
    }
}
