package client.managers;

import java.io.*;

import java.util.HashMap;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.security.*;

/**
 * DataManager
 */
public class DataManager {

    private HashMap<String, Object> dataObject;

    // non-stored variables
    private boolean isLocked;
    private SecretKeySpec key;
    private File fileHandle;

    // possible autoSave
    private Thread autoSaverDaemon;

    private class DataStore {
        public byte[] dataString;
        public byte[] checkSum;
    }
    public DataManager() {

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
        if (this.isLocked) {
            this.key = new SecretKeySpec(key.getBytes(), "AES");
            this.fileHandle = new File("./" + name + ".dat");

            if (this.fileHandle.exists() && this.fileHandle.isFile()) {
                try {
                    // read the contents of teh file.
                    ObjectInputStream tmp = new ObjectInputStream(new FileInputStream(this.fileHandle));
                    DataStore dataStore = (DataStore) tmp.readObject();
                    tmp.close();
                    
                    // create a temp cipher object and decrypt
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE, this.key);
                    byte[] dataString  = cipher.doFinal(dataStore.dataString);
                    
                    // check the data against the checksum
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    byte[] dataDigest = messageDigest.digest(dataString);

                    // if checksum passes then instanciate te object
                    if (dataDigest.toString().equals(dataStore.checkSum.toString())) {
                        this.dataObject = (HashMap<String, Object>) new ObjectInputStream(new ByteArrayInputStream(dataString)).readObject();
                    } else {
                        return false;
                    }
                    
                // I despise this section
                } catch (IOException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (ClassNotFoundException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (NoSuchAlgorithmException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (NoSuchPaddingException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (InvalidKeyException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (IllegalBlockSizeException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (BadPaddingException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                }
                // end of section.
                this.isLocked = false;
                return true;

            } else {
                return false;
            }
        }
        return false;
    }

    public boolean lock() {
        
        return false;
    }

    public boolean createNew(String name, String key) {
        if (!this.isLocked) {
            // create new file
            this.fileHandle = new File("./" + name + ".dat");

            if (!this.fileHandle.exists()) {
                try {
                    this.dataObject = new HashMap<>();
                    DataStore dataStore = new DataStore();

                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    this.key = new SecretKeySpec(key.getBytes(), "AES");
                    Cipher cipher = Cipher.getInstance("AES");

                    // initalise the cipher
                    cipher.init(Cipher.ENCRYPT_MODE, this.key);

                    // write the object to a byte array
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    new ObjectOutputStream(byteArrayOutputStream).writeObject(this.dataObject);

                    // generate the checksum
                    byte[] checksum = messageDigest.digest(byteArrayOutputStream.toByteArray());
                    dataStore.checkSum = messageDigest.digest(checksum);

                    // encrypt the data
                    byte[] encryptedByteArray = cipher.doFinal(byteArrayOutputStream.toByteArray());
                    dataStore.dataString = encryptedByteArray;
                    FileOutputStream tmpOut = new FileOutputStream(this.fileHandle);
                    new ObjectOutputStream(tmpOut).writeObject(dataStore);
                    tmpOut.close();

                } catch (NoSuchAlgorithmException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (InvalidKeyException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (NoSuchPaddingException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (IllegalBlockSizeException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (BadPaddingException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                }

            } else {return false;}
        }
        return false;
    }

    public void forceSave() {

    }

    public Object getObject(String key) {
        return new HashMap<>();
    }

    public void addObject(String key, Object object) {

    }
}