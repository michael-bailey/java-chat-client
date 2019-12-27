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
        System.out.println(a);
        assertEquals(a != null, a == null);
    }

    @Test
    public void testCreation() {
        DataManager a = new DataManager();
        assertEquals(true, a.createNew("test", "Password1"));
    }

    public void testUnlockingOfFile() {

    }
    
}