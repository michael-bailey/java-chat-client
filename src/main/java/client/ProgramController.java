package client;

import java.nio.file.FileSystems;

import client.ui.preference_window.PreferencesWindow;
import client.ui.interfaces.LoginWindowController;
import client.ui.login_display.LoginWindow;
import client.ui.main_window.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;


public class ProgramController extends Application implements LoginWindowController {

    PreferencesWindow prefrenceWindow;
    MainWindow mainWindow;
    LoginWindow loginWindow;

    public static void main(String[] args) throws Exception {
        System.out.println(FileSystems.getDefault().getPath(".").toAbsolutePath());
        launch(args);

    }

    public void start(Stage stage) throws Exception {

        // create windows in memory
        this.prefrenceWindow = new PreferencesWindow();
        this.mainWindow = new MainWindow();
        this.loginWindow = new LoginWindow(this);

        loginWindow.show();

    }

    @Override
    public void LoginDidPass() {
        this.loginWindow.hide();
        this.mainWindow.show();

    }

    @Override
    public void LoginDidFail() {
        // TODO Auto-generated method stub

    }

    @Override
    public void LoginDidCreateUser() {
        // TODO Auto-generated method stub

    }

    @Override
    public void LoginDidCreateUserFailed() {
        // TODO Auto-generated method stub

    }
}
