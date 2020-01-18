import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            server.ProgramController.main(args);
        } else {
            client.ProgramController.main(args);
        }
    }
}