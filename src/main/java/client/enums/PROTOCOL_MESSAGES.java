package client.enums;

import java.util.regex.Pattern;

public class PROTOCOL_MESSAGES {

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
}