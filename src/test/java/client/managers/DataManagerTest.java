package client.managers;

import org.junit.Test;

import java.io.File;
import java.io.Serializable;


import static org.junit.Assert.*;

public class DataManagerTest {

    public DataManagerTest() {  }

    @Test
    public void filenameChecksValid() {
        String[] correctFilenames = {
                "filename",
                "Filename",
                "Filename12345",
                "123124Filename",
                "filenamE23"
        };

        String[] wrongFilenames = {
                "File!",
                "file name",
                "/bin/filename",
                "/usr/bin/filename",
                "../../../../../../../filename"
        };

        int count = 0;
        for (String i : correctFilenames) {
            System.out.println("number" + count);
            assertTrue(DataManager.checkFileNameValid(i));
            count++;
        }

        count = 0;
        for (String i : wrongFilenames) {
            System.out.println("number" + count);
            assertFalse(DataManager.checkFileNameValid(i));
            count++;
        }
    }

    @Test
    public void passwordChecksValid() {
        String[] correctPasswords = {
                "Password!2",
                "PAS!@£$5%Word",
                "/\\']pass45wOrd",
                "E21234567890@£$%^DFGedfgh$%^&*(*&%RFGBNgr4rfg"
        };

        String[] wrongPasswords = {
                "",
                "pass word not guessable",
                "pass",
                ")(87",
                "Pas2w)R",
                "asdfghjkjhgfrewrtgyhjkljhgfds",
                "ASDFGHJhgfdsadfghjkJHGFDSDFGHJKHGFDS",
                "3456789oIUHGFCXZASErfghnmJUHYtgrfDcvBNmkjuyHTf",
        };

        int count = 0;
        for (String i : correctPasswords) {
            System.out.println("number" + count);
            assertTrue(DataManager.checkPasswordValid(i));
            count++;
        }

        count = 0;
        for (String i : wrongPasswords) {
            System.out.println("number" + count);
            assertFalse(DataManager.checkPasswordValid(i));
            count++;
        }
    }

    @Test
    public void isObjectCreated() {
        DataManager a = new DataManager();
        assertTrue(a instanceof DataManager);
    }

    @Test
    public void doesCreateNewFile() {
        var filename = "testfile";
        var password = "validPassword!2";

        new File(filename + ".dat").delete();

        DataManager a = new DataManager();

        assertTrue(a instanceof DataManager);

        boolean b = a.createNew(filename, password);
        assertTrue(b);
        assertTrue(new File(filename + ".dat").exists());
    }

    @Test
    public void lockingFileAfterUnlock() {
        var filename = "testfile";
        var password = "validPassword!2";

        new File(filename + ".dat").delete();

        DataManager a = new DataManager();
        assertTrue(a.createNew(filename, password));
        assertTrue(a.lock());
    }

    @Test
    public void unlockingFile() {
        var filename = "testfile";
        var password = "validPassword!2";

        new File(filename + ".dat").delete();

        DataManager a = new DataManager();
        assertTrue(a.createNew(filename, password));
        assertTrue(a.lock());
        assertTrue(a.unlock(filename, password));
    }

    @Test
    public void savingDataDuringUnlock() {
        var filename = "testfile";
        var password = "validPassword!2";

        new File(filename + ".dat").delete();

        DataManager a = new DataManager();
        assertTrue(a.createNew(filename, password));

        new File(filename + ".dat").delete();

        assertTrue(a.save());
        assertTrue(a.lock());
    }

    @Test
    public void addingPlusRemovingAndGettingObject() {
        var filename = "testfile";
        var password = "validPassword!2";

        new File(filename + ".dat").delete();

        DataManager a = new DataManager();
        assertTrue(a.createNew(filename, password));

        class testObject implements Serializable {
            public String text = "hello world";
        }

        assertTrue(a.addObject("test", new testObject()));
        assertTrue(a.getObject("test") instanceof testObject);
        assertTrue(a.removeObject("test"));
        assertNull(a.getObject("test"));
    }

}
