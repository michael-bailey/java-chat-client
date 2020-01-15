package baselib.classes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * NetworkServiceTest
 */
public class NetworkServiceTest implements NetworkServiceController {

    public NetworkServiceTest() {
        
    }

    @Test
    public void testIfCreated() {
        NetworkService a = new NetworkService("testService", this);
        assertTrue(a instanceof NetworkService);
    }

    @Test
    public void testStart() {
        System.out.println(Thread.activeCount());
        NetworkService a = new NetworkService("testService", this);
    }
}