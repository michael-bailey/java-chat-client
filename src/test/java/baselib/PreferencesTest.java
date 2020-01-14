package baselib;

import baselib.legacy.Preferences;



import org.junit.*;


public class PreferencesTest {

    public PreferencesTest() {

    }

    @Test
    public void testIfSingleton() {
        Preferences prefs = Preferences.getInstance();
        Assert.assertEquals(prefs, Preferences.getInstance());
    }

    @Test
    public void testSettingAndGettingPreference() {
        Preferences prefs = Preferences.getInstance();
        prefs.setPreference("darkMode",true);
        Assert.assertEquals(prefs.getPreference("darkMode"), true);
    }

    @Test
    public void add_and_retrieve_object_preference() {
        Preferences prefs = Preferences.getInstance();
        prefs.setPreference("darkMode",true);
        Assert.assertEquals(prefs.getPreference("darkMode"), true);
    }

    @Test
    public void testSavedPreferences () {
        Preferences prefs = Preferences.getInstance();
        Assert.assertEquals(prefs.getPreference("darkMode"), true);
    }
}