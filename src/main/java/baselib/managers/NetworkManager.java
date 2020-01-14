package baselib.managers;

import java.util.ArrayList;
import java.util.Iterator;

import baselib.classes.NetworkService;
import baselib.interfaces.NetworkManagerController;

/**
 * Network Manager
 * this class provides a way of managing multiple ways 
 * data can be sent to other computers.
 * 
 * @author michael-bailey
 * @version 1.0
 * @since 1.0
 */
public class NetworkManager extends Object {

    ArrayList<NetworkService> services;
    NetworkManagerController controller;

    public NetworkManager() {
        this.services = new ArrayList<>();

    }

    public void addService(NetworkService service) {
        this.services.add(service);
    }

    public boolean removeService(String serviceName) {
        int j = 0;
        Iterator<NetworkService> i = this.services.iterator();
        while (i.hasNext()) {
            NetworkService tmp =  i.next();
            if (tmp.serviceName == serviceName) {
                this.services.remove(j);
                return true;
            }
            j++;
        }
        return false;
    }

    public boolean start(String serviceName) {
        Iterator<NetworkService> i = this.services.iterator();
        while (i.hasNext()) {
            NetworkService tmp =  i.next();
            if (tmp.serviceName == serviceName) {
                tmp.start();
                return true;
            }
        }
        return false;
    }

    public boolean stop(String serviceName) {
        Iterator<NetworkService> i = this.services.iterator();
        while (i.hasNext()) {
            NetworkService tmp =  i.next();
            if (tmp.serviceName == serviceName) {
                tmp.stop();
                return true;
            }
        }
        return false;
    }

    public String getStatus(String serviceName) {
        Iterator<NetworkService> i = this.services.iterator();
        while (i.hasNext()) {
            NetworkService tmp =  i.next();
            if (tmp.serviceName == serviceName) {
                return tmp.getStatus();
            }
        }
        return "";
    }

    public void startAll() {
        Iterator<NetworkService> i = this.services.iterator();
        while (i.hasNext()) {
            i.next().start();
        }
    }

    public void stopAll() {
        Iterator<NetworkService> i = this.services.iterator();
        while (i.hasNext()) {
            i.next().start();
        }
    }

    public boolean send(String serviceName, Object data) {
        Iterator<NetworkService> i = this.services.iterator();
        while (i.hasNext()) {
            NetworkService tmp =  i.next();
            if (tmp.serviceName == serviceName) {
                tmp.send(data);
                return true;
            }
        }
        return false;
    }
}