package client.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * message
 * this defined all the data required for storing a message.
 * @author michael-local
 * @version 1.0
 * @since 1.0
 */
public class Message extends Object {

    // defining the date and time that the message was recieved
    LocalDate recievedDate;
    LocalTime recievedTime;

    // defining the message storage and checksum
    String message;
    String checkSum;

    public Message(String Message) {
        recievedDate = LocalDate.now();
        recievedTime = LocalTime.now();
    }


    public String getCheckSum() {
        return checkSum;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getRecievedDate() {
        return recievedDate;
    }

    public LocalTime getRecievedTime() {
        return recievedTime;
    }
}
