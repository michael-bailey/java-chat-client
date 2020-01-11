package baselib.classes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.google.common.collect.Iterators;

import baselib.managers.NetworkManager;

/**
 * NetworkService is to be extended by other classes. provides a default way of handling incoming messages
 * replace the constructor to create the relevent interfaces for communication
 * replace the run function to scan through the Queue and handle data.
 * 
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */
public class NetworkService implements Runnable {

    /**
     * a unique name for the service
     * @since 1.0
     */
    public String serviceName;

    /**
     * a way of the service to communicate with the manager
     * @since 1.0
     */
    private NetworkManager networkManager;

    /**
     * A thread that should constantly check for incoming and outgoing data
     * @since 1.0
     */
    private Thread thread;

    /**
     * a boolean stating wether the service is running for use in the run function
     */
    private boolean running;
    
    /**
     * initalises the service by setting up its name, its callback, and its daemon.
     * @param serviceName a unique name used for identifying a service
     * @param networkManager a reference to the network manager
     * @since 1.0
     */
    NetworkService(String serviceName, NetworkManager networkManager) {
        this.serviceName = serviceName;

        this.networkManager = networkManager;

        this.thread = new Thread(this);
        this.thread.setDaemon(true);
    }

    /**
     * get the running state of the service.
     * @since 1.0
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * gets the name of the service.
     * @since 1.0
     */
    public String getString() {
        return this.serviceName;
    }

    public void send(Object object) {

    }

    /**
     * sets the thread to start
     * @since 1.0
     */
    public void start() {
        this.running = true;
        this.thread.start();
        System.out.println("the service has been started.");
    }

    /**
     * sets the thread to stop
     * @since 1.0
     */
    public void stop() {
        this.running = false;
        System.out.println("the service has been stopped.");
    }

    /**
     * this should be overwritten with the code to handle any incoming or outgoing data.
     * @since 1.0
     */
    private void executor() {
        System.out.println("executed");
    }

    /**
     * this runs the executor function for the period that the thread is set to run.
     * @since 1.0
     */
    @Override
    public void run() {
        while(this.running) {
            System.out.println("the service is running.");
            this.executor();
        }
    }
}