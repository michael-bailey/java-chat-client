package client.managers;

import client.classes.Contact;
import client.classes.Server;
import client.managers.Delegates.INetworkManagerDelegate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import server.JavaServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class NetworkManagerTest implements INetworkManagerDelegate {

    private static NetworkManager networkManager;
    private static JavaServer server;


    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    @BeforeClass
    public static void setupServers() throws IOException {
        server = new JavaServer();
        server.start();

        networkManager = new NetworkManager();
        networkManager.ptpStart();
    }

    @AfterClass
    public static void teardownServers() throws InterruptedException {
        networkManager.ptpStopThreads();
        server.stop();
    }

    @Test
    public void objectCreated() {

        assertNotNull(networkManager);
    }

// MARK: tests for auxiliary functions.

    @Test
    public void getServerDetails() throws IOException {
        assertNotNull(networkManager);
        Server a = networkManager.getServerDetails("127.0.0.1");
        assertNotNull(a);
    }

// MARK: tests for the ptp networking.

    @Test
    public void ptpThreadingTest() throws InterruptedException {

        // start the network manager and start the ptp functions.
        assertNotNull(networkManager);

        // create thread pool of 128 mock clients.
        ExecutorService clientPool = Executors.newFixedThreadPool(128);

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
            assertNotNull(networkManager);

            Socket mockClient = new Socket("127.0.0.1", 6001);
            var in = new DataInputStream(mockClient.getInputStream());
            var out = new DataOutputStream(mockClient.getOutputStream());

            // this will have built in checks in the actual client
            assertEquals("?request:", in.readUTF());

            System.out.println("!message: uuid:"+uuid+" username:\""+username+"\" message:\""+message+"\" checksum:"+checksum);
            out.writeUTF("!message: uuid:"+uuid+" username:\""+username+"\" message:\""+message+"\" checksum:"+checksum);

            assertEquals("!success:", in.readUTF());

            networkManager.ptpStopThreads();
            Runtime.getRuntime().gc();
            sleep(1000);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
