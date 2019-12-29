package client.managers;

import client.ui.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

public class DataManagerTest {

    public DataManagerTest() {

    }

    @Test
    public void testIfCreated() {
        DataManager a = new DataManager();
        System.out.println(a.toString());
        assertTrue(a instanceof DataManager);
    }

    @Test
    public void testCreation() {
        new File("test.dat").delete();
        DataManager a = new DataManager();
        assertTrue(a instanceof DataManager);
        boolean b = a.createNew("test", "testKey");
        assertTrue(b);
    }


    public void testUnlockingOfFile() {

    }
    
}