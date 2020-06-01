package client.managers.NetworkModules;

import client.classes.Account;
import org.junit.BeforeClass;
import server.classes.JavaServer;

import java.io.IOException;

public class ServerModuleTest {

    private static Account account;
    private static JavaServer server;
    private static ServerModule serverModule;

    @BeforeClass
    public static void setupServers() throws IOException {
        server = new JavaServer();
        server.start();

        account = new Account.Builder()
                .setUsername("Michael-bailey")
                .setEmail("mickyb18@.gmail.com")
                .build();

        serverModule = new ServerModule();
    }

}
