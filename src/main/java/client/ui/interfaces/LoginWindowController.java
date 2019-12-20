package client.ui.interfaces;

public interface LoginWindowController {
    void LoginDidPass();
    void LoginDidFail();
    void LoginDidCreateUser();
    void LoginDidCreateUserFailed();
}