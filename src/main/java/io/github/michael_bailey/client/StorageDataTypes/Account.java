package io.github.michael_bailey.client.StorageDataTypes;

import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Account implements Serializable {
    private static final long serialVersionUID = -7544412400442925489L;
    
    // account details.
    public final UUID uuid;
    public final String username;
    public final String email;

    // encryption keys
    public boolean encryptionAvaliable;
    public final Key publicKey;
    public final Key privateKey;

    // builder class
    public static class Builder {

        // account details.
        public UUID uuid;
        public String username;
        public String email;

        // encryption keys
        public boolean encryptionAvaliable;
        public Key publicKey;
        public Key privateKey;


        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder enableEncryption() {
            try {
                // this creates the key pair
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
                KeyPair kp = kpg.genKeyPair();
                publicKey = kp.getPublic();
                privateKey = kp.getPrivate();

                //creating a uuid
                this.uuid = UUID.randomUUID();
                return this;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return this;
            }
        }

        public Account build() {
            return new Account(this);
        }
    }

    /**
     *  this generates account info and encryption keys
     * @param builder the Builder used to build the account
     */
    private Account(Builder builder) {

        this.uuid = UUID.randomUUID();
        this.username = builder.username;
        this.privateKey = builder.privateKey;
        this.publicKey = builder.publicKey;
        this.email = builder.email;

    }

}
