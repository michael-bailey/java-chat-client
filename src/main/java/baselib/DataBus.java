package baselib;


import java.util.*;

import baselib.interfaces.DataBusClient;

public class DataBus extends Object {

    // set this class to a singleton (only one bus per application)
    private static DataBus defaultCenter = null;

    // instance attributes 
    // list of all function names with an array of objects
    // that accept the function 
    private HashMap<String, ArrayList<DataBusClient>> clients;

    // this is called to get the instance of the DataBus
    public static DataBus getInstance() {
        if (defaultCenter == null) {
            defaultCenter = new DataBus();
        }
        return defaultCenter;
    }

    public DataBus() {
        this.clients = new HashMap<>();
    }

    public void register(DataBusClient object, String name) {
        if (this.clients.containsKey(name)) {
            for (int i = 0; i < this.clients.get(name).size() - 1; i++) {
                if (this.clients.get(name).get(i) == object) {
                    return;
                }
                this.clients.get(name).add(object);
            }
        } else {
            ArrayList<DataBusClient> tmp = new ArrayList<>();
            tmp.add(object);
            this.clients.put(name, tmp);
            tmp = null;
            
        }
    }

    public void deregister(DataBusClient object, String name) {
        this.clients.get(name).remove(object);
    }


    public HashMap<DataBusClient, String> call(String name, Object parameters) {

        HashMap<DataBusClient, String> results = new HashMap<>();

        Iterator<DataBusClient> i = this.clients.get(name).iterator();
        while (i.hasNext()) {
            DataBusClient a = i.next();
            results.put(a, a.call(name, parameters));
        }
        return results;
    }
}