package client.StorageDataTypes;

import java.io.Serializable;
import java.security.*;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * message
 * this defined all the data required for storing a message.
 * @author michael-local
 * @version 1.0
 * @since 1.0
 */
public class Message extends Object implements Serializable {
    private static final long serialVersionUID = 1286698197022781409L;
    
    // defining the date and time that the message was recieved
    LocalDate date;
    LocalTime time;

    boolean isReceived;

    // defining the message storage and checksum
    String message;
    String checkSum;

    /**
     *
     * @param message
     */
    public Message(String message, boolean isReceived) {

        this.message = message;
        this.isReceived = isReceived;

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            this.checkSum = new String(digest.digest(message.getBytes()));

            date = LocalDate.now();
            time = LocalTime.now();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getCheckSum() {
        return checkSum;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getRecievedDate() {
        return date;
    }

    public LocalTime getRecievedTime() {
        return time;
    }
}
