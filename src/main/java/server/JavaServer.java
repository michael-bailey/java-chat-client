package server;



import client.enums.PROTOCOL_MESSAGES;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static client.enums.PROTOCOL_MESSAGES.*;

public class JavaServer {
    String name = "testserver";
    String owner = "michael-bailey";

    private final ArrayList<Worker> clientList = new ArrayList();

    private final ServerSocket srvSock;
    private final ExecutorService threadPool;
    private Boolean running = false;

    private final Thread thread;

    public JavaServer() throws IOException {
        thread = new Thread(() -> run());
        threadPool = Executors.newFixedThreadPool(128);
        srvSock = new ServerSocket(6000);
    }

    public void start() {
        running = true;
        thread.start();
    }

    public void stop() {
        synchronized (this.running) {
            this.running = false;
        }
    }

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
                    case INFO:
                        out.writeUTF("!success: name:"+this.name+" owner:"+this.owner+"");
                        out.flush();
                        connection.close();
                        break;

                    case CONNECT:
                        Worker worker = new Worker(connection);
                        this.clientList.add(worker);
                        threadPool.execute(worker);

                        for (Worker i : this.clientList) {
                            i.updateClientList(this.clientList);
                        }
                        break;
                    default:
                        System.out.println("client sent" + response);
                        connection.close();
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
