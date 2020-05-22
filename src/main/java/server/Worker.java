package server;

import client.enums.PROTOCOL_MESSAGES;

import javax.jmdns.impl.tasks.state.Prober;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class Worker implements Runnable {

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
        try {
            while (connected) {

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

            }
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
            this.connected = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void updateClientList(ArrayList<Worker> clientList) {
        try {
            out.writeUTF("!clientUpdate:");
            for (Worker i : clientList) {
                out.writeUTF("!client: username:" + i.username + "uuid:" + i.uuid + "ipaddress:" + i.ipaddress);
            }
            out.writeUTF("!success:");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnet() throws IOException {
        out.writeUTF(String.valueOf(PROTOCOL_MESSAGES.DISCONNECT));
        this.connected = false;
    }
}
