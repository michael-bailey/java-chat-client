package basekit;

import basekit.DataBus;
import basekit.interfaces.DataBusClient;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class DataBusTest {

    public DataBusTest() {
        
    }

    @Test
    public void testIfSingleton() {
        DataBus bus = DataBus.getInstance();
        assertEquals(bus, DataBus.getInstance());
    }

    @Test
    public void testObjectConnecting() {

    }

    @Test
    public void testObjectDisconnecting() {

    }

    @Test 
    public void testCallAndResponse() {

    }
}

class DataBusClient1 implements DataBusClient {

	@Override
	public void call(DataBusClient caller, String message, Object params) {
		switch (message) {
            case "find":
                caller.respond(this, "found none.");
                break;
        
            default:
                caller.respond(this, "found none.");
                break;
        }
	}

    @Override
    public void respond(DataBusClient responder, String result) {
        System.out.println(responder.toString() + " responded with" + result);
    }
}

class DataBusClient2 implements DataBusClient {

    @Override
    public void call(DataBusClient caller, String message, Object params) {

    }

    @Override
    public void respond(DataBusClient responder, String result) {

    }

}