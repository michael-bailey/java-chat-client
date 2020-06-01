package client.DOCCoder;

import java.util.HashMap;

public class DOCCoder {
    public static String encode(HashMap<String, String> data) {
        var output = "";
        data.forEach((key, value) -> {
            var special = value.matches(".*(:| ).*");
            output.concat(key + ":" + (special ? value : "\"" + value + "\""));
        });
        return "";
    }

}
