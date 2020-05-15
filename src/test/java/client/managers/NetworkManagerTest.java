package client.managers;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class NetworkManagerTest {

    public NetworkManagerTest() {

    }

    @Test
    public void objectCreated() {
        var netmgr = new NetworkManager();
        assertNotNull(netmgr);
    }

    public void serverConnect() {
        
        var netmgr = new NetworkManager();
        assertNotNull(netmgr);
    }
}
