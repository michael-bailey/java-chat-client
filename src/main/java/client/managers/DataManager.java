package client.managers;

import client.managers.data_types.DataStore;

import java.beans.Encoder;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.annotations.Beta;

import org.checkerframework.checker.units.qual.s;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @version 1.0
 * @since 1.0
 * @author michael-bailey
 */
public class DataManager {

    private HashMap<String, Object> dataObject;

    byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    IvParameterSpec ivspec = new IvParameterSpec(iv);

    /**
     * used to tell if data int he file is loaded.
     */
    private boolean isLocked;

    /**
     * a store for the current files encryption key
     * @see javax.crypto.SecretKey
     */
    private SecretKey secretKey;

    /**
     * a string representation of the salt used in the key
     */
    private String saltString;

    /**
     * a file object for the current file
     */
    private File fileHandle;

    /**
     * this holds the thread used to autosave the data.
     */
    @Beta
    private Thread autoSaverDaemon;

    /**
     * nulls all internal state on creation
     */
    public DataManager() {

        this.isLocked = true;
        this.secretKey = null;
        this.fileHandle = null;

        this.autoSaverDaemon = null;
    }

    /**
     * loads data stored in a file into the data manager.
     * @param name the name of the file that contains the data.
     * @param password the pasword used when encrypting the data.
     * @return true when loaded.
     * @since 1.0
     */
    public boolean unlock(String name, String password) {
        if (this.isLocked) {
            // create new file
            this.fileHandle = new File("./" + name + ".dat");

            if (this.fileHandle.exists() && this.fileHandle.isFile()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(this.fileHandle);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    Base64.Decoder decoder = Base64.getDecoder();
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

                    // reserving names.
                    KeySpec keySpec;

                    // read object from the file.
                    DataStore dataStore = (DataStore) objectInputStream.readObject();
                    fileInputStream.close();

                    // decode from base64.
                    byte[] dataArray = decoder.decode(dataStore.dataString);
                    byte[] saltArray = decoder.decode(dataStore.salt);
                    byte[] checkSumArray = decoder.decode(dataStore.checkSum);
                    new PBE
                    // creating key.
                    keySpec = new PBEKeySpec(password.toCharArray(), saltArray, 256, 256);
                    this.secretKey = secretKeyFactory.generateSecret(keySpec);
                    this.secretKey = new SecretKeySpec(this.secretKey.getEncoded(), "AES"); // this is the key

                    // initalise the cipher.
                    cipher.init(Cipher.DECRYPT_MODE, this.secretKey, ivspec);

                    // decrypt the data.
                    byte[] decryptedDataArray = cipher.doFinal(dataArray);

                    // generate checksum.
                    byte[] checkSumOfData = messageDigest.digest(decryptedDataArray);

                    // compare checksum
                    if (new String(checkSumOfData).equals(new String(checkSumArray))) {
                        this.dataObject = (HashMap<String, Object>) new ObjectInputStream(
                                new ByteArrayInputStream(decryptedDataArray)).readObject();
                        this.isLocked = false;
                        this.saltString = saltString;
                        return true;
                    } else {
                        System.out.println("checksum failed");
                        return false;
                    }

                } catch (FileNotFoundException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (InvalidKeySpecException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (ClassNotFoundException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
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
                    System.out.println("Bad Padding (possible wrong password)");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (InvalidAlgorithmParameterException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    /*
    public boolean lock() {
        if (!this.isLocked) {
            DataStore dataStore = new DataStore();     
            try {
                // creating instances.
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                Base64.Encoder encoder = Base64.getEncoder();

                // initalise the cipher.
                cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, ivspec);

                // write the object to a byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                new ObjectOutputStream(byteArrayOutputStream).writeObject(this.dataObject);

                // generate the checksum
                byte[] checksum = messageDigest.digest(byteArrayOutputStream.toByteArray());
                String checkSumString = encoder.encodeToString(checksum);

                // encrypt the data
                byte[] encryptedByteArray = cipher.doFinal(byteArrayOutputStream.toByteArray());
                String encryptedDataString = encoder.encodeToString(encryptedByteArray);

                // add then to the dataStore
                dataStore.dataString = encryptedDataString;
                dataStore.checkSum = checkSumString;
                dataStore.salt = this.saltString;

                FileOutputStream tmpOut = new FileOutputStream(this.fileHandle);
                new ObjectOutputStream(tmpOut).writeObject(dataStore);
                tmpOut.close();

                // set unlocked
                this.isLocked = !this.isLocked;
                return true;
            } catch (NoSuchAlgorithmException e) {
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
            } catch (InvalidAlgorithmParameterException e) {
                System.out.println("class not found occurred");
                System.out.println("=== Stack Trace ===");
                e.printStackTrace();
                return false;
            }
            }else {
                return false;
            }
            
        }
        */

        

        /**
         * 
         */
        public boolean createNew(String name, String password) {
            if (this.isLocked) {
                // create new file
                this.fileHandle = new File("./" + name + ".dat");

                if (!this.fileHandle.exists()) {
                    try {
                        // creating objects
                        this.dataObject = new HashMap<>();
                        DataStore dataStore = new DataStore();
                        byte[] salt = new byte[16];

                        // creating instances.
                        SecureRandom sr = SecureRandom.getInstanceStrong();
                        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                        Base64.Encoder encoder = Base64.getEncoder();

                        // reserving names.
                        KeySpec keySpec;

                        // creating the salt
                        sr.nextBytes(salt);
                        String saltString = encoder.encodeToString(salt);

                        // assigning vars
                        keySpec = new PBEKeySpec(password.toCharArray(), salt, 256, 256);
                        this.secretKey = secretKeyFactory.generateSecret(keySpec);
                        this.secretKey = new SecretKeySpec(this.secretKey.getEncoded(), "AES"); // this is the key

                        // initalise the cipher.
                        cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, ivspec);

                        // write the object to a byte array
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        new ObjectOutputStream(byteArrayOutputStream).writeObject(this.dataObject);

                        // generate the checksum
                        byte[] checksum = messageDigest.digest(byteArrayOutputStream.toByteArray());
                        String checkSumString = encoder.encodeToString(checksum);

                        // encrypt the data
                        byte[] encryptedByteArray = cipher.doFinal(byteArrayOutputStream.toByteArray());
                        String encryptedDataString = encoder.encodeToString(encryptedByteArray);

                        // add then to the dataStore
                        dataStore.dataString = encryptedDataString;
                        dataStore.checkSum = checkSumString;
                        dataStore.salt = saltString;

                        FileOutputStream tmpOut = new FileOutputStream(this.fileHandle);
                        new ObjectOutputStream(tmpOut).writeObject(dataStore);
                        tmpOut.close();

                        // set unlocked
                        this.isLocked = !this.isLocked;
                        return true;

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
                    } catch (InvalidKeySpecException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                } else {
                    return false;
                }
            }
            return false;
        }

        public void Save() {

        }

        public Object getObject(String key) {
            return new HashMap<>();
        }

        public void addObject(String key, Object object) {

        }
}


// these websites where used to kelp with the keygeneration
// https://stackoverflow.com/questions/3451670/java-aes-and-using-my-own-key
// https://javapapers.com/java/java-file-encryption-decryption-using-aes-password-based-encryption-pbe/