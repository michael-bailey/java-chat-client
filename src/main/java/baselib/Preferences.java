package baselib;

import java.io.*;
import java.util.HashMap;

public class Preferences {
    private static Preferences instance;

    // properties for the preferences model
    private File file;
    private HashMap<String, Object> preferences;

    private Preferences() {
        String fileName = "./preferences.pref";
        file = new File(fileName);

        preferences = new HashMap<String, Object>();
        preferences.put("Version","1.0.0");

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

    public static Preferences getInstance() {
        if ( instance == null ) {
            instance = new Preferences();
        }
        return instance;
    }

    public void setPreference(String name, Object value) {
        preferences.put(name, value);
        try {
            write();
        } catch (IOException e) {
            System.out.println(" IOException occurred.");
            System.out.println("=== Stack Trace ===");
            e.printStackTrace();
            System.exit(1234589);
        }
    }

    public Object getPreference(String name) {
        return preferences.get(name);
    }

    public boolean hasPreference(String name) {
        return preferences.containsValue(name);
    }

    private void read() throws ClassCastException, IOException, ClassNotFoundException {
        if (file.exists()) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Object tmp = in.readObject();
            in.close();

            if (tmp instanceof HashMap) {
                preferences = (HashMap<String, Object>) tmp;
            } else {
                throw new ClassCastException();
            }

        } else {
            createNewFile();
        }
    }

    private void write() throws IOException{
        if (file.exists()) {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(preferences);
            out.flush();
            out.close();
        } else {
            createNewFile();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(preferences);
            out.flush();
            out.close();
        }
    }

    private void createNewFile() throws IOException {
        if (file.createNewFile()) {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(preferences);
            out.flush();
            out.close();
        } else {
            System.exit(1234574321);
        }
    }
}
