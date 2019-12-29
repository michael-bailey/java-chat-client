package client.managers.data_types;

import java.io.Serializable;

public class DataStore implements Serializable {
    private static final long serialVersionUID = -5813387299140430418L;
    public String dataString;
    public String checkSum;
    public String salt;

    public DataStore() {
        
    }
}