package client.managers;

import client.classes.Server;
import com.google.gson.stream.JsonToken;
import org.junit.Test;

import java.io.*;
import java.net.SocketException;
import java.nio.charset.Charset;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.*;


import static org.junit.Assert.*;

class Worker implements Runnable {

    private final Socket connection;
    private InputStreamReader in;
    private OutputStreamWriter out;

    @Override
    public void run() {
        try {
            while (true) {
                char[] buffer = new char[512];
                in.read(buffer);
                out.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Worker(Socket connection) {
        this.connection = connection;

        try {
            in = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));
            out = new OutputStreamWriter(new BufferedOutputStream(connection.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class TestServer extends Thread {

    UUID uuid = UUID.randomUUID();
    String name = "testserver";

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

                if ("!info:".equals(response)) {
                    out.writeUTF("!success: name:testing owner:UNKNOWN");
                    out.flush();
                    connection.close();
                } else {
                    threadPool.execute(new Worker(connection));
                }
            } catch (IOException e) {

            }
        }
    }
}

public class NetworkManagerTest {

    public NetworkManagerTest() {

    }

    @Test
    public void objectCreated() {
        var netmgr = new NetworkManager();
        assertNotNull(netmgr);
    }

    @Test
    public void getServerDetails() throws IOException {
        TestServer server = new TestServer();

        var netmgr = new NetworkManager();
        assertNotNull(netmgr);


        Server a = netmgr.getServerDetails("127.0.0.1");
        assertNotNull(a);

        server.stopServer();
    }
}
