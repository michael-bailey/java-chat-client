package io.github.michael_bailey.client.managers;

import io.github.michael_bailey.client.Delegates.Interfaces.INetworkManagerDelegate;
import io.github.michael_bailey.client.StorageDataTypes.Account;
import io.github.michael_bailey.client.StorageDataTypes.Contact;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import io.github.michael_bailey.java_server.classes.JavaServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NetworkManagerTest implements INetworkManagerDelegate {

    private static NetworkManager networkManager;
    private static INetworkManagerDelegate testNetworkDelegate;
    private static Account account;
    private static JavaServer server;

    @BeforeClass
    public static void setupServers() throws IOException {
        server = new JavaServer();
        server.start();

        account = new Account.Builder()
                .setUsername("Michael-bailey")
                .setEmail("mickyb18@.gmail.com")
                .build();
        
        testNetworkDelegate = new INetworkManagerDelegate() {
            @Override
            public void ptpReceivedMessage(HashMap<String, String> data) {
                assertEquals("michael-bailey" , data.get("name"));
            }

            @Override
            public void stdReceivedMessage(HashMap<String, String> data) {

            }

            @Override
            public void updateClientList(ArrayList<Contact> contacts) {
                for (Contact i : contacts) {
                    System.out.println("i = " + i);
                }
            }
        };

        networkManager = new NetworkManager(account, testNetworkDelegate);
        networkManager.start();
    }

    @AfterClass
    public static void teardownServers() throws InterruptedException {
        networkManager.stop();
        server.stop();
    }

    @Test
    public void objectCreated() {
        assertNotNull(networkManager);
    }

// MARK: tests for auxiliary functions.

// TODO fix test.
/*
    @Test
    public void getServerDetails() throws IOException {
        System.out.println("connecting");
        Server a = networkManager.getServerDetails("127.0.0.1");
        System.out.println("got server");
        assertNotNull(a);
    }
 */


// MARK: tests for the ptp networking.

    @Test
    public void ptpThreadingTest() throws InterruptedException {

        // start the network manager and start the ptp functions.
        assertNotNull(networkManager);

        // create thread pool of 128 mock clients.
        ExecutorService clientPool = Executors.newFixedThreadPool(128);

        // create and start all 128 mock clients
        for (int i = 0; i < 128; i++) {

            // execute new mock io.github.michael_bailey.client
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

            // this will have built in checks in the actual io.github.michael_bailey.client
            assertEquals("?request:", in.readUTF());

            System.out.println("!message: uuid:"+uuid+" username:\""+username+"\" message:\""+message+"\" checksum:"+checksum);
            out.writeUTF("!message: uuid:"+uuid+" username:\""+username+"\" message:\""+message+"\" checksum:"+checksum);

            assertEquals("!success:", in.readUTF());

            networkManager.stop();
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
    public synchronized void connectToAndDisconnectFromServer() throws IOException, InterruptedException {
        Server serverDetails = networkManager.getServerDetails("127.0.0.1");


        networkManager.connect(serverDetails);
        sleep(1000);
        networkManager.disconnect();

    }
    */

    /*
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
    public void stdReceivedMessage(HashMap<String, String> data) {

    }

    @Override
    public void updateClientList(ArrayList<Contact> contacts) {

    }
}
