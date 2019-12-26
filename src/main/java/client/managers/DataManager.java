package client.managers;

import java.io.*;
import java.util.HashMap;

/**
 * DataManager
 */
public class DataManager {

    private String dataString;
    private String checkSum;
    private HashMap<String, Object> dataObject;

    // non-stored variables
    private boolean isLocked;
    private String key;

    private File fileHandle;

    // possible autoSave
    private Thread autoSaverDaemon;

    private class DataStore {
        public String dataString;
        public String checkSum;
        public HashMap<String, Object> dataObject;
    }

    public DataManager() {
        
        this.dataString = null;
        this.checkSum = null;
        this.dataObject = null;
        this.isLocked = true;
        this.key = null;
        this.fileHandle = null;
        this.autoSaverDaemon = null;
    }

    /* unlocks the data stored in a file.
     * loads the contents of a file
     * checks to see if the data is unlocked
     * if not then decrypts the data with the key
     * checks the data with the checksum to prevent tampering/ wrong key
     * loads the hashmap to a variable and sets a boolean var to say it is unlocked
     */ 
    public boolean unlock(String name, String key) {
        // create a file handle
        this.fileHandle = new File("./" + name + ".dat");

        // check if the file is a file and can be read
        if (this.fileHandle.exists() && this.fileHandle.isFile()) {

            try {
                ObjectInputStream tmp = new ObjectInputStream(new FileInputStream(this.fileHandle));
                DataStore tmpStore = (DataStore) tmp.readObject();

                this.dataString = null;
                this.checkSum = null;
                this.dataObject = null;


                this.isLocked = true;
                this.key = null;
                this.fileHandle = null;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
            return true;

        } else {
            return false;
        }
    }

    public boolean lock() {
        return false;
    }

    public Object getObject(String key) {
        return new HashMap<>();
    }

    public void addObject(String key, Object object) {

    }
}