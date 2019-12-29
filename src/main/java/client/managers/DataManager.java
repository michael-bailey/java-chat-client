package client.managers;

import client.managers.data_types.DataStore;

import java.beans.Encoder;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.checkerframework.checker.units.qual.s;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


/**
 * DataManager
 */

public class DataManager {

    private HashMap<String, Object> dataObject;

    // non-stored variables
    private boolean isLocked;
    private SecretKey key;
    private File fileHandle;

    // possible autoSave
    private Thread autoSaverDaemon;

    public DataManager() {


        this.isLocked = true;
        this.key = null;
        this.fileHandle = null;

        this.autoSaverDaemon = null;
    }

        /*
         * unlocks the data stored in a file. loads the contents of a file checks to see
         * if the data is unlocked if not then decrypts the data with the key checks the
         * data with the checksum to prevent tampering/ wrong key loads the hashmap to a
         * variable and sets a boolean var to say it is unlocked
         */
        public boolean unlock(String name, String key) {

            return false;
        }

        public boolean lock() {

            return false;
        }

        // these websites where used to kelp with the keygeneration
        // https://stackoverflow.com/questions/3451670/java-aes-and-using-my-own-key
        // https://javapapers.com/java/java-file-encryption-decryption-using-aes-password-based-encryption-pbe/
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
                        SecretKey secretKey;

                        // creating the salt
                        sr.nextBytes(salt);
                        String saltString = encoder.encodeToString(salt);

                        // assigning vars
                        keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
                        secretKey = secretKeyFactory.generateSecret(keySpec);
                        secretKey = new SecretKeySpec(secretKey.getEncoded(), "AES"); // this is the key

                        // initalise the cipher.
                        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

                        // write the object to a byte array
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        new ObjectOutputStream(byteArrayOutputStream).writeObject(this.dataObject);

                        // generate the checksum
                        byte[] checksum = messageDigest.digest(byteArrayOutputStream.toByteArray());
                        String checkSumString = encoder.encodeToString(messageDigest.digest(checksum));

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