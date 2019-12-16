package basekit;


import java.util.*;

import basekit.interfaces.DataBusClient;

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


    public void call(DataBusClient caller, String name, Object parameters) {
        try {
            Iterator<DataBusClient> i = this.clients.get(name).iterator();
            while (i.hasNext()) {
                DataBusClient a = i.next();
                a.call(caller, name, parameters);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }
}