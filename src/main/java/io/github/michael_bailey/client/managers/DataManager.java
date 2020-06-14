package io.github.michael_bailey.client.managers;

import com.google.common.annotations.Beta;
import io.github.michael_bailey.client.managers.DataStore.DataStore;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashMap;

/**
 * Data Manager
 *
 *  this class privides a way to store objects using key value pairs 
 *  so that they can be stored securely in a file for later use.
 *
 * @version 1.0
 * @since 1.0
 * @author michael-bailey
 */
public class DataManager implements Closeable {

    private HashMap<String, Object> dataObject;

    final byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    final IvParameterSpec ivspec = new IvParameterSpec(iv);

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
    private final Thread autoSaverDaemon;

    /**
     * establishes inital state on creation
     * @since 1.0
     */
    public DataManager() {
        System.out.println(this);

        this.isLocked = true;
        this.secretKey = null;
        this.fileHandle = null;

        this.autoSaverDaemon = null;
    }

    /**
     * loads data stored in a file into the data manager.
     * @param name the name of the file that contains the data.
     * @param password the password used when encrypting the data.
     * @return true when loaded.
     * @since 1.0
     */
    public boolean unlock(String name, String password) {

		// check if the object is currently unlocked
        if (this.isLocked) {

            // create new file object
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
                    byte[] salt = decoder.decode(dataStore.salt);
                    byte[] checkSumArray = decoder.decode(dataStore.checkSum);
                    
                    // creating key.
                    keySpec = new PBEKeySpec(password.toCharArray(), salt, 256, 256);
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
                        this.dataObject = (HashMap<String, Object>) new ObjectInputStream(new ByteArrayInputStream(decryptedDataArray)).readObject();
                        this.saltString = dataStore.salt;
                        this.isLocked = false;
                        return true;
                    } else {
                        System.out.println("checksum failed");
                        return false;
                    }

                } catch (FileNotFoundException e) {
                    System.out.println("the file was not found");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (InvalidKeySpecException e) {
                    System.out.println("invalid keyspec exception");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (ClassNotFoundException e) {
                    System.out.println("class not found occurred");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    System.out.println("something horrible happened");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (NoSuchAlgorithmException e) {
                    System.out.println("algorithm not right");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                } catch (NoSuchPaddingException e) {
                    System.out.println("padding error (if you reached this stop messing with the code and undo)");
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
                    System.out.println("wrong password");
                } catch (InvalidAlgorithmParameterException e) {
                    System.out.println("this doesn't matter");
                    System.out.println("=== Stack Trace ===");
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * called when data is to be saved and the object be reset to its original state
     * @return true if it completed
     * @since 1.0
     */
    public boolean lock() {
		// check if the object is currently unlocked
        if (!this.isUnlocked()) {
            return false;
        }

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

            // add then to the dataStore
            dataStore.dataString = encoder.encodeToString(encryptedByteArray);
            dataStore.checkSum = checkSumString;
            dataStore.salt = this.saltString;

            FileOutputStream tmpOut = new FileOutputStream(this.fileHandle);
            new ObjectOutputStream(tmpOut).writeObject(dataStore);
            tmpOut.close();

            // set unlocked
            this.isLocked = !this.isLocked;
            this.dataObject = null;
            this.saltString = null;
            this.secretKey = null;
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
    }

    /**
     * creates a new data file and saves a blank hash map
     * @param name the name of the file that will be created.
     * @param password the pasword used when encrypting the data.
     * @return true when created and ready to use.
     * @since 1.0
     */
    public boolean createNew(String name, String password) {

        //if the datamanager is unlocked then a file can't be created.
        if (this.isUnlocked()) {
            return false;
        }

        // check if the name and password is null or empty
        if (name == null || password == null || password.equals("") || name.equals("")) {
            return false;
        }

        // do tests on the name and password to ensure they are valid
        if(!DataManager.checkFileNameValid(name) && !DataManager.checkPasswordValid(password)) {
            return false;
        }

        // if all preliminary data checks pass start creating the object structure

        // check if a file in that name exists
        this.fileHandle = new File("./" + name + ".dat");
        if (this.fileHandle.exists()) {
            return false;
        }

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
            this.saltString = encoder.encodeToString(salt);

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

            // add then to the dataStore
            dataStore.dataString = encoder.encodeToString(encryptedByteArray);
            dataStore.checkSum = checkSumString;
            dataStore.salt = encoder.encodeToString(salt);

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

    }

    /**
     * forces the data to be saved to the file
     * 
     * @return true if it was successful
     */
    public boolean save() {
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

                // add then to the dataStore
                dataStore.dataString = encoder.encodeToString(encryptedByteArray);
                dataStore.checkSum = checkSumString;
                dataStore.salt = this.saltString;

                FileOutputStream tmpOut = new FileOutputStream(this.fileHandle);
                new ObjectOutputStream(tmpOut).writeObject(dataStore);
                tmpOut.close();

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
        } else {
            return false;
        }
    }

    /**
     * this gets the data from a unlocked data store
     * @param key the key of where the object is stored
     * @return returns the new object that is decoded
     */
    public Object getObject(String key) {
        if (!this.isLocked) {
            return this.dataObject.get(key);
        } else {
            return null;
        }
    }

    /**
     * this adds an object to the unlocked
     * @param key the key where the object is to be stored
     * @param object the object thta is to be stored
     * @return boolean if the function finished correctly
     */
    public boolean addObject(String key, Object object) {
        if (!this.isLocked) {
            this.dataObject.put(key, object);
            return true;
        } else {
            return false;
        }
    }

    /**
     * removes the objecto stored at the key provided
     * technically sets it to null
     * @param key the key of the object to be removed
     * @return boolean if the function succeded
     */
    public Boolean removeObject(String key) {
        if (this.dataObject.containsKey(key)) {
            this.dataObject.remove(key);
            return true;
        }
        return false;
    }

    /**
     * used to close the file when it is lost to gc or the program is closing down
     */
    @Override
    public void close() {
        this.lock();
    }

    public boolean isUnlocked() {
        return !isLocked;
    }


    /**
     * checkFileNameValid
     *
     * this is a helper function that checks a string to see if it is a valid file name.
     * invalid file names include any string with a special character,
     *                            are less than 4 characters,
     *                            and contain spaces.
     *
     *
     *
     * @param filename the filename without the extension (that is added in other calls)
     * @return boolean whether the string is a valid file name.
     */
    public static boolean checkFileNameValid(String filename) {
        return filename.matches("^[a-zA-Z0-9]*$");
    }

    /**
     * checkFileNameValid
     *
     * this is a helper function that checks a string to see if it is a valid password.
     * valid passwords must include a special character,
     *                            be more than 8 characters,
     *                            contain at least one capital letter.
     *                            can be infinite in length (theoretically)
     *
     * credit to https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
     * for a regex to match any password
     *
     * @param password the files password
     * @return boolean whether the string is a valid password.
     */
    public static boolean checkPasswordValid(String password) {
        var valid = false;

        if (!(password.length() > 0)) {
            return false;
        }

        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@£$%^&*()_+\\-\"'/?`~=#€])(?=\\S+$).{8,}$");
    }
}
// these websites where used to kelp with the key generation
// https://stackoverflow.com/questions/3451670/java-aes-and-using-my-own-key
// https://javapapers.com/java/java-file-encryption-decryption-using-aes-password-based-encryption-pbe/
