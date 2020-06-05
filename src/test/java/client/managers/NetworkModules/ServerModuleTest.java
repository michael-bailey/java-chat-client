package client.managers.NetworkModules;

import client.StorageDataTypes.Account;
import client.StorageDataTypes.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import server.classes.JavaServer;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerModuleTest {

    private static Account account;
    private static JavaServer server;
    private static ServerModule serverModule;

    @BeforeClass
    public static void setupServers() throws IOException {
        server = new JavaServer();
        assertTrue(server.start());


        account = new Account.Builder()
                .setUsername("Michael-bailey")
                .setEmail("michael@email.com")
                .build();

        serverModule = new ServerModule();
    }

    @Test
    // todo fix this test somehow
    public void testGetServerDetails() {
        Server serverDetails;
        serverDetails = serverModule.getServerDetails("127.0.0.1");
        assertEquals("testserver", serverDetails.name);
        assertEquals("michael-bailey", serverDetails.owner);
    }

    @AfterClass
    public static void teardownServer() {
        server.stop();
        server = null;
        serverModule = null;
        account = null;
    }
}
