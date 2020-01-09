package client.interfaces;

public interface LoginWindowController {
    default void LoginRequest(String username, String Password) {
        System.out.println("<LoginRequest Called>");
    }

    default void LoginRequest() {
        System.out.println("<LoginRequest Called>");
    }

    default void LoginCreateUser(String username, String Password) {
        System.out.println("<LoginCreateUser Called>");
    }

    default void LoginCreateUser() {
        System.out.println("<LoginCreateUser Called>");
    }
}