package baselib.interfaces;



// this will be implemented for all objects that need to communicate on the data bus.
public interface DataBusClient {

    // this will be part of every client to the data bus
    // this should implement all the functions of the class that
    // should be avaliable to be called by other sections of the code.

    // this should take data passed into it and parse it to run a function of the object
    public String call(String message, Object params);
}
