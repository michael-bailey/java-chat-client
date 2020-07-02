package io.github.michael_bailey.client.managers.NetworkModules;

import io.github.michael_bailey.client.Delegates.Interfaces.IServerModuleDelegate;
import io.github.michael_bailey.client.models.Account;
import io.github.michael_bailey.client.models.Server;
import io.github.michael_bailey.java_server.classes.JavaServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

// these require a compiled version of the server running
public class ServerModuleTest implements IServerModuleDelegate {

    private static Account account;
    private static JavaServer server;
    private static ServerModule serverModule;

    @BeforeClass
    public static void setupServers() throws IOException {
        server = new JavaServer();
        server.start();

        account = new Account();
        account.displayName = "bob";
        account.uuid = UUID.randomUUID();

        serverModule = new ServerModule(account);
    }

    @Test
    public void testGetServerDetails() {
        System.out.println("getting details");
        Server serverDetails = serverModule.getServerDetails("127.0.0.1");


        System.out.println("testing details...");
        assertEquals("testserver", serverDetails.displayName);
        System.out.println("testing details...");
        assertEquals("michael-bailey", serverDetails.ownerEmail);
        System.out.println("details tested");
    }

    @Test
    public void testConnectReceiveClientsAndDisconnect() throws InterruptedException {
        var serverDetails = serverModule.getServerDetails("127.0.0.1");
        serverModule.connect(serverDetails);
        assertTrue(serverModule.isConnected());


        sleep(10000);
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

}
