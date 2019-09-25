package client;

import BaseKit.DataTypes.Notification;
import BaseKit.NotificationCenter;
import BaseKit.Preferences;
import com.google.gson.*;

public class ProgramController {

    // app properties
    private NotificationCenter center;
    private Preferences preferences;

    private ProgramController() throws Exception {

        // setting up app properties
        this.center = NotificationCenter.getDefaultCenter();
        this.preferences = Preferences.getPreferences();

        preferences.setPreference("hello world", "dfgdgddfgdfgdfgdfg");

        center.addReciever(this, i -> this.call1(), );
    }

    public static void main(String[] args) throws Exception {
        ProgramController program = new ProgramController();
    }

    private void call1() {
        System.out.println("this is a call1");
    }

    private void call2() {
        System.out.println("this is a call2");
    }
}