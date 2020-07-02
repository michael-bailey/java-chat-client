package io.github.michael_bailey.client.managers.NetworkModules;

import io.github.michael_bailey.client.Delegates.Interfaces.IPTPWorkerDelegate;
import io.github.michael_bailey.client.Delegates.PTPWorkerDelegate;
import io.github.michael_bailey.java_server.Protocol.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static io.github.michael_bailey.java_server.Protocol.Command.*;


public class PTPWorker implements Runnable {

    private final IPTPWorkerDelegate delegate;
    private final Socket connection;

    public PTPWorker(Socket socket, IPTPWorkerDelegate delegate) {
        this.connection = socket;
        this.delegate = delegate;
    }

    public PTPWorker(Socket socket) {
        this.connection = socket;
        this.delegate = new PTPWorkerDelegate();
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            DataInputStream in = new DataInputStream(connection.getInputStream());

            out.writeUTF(Command.valueOf(Command.REQUEST).toString());
            Command command = valueOf(in.readUTF());

            HashMap<String, String> data = new HashMap<>();

            System.out.println("ptpWorker: command = " + command);
            System.out.println("ptpWorker: command.command = " + command.command);

            switch (command.command) {

                case MESSAGE:
                    this.delegate.willReceiveMessage();
                    out.writeUTF(valueOf(SUCCESS).toString());
                    this.delegate.didReceiveMessage();
                    return;

                case TEST:
                    this.delegate.willReceiveTest();
                    out.writeUTF(valueOf(SUCCESS).toString());
                    this.delegate.didReceiveTest();
                    return;

                default:
                    System.out.println("unknown request:");
                    System.out.println("command = " + command);
                    out.writeUTF(ERROR);
                    connection.close();
                    return;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
