package client.classes;

import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public class Account implements Serializable {
    private static final long serialVersionUID = -7544412400442925489L;
    
    // account details.
    UUID uuid;
    String username;

    // encryption keys
    Key publicKey;
    Key privateKey;

    /**
     *  this generates account info and encryption keys
     * @param username the Username of the user
     */
    public Account(String username) {

        this.username = username;
        try {
            // this creates the key pair
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();

            //creating a uuid
            this.uuid = UUID.randomUUID();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public String getUsername() {
        return username;
    }

    public boolean setUsername(String username) {
        if (username.isEmpty() || username.isBlank()) {
            return false;
        }
        this.username = username;
        return true;
    }

    public UUID getuuid() {
        return uuid;
    }

    public Key getPublicKey() {
        return publicKey;
    }

    public Key getPrivateKey() {
        return privateKey;
    }
}
