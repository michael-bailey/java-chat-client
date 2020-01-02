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

    @Test
    public void testUnlockingFileCorrectPassword() {
        new File("helloWorld.dat").delete();
        DataManager a = new DataManager();
        a.createNew("helloWorld", "Password1234");
        a = null;
        a = new DataManager();
        boolean b = a.unlock("helloWorld", "Password1234");
        assertTrue(b);
    }

    @Test
    public void testUnlockingFileWrongPassword() {
        new File("helloWorld.dat").delete();
        DataManager a = new DataManager();
        a.createNew("helloWorld", "Password1234");
        a = null;
        a = new DataManager();
        boolean b = a.unlock("helloWorld", "password1234");
        assertTrue(!b);
    }

    @Test
    public void testUnlockingFileWrongName() {
        new File("helloWorld.dat").delete();
        DataManager a = new DataManager();
        a.createNew("helloWorld", "Password1234");
        a = null;
        a = new DataManager();
        boolean b = a.unlock("helloworld", "Password1234");
        assertTrue(!b);
    }

    
    @Test
    public void testAddingAndGettingValues() {
        new File("helloWorld.dat").delete();
        DataManager a = new DataManager();
        a.createNew("helloWorld", "Password1234");
        Object b = new Object();
        a.addObject("bob", b);
        assertEquals(b, a.getObject("bob"));
    }

    @Test
    public void testaddingNullValue() {
        new File("helloWorld.dat").delete();
        DataManager a = new DataManager();
        a.createNew("helloWorld", "Password1234");
    }

    @Test
    public void testGettingNonExistantKey() {
        new File("helloWorld.dat").delete();
        DataManager a = new DataManager();
        a.createNew("helloWorld", "Password1234");
    }

    @Test
    public void testLockingAndUnlockingFile() {
        new File("helloWorld.dat").delete();
        DataManager a = new DataManager();
        a.createNew("helloWorld", "Password1234");
    }

    @Test
    public void testForceSaveData() {
        new File("helloWorld.dat").delete();
        DataManager a = new DataManager();
        a.createNew("helloWorld", "Password1234");
    }


// TODO imlement this test for locking
/*
    public void testLockingOfFile() {
        new File("helloWorld.dat").delete();
        DataManager a = new DataManager();
        a.createNew("helloWorld", "Password1234");
        boolean b = a.lock();
        assertTrue(!b);
    }
*/
    
}