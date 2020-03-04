package client.classes;

import java.io.Serializable;

public class Contact implements Serializable {
    private static final long serialVersionUID = 365392247250419493L;
    
    public String contactName;
    public String contactUserID;

    public Contact(String contactName, String contactUserID) {
        this.contactName = contactName;
        this.contactUserID = contactUserID;
    }
}
