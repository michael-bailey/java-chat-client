package client.data_types;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.WriteAbortedException;
import java.math.BigInteger;
import java.security.*;
import java.util.HashMap;

public class UserData {
    private static UserData instance;

    // the file which this object will be working on
    private File file;

    private String privateKey;
    private String publicKey;
    private String password;
    private String username;
    private String userID;
    private String salt;

    HashMap<String, String> storage;

    public static UserData getInstance() {
        if (UserData.instance == null) {
            UserData.instance = new UserData();
        }
        return UserData.instance;
    }

    private UserData() {
        String fileName = "./User.dat";
        file = new File(fileName);

        try {
            read();
        } catch (ClassCastException e) {
            System.out.println("class not found occurred");
            System.out.println("=== Stack Trace ===");
            e.printStackTrace();
            System.exit(1234589);
        } catch (IOException e) {
            System.out.println(" IOException occurred.");
            System.out.println("=== Stack Trace ===");
            e.printStackTrace();
            System.exit(1234589);
        } catch (ClassNotFoundException e) {
            System.out.println(" Class not found occurred.");
            System.out.println("=== Stack Trace ===");
            e.printStackTrace();
            System.exit(1234589);
        }
    }

    private void read() throws ClassCastException, IOException, ClassNotFoundException {
        if (file.exists()) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Object tmp = in.readObject();
            in.close();

            if (tmp instanceof HashMap) {
                this.storage = (HashMap<String, String>) tmp;

                this.privateKey = this.storage.get("privateKey");
                this.publicKey = this.storage.get("publicKey");
                this.username = this.storage.get("username");
                this.password = this.storage.get("password");
                this.userID = this.storage.get("userID");
                this.salt = this.storage.get("salt");
            } else {
                this.storage = new HashMap<>();
            }

        } else {
            createNewFile();
        }
    }

    private void write() throws IOException {

        this.storage.put("privateKey", this.privateKey);
        this.storage.put("publicKey", this.publicKey);
        this.storage.put("password", this.password);
        this.storage.put("username", this.username);
        this.storage.put("userID", this.userID);
        this.storage.put("salt", this.salt);

        if (file.exists()) {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this.storage);
            out.flush();
            out.close();
        } else {
            createNewFile();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this.storage);
            out.flush();
            out.close();
        }
    }

    private void createNewFile() throws IOException {
        if (file.createNewFile()) {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this.storage);
            out.flush();
            out.close();
        } else {
            System.exit(1234574321);
        }
    }
}