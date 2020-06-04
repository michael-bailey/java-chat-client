package client.Protocol;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;;

public class Command {

    public final static Pattern parser = Pattern.compile("([?!])([a-zA-z0-9]*):|([a-zA-z]*):([a-zA-Z0-9\\-+\\[\\]{}_=/]+|(\"(.*?)\")+)");


    public final static String REQUEST = "?request:";
    public final static String INFO = "?info!";

    // results
    public final static String SUCCESS = "!success:";
    public final static String ERROR = "!error:";

    // connect commands
    public final static String CONNECT = "!connect:";
    public final static String DISCONNECT = "!disconnect:";

    // server commands
    public final static String UPDATE_CLIENTS = "!clientUpdate:";
    public final static String CLIENT = "!client:";
    public final static String TEST = "!test:";

    // ptp commands
    public final static String MESSAGE = "!message:";

    public final String command;

    private final HashMap<String, String> params;

    public Command(String command, HashMap<String, String> params) {
        this.command = command;
        this.params = params;
    }

    public Command(String command) {
        this.command = command;
        this.params = null;
    }

    public static Command valueOf(String string) {
        Matcher matcher = parser.matcher(string);

        matcher.find();
        String command = matcher.group();

        HashMap<String, String> params = new HashMap<>();
        while (matcher.find()) {
            String nextParam = matcher.group();

            var a = nextParam.split(":", 2);
            params.put(a[0], a[1]);
        }

        return new Command(command, params);
    }

    @Override
    public String toString() {
        String paramString = new String();
        if (params != null) {
            params.forEach((key, value) -> {
                paramString.concat(" " + key + ":" + value);
            });
            return command + paramString;
        } else {
            return command;
        }
    }

    public String getParam(String key) {
        if (params != null) {
            return params.get(key);
        }
        return "";
    }

    public void forEach(BiConsumer<String, String> consumer) {
        this.params.forEach(consumer);
    }
}
