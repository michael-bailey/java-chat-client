package client.managers.NetworkModules;

import client.Delegates.Interfaces.IServerModuleDelegate;
import client.StorageDataTypes.Account;
import client.StorageDataTypes.Contact;
import client.StorageDataTypes.Server;



import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import server.classes.JavaServer;

import java.io.IOException;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class ServerModuleTest implements IServerModuleDelegate {

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
    public void testGetServerDetails() {
        System.out.println("getting details");
        Server serverDetails = serverModule.getServerDetails("127.0.0.1");
        System.out.println("testing details...");
        assertEquals("testserver", serverDetails.name);
        System.out.println("testing details...");
        assertEquals("michael-bailey", serverDetails.owner);
        System.out.println("details tested");
    }

    @Test
    public void testConnectReceiveClientsAndDisconnect() throws InterruptedException {
        var serverDetails = serverModule.getServerDetails("127.0.0.1");
        serverModule.connect(serverDetails);
        assertTrue(serverModule.isConnected());
        sleep(1000);
        serverModule.disconnect();
        assertFalse(serverModule.isConnected());

    }

    @AfterClass
    public static void teardownServer() {
        server.stop();
        server = null;
        serverModule = null;
        account = null;
    }

    // MARK: delegate methods

    @Override
    public void serverWillConnect() {
        System.out.println("server is attempting a connection");
    }

    @Override
    public void serverDidConnect() {
        System.out.println("server has connected");
    }

    @Override
    public void serverWillUpdateClients() {
        System.out.println("updating clients");
    }

    @Override
    public void serverDidUpdateClients(Contact[] clients) {
        assertNotNull(clients);
    }
}
