package io.github.michael_bailey.client.managers.NetworkModules;

import io.github.michael_bailey.client.Delegates.Interfaces.IPTPModuleDelegate;
import io.github.michael_bailey.client.Delegates.Interfaces.IPTPWorkerDelegate;
import io.github.michael_bailey.client.Delegates.PTPModuleDelegate;
import io.github.michael_bailey.client.models.Account;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PTPModule implements IPTPWorkerDelegate {

    private final int listenPort = 6001;
    private final IPTPModuleDelegate delegate;
    private final Account account;

    private boolean running = false;

    // incoming connection parts
    ServerSocket incomingSocket;
    Thread incomingMainThread;
    ExecutorService incomingConnectionPool;


    public PTPModule(Account account, IPTPModuleDelegate delegate) {
        this.account = account;
        this.delegate = delegate;
    }

    public PTPModule(Account account) {
        this.account = account;
        this.delegate = new PTPModuleDelegate();
    }

    private void incomingThread() {
        while (running) {
            try {
                Socket connection = incomingSocket.accept();
                incomingConnectionPool.execute(new PTPWorker(connection, this));
            } catch (IOException e) {
                System.out.println("socket closed");
            }
        }
    }

    public boolean startIncoming() {
        try {
            incomingConnectionPool = Executors.newCachedThreadPool();
            incomingMainThread = new Thread(() -> this.incomingThread());
            incomingSocket = new ServerSocket(listenPort);

            running = true;


            incomingMainThread.start();
            return true;

        } catch (IOException e) {
            System.out.println("PTPModule: Error, " + e.getMessage());
            return false;
        }
    }

    public boolean stopIncoming() {
        try {
            running = false;

            incomingSocket.close();

            incomingMainThread.interrupt();
            incomingConnectionPool.shutdownNow();

            incomingMainThread = null;
            incomingConnectionPool = null;
            incomingSocket = null;

            return true;
        } catch (IOException e) {
            System.out.println("PTPModule: Error, " + e.getMessage());
            return false;
        }
    }

    public boolean startOutgoing() {
        return false;
    }

    public boolean stopOutgoing() {
        return false;
    }

    public boolean start() {
        return startIncoming() && startOutgoing();
    }

    public boolean stop() {
        return stopIncoming() && stopOutgoing();
    }


}
