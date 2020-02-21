package baselib.managers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import baselib.classes.DataStore;

public class DataManagerTest {

    public DataManagerTest() {
        new DataStore();
    }

    @Test
    public void testIfCreated() {
        DataManager a = new DataManager();
        System.out.println(a.toString());
        assertTrue(a instanceof DataManager);
    }

    @Test
    public void testCreation() {
        new File("testtest.dat").delete();
        DataManager a = new DataManager();
        assertTrue(a instanceof DataManager);
        boolean b = a.createNew("testtest", "testtestKey");
        assertTrue(b);
    }

    @Test
    public void testCreationNullInputs() {
        new File("test.dat").delete();
        DataManager a = new DataManager();
        assertTrue(a instanceof DataManager);
        boolean b = a.createNew(null, null);
        assertTrue(!b);
    }

    @Test
    public void testCreationEmptyInputs() {
        new File("test.dat").delete();
        DataManager a = new DataManager();
        assertTrue(a instanceof DataManager);
        boolean b = a.createNew("", "");
        assertTrue(!b);
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
        boolean b = a.unlock("oof", "Password1234");
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
    public void testForceSaveData() {
        new File("helloWorld.dat").delete();
        DataManager a = new DataManager();
        a.createNew("helloWorld", "Password1234");
    }



    @Test
    public void testLockingOfFile() {
        int i = 0;
        System.out.println(i++);
        new File("helloWorld.dat").delete();
        System.out.println(i++);
        DataManager a = new DataManager();
        System.out.println(i++);
        boolean d = a.createNew("helloWorld", "Password1234");
        System.out.println(i++);
        assertTrue(d);
        System.out.println(i++);
        a.addObject("testObject", "hello world");
        System.out.println(i++);
        boolean b = a.lock();
        System.out.println(i++);
        assertTrue(b);
        System.out.println(i++);
        a.unlock("helloWorld", "Password1234");
        System.out.println(i++);
        String c = (String) a.getObject("testObject");
        System.out.println(i++);
        assertTrue(c.equals("hello world"));
        System.out.println(i++);
        b = a.lock();
        System.out.println(i++);
        assertTrue(b);
        System.out.println(i++);
        a.unlock("helloWorld", "Password1234");
        System.out.println(i++);
        c = (String) a.getObject("testObject");
        System.out.println(i++);
        assertTrue(c.equals("hello world"));
    }

}
