package BaseKit;

import BaseKit.DataTypes.Notification;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class NotificationCenter extends Object {

    // static members
    private static NotificationCenter defaultCenter = null;

    // instance members
    private HashMap<String, ArrayList<ArrayList<Object>>> registeredNotifications = null;

    public static NotificationCenter getDefaultCenter() {
        if (defaultCenter == null) {
            defaultCenter = new NotificationCenter();
        }
        return defaultCenter;
    }

    public void addReciever(Object reciever, Method callingMethod, String notificationName) {

        // create the notification definition in the hashmap if it doesn't exist.
        if (this.registeredNotifications.containsKey(notificationName)) {
            this.registeredNotifications.put(notificationName, new ArrayList<ArrayList<Object>>());
        }

        // create the array with the method and the object
        ArrayList<Object> tmpArray = new ArrayList<>();
        tmpArray.add(reciever);
        tmpArray.add(callingMethod);
        this.registeredNotifications.get(notificationName).add(tmpArray);
    }

    public void removeReciever(Object reciever, String notificationName) {

    }

    public void sendNotification(Object sender, String notificationName, Object data) {

    }
}