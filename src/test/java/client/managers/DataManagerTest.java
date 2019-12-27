package client.managers;

import client.ui.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DataManagerTest {

    public DataManagerTest() {

    }

    @Test
    public void testIfCreated() {
        DataManager a = new DataManager();
        assertEquals(a != null, a == null);
    }

    
}