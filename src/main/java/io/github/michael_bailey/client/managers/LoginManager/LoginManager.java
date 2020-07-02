package io.github.michael_bailey.client.managers.LoginManager;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class LoginManager<T extends Serializable> {

    private File usersFile = new File("~/.local/users.login");

    private HashMap<String, T> users = new HashMap<>();

    private String currentUser = null;

    public LoginManager() throws IOException {

        // if the file does not exist then
        if (!this.usersFile.exists()) {
            this.usersFile.createNewFile();
            this.usersFile.setReadable(true);
            this.usersFile.setWritable(true);
            this.usersFile.setExecutable(false);

            var out = new FileOutputStream(this.usersFile);
            var json = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            var string = json.toJson(this.users);
            out.write(string.getBytes());
        }


    }

    public T login() {
        if (this.currentUser != null) {

        }
        return null;
    }

    public boolean createUser() {
        return true;
    }

    public void removeUser(String userid) {
        this.users.remove(userid);
    }

    public boolean logout() {
        return true;
    }
}
